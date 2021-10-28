package com.example.studying;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.studying.entity.FundHeavy;
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

public class MainActivity2 extends FragmentActivity implements View.OnClickListener {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

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


    private ArrayList<FundHeavyInfo> fundInfoList=new ArrayList<FundHeavyInfo>();


    SearchFragment1 searchFragment1;
    SearchFragment2 searchFragment2;
    SearchFragment3_1 searchFragment3_1;
    SearchFragment3_2 searchFragment3_2;
    SearchFragment3_10 searchFragment3_10;

    private    ArrayList<FundHeavyInfo> temp;
    private ArrayList<Stock> stockList=new ArrayList<Stock>();
    private ArrayList<String> typeList=new ArrayList<String>();
    private ArrayList<Stock> stockListNormal=new ArrayList<Stock>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        initViews();
        initEvents();



        int i=getIntent().getIntExtra("i",0);
        Intent intent = this.getIntent();
        stockList = (ArrayList<Stock>) intent.getSerializableExtra("stockList");
        typeList = (ArrayList<String>) intent.getSerializableExtra("typeList");
        initData();

        mViewPager.setCurrentItem(i);


        resetTab();
        selectTab(i);


        if(stockList!=null){

            System.out.println(11111111);
            System.out.println(stockList.get(0).getName());
            selectTab(2);
        }
        else if(typeList!=null){

            System.out.println(typeList.get(0));
            selectTab(3);
        }
        else return;
        //System.out.println(stockList.get(1).getName());
    }

    private void initData() {
        searchFragment1=new SearchFragment1();
        searchFragment2=new SearchFragment2();
//        searchFragment3_1=new SearchFragment3_1(stockList);
        searchFragment3_1=new SearchFragment3_1(stockList);
        searchFragment3_2=new SearchFragment3_2(typeList);
        searchFragment3_10=new SearchFragment3_10();

        mFragments = new ArrayList<>();

        mFragments.add(searchFragment1);
        mFragments.add(searchFragment2);
        mFragments.add(searchFragment3_1);
        mFragments.add(searchFragment3_2);
        mFragments.add(searchFragment3_10);

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
        mViewPager.setOffscreenPageLimit(5);
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

        editText.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                }
                return false;
            }
        });
        /*editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH)
            }
        });*/
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

    public ArrayList<FundHeavyInfo> getTemp(){
        return temp;
    }

    @Override
    public void onClick(View v) {
        setTab(v.getId());
    }

    private void setTab(int i){
        resetTab();

        switch (i) {
            case R.id.top_tab1:
                selectTab(0);
                break;
            case R.id.top_tab2:
                selectTab(1);
                break;
            case R.id.top_tab3:
            case R.id.top_tab4:
                selectTab(2);
                break;
            case R.id.top_tab5:
                selectTab(3);
                break;
        }
    }

    private void selectTab(int i) {
        switch (i) {
            case 0:
                mLir1.setVisibility(View.VISIBLE);
                mLir2.setVisibility(View.GONE);
                mTex1.setTextColor(Color.parseColor("#FF0000"));
                editText.setHint("基金名称");
                editText.setVisibility(View.VISIBLE);
                searchBut.setVisibility(View.VISIBLE);
                titleTex.setVisibility(View.GONE);
                initbtn_login4();
                break;
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
            case 2:
                mLir1.setVisibility(View.GONE);
                mLir2.setVisibility(View.VISIBLE);
                mTex3.setTextColor(Color.parseColor("#FF0000"));
                mTex4.setTextColor(Color.parseColor("#FF0000"));
                titleTex.setVisibility(View.VISIBLE);
                editText.setVisibility(View.INVISIBLE);
                searchBut.setVisibility(View.INVISIBLE);
                titleTex.setText("基金筛选");
                break;
            case 3:
                mLir1.setVisibility(View.GONE);
                mLir2.setVisibility(View.VISIBLE);
                mTex3.setTextColor(Color.parseColor("#FF0000"));
                mTex5.setTextColor(Color.parseColor("#FF0000"));
                titleTex.setVisibility(View.VISIBLE);
                editText.setVisibility(View.INVISIBLE);
                searchBut.setVisibility(View.INVISIBLE);
                titleTex.setText("基金筛选");
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //设置默认的tab的图标
    private void resetTab() {
        mTex1.setTextColor(Color.parseColor("#000000"));
        mTex2.setTextColor(Color.parseColor("#000000"));
        mTex3.setTextColor(Color.parseColor("#000000"));
        mTex4.setTextColor(Color.parseColor("#000000"));
        mTex5.setTextColor(Color.parseColor("#000000"));
    }

    public  void clickBack(View view){
        switch (view.getId()){
            case R.id.back_icon:
                finish();
        }
    }

    private void initbtn_login4() {
        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByGeneralSearch?id=000013";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByGeneralSearch?id=";
                //请求传入的参数
                String urlAdd= editText.getText().toString().trim();
                System.out.println(urlAdd);
                searchFragment1.saveSearchHistory1(urlAdd);
                searchFragment1.getsearchHistory1();

                RequestBody requestBody = new FormBody.Builder().build();
                url=url+urlAdd;
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
                        //if(response.body().string()==null)return;
                        String strByJson = response.body().string();

                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                        Gson gson = new Gson();

                        ArrayList<FundHeavyInfo> fundHeavyInfoList = new ArrayList<>();

                        System.out.println(strByJson);
                        //加强for循环遍历JsonArray

                        for (JsonElement fundHeavyInfo : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            FundHeavyInfo fundHeavyInfoBean = gson.fromJson(fundHeavyInfo, FundHeavyInfo.class);
                            fundHeavyInfoList.add(fundHeavyInfoBean);
                            if(fundHeavyInfoBean==null){
                                Looper.prepare();
                                //System.out.println(data);
                                Toast.makeText(MainActivity2.this, "暂无相关信息", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                            System.out.println("这下面是 基金信息的代码、名字、全名、法人公司名、管理者");
                            System.out.println(fundHeavyInfoBean.getId());
                            System.out.println(fundHeavyInfoBean.getName());
                            System.out.println(fundHeavyInfoBean.getFull_name());
                            System.out.println(fundHeavyInfoBean.getLegal_person());
                            System.out.println(fundHeavyInfoBean.getManager());
                            System.out.println("这上面是 基金信息的代码、名字、全名、法人公司名、管理者");
                        }
                        //
         /*       ArrayList<FundHeavyInfo> fundHeavyInfoList = new ArrayList<>();
                FundHeavyInfo fundHeavyInfoBean=new FundHeavyInfo();
                fundHeavyInfoBean.setLegal_person("aaa");
                fundHeavyInfoBean.setName("bbb");
                fundHeavyInfoBean.setManager("ccc");
                fundHeavyInfoBean.setId("ddd");
                fundHeavyInfoBean.setFull_nameame("eee");
                fundHeavyInfoList.add(fundHeavyInfoBean);*/
                        temp=fundHeavyInfoList;
                        fundInfoList =fundHeavyInfoList;
                        //System.out.println(stockList1);


//                Toast.makeText(MainActivity2.this, fundHeavyInfoList.get(0).getName(), Toast.LENGTH_SHORT).show();

                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(MainActivity2.this, strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            searchFragment1.fundSearchResult();
                            searchFragment1.update(fundInfoList);
                            // Do some stuff
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }});
                closeActivity.run();

            }
        });
    }
    private void initbtn_login5() {
        searchBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=平安";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=";
                //请求传入的参数
                String urlAdd= editText.getText().toString();
                RequestBody requestBody = new FormBody.Builder().build();
                url+=urlAdd;
                searchFragment2.saveSearchHistory2(urlAdd);
                searchFragment2.getsearchHistory2();

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
                        Looper.prepare();
                        stockListNormal=stockBeanList;
                        Toast.makeText(MainActivity2.this, strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            //searchFragment2.fundSearchResult();
                            searchFragment2.update(stockListNormal);
                            // Do some stuff
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }});
                closeActivity.run();
            }
        });
    }
}