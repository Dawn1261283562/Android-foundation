package com.example.studying;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studying.entity.User;
import com.example.studying.entity.fundHeavy;
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


public class SearchFragment3_2 extends androidx.fragment.app.Fragment {
    private View mView;

    private Button btn_login1;
    private Button btn_login;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {

            mView = inflater.inflate(R.layout.search_fragment3_2, container, false);
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
        //绑定控件
        //Button button=(Button)findViewById(R.id.button01);

        btn_login1=(Button)mView.findViewById(R.id.btn_login1);
        //为登录按钮设置点击事件
        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(123);
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockAllTypeRadio?num=2&TypeList=军工,计算机";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder()
                        .build();

                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {




//                        ResponseBody data = response.body();
//                        String strByJson = response.body().string();

                        ResponseBody data = response.body();
                        String strByJson = response.body().string();

//                        Gson gson = new Gson();
//                        User bean = gson.fromJson(jstr, User.class);
//                        System.out.println(bean.id);
//                        System.out.println(bean.username);
//                        System.out.println(bean.password);

                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<fundHeavy> userBeanList = new ArrayList<>();

                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray
                        for (JsonElement fundHeavy : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            fundHeavy userBean = gson.fromJson(fundHeavy, fundHeavy.class);
                            userBeanList.add(userBean);

                            System.out.println(userBean.id);
                            System.out.println(userBean.name);
                            System.out.println(Arrays.toString(userBean._stock_id));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
                            System.out.println(Arrays.toString(userBean._stock_ratio));
                            System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
                            System.out.println(userBean.hits);

                        }

                        //String json = new String(data.bytes());
                        //var obj = data.parseJSON();
                        //JSONArray.fromObject(stu);


                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();

//
//                        Looper.prepare();
//                        System.out.println(strByJson);
//                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
//                        Looper.loop();
                    }
                });
            }
        });
    }
}