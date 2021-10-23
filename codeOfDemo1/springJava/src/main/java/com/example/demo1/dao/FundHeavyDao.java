
package com.example.demo1.dao;

import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.FundHeavyInfo;
import com.example.demo1.entity.User;

import java.util.List;

public interface FundHeavyDao {



    //全部sql拉取
    List<FundHeavy> getListAll();

    void setFundHeavyAll();

    void preprocess();

    List<FundHeavyInfo> getFundHeavyInfoByNotId(String str);

    List<FundHeavyInfo> getFundHeavyInfoById(String id);

//    List<FundHeavy> getById(String id);
    //测试代码
    //FundHeavy getFundHeavy();
}
