package com.example.demo1.dao;

import com.example.demo1.entity.Stock;

import java.util.List;

public interface StockDao {
    Stock getById(String id);
    int updateHitsBySearch(Stock stock);
    int update(Stock stock);
    int ReduceHit(); //每天衰减热度

    List<Stock> getByOther(String id);

    String getFormalId(String id);

    // 获取热度前num的股票，num从前端获取
    List<Stock> getStockListByHot(int num);

    //  Stock getOneStock(Stock stock);
}
