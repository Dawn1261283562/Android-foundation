package com.example.studying;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studying.entity.FundHeavy;
import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SearchFragment3_10 extends androidx.fragment.app.Fragment {
    private View mView;

    private Button btn_login1;
    private Button btn_login2;
    private Button btn_login3;
    private Button btn_login4;
    private Button btn_login5;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {

            mView = inflater.inflate(R.layout.search_fragment3_10, container, false);
        }
        initView();
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    private void initView() {
        btn_login1=(Button)mView.findViewById(R.id.btn_login1);
        btn_login2=(Button)mView.findViewById(R.id.btn_login2);
        btn_login3=(Button)mView.findViewById(R.id.btn_login3);
        btn_login4=(Button)mView.findViewById(R.id.btn_login4);
        btn_login5=(Button)mView.findViewById(R.id.btn_login5);

        initbtn_login1();//板块
        initbtn_login2();//持仓搜索（考虑期望比例）
        initbtn_login3();//持仓搜索（无期望比例）
        initbtn_login4();//普通搜索 基金信息（注意！！！是信息 不是 重仓信息！）
        initbtn_login5();//普通搜索 股票信息

        //板块搜索设置点击事件

    }

    private void initbtn_login1() {
        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockAllTypeRadio?num=2&TypeList=军工,计算机";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder().build();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody data = response.body();
                        String strByJson = response.body().string();
                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<FundHeavy> userBeanList = new ArrayList<>();
                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray
                        for (JsonElement fundHeavy : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            FundHeavy userBean = gson.fromJson(fundHeavy, FundHeavy.class);
                            userBeanList.add(userBean);

                            System.out.println("这下面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度（一共十个优质基金）");
                            System.out.println(userBean.id);
                            System.out.println(userBean.name);
                            System.out.println(userBean.getScore());
                            System.out.println(Arrays.toString(userBean._stock_id));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
                            System.out.println(Arrays.toString(userBean._stock_ratio));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
                            System.out.println(userBean.hits);
                            System.out.println("这上面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度");
                        }

                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
    private void initbtn_login2() {
        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockScore?num=3&stockIdList=002709.SZ,000799.SZ,000009.SZ&stockRadioList=0.88,0.86,0.7";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder().build();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody data = response.body();
                        String strByJson = response.body().string();

                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<FundHeavy> userBeanList = new ArrayList<>();

                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray
                        for (JsonElement fundHeavy : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            FundHeavy userBean = gson.fromJson(fundHeavy, FundHeavy.class);
                            userBeanList.add(userBean);

                            System.out.println("这下面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度（一共十个优质基金）");
                            System.out.println(userBean.id);
                            System.out.println(userBean.name);
                            System.out.println(userBean.getScore());
                            System.out.println(Arrays.toString(userBean._stock_id));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
                            System.out.println(Arrays.toString(userBean._stock_ratio));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
                            System.out.println(userBean.hits);
                            System.out.println("这上面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度");
                        }
                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
    private void initbtn_login3() {
        btn_login3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockList?num=4&stockIdList=000001.SZ,000858.SZ,002475.SZ,002050.SZ";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder().build();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody data = response.body();
                        String strByJson = response.body().string();

                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<FundHeavy> userBeanList = new ArrayList<>();
                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray
                        for (JsonElement fundHeavy : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            FundHeavy userBean = gson.fromJson(fundHeavy, FundHeavy.class);
                            userBeanList.add(userBean);

                            System.out.println("这下面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度（一共十个优质基金）");
                            System.out.println(userBean.id);
                            System.out.println(userBean.name);
                            System.out.println(userBean.getScore());
                            System.out.println(Arrays.toString(userBean._stock_id));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
                            System.out.println(Arrays.toString(userBean._stock_ratio));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
                            System.out.println(userBean.hits);
                            System.out.println("这上面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度");
                        }
                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
    private void initbtn_login4() {
        btn_login4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByGeneralSearch?id=000013";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder().build();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody data = response.body();
                        String strByJson = response.body().string();

                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<FundHeavyInfo> fundHeavyInfoList = new ArrayList<>();

                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray

                        for (JsonElement fundHeavyInfo : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            FundHeavyInfo fundHeavyInfoBean = gson.fromJson(fundHeavyInfo, FundHeavyInfo.class);
                            fundHeavyInfoList.add(fundHeavyInfoBean);

                            System.out.println("这下面是 基金信息的代码、名字、全名、法人公司名、管理者");
                            System.out.println(fundHeavyInfoBean.getId());
                            System.out.println(fundHeavyInfoBean.getName());
                            System.out.println(fundHeavyInfoBean.getFull_name());
                            System.out.println(fundHeavyInfoBean.getLegal_person());
                            System.out.println(fundHeavyInfoBean.getManager());
                            System.out.println("这上面是 基金信息的代码、名字、全名、法人公司名、管理者");
                        }
                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
    private void initbtn_login5() {
        btn_login5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=sz000001";
                RequestBody requestBody = new FormBody.Builder().build();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody data = response.body();
                        String strByJson = response.body().string();
                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<Stock> stockBeanList = new ArrayList<Stock>();

                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray
                        for (JsonElement stock : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            Stock stockBean = gson.fromJson(stock, Stock.class);
                            stockBeanList.add(stockBean);

                            System.out.println("这下面是 股票的代码、名字、板块集、股价、热度");
                            System.out.println(stockBean.getId());
                            System.out.println(stockBean.getName());
                            System.out.println(stockBean.getType());
                            System.out.println(stockBean.getPrice());
                            System.out.println(stockBean.getHits());
                            System.out.println("这上面是 股票的代码、名字、板块集、股价、热度");
                        }
                        Looper.prepare();
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
}


//    private void initbtn_login1() {
//        btn_login1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(123);
//                String url = "http://localhost:8080/user/lgoin";
//                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockAllTypeRadio?num=2&TypeList=军工,计算机";
//                //请求传入的参数
//                RequestBody requestBody = new FormBody.Builder()
//                        .build();
//
//                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Looper.prepare();
//                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//
//
//
////                        ResponseBody data = response.body();
////                        String strByJson = response.body().string();
//
//                        ResponseBody data = response.body();
//                        String strByJson = response.body().string();
//
////                        Gson gson = new Gson();
////                        User bean = gson.fromJson(jstr, User.class);
////                        System.out.println(bean.id);
////                        System.out.println(bean.username);
////                        System.out.println(bean.password);
//
//                        JsonParser parser = new JsonParser();
//                        //将JSON的String 转成一个JsonArray对象
//                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
//
//                        Gson gson = new Gson();
//                        ArrayList<fundHeavy> userBeanList = new ArrayList<>();
//
//                        System.out.println(strByJson);
//                        //加强for循环遍历JsonArray
//                        for (JsonElement fundHeavy : jsonArray) {
//                            //使用GSON，直接转成Bean对象
//                            fundHeavy userBean = gson.fromJson(fundHeavy, fundHeavy.class);
//                            userBeanList.add(userBean);
//
//                            System.out.println(userBean.id);
//                            System.out.println(userBean.name);
//                            System.out.println(userBean.getScore());
//                            System.out.println(Arrays.toString(userBean._stock_id));
//                            System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
//                            System.out.println(Arrays.toString(userBean._stock_ratio));
//                            System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
//                            System.out.println(userBean.hits);
//
//                        }
//
//                        //String json = new String(data.bytes());
//                        //var obj = data.parseJSON();
//                        //JSONArray.fromObject(stu);
//
//
//                        Looper.prepare();
//                        System.out.println(data);
//                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//
////
////                        Looper.prepare();
////                        System.out.println(strByJson);
////                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
////                        Looper.loop();
//                    }
//                });
//            }
//        });
//    }