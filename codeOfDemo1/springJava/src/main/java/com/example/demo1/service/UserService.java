
package com.example.demo1.service;

import com.example.demo1.entity.User;

/**
 * @date 2021/5/21 12:12
 */
public interface UserService {

    int insert(User user);

    int deleteByPhone(String username);

    int update(User user);

    User getByPhone(String username);

    User login(String username, String password);

    //int deleteById(long id);

    //User getById(long id);
}
