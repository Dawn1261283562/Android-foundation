package com.example.demo1.controller;

import com.example.demo1.entity.FundCollection;
import com.example.demo1.entity.User;
import com.example.demo1.service.FundCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/collection")
public class FundCollectionController {

    @Autowired
    private FundCollectionService fundCollectionService;

    @RequestMapping("/insert")
    @ResponseBody
    public FundCollection insert(int id, int username,String name) {
        FundCollection fundCollection=new FundCollection();
        fundCollection.setId(id);
        fundCollection.setUsername(username);//这里插入的是用户表中用户的ID
        fundCollection.setName(name);//这里插入的是基金的ID

        int result = this.fundCollectionService.insert(fundCollection);
        System.out.println(result);
        return fundCollection;
    }

    @RequestMapping("/deleteByUsernameANdName")
    public void deleteByUsernameANdName(int username,String name) {
        int result = this.fundCollectionService.deleteByUsernameANdName(username,name);
        System.out.println(result);
    }

    @RequestMapping("/ifCollect")
    public boolean ifCollect(int username,String name) {
        List<FundCollection> w = (List<FundCollection>) this.fundCollectionService.getByUser(username);

        //Iterator<FundCollection> wIt = w.iterator();

        for(int i=0;i<w.size();i++){
            FundCollection wItem= w.get(i);
            //System.out.println(wItem.getName());
            if(Objects.equals(wItem.getName(), name))return true;
        }
        return false;
    }


    //改-好像没用到，就没有看具体是否实现，不用管
    @RequestMapping("/update")
    public void update() {
        FundCollection fundCollection = new FundCollection();
        fundCollection.setId(1);
        fundCollection.setUsername(123);
        this.fundCollectionService.update(fundCollection);
    }

    @RequestMapping("/getListByUser")
    @ResponseBody
    public List<FundCollection> getListByUser(int username) {
        List<FundCollection> w = (List<FundCollection>) this.fundCollectionService.getByUser(username);
        return w;
    }
}
