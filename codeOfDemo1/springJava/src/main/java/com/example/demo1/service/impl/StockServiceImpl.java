package com.example.demo1.service.impl;

import com.example.demo1.dao.StockDao;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.Stock;
import com.example.demo1.entity.User;
import com.example.demo1.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public int update(Stock stock) {
        return stockDao.update(stock);
    }
}
