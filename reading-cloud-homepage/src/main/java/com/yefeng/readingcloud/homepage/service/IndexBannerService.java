package com.yefeng.readingcloud.homepage.service;

import com.yefeng.readingcloud.homepage.vo.IndexBannerVO;

/**
 * Banner服务
 * @author: yefeng
 * @since: 2020/4/6
 */
public interface IndexBannerService {

    /**
     * 获取Banner VO
     * @param bannerId
     * @return
     */
    IndexBannerVO getBannerVO(Integer bannerId);
}
