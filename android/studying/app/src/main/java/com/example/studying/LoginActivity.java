package com.example.studying;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editAccountNumber;
    EditText editPassword;
    Button signupBut;
    Button loginBut;

    private String username;

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
    }

    private void initEvents() {
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                String userAccount=editAccountNumber.getText().toString();
                if(!userAccount.equals("")){
                    ((Data)getApplicationContext()).setUsername(userAccount);

                    finish();
                }
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