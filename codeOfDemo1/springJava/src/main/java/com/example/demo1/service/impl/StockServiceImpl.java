package com.example.demo1.service.impl;

import com.example.demo1.dao.StockDao;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.Stock;
import com.example.demo1.entity.User;
import com.example.demo1.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public int update(Stock stock) {
        return stockDao.update(stock);
    }

    @Override
    public List<Stock> getById(Stock stock){
        List<Stock>stockList  = new ArrayList<Stock>();
//        System.out.println(stock.getId());
        Stock stock1=stockDao.getById(stock.getId());
//        System.out.println(stock1.getId());
//        System.out.println(stock1.getName());

        stockList.add(stock1);
//        System.out.println(stockList);
        return stockList;
    }

    @Override
    public List<Stock> getByOther(String id) {

        List<Stock>stockList1 =stockDao.getByOther(id);


        return stockList1;
    }

    @Override
    public String getFormalId(String id){

        String formalId =stockDao.getFormalId(id);


        return formalId;
    }

}
