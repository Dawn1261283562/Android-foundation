package com.example.studying;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studying.utils.HttpGetRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PageFragment2 extends androidx.fragment.app.Fragment {
    private View mView;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment2, container, false);
        }
        //获取到用户选择的基金
        fundSlected();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        ListView listView = (ListView) mView.findViewById(R.id.list_fund_selected);
        listView.setAdapter(fundAdapter);

        //绑定控件
        et_username = (EditText) mView.findViewById(R.id.edit_username);
        et_password = (EditText) mView.findViewById(R.id.edit_pwd);
        btn_login = (Button) mView.findViewById(R.id.btn_login);
        initView();

        return mView;
    }

    //Request 请求代码
    private void initView() {

        //为登录按钮设置点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://4s348z6897.qicp.vip/stock/hellos?id=sz000004";
                url = "http://localhost:8080/stock/hellos?id=";
                url = "http://localhost:8080/user/getListByPhone?userName=";
                url = "http://43m486x897.yicp.fun:50117/user/getListByPhone?userName=";
                url="http://4s348z6897.qicp.vip/user/getListByPhone?userName=";
                url = "http://4s348z6897.qicp.vip/stock/hellos?id=";


                url = "http://localhost:8080/stock/hellos?id=";
                url = "http://43m486x897.yicp.fun/fundHeavy/hellos?id=";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", et_username.getText().toString())
                        .add("password", et_password.getText().toString())
                        .build();
                url=url+et_username.getText().toString();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "post请求失败", Toast.LENGTH_SHORT).show();
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
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }

    private void fundSlected() {
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("基金1","012345",1.3333,0.0161,1.3334,-0.0104);
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("改222","233333",1.4444,-0.0133,1.2333,-0.0122);
        fundGeneralList.add(fundGeneral2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}

//    private EditText et_username;
//
//    private EditText et_password;
//
//    private Button btn_login;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//    }
//
//    private void initView() {
//        //绑定控件
//        et_username = findViewById(R.id.edit_username);
//        et_password = findViewById(R.id.edit_pwd);
//        btn_login = findViewById(R.id.btn_login);
//
//        //为登录按钮设置点击事件
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://加上刚才复制的ip地址:8080/user/lgoin";
//
//                //请求传入的参数
//                RequestBody requestBody = new FormBody.Builder()
//                        .add("username", et_username.getText().toString())
//                        .add("password", et_password.getText().toString())
//                        .build();
//
//                HttpPostRequest.okhttpPost(url, requestBody, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Looper.prepare();
//                        Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Looper.prepare();
//                        Toast.makeText(MainActivity.this, "成功,用户名为：" + et_username.getText().toString(), Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
//            }
//        });
//    }
