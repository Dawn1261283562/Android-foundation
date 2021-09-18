package com.example.demo1.controller;

import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public User insert(long id,String password) {
        User user = new User();
        //int id = new Random().nextInt(10000);
        user.setId(id);
        user.setUsername( ""+id);
        user.setPassword(password);

        int result = this.userService.insert(user);
        System.out.println(result);
        return user;
    }

    @RequestMapping("/deleteByPhone")
    public void deleteByPhone(String username) {
        int result = this.userService.deleteByPhone(username);
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
    public User getByPhone(String username) {
        User user = this.userService.getByPhone(username);
        System.out.println(user.getUsername());
        return user;
    }

    @RequestMapping("/login")
    @ResponseBody
    public User login(String username, String password){
        User user = this.userService.login( username, password);
        System.out.println(user.toString());
        return user;
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

