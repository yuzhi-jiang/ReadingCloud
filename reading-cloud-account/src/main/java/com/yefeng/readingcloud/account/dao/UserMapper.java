package com.yefeng.readingcloud.account.dao;

import com.yefeng.readingcloud.common.pojo.account.User;
import org.apache.ibatis.annotations.Update;

/**
 * 用户
 * @author: yefeng
 * @since: 2020/4/10
 */
public interface UserMapper {

    int insert(User user);

    int updateByLoginName(User user);

    User selectByLoginName(String loginName);

    @Update("update user set user_pwd = #{userPwd} where login_name = #{loginName}")
    int updatePassword(User user);
}
