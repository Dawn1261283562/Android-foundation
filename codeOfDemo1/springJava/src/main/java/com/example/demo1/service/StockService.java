package com.example.demo1.service;

import com.example.demo1.entity.Stock;
import com.example.demo1.entity.User;

import java.util.List;

public interface StockService {

    int update(Stock stock);

    List<Stock> getById(Stock stock);

    List<Stock> getByOther(String id);

    String getFormalId(String id);

    List<Stock> getStockListByHot(int num);
}
