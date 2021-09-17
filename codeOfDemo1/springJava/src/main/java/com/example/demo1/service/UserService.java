
package com.example.demo1.service;

import com.example.demo1.entity.User;

/**
 * @date 2021/5/21 12:12
 */
public interface UserService {

    int insert(User user);

    int deleteById(long id);

    int update(User user);

    User getById(long id);

    User login(long id, String password);
}
