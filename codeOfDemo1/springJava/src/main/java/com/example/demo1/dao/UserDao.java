package com.example.demo1.dao;

import com.example.demo1.entity.User;

import java.util.List;

/**
 * @date 2021/5/21 9:21
 */
public interface UserDao {

    /**增**/
    int insert(User user);
    /**删**/
    int deleteByPhone(String username);
    /**改**/
    int update(User user);
    /**查**/

    User getByPhone(String username);

    List<User> getListByPhone(String username);




    /**没用的实现，可以不看**/
    int deleteById(long id);
    User getById(long id);
    User login(String username, String password);



}