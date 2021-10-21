package com.example.studying.utils;


import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Get请求
 */
public class HttpGetRequest {
    public static Request sendOkHttpGetRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
        return request;
    }
}

