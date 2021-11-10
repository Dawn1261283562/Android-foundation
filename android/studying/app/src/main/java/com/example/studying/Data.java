package com.example.studying;

import android.app.Application;
import android.content.SharedPreferences;

public class Data extends Application {

    public static final String SEARCH_HISTORY = "data";


    private long lastLoginTime;
    private String username;
    SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        long theLoginTime=System.currentTimeMillis();

        sp = getSharedPreferences(SEARCH_HISTORY, 0);

        lastLoginTime=sp.getLong("lastLoginTime",theLoginTime);

        if(theLoginTime-lastLoginTime>=3*24*60*60*1000){
            setUsername(null);
        }
        else{
            username=sp.getString("username",null);
        }

//        lastLoginTime = theLoginTime;
        sp.edit().putLong("lastLoginTime",theLoginTime).commit();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

        sp.edit().putString("username",this.username).commit();
    }

}
