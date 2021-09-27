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
@RequestMapping("/fundHeavy")
public class FundHeavyController {
    @Autowired
    private FundHeavyService fundHeavyService;

    //注意，开销极大
    @RequestMapping("/getListAll")
    @ResponseBody
    public List<FundHeavy> getListAll() {


        List<FundHeavy> result = this.fundHeavyService.getListAll();
        //System.out.println(result.id);

        return result;
    }


    //模式一
    @RequestMapping("/getListByStockList")
    @ResponseBody
    public List<FundHeavy> getListByStockList(int num,String[] stockIdList) {

        List<FundHeavy> result = this.fundHeavyService.getListByStockList( num, stockIdList);
        return result;
    }

    //模式二
    @RequestMapping("/getListByStockScore")
    @ResponseBody
    public List<FundHeavy>  getListByStockScore(int num,String[] stockIdList,String[] stockRadioList) {

        System.out.println(stockIdList);
        List<FundHeavy> result = this.fundHeavyService.getListByStockScore(num,stockIdList,stockRadioList);
        //System.out.println(result.id);
        //result=null;
        return result;
    }


//    @RequestMapping("/getListByStock")
//    @ResponseBody
//    public List<FundHeavy>  getListByStock() {
//
//
//        List<FundHeavy> result = this.fundHeavyService.getListAll();
//        //System.out.println(result.id);
//
//        return result;
//    }

    //    @RequestMapping("/getFundHeavy")
//    @ResponseBody
//    public String getFundHeavy() {
//
//
//        FundHeavy result = this.fundHeavyService.getFundHeavy();
//        System.out.println(result.id);
//
//        return result.id;
//    }

    //    @RequestMapping("/getList")
//    @ResponseBody
//    public int getList() {
//
//
//        List<FundHeavy> result = this.fundHeavyService.getList();
//        //System.out.println(result.id);
//
//        return 123;
//    }

    //模式二
    @RequestMapping("/getListByStockAllType")
    @ResponseBody
    public List<FundHeavy>  getListByStockScore(int num,String[] TypeList) {

        System.out.println(TypeList);
        List<FundHeavy> result = this.fundHeavyService.getListByStockAllType(num,TypeList);
        //System.out.println(result.id);
        //result=null;
        return result;
    }
}
