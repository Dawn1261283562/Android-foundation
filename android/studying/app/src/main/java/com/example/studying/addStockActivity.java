package com.example.studying;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class addStockActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;
    addStockFragment addStockFragment;

    private LinearLayout mLir1;
    private LinearLayout mLir2;

    private TextView mTex1;
    private TextView mTex2;
    private TextView mTex3;
    private TextView mTex4;
    private TextView mTex5;

    private EditText editText;
    private Button searchBut;
    private TextView titleTex;
    private ArrayList<Stock> stockList=new ArrayList<Stock>();
    private ArrayList<Stock> stockList1=new ArrayList<Stock>();
    private ArrayList<FundHeavyInfo> temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        initViews();
        initEvents();
        initData();

        Intent intent = this.getIntent();
        stockList = (ArrayList<Stock>) intent.getSerializableExtra("stockList");

        selectTab(1);
        //System.out.println(stockList);

    }

    private void initData() {
        mFragments = new ArrayList<>();


        mFragments.add(new addStockFragment());

        addStockFragment= (addStockFragment)mFragments.get(0);


        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }
        };
        //设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment页面
                mViewPager.setCurrentItem(position);
                resetTab();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置3个按钮的点击事件
        mTex1.setOnClickListener(this);
        mTex2.setOnClickListener(this);
        mTex3.setOnClickListener(this);
        mTex4.setOnClickListener(this);
        mTex5.setOnClickListener(this);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vPager);

        mLir1 = (LinearLayout) findViewById(R.id.top_Tab1);
        mLir2 = (LinearLayout) findViewById(R.id.top_Tab2);

        mTex1 = (TextView) findViewById(R.id.top_tab1);
        mTex2 = (TextView) findViewById(R.id.top_tab2);
        mTex3 = (TextView) findViewById(R.id.top_tab3);
        mTex4 = (TextView) findViewById(R.id.top_tab4);
        mTex5 = (TextView) findViewById(R.id.top_tab5);

        editText=(EditText)findViewById(R.id.search_edit1);
        searchBut=findViewById(R.id.search_but1);
        titleTex = (TextView) findViewById(R.id.title_text);
    }

    @Override
    public void onClick(View v) {
        setTab(v.getId());
    }

    private void setTab(int i){
        resetTab();

        switch (i) {

            case R.id.top_tab2:
                selectTab(1);
                break;



        }
    }

    private void selectTab(int i) {
        switch (i) {

            case 1:
                mLir1.setVisibility(View.VISIBLE);
                mLir2.setVisibility(View.GONE);
                mTex2.setTextColor(Color.parseColor("#FF0000"));
                editText.setHint("股票名称");
                editText.setVisibility(View.VISIBLE);
                searchBut.setVisibility(View.VISIBLE);
                titleTex.setVisibility(View.GONE);
                initbtn_login5();
                break;

        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //设置默认的tab的图标
    private void resetTab() {
        mTex2.setTextColor(Color.parseColor("#000000"));
    }

    public  void clickBack(View view){
        switch (view.getId()){
            case R.id.back_icon:
                finish();
        }
    }



    private void initbtn_login5() {
        searchBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(123);
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=平安";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=";
                //请求传入的参数
                String urlAdd= editText.getText().toString();
                RequestBody requestBody = new FormBody.Builder().build();
                url+=urlAdd;


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
                        String strByJson = response.body().string();
                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();
                        ArrayList<Stock> stockBeanList = new ArrayList<Stock>();

                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray
                        for (JsonElement stock : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            Stock stockBean = gson.fromJson(stock, Stock.class);
                            stockBeanList.add(stockBean);

                            System.out.println("这下面是 股票的代码、名字、板块集、股价、热度");
                            System.out.println(stockBean.getId());
                            System.out.println(stockBean.getName());
                            System.out.println(stockBean.getType());
                            System.out.println(stockBean.getPrice());
                            System.out.println(stockBean.getHits());
                            System.out.println("这上面是 股票的代码、名字、板块集、股价、热度");
                        }
                        System.out.println(stockBeanList);
                        stockList1 =stockBeanList;




//                        if(stockList!=null)
//                        stockList.addAll(stockBeanList);
//                        else {
//                            stockList=stockBeanList;
//                        }
//
//
//                        System.out.println(111111);
                        System.out.println(stockList1);
                        Looper.prepare();
                        //addStockFragment.BB.performClick();
                        Toast.makeText(addStockActivity.this, strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                addStockFragment.update(stockList1);



            }
        });
    }
}