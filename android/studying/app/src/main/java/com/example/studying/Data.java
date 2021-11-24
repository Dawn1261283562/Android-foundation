package com.example.studying;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.widget.Toast;


public class Data extends Application {

    public static final String SEARCH_HISTORY = "data";


    private long lastLoginTime;
    private long theLoginTime;
    private String username;
    SharedPreferences sp;

    private boolean addStockTips;

    @Override
    public void onCreate() {
        super.onCreate();


        sp = getSharedPreferences(SEARCH_HISTORY, 0);

        username = sp.getString("username", null);
        lastLoginTime = sp.getLong("lastLoginTime", theLoginTime);
        theLoginTime = System.currentTimeMillis();

        sp.edit().putLong("lastLoginTime",theLoginTime).commit();

        addStockTips=false;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public long getTheLoginTime() {
        return theLoginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

        sp.edit().putString("username",this.username).commit();
    }

    public boolean getAddStockTips() {
        return addStockTips;
    }

    public void setAddStockTips(boolean addStockTips) {
        this.addStockTips = addStockTips;
    }
}
