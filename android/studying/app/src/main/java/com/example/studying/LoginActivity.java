package com.example.studying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editAccountNumber;
    EditText editPassword;
    Button signupBut;
    Button loginBut;
    String flagSuccess = "false";
    private String username;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
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
        initEvents();
        initData();
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



    private void initEvents() {
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String userAccount=editAccountNumber.getText().toString();
//                if(!userAccount.equals("")){
//                    ((Data)getApplicationContext()).setUsername(userAccount);
//
//                    //finish();
//                }
                //String userAccount=editAccountNumber.getText().toString();
                String passWord=editPassword.getText().toString();
//                if(!userAccount.equals("")){
//                    ((Data)getApplicationContext()).setUsername(userAccount);
//
//                    finish();
//                }
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByGeneralSearch?id=000013";
                url = "http://43m486x897.yicp.fun/user/login?userName=";
                String urlNext="&passWord=";
                //请求传入的参数
//                String urlAdd= editText.getText().toString().trim();
//                System.out.println(urlAdd);
//                searchFragment1.saveSearchHistory1(urlAdd);
//                searchFragment1.getsearchHistory1();

                RequestBody requestBody = new FormBody.Builder().build();
                url=url+userAccount+urlNext+passWord;

//                if(urlAdd.equals("")){
//
//                    Toast.makeText(LoginActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
//
//                    return;
//                }

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
                            flagSuccess =strByJson;
                            if(strByJson.equals("true")){

                                Message message = new Message();
                                message.what = 1;
                                mHandler.sendMessage(message);
                            }

                            Looper.prepare();
                            //System.out.println(data.string());

                            Toast.makeText(LoginActivity.this, flagSuccess, Toast.LENGTH_SHORT).show();

                            Looper.loop();
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this, "无相关信息", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
//                        Looper.prepare();
//                        System.out.println(data.string());
//                        Toast.makeText(LoginActivity.this, data.string(), Toast.LENGTH_SHORT).show();
//                        Looper.loop();


                    }
                });

                System.out.println(flagSuccess);

//                Thread closeActivity = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                            searchFragment1.fundSearchResult();
//                            searchFragment1.update(fundInfoList);
//                            // Do some stuff
//                        } catch (Exception e) {
//                            e.getLocalizedMessage();
//                        }
//                    }});
//                closeActivity.run();
            }
        });
        signupBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initViews() {
        editAccountNumber=(TextInputEditText)findViewById(R.id.edit_account_number);
        editPassword=(EditText)findViewById(R.id.edit_password);
        signupBut=(Button)findViewById(R.id.button_signup);
        loginBut=(Button) findViewById(R.id.login_button);
    }

}