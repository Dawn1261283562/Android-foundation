package com.example.studying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.studying.entity.FundHeavy;
import com.example.studying.entity.User;
import com.example.studying.utils.HttpGetRequest;
import com.example.studying.utils.HttpPostRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

//txml
//FragmentActivity
public class MainActivity extends   FragmentActivity implements View.OnClickListener  {

/*    private EditText et_username;

    private EditText et_password;

    private Button btn_login;*/






    //??????ViewPager
    private ViewPager mViewPager;
    //?????????
    private FragmentPagerAdapter mAdapter;
    //??????Fragment?????????
    private List<Fragment> mFragments;

    //3???Tab???????????????
    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;

    //3???Tab?????????ImageButton
    private ImageButton mImg1;
    private ImageButton mImg2;
    private ImageButton mImg3;

    private TextView mText1;
    private TextView mText2;
    private TextView mText3;

    private PageFragment1 pageFragment1;
    private PageFragment2 pageFragment2;
    private PageFragment3 pageFragment3;

    private String username;
    private ArrayList<FundHeavy> fundHeavyList=new ArrayList<FundHeavy>();
    Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
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



        Data data = (Data)getApplicationContext();
        if(data.getUsername()!=null){
            if ((data.getTheLoginTime() - data.getLastLoginTime())/(24*60*60*1000) > 3 ) {
                data.setUsername(null);
//            Toast.makeText(this, "????????????????????????????????????", Toast.LENGTH_LONG).show();
                /*AlertDialog alertDialog =*/new AlertDialog.Builder(MainActivity.this)
                        .setTitle("????????????")
                        .setMessage("????????????????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
               /* alertDialog.getWindow().setAttributes(getWindow().getAttributes());
                alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);*/
            }
        }


        initViews();//???????????????
        initEvents();//???????????????
        initData();//???????????????
    }

    private void initData() {
        Data data = (Data)getApplicationContext();
        username=data.getUsername();

        mFragments = new ArrayList<>();
        //???3???Fragment???????????????
        pageFragment2=new PageFragment2();
        pageFragment1=new PageFragment1();
        pageFragment3=new PageFragment3();

        mFragments.add(pageFragment1);
        mFragments.add(pageFragment2);
        mFragments.add(pageFragment3);

        //??????????????????
            mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//?????????????????????????????????Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//???????????????Fragment?????????
                return mFragments.size();
            }
        };
        //??????ViewPager????????????
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        //??????ViewPager???????????????
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //??????????????????
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //??????????????????
            @Override
            public void onPageSelected(int position) {
                if(username==null){
                    if(position==1||position==2) {
                        login();
                    }
                }
                resetImg();
                selectTab(position);
//                pageFragment2.fundSlected();
            }

            @Override
            //??????????????????????????????
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mHandler=new Handler(Looper.getMainLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what){
//                    case 1:
//
//
//
//                        break;
//                    case 2:
//
//                }
//            }
//        };
    }

    private void initEvents() {
        //??????3???Tab???????????????
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vPager);

        mTab1 = (LinearLayout) findViewById(R.id.tab1);
        mTab2 = (LinearLayout) findViewById(R.id.tab2);
        mTab3 = (LinearLayout) findViewById(R.id.tab3);

        mImg1 = (ImageButton) findViewById(R.id.tab_img1);
        mImg2 = (ImageButton) findViewById(R.id.tab_img2);
        mImg3 = (ImageButton) findViewById(R.id.tab_img3);

        mText1 = (TextView) findViewById(R.id.tab_text1);
        mText2 = (TextView) findViewById(R.id.tab_text2);
        mText3 = (TextView) findViewById(R.id.tab_text3);

/*        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("http://www.baidu.com");*/
/*//???????????????WebView??????????????????
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //??????????????????WebView??????????????????
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        username=((Data)getApplicationContext()).getUsername();

        if(username==null){
            mViewPager.setCurrentItem(0);
            resetImg();
            selectTab(0);
        }
    }

    @Override
    public void onClick(View v) {
        //??????resetImg??????????????????
        resetImg();

        //???????????????Tab??????????????????????????????selectTab??????????????????
        switch (v.getId()) {
            case R.id.tab1:
                selectTab(0);
                break;
            case R.id.tab2:
                selectTab(1);
                break;
            case R.id.tab3:
                selectTab(2);
                break;
        }
    }

    private void selectTab(int i) {
        //?????????????????????tab?????????
        switch (i) {
            case 0:
                mText1.setTextColor(getResources().getColor(R.color.chosen));
                mImg1.setImageResource(R.mipmap.found_chosen);
                break;
            case 1:
                mText2.setTextColor(getResources().getColor(R.color.chosen));
                mImg2.setImageResource(R.mipmap.star_chosen);

                break;
            case 2:
                mText3.setTextColor(getResources().getColor(R.color.chosen));
                mImg3.setImageResource(R.mipmap.user_chosen);
                break;
        }
        mViewPager.setCurrentItem(i);
    }

    //???????????????tab?????????
    private void resetImg() {
        mImg1.setImageResource(R.mipmap.found_normal);
        mImg2.setImageResource(R.mipmap.star_normal);
        mImg3.setImageResource(R.mipmap.user_normal);

        mText1.setTextColor(getResources().getColor(R.color.normal));
        mText2.setTextColor(getResources().getColor(R.color.normal));
        mText3.setTextColor(getResources().getColor(R.color.normal));
    }

    private void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    //Request ????????????
/*    private void initView() {
        //????????????
        et_username = findViewById(R.id.edit_username);
        et_password = findViewById(R.id.edit_pwd);
        btn_login = findViewById(R.id.btn_login);


        //?????????????????????????????????
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

                //url = "http://localhost:8080/stock/hellos?id=";

                //http://43m486x897.yicp.fun
                //http://4s348z6897.qicp.vip
                url = "http://43m486x897.yicp.fun"+"/fundHeavy/hellos?id=";
                url = "http://4s348z6897.qicp.vip/stock/oneStock?id=";
                //?????????????????????
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", et_username.getText().toString())
                        .add("password", et_password.getText().toString())
                        .build();
                url=url+et_username.getText().toString();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "post????????????", Toast.LENGTH_SHORT).show();
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
//                        //???JSON???String ????????????JsonArray??????
//                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
//
//                        Gson gson = new Gson();
//                        ArrayList<User> userBeanList = new ArrayList<>();
//
//                        //??????for????????????JsonArray
//                        for (JsonElement user : jsonArray) {
//                            //??????GSON???????????????Bean??????
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
                        Toast.makeText(MainActivity.this, strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }*/

}
