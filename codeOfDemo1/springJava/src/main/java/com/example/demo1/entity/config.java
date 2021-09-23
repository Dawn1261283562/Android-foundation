package com.example.demo1.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//                        整体不用看
@Configuration
public class config {

    @Bean
    public User getUser(){
        User user=new User();
        return user;
    }
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int i=1;
}
