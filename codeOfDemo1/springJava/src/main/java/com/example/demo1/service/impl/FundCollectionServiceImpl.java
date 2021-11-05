package com.example.demo1.service.impl;

import com.example.demo1.dao.FundCollectionDao;
import com.example.demo1.entity.FundCollection;
import com.example.demo1.service.FundCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FundCollectionServiceImpl implements FundCollectionService{
    @Autowired
    private FundCollectionDao fundCollectionDao;

    @Override
    public int insert(FundCollection collection){
        return fundCollectionDao.insert(collection);
    }

    @Override
    public int deleteByUsernameANdName(int username, String name) {
        return fundCollectionDao.deleteByUsernameANdName(username,name);
    }

    @Override
    public int update(FundCollection collection){
        return fundCollectionDao.update(collection);
    }

    @Override
    public List<FundCollection> getByUser(int username){
        return fundCollectionDao.getByUser(username);
    }
}
