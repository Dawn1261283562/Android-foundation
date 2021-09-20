package com.example.demo1.dao.impl;

import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @date 2021/5/21 9:26
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(User user) {
        String sql = "insert into m_user(id,username,password) values(?,?,?)";
        return this.jdbcTemplate.update(
                sql,
                 0,
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    public int deleteById(long id) {
        String sql = "delete from m_user where id = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    @Override
    public int deleteByPhone(String username) {
        String sql = "delete from m_user where username = ?";
        return this.jdbcTemplate.update(sql, username);
    }

    @Override
    public int update(User user) {
        String sql = "update m_user set password = ? where id = ?";
        return this.jdbcTemplate.update(
                sql,
                user.getPassword(),
                user.getId()
        );
    }

    @Override
    public User getById(long id) {
        String sql = "select * from m_user where id = ?";
        return this.jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }, id);
    }

    @Override
    public User getByPhone(String username) {
        String sql = "select * from m_user where username = ?";
        return this.jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }, username);
    }

    @Override
    public List<User> getListByPhone(String username) {

        String sql = "select * from m_user where username LIKE  '%'|| ? ||'%'";

        List<User> users1 = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                //User user = new User(id, name, psw);
                return user;
            }
        },username);

        return users1;


    }

    @Override
    public User login(String username, String password) {
        String sql = "select * from m_user where username=? and password=?";
        return this.jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }, username,password);
    }
//    @Override
//    public User login(long id, String password) {
//        String sql = "select * from m_user where id=? and password=?";
//        return this.jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
//            User user = new User();
//            user.setId(resultSet.getLong("id"));
//            user.setUsername(resultSet.getString("username"));
//            user.setPassword(resultSet.getString("password"));
//            return user;
//        }, id,password);
//    }
}


