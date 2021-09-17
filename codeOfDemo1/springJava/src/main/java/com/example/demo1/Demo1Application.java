package com.example.demo1;
import com.example.demo1.entity.User;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        //test for user1.toString();
        User user1 = new User();
        user1.setId(3);
        user1.setUsername("asd");
        user1.setUsername("lpl");
        //user1.toString();
        System.out.print( user1.toString());;
        SpringApplication.run(Demo1Application.class, args);
    }


}
