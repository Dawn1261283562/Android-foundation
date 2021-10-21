package com.example.studying;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

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

    private TextView titleTex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        initViews();
        initEvents();
        initData();

        int i=getIntent().getIntExtra("i",0);
        mViewPager.setCurrentItem(i);
        resetTab();
        selectTab(i);

    }

    private void initData() {
        mFragments = new ArrayList<>();

        mFragments.add(new SearchFragment1());
        mFragments.add(new SearchFragment2());
        mFragments.add(new SearchFragment3_1());
        mFragments.add(new SearchFragment3_2());

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

        titleTex = (TextView) findViewById(R.id.title_text);
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
                titleTex.setText("搜索页面");
                break;
            case 1:
                mLir1.setVisibility(View.VISIBLE);
                mLir2.setVisibility(View.GONE);
                mTex2.setTextColor(Color.parseColor("#FF0000"));
                titleTex.setText("搜索页面");
                break;
            case 2:
                mLir1.setVisibility(View.GONE);
                mLir2.setVisibility(View.VISIBLE);
                mTex3.setTextColor(Color.parseColor("#FF0000"));
                mTex4.setTextColor(Color.parseColor("#FF0000"));
                titleTex.setText("基金筛选");
                break;
            case 3:
                mLir1.setVisibility(View.GONE);
                mLir2.setVisibility(View.VISIBLE);
                mTex3.setTextColor(Color.parseColor("#FF0000"));
                mTex5.setTextColor(Color.parseColor("#FF0000"));
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
}