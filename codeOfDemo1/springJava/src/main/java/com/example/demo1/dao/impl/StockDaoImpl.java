package com.example.demo1.dao.impl;

import com.example.demo1.dao.StockDao;
import com.example.demo1.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class StockDaoImpl implements StockDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Stock getById(String id) {
        String sql="select * from m_stock where id = ?";
        return this.jdbcTemplate.queryForObject(sql, new RowMapper<Stock>() {
            @Override
            public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
                Stock stock=new Stock();
                stock.setId(resultSet.getString("id"));
                stock.setName(resultSet.getString("name"));
                stock.setType(resultSet.getString("type"));
                return stock;
            }
        },id);
    }
}
