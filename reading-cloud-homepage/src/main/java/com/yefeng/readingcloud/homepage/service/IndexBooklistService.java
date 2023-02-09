package com.yefeng.readingcloud.homepage.service;

import com.yefeng.readingcloud.common.pojo.index.IndexBooklist;
import com.yefeng.readingcloud.homepage.vo.IndexBooklistVO;

/**
 * 书单服务
 * @author: yefeng
 * @since: 2020/4/6
 */
public interface IndexBooklistService {

    /**
     * 获取书单VO
     * @param booklistId
     * @param clientRandomNumber
     * @return
     */
    IndexBooklistVO getIndexBooklistVO(Integer booklistId, Integer clientRandomNumber);

    /**
     * 查询书单信息
     * @param booklistId
     * @return
     */
    IndexBooklist getIndexBooklistById(Integer booklistId);

    /**
     * 获取随机榜单VO
     * @param booklist
     * @param clientRandomNumber
     * @return
     */
    IndexBooklistVO getRandomIndexBooklistVO(IndexBooklist booklist, Integer clientRandomNumber);
}
