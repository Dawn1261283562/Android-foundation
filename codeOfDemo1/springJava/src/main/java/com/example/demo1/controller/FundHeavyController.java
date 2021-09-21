package com.example.demo1.controller;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.User;
import com.example.demo1.service.FundHeavyService;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/fundheavy")
public class FundHeavyController {
    @Autowired
    private FundHeavyService fundHeavyService;

    @RequestMapping("/getfundheavy")
    @ResponseBody
    public String getFundHeavy() {


        FundHeavy result = this.fundHeavyService.getFundHeavy();
        System.out.println(result.id);

        return result.id;
    }

    @RequestMapping("/getlist")
    @ResponseBody
    public List<FundHeavy> getList() {


        List<FundHeavy> result = this.fundHeavyService.getList();
        //System.out.println(result.id);

        return result;
    }
}
