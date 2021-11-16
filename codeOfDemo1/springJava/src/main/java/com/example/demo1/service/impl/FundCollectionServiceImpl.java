package com.example.demo1.service.impl;

import com.example.demo1.dao.FundCollectionDao;
import com.example.demo1.dao.FundHeavyDao;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.FundCollection;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.FundHeavyInfo;
import com.example.demo1.entity.User;
import com.example.demo1.service.FundCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FundCollectionServiceImpl implements FundCollectionService{
    @Autowired
    private FundCollectionDao fundCollectionDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private FundHeavyDao fundHeavyDao;

    @Override
    public int insert(FundCollection collection){

        User user= userDao.getByPhone(String.valueOf(collection.getUsername()));
        String phoneNum=user.getUsername();
        System.out.println(phoneNum);
        System.out.println(phoneNum);

        return fundCollectionDao.insert(collection);
    }

    @Override
    public int deleteByUsernameANdName(String username, String name) {
        return fundCollectionDao.deleteByUsernameANdName(username,name);
    }

    @Override
    public int update(FundCollection collection){
        return fundCollectionDao.update(collection);
    }

    @Override
    public List<FundHeavy> getByUser(String username){

        List<FundCollection> temp=fundCollectionDao.getByUser(username);

        List<FundHeavy> temp_1= new ArrayList<FundHeavy>();

        for (FundCollection str : temp) {

            //System.out.println(fundHeavyDao.getFundHeavyById(str.getName()));
            if(fundHeavyDao.getFundHeavyById(str.getName()).get(0) !=null){
                System.out.println(str.getName());
                List<FundHeavy> y=  fundHeavyDao.getFundHeavyById( str.getName());


                temp_1.addAll(y);
            }
            else if (fundHeavyDao.getFundHeavyInfoById( str.getName()).get(0) !=null){
                List<FundHeavyInfo> y1=  fundHeavyDao.getFundHeavyInfoById( str.getName());
                System.out.println(str.getName());

                List<FundHeavy> y=new ArrayList<FundHeavy>();
                FundHeavy yt=new FundHeavy();
                yt.setId(y1.get(0).getId());
                yt.setName(y1.get(0).getName());


                temp_1.add(yt);
            }
            else{
                List<FundHeavy> y=new ArrayList<FundHeavy>();
                FundHeavy yt=new FundHeavy();
                yt.setId(str.getName());
                yt.setName(str.getName());

                temp_1.add(yt);
            }


        }


        return temp_1;
    }
}
