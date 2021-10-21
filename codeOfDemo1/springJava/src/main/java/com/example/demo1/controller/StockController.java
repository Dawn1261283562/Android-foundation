package com.example.demo1.controller;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.Stock;
import com.example.demo1.entity.User;
import com.example.demo1.service.*;
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
    @Autowired
    private StockService stockService;
//    @Autowired
//    User user_1;

    //@RequestMapping("/hellos")
    @RequestMapping("/oneStock")
    public String oneStock(String id) {
        String url="http://hq.sinajs.cn/list="+id;//sz000006
        HttpMethod method=HttpMethod.GET;
        MultiValueMap<String,String> params=new LinkedMultiValueMap<>();

//        List<FundHeavy>fundHeavy = fundHeavyService.getListAll();
//        for(int i=0;i<fundHeavy.size();i++){
//            String s= fundHeavy.get(i).id;
//            System.out.println(s);
//        }

        String data=httpClient.client(url,method,params);
        String[] nums = data.split(",") ;
        //System.out.println(nums[3]);
        Stock stock = new Stock();
        String temp=nums[3];

        String temp1=id.substring(0,2);String temp2=id.substring(2,8);
        System.out.println(temp2+'.'+temp1);
        String id_restructure=temp2+'.'+temp1;
        stock.setId(id_restructure);
        stock.setPrice(temp);
        stockService.update(stock);
        //System.out.println(stock.getPrice());

        return data;


    }



}