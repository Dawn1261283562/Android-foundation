package com.example.demo1.controller;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.Stock;
import com.example.demo1.entity.User;
import com.example.demo1.service.*;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @RequestMapping("/searchStock")
    public List<Stock> searchStock(String id) {
        String regex = "\\d{6}.[a-zA-Z][a-zA-Z]";
        String regex1 = "[a-zA-Z][a-zA-Z]\\d{6}";
        String regex2 ="\\d{6}";
        if(id.matches(regex2)){
            String formalId =stockService.getFormalId(id);


            id =formalId;
            //System.out.println(id);
            String temp1=id.substring(0,6);String temp2=id.substring(7,9);
            //System.out.println(temp2+temp1);
            String id_restructure=temp2+temp1;
            id=id_restructure;
        }
        else if(id.matches(regex)){
            //System.out.println(1233);
            String temp1=id.substring(0,6);String temp2=id.substring(7,9);
            //System.out.println(temp2+temp1);
            String id_restructure=temp2+temp1;
            id=id_restructure;
            //System.out.println(id);
        }
        else if(id.matches(regex1)){
            String temp1=id.substring(0,2);String temp2=id.substring(2,8);
            temp1=temp1.toLowerCase();
            id=temp1+temp2;
        }
        else {

            List<Stock>temp = null;
            temp =stockService.getByOther(id);
            for (Stock Str :temp ){
                String _id=Str.getId();

                if(_id.matches(regex)){

                }
                else{
                    continue;
                }
                String _temp1=_id.substring(0,6);String _temp2=_id.substring(7,9);
                //System.out.println(temp2+temp1);
                String _id_restructure=_temp2+_temp1;
                _id=_id_restructure;
                String _url="http://hq.sinajs.cn/list="+_id.toLowerCase();//sz000006
                HttpMethod _method=HttpMethod.GET;
                MultiValueMap<String,String> _params=new LinkedMultiValueMap<>();

//        List<FundHeavy>fundHeavy = fundHeavyService.getListAll();
//        for(int i=0;i<fundHeavy.size();i++){
//            String s= fundHeavy.get(i).id;
//            System.out.println(s);
//        }

                String _data=httpClient.client(_url,_method,_params);
                String[] _nums = _data.split(",") ;
                //System.out.println(nums[3]);
                Stock _stock = new Stock();
                if(_nums.length>3) {
                    String tempS = _nums[3];
                    Str.setPrice(tempS);
                }
                String _temp11=_id.substring(0,2);String _temp22=_id.substring(2,8);
                //System.out.println(_temp22+'.'+_temp11);
                String idRestructure=_temp22+'.'+_temp11;
                _stock.setId(idRestructure);

                stockService.update(_stock);


            }

            //stockList.add(stock1);
            return temp;
        }
        String url="http://hq.sinajs.cn/list="+id.toLowerCase();//sz000006
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
        if(nums.length>3) {
            String temp = nums[3];
            stock.setPrice(temp);
        }
        String temp1=id.substring(0,2);String temp2=id.substring(2,8);
        //System.out.println(temp2+'.'+temp1);
        String id_restructure=temp2+'.'+temp1;
        stock.setId(id_restructure);

        stockService.update(stock);
        List<Stock>temp = null;
        temp =stockService.getById(stock);



        //stockList.add(stock1);
        return temp;



    }

    @RequestMapping("/getById")
    public List<Stock> getById(String id) {

        String temp3=id.substring(0,6);String temp4=id.substring(7,9);
        //System.out.println(temp2+temp1);
        String id_restructure1=temp4+temp3;
        id=id_restructure1;
        String url="http://hq.sinajs.cn/list="+id.toLowerCase();//sz000006
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
        if(nums.length>3) {
            String temp = nums[3];
            stock.setPrice(temp);
        }
        String temp1=id.substring(0,2);String temp2=id.substring(2,8);
        //System.out.println(temp2+'.'+temp1);
        String id_restructure=temp2+'.'+temp1;
        stock.setId(id_restructure);
        List<Stock>stockList = null;
        stockList =stockService.getById(stock);


        //stockList.add(stock1);
        return stockList;
    }
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
        //System.out.println(temp2+'.'+temp1);
        String id_restructure=temp2+'.'+temp1;
        stock.setId(id_restructure);
        stock.setPrice(temp);
        stockService.update(stock);
        //System.out.println(stock.getPrice());

        return data;


    }

    //ZC--StockController.class
    @RequestMapping("/allStock")
    public void allStock(){
        String id="sz000001";
        for (int i = 0; i < 2541; i++) {
            try {
                String id_num = id.substring(2);
                int num1 = Integer.parseInt(id_num);
                num1++;
                String str = String.format("%06d", num1);
                String ret = id.substring(0, 2) + str;
                id = ret;

                String url = "http://hq.sinajs.cn/list=" + ret;//sz000006
                HttpMethod method = HttpMethod.GET;
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();


                String data = httpClient.client(url, method, params);
                String[] nums = data.split(",");
                Stock stock = new Stock();
                String temp = nums[3];

                String temp1 = ret.substring(0, 2);
                String temp2 = ret.substring(2, 8);
                //System.out.println(temp2 + '.' + temp1);
                String id_restructure = temp2 + '.' + temp1;
                stock.setId(id_restructure);
                stock.setPrice(temp);
                stockService.update(stock);

                //System.out.println(data);
            }
            catch (Exception e){
                System.out.println("无法找到该数据。");
            }



        }
    }

    @RequestMapping("/getStockListByHot")
    public List<Stock> getStockListByHot(int wantedNum){
        List<Stock> temp=stockService.getStockListByHot(wantedNum);

        for (Stock Str :temp ){
            String _id=Str.getId();
            String _temp1=_id.substring(0,6);String _temp2=_id.substring(7,9);
            //System.out.println(temp2+temp1);
            String _id_restructure=_temp2+_temp1;
            _id=_id_restructure;
            String _url="http://hq.sinajs.cn/list="+_id.toLowerCase();//sz000006
            HttpMethod method=HttpMethod.GET;
            MultiValueMap<String,String> params=new LinkedMultiValueMap<>();

//        List<FundHeavy>fundHeavy = fundHeavyService.getListAll();
//        for(int i=0;i<fundHeavy.size();i++){
//            String s= fundHeavy.get(i).id;
//            System.out.println(s);
//        }

            String data=httpClient.client(_url,method,params);
            String[] nums = data.split(",") ;
            //System.out.println(nums[3]);
            Stock stock = new Stock();
            if(nums.length>3) {
                String tempS = nums[3];
                stock.setPrice(tempS);
            }
            String _temp11=_id.substring(0,2);String _temp22=_id.substring(2,8);
            //System.out.println(_temp22+'.'+_temp11);
            String idRestructure=_temp22+'.'+_temp11;
            stock.setId(idRestructure);

            stockService.update(stock);


        }



        return temp;
    }
}