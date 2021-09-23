package com.example.demo1.service.impl;

import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int deleteByPhone(String username) {
        return userDao.deleteByPhone(username);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public User getByPhone(String username){return userDao.getByPhone(username);}

    @Override
    public List<User> getListByPhone(String username){return userDao.getListByPhone(username);}


    //不看
    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }
}

