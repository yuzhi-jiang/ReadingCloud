package com.yefeng.readingcloud.homepage.service.impl;

import com.yefeng.readingcloud.common.cache.RedisExpire;
import com.yefeng.readingcloud.common.cache.RedisHomepageKey;
import com.yefeng.readingcloud.common.cache.RedisService;
import com.yefeng.readingcloud.common.pojo.index.IndexBanner;
import com.yefeng.readingcloud.common.pojo.index.IndexBannerItem;
import com.yefeng.readingcloud.common.utils.CommonUtil;
import com.yefeng.readingcloud.homepage.dao.IndexBannerItemMapper;
import com.yefeng.readingcloud.homepage.dao.IndexBannerMapper;
import com.yefeng.readingcloud.homepage.service.IndexBannerService;
import com.yefeng.readingcloud.homepage.vo.BannerItemVO;
import com.yefeng.readingcloud.homepage.vo.IndexBannerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Banner服务
 * @author: yefeng
 * @since: 2020/4/6
 */
@Service
public class IndexBannerServiceImpl implements IndexBannerService {

    @Autowired
    private IndexBannerMapper indexBannerMapper;

    @Autowired
    private IndexBannerItemMapper indexBannerItemMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public IndexBannerVO getBannerVO(Integer bannerId) {
        String key = RedisHomepageKey.getBannerVoKey(bannerId);
        IndexBannerVO vo = this.redisService.getCache(key, IndexBannerVO.class);
        if (vo != null) {
            return vo;
        }

        // DB查询不存在返回空
        IndexBanner indexBanner = this.indexBannerMapper.selectById(bannerId);
        if (indexBanner == null) {
            return null;
        }

        // DB查询Banner明细
        List<IndexBannerItem> bannerItems = this.indexBannerItemMapper.findPageWithResult(bannerId);
        if (CommonUtil.isEmpty(bannerItems)) {
            return null;
        }

        List<BannerItemVO> items = new ArrayList<>();
        for (int i = 0; i < bannerItems.size(); i++) {
            BannerItemVO item = new BannerItemVO();
            IndexBannerItem banner = bannerItems.get(i);
            BeanUtils.copyProperties(banner, item);
            items.add(item);
        }
        vo = new IndexBannerVO(indexBanner.getId(), indexBanner.getName(), items);
        this.redisService.setExpireCache(key, vo, RedisExpire.HOUR_FOUR);
        return vo;
    }
}