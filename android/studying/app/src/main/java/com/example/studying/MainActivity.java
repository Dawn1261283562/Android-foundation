package com.example.studying;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //3个Tab对应的布局
    private LinearLayout mTab1;
    private LinearLayout mTab2;
    private LinearLayout mTab3;

    //3个Tab对应的ImageButton
    private ImageButton mImg1;
    private ImageButton mImg2;
    private ImageButton mImg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initEvents();//初始化事件
        initData();//初始化数据
    }

    private void initData() {
        mFragments = new ArrayList<>();
        //将3个Fragment加入集合中
        mFragments.add(new PageFragment1());
        mFragments.add(new PageFragment2());
        mFragments.add(new PageFragment3());

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
                resetImg();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置3个Tab的点击事件
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
    }

    @Override
    public void onClick(View v) {
        //调用resetImg设置默认图标
        resetImg();

        //根据点击的Tab切换不同的页面，调用selectTab点亮相应图标
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
        //点亮当前点击的tab的图标
        switch (i) {
            case 0:
                mImg1.setImageResource(R.mipmap.icon1_2);
                break;
            case 1:
                mImg2.setImageResource(R.mipmap.icon1_2);
                break;
            case 2:
                mImg3.setImageResource(R.mipmap.icon1_2);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //设置默认的tab的图标
    private void resetImg() {
        mImg1.setImageResource(R.mipmap.icon1_1);
        mImg2.setImageResource(R.mipmap.icon1_1);
        mImg3.setImageResource(R.mipmap.icon1_1);
    }
}
