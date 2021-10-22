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

import com.example.studying.utils.HttpGetRequest;

import java.io.IOException;

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
//        View rootView = inflater.inflate(R.layout.search_fragment3_2, container, false);
//        Button button = (Button) rootView.findViewById(R.id.btn_login1);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://localhost:8080/user/lgoin";
//                url = "http://4s348z6897.qicp.vip/stock/oneStock?id=sz000004";
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
////                        ResponseBody data = response.body();
////                        String strByJson = response.body().string();
////
//////                        Gson gson = new Gson();
//////                        User bean = gson.fromJson(jstr, User.class);
//////                        System.out.println(bean.id);
//////                        System.out.println(bean.username);
//////                        System.out.println(bean.password);
////
////                        JsonParser parser = new JsonParser();
////                        //将JSON的String 转成一个JsonArray对象
////                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
////
////                        Gson gson = new Gson();
////                        ArrayList<User> userBeanList = new ArrayList<>();
////
////                        //加强for循环遍历JsonArray
////                        for (JsonElement user : jsonArray) {
////                            //使用GSON，直接转成Bean对象
////                            User userBean = gson.fromJson(user, User.class);
////                            userBeanList.add(userBean);
////
////                            System.out.println(userBean.id);
////                            System.out.println(userBean.username);
////                            System.out.println(userBean.password);
////                        }
////
////                        //String json = new String(data.bytes());
////                        //var obj = data.parseJSON();
////                        //JSONArray.fromObject(stu);
////
////
////                        Looper.prepare();
////                        System.out.println(data);
////                        Toast.makeText(MainActivity.this, strByJson, Toast.LENGTH_SHORT).show();
////                        Looper.loop();
//
//
//                        ResponseBody data = response.body();
//                        String strByJson = response.body().string();
//
//                        Looper.prepare();
//                        System.out.println(strByJson);
//                        //Toast.makeText(MainActivity.this, strByJson, Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
//            }
//        });
//        return rootView;
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
                url = "http://4s348z6897.qicp.vip/stock/oneStock?id=sz000004";
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
//                        ArrayList<User> userBeanList = new ArrayList<>();
//
//                        //加强for循环遍历JsonArray
//                        for (JsonElement user : jsonArray) {
//                            //使用GSON，直接转成Bean对象
//                            User userBean = gson.fromJson(user, User.class);
//                            userBeanList.add(userBean);
//
//                            System.out.println(userBean.id);
//                            System.out.println(userBean.username);
//                            System.out.println(userBean.password);
//                        }
//
//                        //String json = new String(data.bytes());
//                        //var obj = data.parseJSON();
//                        //JSONArray.fromObject(stu);
//
//
//                        Looper.prepare();
//                        System.out.println(data);
//                        Toast.makeText(MainActivity.this, strByJson, Toast.LENGTH_SHORT).show();
//                        Looper.loop();


                        ResponseBody data = response.body();
                        String strByJson = response.body().string();

                        Looper.prepare();
                        System.out.println(strByJson);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }
}