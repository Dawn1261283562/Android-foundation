package com.example.demo1.service.impl;

import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2021/5/21 12:13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public int deleteById(long id) {
        return userDao.deleteById(id);
    }

    @Override
    public int deleteByPhone(String username) {
        return userDao.deleteByPhone(username);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }

    @Override
    public User getByPhone(String username){return userDao.getByPhone(username);}
    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }
}

