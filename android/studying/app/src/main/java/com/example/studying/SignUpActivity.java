package com.example.studying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    EditText editEmail;
    EditText editPassword1;
    EditText editPassword2;
    Button signUpBut;
    ImageButton clearTextButton1;
    ImageButton clearTextButton2;
    ImageButton clearTextButton3;
    ImageButton clearTextButton4;

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
                String userEmail=editEmail.getText().toString();

                String passWord1=editPassword1.getText().toString();
                String passWord2=editPassword2.getText().toString();

                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/user/insert?id=";
                String urlNext="&passWord=";
                System.out.println(1);
                System.out.println(passWord1);
                //请求传入的参数


                RequestBody requestBody = new FormBody.Builder().build();
                url=url+userAccount+urlNext+passWord1;
                if(passWord1.equals("")||userAccount.equals("")){
                    Toast.makeText(SignUpActivity.this, "完善相关信息", Toast.LENGTH_SHORT).show();

                    return;
                }

//                String regex2 ="\\d{11}";
                String regex2 ="[1][358]\\d{9}";

                if(userAccount.matches(regex2)){
                }
                else{
                    Toast.makeText(SignUpActivity.this, "请填入手机号格式作为用户名", Toast.LENGTH_SHORT).show();

                    return;
                }

                String regex3="^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
                if(!userEmail.equals("")&&!userEmail.matches(regex3)){
                    Toast.makeText(SignUpActivity.this, "请填入正确的邮箱", Toast.LENGTH_SHORT).show();

                    return;
                }

                if(passWord1.length()<6){
                    Toast.makeText(SignUpActivity.this, "请输入6-16位数字的密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!passWord1.equals(passWord2)){
                    Toast.makeText(SignUpActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
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

        editAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editAccountNumber.getText().length()>0){
                    clearTextButton1.setVisibility(View.VISIBLE);
                }
                else{
                    clearTextButton1.setVisibility(View.INVISIBLE);
                }
            }
        });
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editEmail.getText().length()>0){
                    clearTextButton2.setVisibility(View.VISIBLE);
                }
                else{
                    clearTextButton2.setVisibility(View.INVISIBLE);
                }
            }
        });
        editPassword1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editPassword1.getText().length()>0){
                    clearTextButton3.setVisibility(View.VISIBLE);
                }
                else{
                    clearTextButton3.setVisibility(View.INVISIBLE);
                }
            }
        });
        editPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editPassword2.getText().length()>0){
                    clearTextButton4.setVisibility(View.VISIBLE);
                }
                else{
                    clearTextButton4.setVisibility(View.INVISIBLE);
                }
            }
        });


        clearTextButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccountNumber.setText("");
            }
        });
        clearTextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEmail.setText("");
            }
        });
        clearTextButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPassword1.setText("");
            }
        });
        clearTextButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPassword2.setText("");
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
        editEmail=findViewById(R.id.signup_edit2);
        editPassword1=findViewById(R.id.signup_edit3);
        editPassword2=findViewById(R.id.signup_edit4);
        signUpBut=findViewById(R.id.signup_button);
        clearTextButton1=(ImageButton) findViewById(R.id.signup_clear_but1);
        clearTextButton2=(ImageButton) findViewById(R.id.signup_clear_but2);
        clearTextButton3=(ImageButton) findViewById(R.id.signup_clear_but3);
        clearTextButton4=(ImageButton) findViewById(R.id.signup_clear_but4);
    }
}