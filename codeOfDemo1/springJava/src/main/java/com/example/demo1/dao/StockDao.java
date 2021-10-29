package com.example.demo1.dao;

import com.example.demo1.entity.Stock;

import java.util.List;

public interface StockDao {
    Stock getById(String id);
    int updateHitsBySearch(Stock stock);
    int update(Stock stock);

    List<Stock> getByOther(String id);

    String getFormalId(String id);

    //  Stock getOneStock(Stock stock);
}
