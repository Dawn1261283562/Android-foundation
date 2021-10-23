package com.example.demo1.service;

import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.FundHeavyInfo;
import com.example.demo1.entity.User;

import java.util.List;

public interface FundHeavyService {



    //获取全部基金重仓持有信息无
    List<FundHeavy> getListAll();

    //模式一: 持仓搜索，基于持仓股种类
    List<FundHeavy> getListByStockList(int num,String[] stockIdList);

    //模式二: 持仓匹配程度搜索，基于客户 持仓股占比期望集合 进行匹配
    List<FundHeavy> getListByStockScore(int num,String[] stockIdList,String[] stockRadioList);

    //tip:num表示前端期望后端返回基金的数量，typeList表示前端向后端传的板块数组
    //模式三:
    List<FundHeavy> getListByStockType(int num,String[] TypeList);

    //模式三: 板块搜索简版，不考虑股票占比，按板块匹配数与集中度进行匹配
    List<FundHeavy> getListByStockAllType(int num,String[] TypeList);

    //模式三加: 板块搜索，考虑股票占比以及按板块匹配数与集中度进行匹配
    List<FundHeavy> getListByStockAllTypeRadio(int num,String[] TypeList);

    //基金普通搜索，如果是ID样式，则根据ID搜索基金，如果不是ID样式，则进行子字符串匹配
    List<FundHeavyInfo> getListByGeneralSearch(String str);


    //测试用
    //FundHeavy getFundHeavy();
}
