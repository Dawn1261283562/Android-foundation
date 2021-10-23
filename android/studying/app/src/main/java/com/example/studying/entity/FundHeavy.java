package com.example.studying.entity;

import java.util.HashSet;
import java.util.Set;

public class FundHeavy {

    //public double score = 0;//用于评分的临时变量
    public String id;
    public String name;
    private String score;
    public static final int size = 10;//数组固定大小

    public String[] _stock_id = new String[size];
    //private String[] stock_name = new String[size];
    public String[] _stock_ratio = new String[size];
    public String[] _stock_all_type = new String[size];
    //因为每只基金有10只股票，可能两只股票有相同的板块，所以用HashSet自动去重
    public int hits = 0;


    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Set<String> stock_type = new HashSet<String>();

    public void set_id(String id) {
        this.id = id;
    }

    public Set<String> get_stock_type() {return stock_type;}

    //public void set_stock_type(Set<String> stock_type) {
    //    this.stock_type = stock_type;
    //}

    public void set_name(String name) {
        this.name = name;
    }

    public String[] get_stock_id() {
        return _stock_id;
    }

//    //public String[] get_stock_name() {
//        return stock_name;
//    }

    public String[] get_stock_ratio() {
        return _stock_ratio;
    }
    public String[] get_stock_all_Type() {
        return _stock_all_type;
    }

    public void set_stock_id(int i,String stock_id){
        this._stock_id[i]=stock_id;
    }
    //    //public void set_stock_name(int i,String stock_name){
//        this.stock_name[i]=stock_name;
//    }
    public void set_stock_ratio(int i,String stock_ratio){
        this._stock_ratio[i]=stock_ratio;
    }
    public void set_Stock_all_type(int i,String stock_type){
        this._stock_all_type[i]=stock_type;
    }
    public void set_stock_type(String stock_type){
        if(!this.stock_type.contains(stock_type))this.stock_type.add(stock_type);
    }


}