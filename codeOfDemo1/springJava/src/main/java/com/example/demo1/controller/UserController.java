package com.example.demo1.controller;

import com.example.demo1.entity.User;
import com.example.demo1.service.HttpClient;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author wangrui
 * @date 2021/5/21 12:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @RequestMapping("/insert")
    @ResponseBody
    public int insert(long id,String passWord) {
        User user = new User();
        //int id = new Random().nextInt(10000);
        user.setId(id);
        user.setUsername( ""+id);

        user.setPassword(passWord);
        System.out.println(user.getPassword());

        int result = this.userService.insert(user);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/deleteByPhone")
    public void deleteByPhone(String userName) {
        int result = this.userService.deleteByPhone(userName);
        System.out.println(result);
    }

    @RequestMapping("/update")
    public void update() {
        User user = new User();
        user.setId(1);
        user.setPassword("test123");
        this.userService.update(user);
    }

    @RequestMapping("/getByPhone")
    @ResponseBody
    public User getByPhone(String userName) {
        User user = this.userService.getByPhone(userName);
        System.out.println(user.getUsername());
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

//        System.out.println( "this"+user_1.getUsername());
        return user;
    }

    @RequestMapping("/getListByPhone")
    @ResponseBody
    public List<User> getListByPhone(String userName) {
        List<User> w = this.userService.getListByPhone(userName);
        //System.out.println(user.getUsername());
        return w;
    }

    @RequestMapping("/login")
    @ResponseBody
    public boolean login(String userName, String passWord){
        boolean user = this.userService.login( userName, passWord);
        //System.out.println(user.toString());
        return user;
    }

    @RequestMapping("/ifExist")
    @ResponseBody
    public boolean ifExist(String userName) {
        User user = this.userService.getByPhone(userName);
        System.out.println(user.getUsername());
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

//        System.out.println( "this"+user_1.getUsername());
        if(user!=null)return true;
        else
        return false;
    }

    //    @RequestMapping("/save")
    //    @ResponseBody
    //    public User save() {
    //        User user = new User();
    //        int id = new Random().nextInt(10000);
    //        user.setId(id);
    //        user.setUsername("张三" + id);
    //        user.setPassword("zhangsan" + id);
    //
    //        int result = this.userService.insert(user);
    //        System.out.println(result);
    //        return user;
    //    }

    //    @RequestMapping("/deleteById")
    //    public void deleteById(Integer id) {
    //        int result = this.userService.deleteById(id);
    //        System.out.println(result);
    //    }

    //    @RequestMapping("/getById")
    //    @ResponseBody
    //    public User getById(Integer id) {
    //        User user = this.userService.getById(id);
    //        System.out.println(user.getUsername());
    //        return user;
    //    }


}

