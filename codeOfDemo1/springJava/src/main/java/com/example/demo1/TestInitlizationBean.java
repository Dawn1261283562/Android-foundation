package com.example.demo1;

import org.springframework.beans.factory.InitializingBean;

public class TestInitlizationBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestInitlizationBean......");
    }
}
