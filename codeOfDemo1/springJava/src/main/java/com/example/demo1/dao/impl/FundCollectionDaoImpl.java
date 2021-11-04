package com.example.demo1.dao.impl;

import com.example.demo1.dao.FundCollectionDao;
import com.example.demo1.entity.FundCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FundCollectionDaoImpl implements FundCollectionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int insert(FundCollection collection) {
        String sql = "insert into fund_collection(id,username,name) values(?,?,?)";
        return this.jdbcTemplate.update(
                sql,
                0,
                collection.getUsername(),
                collection.getName()
        );
    }

    @Override
    public int deleteByUsernameANdName(int username, String name) {
        String sql = "delete from fund_collection where username= ? and name = ?";
        return this.jdbcTemplate.update(sql,username,name);
    }

    @Override
    public int update(FundCollection collection) {
        String sql = "update fund_collection set username= ? where id = ?";
        return this.jdbcTemplate.update(
                sql,
                collection.getUsername(),
                collection.getId()
        );
    }

    @Override
    public List<FundCollection> getByUser(int username) {
        String sql = "select * from fund_collection where username = ?";
        List<FundCollection> temp=jdbcTemplate.query(sql, new RowMapper<FundCollection>() {
            @Override
            public FundCollection mapRow(ResultSet resultSet, int i) throws SQLException {
                FundCollection collection = new FundCollection();
                collection.setId(resultSet.getInt("id"));
                collection.setUsername(resultSet.getInt("username"));
                collection.setName(resultSet.getString("name"));
                return collection;
            }
        }, username);
        return temp;
    }
}
