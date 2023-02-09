package com.yefeng.readingcloud.gateway.swagger;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: 采用路由id做资源名称,predicates.Path做映射的方式.
 * 如果需要自定义资源名称,可以单独配置一个集合(route.id,name),创建swaggerResource时,
 * 通过route.id获取name.
 * <p>例如:     swagger:
 *               route_names:
 *                 - name: 订单
 *                   id: order_route
 *                 - name: 订单1
 *                   id: order_route1
 *              spring: cloud: gateway: routes:
 *                                         - id: order_route
 *                                         - id: order_route1
 *  </p>
 * 重新Gateway的方法还没研究出来 - -||
 *
 * @Author:
 * @Date: 2021-04-25 15:38
 * @Version 1.0
 **/
@Component
@Primary
public class Swagger2 implements SwaggerResourcesProvider {

    protected static final String API_URI = "/v2/api-docs";//固定后缀
    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;
    //资源集合
    private static List<SwaggerResource> resources;
	//自定义资源名称需要在这里初始化
    public Swagger2(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
    //做了一个简单缓存,没仔细设计,其实可有可无用处不大

            resources = new ArrayList<>();

            List<String> routes = new ArrayList<>();
            routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
            //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
            gatewayProperties.getRoutes().stream()
                    .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                    .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                            .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                    predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                            .replace("/**", API_URI)))));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}
