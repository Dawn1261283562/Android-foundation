package com.example.demo1.dao.impl;

import com.example.demo1.dao.FundHeavyDao;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class FundHeavyDaoImpl implements FundHeavyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public FundHeavy getFundHeavy() {
        String sql = "select * from m_fund_heavy where id =? ";
                String s1="000001.OF";String s2="000309.OF";
        String s3="000513.OF";String s4="000893.OF";//or id =?or id =?,s3,s4or id =?,s2

        return this.jdbcTemplate.queryForObject(sql, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));
//                user.setUsername(resultSet.getString("username"));
//                user.setPassword(resultSet.getString("password"));
                return fundHeavy;
            }
        },s1);
    }

    @Override
    public List<FundHeavy> getList() {

        String sql = "select * from m_fund_heavy where id  =?or id =?or id =?or id =?";
        //select * from d_menu where name like concat('%',?,'%')or id =?,s2or id =?
        String s1="000001.OF";String s2="000309.OF";String s3="000513.OF";String s4="000893.OF";
        List<FundHeavy> fundHeavy1 = jdbcTemplate.query(sql, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));

                //User user = new User(id, name, psw);
                return fundHeavy;
            }
        },s1,s2,s3,s4);

        return fundHeavy1;


    }
}
