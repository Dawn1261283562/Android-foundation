package com.example.demo1.dao;

import com.example.demo1.entity.FundCollection;

import java.util.List;

public interface FundCollectionDao {
    /**增**/
    int insert(FundCollection collection);
    /**删**/
    int deleteByUsernameANdName(int username,String name);
    /**改**/
    int update(FundCollection collection);
    /**查**/
    List<FundCollection> getByUser(int username);

}
