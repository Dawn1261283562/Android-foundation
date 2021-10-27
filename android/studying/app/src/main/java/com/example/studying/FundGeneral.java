package com.example.studying;

import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;

public class FundGeneral {
    private String fund1;
    private String fund2;
    private String fund3;

    private FundHeavyInfo fundHeavyInfo;
    private Stock stock;
    private String type;

    private boolean selectFund=false;

    public void setFundHeavyInfo(FundHeavyInfo fundHeavyInfo) {
        this.fundHeavyInfo = fundHeavyInfo;
    }

    public FundHeavyInfo getFundHeavyInfo() {
        return fundHeavyInfo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public FundGeneral(String fund1, String fund2, String fund3){
        this.fund1=fund1;
        this.fund2=fund2;
        this.fund3=fund3;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public String getFund1(){
        return fund1;
    }

    public String getFund2(){
        return fund2;
    }

    public String getFund3() {
        return fund3;
    }

    public void setSelectFund(boolean selectFund) {
        this.selectFund = selectFund;
    }

    public Boolean getSelectFund() {
        return selectFund;
    }
}
