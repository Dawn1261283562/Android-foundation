
package com.example.demo1.service;

import com.example.demo1.entity.User;

import java.util.List;

/**
 * @date 2021/5/21 12:12
 *
 *  注意：id
 */
public interface UserService {

    //注意：id 在开发中无实际意义
    int insert(User user);

    int deleteByPhone(String username);

    int update(User user);

    // 严格匹配 用户名（用户名即是唯一手机号）
    User getByPhone(String username);

    //sql  字符匹配 例子： “61” 匹配 15361202831 6161616161等等
    List<User> getListByPhone(String username);

    //无用实现目前
    boolean login(String username, String password);

    //int deleteById(long id);
    //User getById(long id);
}
