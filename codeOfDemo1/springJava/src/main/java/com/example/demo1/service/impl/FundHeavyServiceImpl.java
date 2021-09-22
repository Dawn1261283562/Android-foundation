package com.example.demo1.service.impl;

import com.example.demo1.dao.FundHeavyDao;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.FundHeavy;
//import com.example.demo1.entity.Instance;
import com.example.demo1.entity.User;
import com.example.demo1.service.FundHeavyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundHeavyServiceImpl implements FundHeavyService {
    @Autowired
    private FundHeavyDao fundHeavyDao;
    //@Autowired
    //private Instance instance;

    @Override
    public FundHeavy getFundHeavy() {
        return fundHeavyDao.getFundHeavy();
    }
    //FundHeavy getFundHeavy();

    @Override
    public List<FundHeavy> getList() {
        return fundHeavyDao.getList();
    }

}
