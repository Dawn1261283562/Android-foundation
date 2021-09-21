package com.example.demo1.dao;

import com.example.demo1.entity.User;

import java.util.List;

/**
 * @date 2021/5/21 9:21
 */
public interface UserDao {

    //UserDao UserDao();
    /**增**/
    int insert(User user);
    /**删**/
    int deleteById(long id);
    int deleteByPhone(String username);
    /**改**/
    int update(User user);
    /**查**/
    User getById(long id);

    User getByPhone(String username);

    List<User> getListByPhone(String username);
    /**登录**/
    User login(String username, String password);

}