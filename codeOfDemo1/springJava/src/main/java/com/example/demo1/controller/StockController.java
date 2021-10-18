package com.example.demo1.controller;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.User;
import com.example.demo1.service.FundHeavyService;
import com.example.demo1.service.HttpClient;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/stock")
public class StockController {
//    @Autowired
//    private FundHeavyService fundHeavyService;

    @Autowired
    private HttpClient httpClient;
//    @Autowired
//    User user_1;

    @RequestMapping("/hellos")
    public String hellos(String id) {
        String url="http://hq.sinajs.cn/list="+id;//sz000006
        HttpMethod method=HttpMethod.GET;
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();

//        List<FundHeavy>fundHeavy = fundHeavyService.getListAll();
//        for(int i=0;i<fundHeavy.size();i++){
//            String s= fundHeavy.get(i).id;
//            System.out.println(s);
//        }

        return httpClient.client(url,method,params);


    }



}