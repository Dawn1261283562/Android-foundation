package com.example.studying;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    Button signUpBut;

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
    }

    private void initEvent() {
        signUpBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userAccount=editText1.getText().toString();
                if(!userAccount.equals("")){
                    ((Data)getApplicationContext()).setUsername(userAccount);
                    finish();
                }

            }
        });
    }

    private void initViews() {
        editText1=findViewById(R.id.signup_edit1);
        editText2=findViewById(R.id.signup_edit2);
        editText3=findViewById(R.id.signup_edit3);
        signUpBut=findViewById(R.id.signup_button);
    }
}