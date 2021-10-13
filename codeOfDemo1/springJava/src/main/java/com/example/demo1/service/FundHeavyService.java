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
    //模式三 num表示前端期望后端返回基金的数量，Typelist表示前端向后端传的板块数组
    List<FundHeavy> getListByStockType(int num,String[] TypeList);


    List<FundHeavy> getListByStockAllType(int num,String[] TypeList);

    List<FundHeavy> getListByStockAllTypeRadio(int num,String[] TypeList);
    //测试用
    //FundHeavy getFundHeavy();
}
