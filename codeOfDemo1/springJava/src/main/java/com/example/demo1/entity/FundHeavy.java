package com.example.demo1.entity;

import java.util.HashSet;
import java.util.Set;

public class FundHeavy implements Comparable<FundHeavy> {

    public double score = 0;//用于评分的临时变量
    public String id;
    public String name;
    private static final int size = 10;//数组固定大小

    private String[] stock_id = new String[size];

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //private String[] stock_name = new String[size];
    private String[] stock_ratio = new String[size];
    private String[] stock_all_type = new String[size];
    //因为每只基金有10只股票，可能两只股票有相同的板块，所以用HashSet自动去重
    private int hits = 0;


    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Set<String> stock_type = new HashSet<String>();

//    public void set_id(String id) {
//        this.id = id;
//    }

    public Set<String> get_stock_type() {return stock_type;}

    //public void set_stock_type(Set<String> stock_type) {
    //    this.stock_type = stock_type;
    //}

//    public void set_name(String name) {
//        this.name = name;
//    }

    public String[] get_stock_id() {
        return stock_id;
    }

//    //public String[] get_stock_name() {
//        return stock_name;
//    }

    public String[] get_stock_ratio() {
        return stock_ratio;
    }
    public String[] get_stock_all_Type() {
        return stock_all_type;
    }

    public void set_stock_id(int i,String stock_id){
        this.stock_id[i]=stock_id;
    }
//    //public void set_stock_name(int i,String stock_name){
//        this.stock_name[i]=stock_name;
//    }
    public void set_stock_ratio(int i,String stock_ratio){
        this.stock_ratio[i]=stock_ratio;
    }
    public void set_Stock_all_type(int i,String stock_type){
        this.stock_all_type[i]=stock_type;
    }
    public void set_stock_type(String stock_type){
        if(!this.stock_type.contains(stock_type))this.stock_type.add(stock_type);
    }
    //下面注释这样写太多了
//    public void set_stock_id( String stock_id_1,String stock_id_2,String stock_id_3,
//                              String stock_id_4,String stock_id_5,String stock_id_6,
//                              String stock_id_7,String stock_id_8,String stock_id_9,
//                              String stock_id_10){
//        int n=0;
//        this.stock_id[n++]=stock_id_1;
//        this.stock_id[n++]=stock_id_2;
//        this.stock_id[n++]=stock_id_3;
//        this.stock_id[n++]=stock_id_4;
//        this.stock_id[n++]=stock_id_5;
//        this.stock_id[n++]=stock_id_6;
//        this.stock_id[n++]=stock_id_7;
//        this.stock_id[n++]=stock_id_8;
//        this.stock_id[n++]=stock_id_9;
//        this.stock_id[n++]=stock_id_10;
//    }

//    public void set_stock_name( String stock_name_1,String stock_name_2,String stock_name_3,
//                              String stock_name_4,String stock_name_5,String stock_name_6,
//                              String stock_name_7,String stock_name_8,String stock_name_9,
//                              String stock_name_10){
//        int n=0;
//        this.stock_name[n++]=stock_name_1;
//        this.stock_name[n++]=stock_name_2;
//        this.stock_name[n++]=stock_name_3;
//        this.stock_name[n++]=stock_name_4;
//        this.stock_name[n++]=stock_name_5;
//        this.stock_name[n++]=stock_name_6;
//        this.stock_name[n++]=stock_name_7;
//        this.stock_name[n++]=stock_name_8;
//        this.stock_name[n++]=stock_name_9;
//        this.stock_name[n++]=stock_name_10;
//    }

//    public void set_stock_ratio( String stock_ratio_1,String stock_ratio_2,String stock_ratio_3,
//                                 String stock_ratio_4,String stock_ratio_5,String stock_ratio_6,
//                                 String stock_ratio_7,String stock_ratio_8,String stock_ratio_9,
//                                 String stock_ratio_10){
//        int n=0;
//        this.stock_ratio[n++]=stock_ratio_1;
//        this.stock_ratio[n++]=stock_ratio_2;
//        this.stock_ratio[n++]=stock_ratio_3;
//        this.stock_ratio[n++]=stock_ratio_4;
//        this.stock_ratio[n++]=stock_ratio_5;
//        this.stock_ratio[n++]=stock_ratio_6;
//        this.stock_ratio[n++]=stock_ratio_7;
//        this.stock_ratio[n++]=stock_ratio_8;
//        this.stock_ratio[n++]=stock_ratio_9;
//        this.stock_ratio[n++]=stock_ratio_10;
//    }

    /*
        通过 score 分数来比较
     */
    @Override
    public int compareTo(FundHeavy o) {
        return (int ) (o.score - this.score );
    }

}

//    废弃
//
//    private String stock_name_1 ;
//    private String stock_ratio_1 ;
//    private String stock_id_2 ;
//    private String stock_name_2 ;
//    private String stock_ratio_2 ;
//    private String stock_id_3 ;
//    private String stock_name_3 ;
//    private String stock_ratio_3 ;
//    private String stock_id_4 ;
//    private String stock_name_4 ;
//    private String stock_ratio_4 ;
//    private String stock_id_5 ;
//    private String stock_name_5 ;
//    private String stock_ratio_5 ;
//    private String stock_id_6 ;
//    private String stock_name_6 ;
//    private String stock_ratio_6 ;
//    private String stock_id_7 ;
//    private String stock_name_7 ;
//    private String stock_ratio_7 ;
//    private String stock_id_8 ;
//    private String stock_name_8 ;
//    private String stock_ratio_8 ;
//    private String stock_id_9 ;
//    private String stock_name_9 ;
//    private String stock_ratio_9 ;
//    private String stock_id_10 ;
//    private String stock_name_10 ;
//    private String stock_ratio_10 ;