package com.example.studying.entity;

import java.io.Serializable;

public class Stock<String> implements Serializable {
    private String id;
    private String name;
    private String type;
    private String price;
    private int hits;
    private double expectRadio=0.005;

    public double getExpectRadio() {
        return expectRadio;
    }

    public void setExpectRadio(double expectRadio) {
        this.expectRadio = expectRadio;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
