package com.example.demo1.service;

import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.User;

import java.util.List;

public interface FundHeavyService {


    //开销极大
    List<FundHeavy> getListAll();
    //模式一
    List<FundHeavy> getListByStockList(int num,String[] asd);
    //模式二
    List<FundHeavy> getListByStockScore(int num,String[] stockIdList,String[] stockRadioList);

    //测试用
    //FundHeavy getFundHeavy();
}
