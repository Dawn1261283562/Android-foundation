package com.example.demo1.dao.impl;

import com.example.demo1.dao.StockDao;
import com.example.demo1.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        //System.out.println(id);
        String sql="select * from m_stock where id = ?";
        Stock result = null;
        //System.out.println(1231231231);
        try{
            //System.out.println(1231231231);
            result=this.jdbcTemplate.queryForObject(sql, new RowMapper<Stock>() {
            @Override
            public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
                Stock stock=new Stock();
                stock.setId(resultSet.getString("id"));
                stock.setName(resultSet.getString("name"));
                stock.setType(resultSet.getString("type"));
                stock.setHits(resultSet.getInt("hits"));
                return stock;
            }
        },id);
        } catch(DataAccessException e){
            //System.out.println(5555);
            result=null;
        }

        return result;
    }

    @Override
    public int updateHitsBySearch(String id) {
        Stock stock = getById(id);
        String sql = "update m_stock set hits = ? where id = ?";
        return this.jdbcTemplate.update(
                sql,
                stock.getHits()+1,
                stock.getId()
        );
    }

    @Override
    public int update(Stock stock) {
        //Stock stock = getById(id);
        String sql = "update m_stock set price = ? where id = ?";
        return this.jdbcTemplate.update(
                sql,
                stock.getPrice(),
                stock.getId()
        );
    }


}
