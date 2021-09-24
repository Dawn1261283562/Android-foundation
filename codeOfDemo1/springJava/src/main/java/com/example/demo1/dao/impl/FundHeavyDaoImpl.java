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
    public List<FundHeavy> getListAll() {

        String sql = "select * from m_fund_heavy ";//where id  =?or id =?or id =?or id =?
        //select * from d_menu where name like concat('%',?,'%')or id =?,s2or id =?
        String s1="000001.OF";String s2="000309.OF";String s3="000513.OF";String s4="000893.OF";
        List<FundHeavy> fundHeavy1 = jdbcTemplate.query(sql, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));
                fundHeavy.setName(resultSet.getString("name"));
                //fundHeavy.set_stock_id_1(resultSet.getString("stock_id_1"));
                //fundHeavy.set_stock_name_1(resultSet.getString("id"))
                //fundHeavy.set_stock_id_1(resultSet.getString("id"));

                fundHeavy.set_stock_id(
                        resultSet.getString("stock_id_1"),
                        resultSet.getString("stock_id_2"),
                        resultSet.getString("stock_id_3"),
                        resultSet.getString("stock_id_4"),
                        resultSet.getString("stock_id_5"),
                        resultSet.getString("stock_id_6"),
                        resultSet.getString("stock_id_7"),
                        resultSet.getString("stock_id_8"),
                        resultSet.getString("stock_id_9"),
                        resultSet.getString("stock_id_10"));
                fundHeavy.set_stock_name(
                        resultSet.getString("stock_name_1"),
                        //"s","d","","","","","","","");
                        resultSet.getString("stock_name_2"),
                        resultSet.getString("stock_name_3"),
                        resultSet.getString("stock_name_4"),
                        resultSet.getString("stock_name_5"),
                        resultSet.getString("stock_name_6"),
                        resultSet.getString("stock_name_7"),
                        resultSet.getString("stock_name_8"),
                        resultSet.getString("stock_name_9"),
                        resultSet.getString("stock_name_10"));
                fundHeavy.set_stock_ratio(
                        resultSet.getString("stock_ratio_1"),
                        //"s","d","","","","","","","");
                        resultSet.getString("stock_ratio_2"),
                        resultSet.getString("stock_ratio_3"),
                        resultSet.getString("stock_ratio_4"),
                        resultSet.getString("stock_ratio_5"),
                        resultSet.getString("stock_ratio_6"),
                        resultSet.getString("stock_ratio_7"),
                        resultSet.getString("stock_ratio_8"),
                        resultSet.getString("stock_ratio_9"),
                        resultSet.getString("stock_ratio_10"));
                //User user = new User(id, name, psw);
                return fundHeavy;
            }
        });//,s1,s2,s3,s4

        return fundHeavy1;


    }



    //测试用
//    @Override
//    public FundHeavy getFundHeavy() {
//        String sql = "select * from m_fund_heavy where id =? ";
//        String s1="000001.OF";String s2="000309.OF";
//        String s3="000513.OF";String s4="000893.OF";//or id =?or id =?,s3,s4or id =?,s2
//
//        return this.jdbcTemplate.queryForObject(sql, new RowMapper<FundHeavy>() {
//            @Override
//            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
//                FundHeavy fundHeavy = new FundHeavy();
//                fundHeavy.setId(resultSet.getString("id"));
////                user.setUsername(resultSet.getString("username"));
////                user.setPassword(resultSet.getString("password"));
//                return fundHeavy;
//            }
//        },s1);
//    }
}
