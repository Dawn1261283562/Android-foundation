package com.example.asus.httpcallbacklistener;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String phoneNumber;
    private String password;
    private String userName;
    private String emailAddress;
    private Date registerTime;
    //用于URL传参和取参时的key
    public static String PHONENUMBER = "phoneNumber";
    public static String PASSWORD = "passWord";
    public static String USERNAME = "userName";
    //构造函数
    public User(String phoneNumber,String password,String userName){
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userName = userName;
        registerTime = new Date();
    }
    //属性的get与set方法不再贴出

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public static String getPHONENUMBER() {
        return PHONENUMBER;
    }

    public static void setPHONENUMBER(String PHONENUMBER) {
        User.PHONENUMBER = PHONENUMBER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        User.PASSWORD = PASSWORD;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        User.USERNAME = USERNAME;
    }
}