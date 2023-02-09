package com.yefeng.readingcloud.gateway.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yefeng.readingcloud.common.pojo.account.User;
import com.yefeng.readingcloud.common.result.HttpCodeEnum;
import com.yefeng.readingcloud.common.result.Result;
import com.yefeng.readingcloud.gateway.common.config.SystemPropertiesConfig;
import com.yefeng.readingcloud.gateway.common.utils.JwtUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * 身份认证过滤器
 *
 * @author: yefeng
 * @since: 2020/4/12
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    @Autowired
    private SystemPropertiesConfig systemPropertiesConfig;//白名单
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Set<String> whiteList = this.getWhiteList();        // 白名单Path
        String path = exchange.getRequest().getPath().toString();
        logger.info("请求path: {}", path);
//        System.out.println("白名单:");
        logger.info("白名单: {}", whiteList);
//        whiteList.forEach(s -> logger.info(s));
        // 主页接口、图书接口正则匹配
        boolean indexMatch = Pattern.matches("/index[^\\s]*", path);
        boolean bookMatch = Pattern.matches("/book/[^\\s]*", path);
        boolean account = Pattern.matches("/account/user/[^\\s]*", path);
        boolean search = Pattern.matches("/search/[^\\s]*", path);
        // 白名单接口、开放接口放行
        if (bookMatch || indexMatch ||account||search || whiteList.contains(path)) {
            return chain.filter(exchange);
        }
        String[] segments = path.split("/");
        if (!whiteList.contains(segments[1])) {
            // 认证
            String token = exchange.getRequest().getHeaders().getFirst("token");
            Result<User> result = JwtUtil.validationToken(token);
            if (result.getCode() == HttpCodeEnum.OK.getCode()) {
                // 认证通过
                User user = result.getData();
                // 追加请求头用户信息
                Consumer<HttpHeaders> httpHeaders = httpHeader -> {
                    httpHeader.set("userId", user.getId().toString());
                    httpHeader.set("nickName", user.getNickName());
                };
                ServerHttpRequest serverHttpRequest = exchange.getRequest()
                        .mutate()
                        .headers(httpHeaders)
                        .build();
                exchange.mutate().request(serverHttpRequest).build();
                return chain.filter(exchange);
            }
            // 认证过期、失败，均返回401
            ServerHttpResponse response = exchange.getResponse();
            byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 请求白名单
     *
     * @return
     */
    private Set<String> getWhiteList() {
        String whitelists = this.systemPropertiesConfig.getWhitelist();
        if (StringUtils.isEmpty(whitelists)) {
            return new HashSet<>();
        }
        Set<String> whiteList = new HashSet<>();
        String[] whiteArray = whitelists.split(",");
        for (int i = 0; i < whiteArray.length; i++) {
            String str = whiteArray[i];
            whiteList.add(whiteArray[i]);
        }
        return whiteList;
    }
}
