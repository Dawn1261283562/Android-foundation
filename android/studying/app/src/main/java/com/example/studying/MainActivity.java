package com.example.studying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
//        initView();
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

    //Request 请求代码
/*    private void initView() {
        //绑定控件
        et_username = findViewById(R.id.edit_username);
        et_password = findViewById(R.id.edit_pwd);
        btn_login = findViewById(R.id.btn_login);

        //为登录按钮设置点击事件
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


                url = "http://localhost:8080/stock/hellos?id=";
                url = "http://43m486x897.yicp.fun/fundHeavy/hellos?id=";
                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", et_username.getText().toString())
                        .add("password", et_password.getText().toString())
                        .build();
                url=url+et_username.getText().toString();
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
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
//                        //将JSON的String 转成一个JsonArray对象
//                        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
//
//                        Gson gson = new Gson();
//                        ArrayList<User> userBeanList = new ArrayList<>();
//
//                        //加强for循环遍历JsonArray
//                        for (JsonElement user : jsonArray) {
//                            //使用GSON，直接转成Bean对象
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
                        System.out.println(data);
                        Toast.makeText(MainActivity.this, strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }
        });
    }*/

}
