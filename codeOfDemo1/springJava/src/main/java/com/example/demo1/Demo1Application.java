package com.example.demo1;
import com.example.demo1.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class Demo1Application {

//    @Autowired
//    static JdbcTemplate jdbcTemplate;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Demo1Application.class, args);
    }


}



