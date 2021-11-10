package com.example.studying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studying.utils.HttpGetRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SignUpActivity extends AppCompatActivity {

    EditText editAccountNumber;
    EditText editText2;
    EditText editPassword;
    Button signUpBut;
    Handler mHandler;
    int SUCCESSCODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        initViews();
        initEvent();
        initData();
    }

    private void initEvent() {
        signUpBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userAccount=editAccountNumber.getText().toString();


                String passWord=editPassword.getText().toString();

                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/user/insert?id=";
                String urlNext="&passWord=";
                System.out.println(1);
                System.out.println(passWord);
                //请求传入的参数


                RequestBody requestBody = new FormBody.Builder().build();
                url=url+userAccount+urlNext+passWord;
                if(passWord.equals("")||userAccount.equals("")){
                    Toast.makeText(SignUpActivity.this, "完善相关信息", Toast.LENGTH_SHORT).show();

                    return;
                }
                String regex2 ="\\d{11}";

                if(userAccount.matches(regex2)){
                }
                else{
                    Toast.makeText(SignUpActivity.this, "请填入手机号格式作为用户名", Toast.LENGTH_SHORT).show();

                    return;
                }

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
                        if(response.code()==200) {
                            //if(response.body().string()==null)return;
                            String strByJson = response.body().string();
                            //flagSuccess =strByJson;
                            System.out.println(strByJson);

                            Message message = new Message();
                            message.what = 1;
                            mHandler.sendMessage(message);

                            Looper.prepare();
                            //System.out.println(data.string());

                            Toast.makeText(SignUpActivity.this, "flagSuccess", Toast.LENGTH_SHORT).show();

                            Looper.loop();
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(SignUpActivity.this, "无相关信息", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                    }
                });

            }
        });
    }

    private void initData() {
        mHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        String userAccount=editAccountNumber.getText().toString();
                        ((Data)getApplicationContext()).setUsername(userAccount);

                        finish();
                        break;
                    case 2:

                }
            }
        };
    }

    private void initViews() {
        editAccountNumber=findViewById(R.id.signup_edit1);
        editText2=findViewById(R.id.signup_edit2);
        editPassword=findViewById(R.id.signup_edit3);
        signUpBut=findViewById(R.id.signup_button);
    }
}