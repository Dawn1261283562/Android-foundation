package com.example.demo1.dao.impl;

import com.example.demo1.dao.StockDao;
import com.example.demo1.entity.FundHeavyInfo;
import com.example.demo1.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class StockDaoImpl implements StockDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Stock getById(String id) {
        //增加热度

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
                stock.setPrice(resultSet.getString("price"));
                stock.setHits(resultSet.getInt("hits"));
                return stock;
            }
        },id);
        } catch(DataAccessException e){
            //System.out.println(5555);
            result=null;
        }
        updateHitsBySearch(result);
        return result;
    }

    @Override
    public int updateHitsBySearch(Stock stock) {
        //Stock stock = getById(id);
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

    public List<Stock> getByOther(String id) {
        String sql="select * from m_stock where name LIKE concat('%',?,'%')  "; //+" or price LIKE concat('%',?,'%') ";


        List<Stock> temp =this.jdbcTemplate.query(sql, new RowMapper<Stock>() {
                @Override
                public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
                    Stock stock=new Stock();
                    stock.setId(resultSet.getString("id"));
                    stock.setName(resultSet.getString("name"));
                    stock.setType(resultSet.getString("type"));
                    stock.setPrice(resultSet.getString("price"));
                    stock.setHits(resultSet.getInt("hits"));
                    return stock;
                }
            },id);
        List<Stock> ans = new ArrayList<Stock>(temp);

        String sql1="select * from m_stock where " +
                "  type LIKE concat('%',?,'%') ";

        List<Stock> temp1 =this.jdbcTemplate.query(sql1, new RowMapper<Stock>() {
            @Override
            public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
                Stock stock=new Stock();
                stock.setId(resultSet.getString("id"));
                stock.setName(resultSet.getString("name"));
                stock.setType(resultSet.getString("type"));
                stock.setPrice(resultSet.getString("price"));
                stock.setHits(resultSet.getInt("hits"));
                return stock;
            }
        },id);
        List<Stock> ans1 = new ArrayList<Stock>(temp1);


        ans.addAll(ans1);

        return ans;
    }


    @Override
    public String getFormalId(String id) {
        //System.out.println(id);
        String sql="select * from m_stock where id LIKE concat('%',?,'%') ";
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
                    stock.setPrice(resultSet.getString("price"));
                    stock.setHits(resultSet.getInt("hits"));
                    return stock;
                }
            },id);
        } catch(DataAccessException e){
            //System.out.println(5555);
            result=null;
        }

        return result.getId();
    }

    @Override
    public int ReduceHit() {
        String sql = "update m_stock set hits = hits-hits/3";
        return this.jdbcTemplate.update(sql);
    }

    @Override
    public List<Stock> getStockListByHot(int num) {
        String sql="select * from m_stock";
        List<Stock> result = null;
        try{
            result=this.jdbcTemplate.query(sql, new RowMapper<Stock>() {
                @Override
                public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
                    Stock stock=new Stock();
                    stock.setId(resultSet.getString("id"));
                    stock.setName(resultSet.getString("name"));
                    stock.setType(resultSet.getString("type"));
                    // stock.setPrice(resultSet.getString("price"));
                    stock.setHits(resultSet.getInt("hits"));
                    return stock;
                }
            });
        } catch(DataAccessException e){
            result=null;
        }
        Collections.sort(result, new Comparator<Stock>() {
            @Override
            public int compare(Stock o1, Stock o2) {
                return o2.getHits()-o1.getHits();
            }
        });
        List<Stock> ans=new ArrayList<Stock>();
        for(int i=0;i<num;i++)ans.add(result.get(i));
        return ans;
    }
}
