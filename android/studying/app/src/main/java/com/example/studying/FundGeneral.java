package com.example.studying;

public class FundGeneral {
    private String fund1;
    private String fund2;
    private double fund3;
    private double fund4;
    private double fund5;
    private double fund6;

    public FundGeneral(String fund1,String fund2,double fund3,double fund4,double fund5,double fund6){
        this.fund1=fund1;
        this.fund2=fund2;
        this.fund3=fund3;
        this.fund4=fund4;
        this.fund5=fund5;
        this.fund6=fund6;
    }

    public String getFund1(){
        return fund1;
    }

    public String getFund2(){
        return fund2;
    }

    public double getFund3() {
        return fund3;
    }

    public double getFund4() {
        return fund4;
    }

    public double getFund5() {
        return fund5;
    }

    public double getFund6() {
        return fund6;
    }
}
