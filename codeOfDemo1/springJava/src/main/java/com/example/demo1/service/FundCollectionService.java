package com.example.demo1.service;

import com.example.demo1.entity.FundCollection;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.FundHeavyInfo;

import java.util.List;

public interface FundCollectionService {
    /**增**/
    int insert(FundCollection collection);
    /**删
     * @param name**/
    int deleteByUsernameANdName(String username,String name);
    /**改**/
    int update(FundCollection collection);
    /**查**/
    List<FundHeavy> getByUser(String username);
}
