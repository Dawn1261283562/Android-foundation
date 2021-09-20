package com.example.demo1;
import com.example.demo1.dao.UserDao;
import com.example.demo1.entity.User;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {


    public static void main(String[] args) {

        User user1 = new User();
        user1.setId(3);
        user1.setUsername("asd");
        user1.setUsername("lpl");
        //user1.toString();
        User user2 = new User();
        //UserDao userDao=new UserDao();
        //user2 = userDao.getByPhone("15361022832");
        System.out.print( user2.toString());;
        SpringApplication.run(Demo1Application.class, args);
    }


}



