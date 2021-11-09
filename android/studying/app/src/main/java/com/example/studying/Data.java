package com.example.studying;

import android.app.Application;
import android.content.SharedPreferences;

public class Data extends Application {

    public static final String SEARCH_HISTORY = "data";

    private String username;
    SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();

        sp = getSharedPreferences(SEARCH_HISTORY, 0);

        username=new String();
        username=sp.getString("username",null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

        sp.edit().putString("username",this.username).commit();
    }

}
