package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PageFragment2 extends androidx.fragment.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        TextView txt = (TextView) view.findViewById(R.id.txt2);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "当前页面：1", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
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
}
