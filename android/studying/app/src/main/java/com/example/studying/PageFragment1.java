package com.example.studying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PageFragment1 extends androidx.fragment.app.Fragment {

    private View mView;
    private LinearLayout searTab1;
    private LinearLayout searTab2;
    private LinearLayout searTab3;
    private ListView listView1;
    private List<FundGeneral> fundGeneralList;
    private FundAdapter fundAdapter;
    private ArrayList<Stock> stockListNormal;

    SwipeRefreshLayout swipeRefreshLayout;
    private final int fundShowDefault=6;
    private Button moreFundBut;
    RecyclerView recyclerView;

    Handler mHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment1, container, false);
        }
        initView();
        initEvents();
        initDate();


        return mView;
    }

    private void initDate() {

        mHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        update(stockListNormal);

                        int totalHeight = 0;
                        for (int i = 0; i < fundShowDefault; i++) {
                            View listItem = fundAdapter.getView(i, null, listView1);
                            listItem.measure(0, 0);
                            totalHeight += listItem.getMeasuredHeight();
                        }
                        ViewGroup.LayoutParams params = listView1.getLayoutParams();
                        params.height = totalHeight + (listView1.getDividerHeight() * (fundShowDefault - 1));
//                        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
                        listView1.setLayoutParams(params);
                        moreFundBut.setText("展开更多热点股票");
                        swipeRefreshLayout.setRefreshing(false);
                    case 2:

                }
            }
        };
        fundGeneralList=new ArrayList<>();
        fundAdapter =new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
        listView1.setAdapter(fundAdapter);
        if(fundGeneralList.size()==0){
            listView1.setVisibility(View.GONE);
            moreFundBut.setVisibility(View.GONE);
        }
        else{
            listView1.setVisibility(View.VISIBLE);
            moreFundBut.setVisibility(View.VISIBLE);
        }
        requestForHot();
    }

    private void initEvents() {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundGeneral fundGeneral=fundGeneralList.get(i);
                Toast.makeText(getContext(),fundGeneral.getFund2().toString(),Toast.LENGTH_SHORT).show();

                //Intent intent=new Intent(getActivity(),MainActivity2.class);
                String regex = "\\d{6}.[a-zA-Z][a-zA-Z]";
                String id=fundGeneral.getStock().getId().toString();
                if(id.matches(regex)){
                    //System.out.println(1233);
                    String temp1=id.substring(0,6);String temp2=id.substring(7,9);
                    //System.out.println(temp2+temp1);
                    String id_restructure=temp2+temp1;
                    id=id_restructure;
                    //System.out.println(id);
                    Intent intent=new Intent(getActivity(),Stockinfo.class);
                    intent.putExtra("stockGet", fundGeneral.getStock());
                    startActivity(intent);
                }
/*
                if(fundGeneralList.get(i).getSelectFund()){
                    fundGeneralList.get(i).setSelectFund(false);
                }
                else{
                    fundGeneralList.get(i).setSelectFund(true);
                }*/
                fundAdapter.notifyDataSetChanged();
            }
        });
        moreFundBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moreFundBut.getText().toString().equals("展开更多热点股票")){
                    int totalHeight = 0;
                    for (int i = 0; i < fundAdapter.getCount(); i++) {
                        View listItem = fundAdapter.getView(i, null, listView1);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight();
                    }
                    ViewGroup.LayoutParams params = listView1.getLayoutParams();
                    params.height = totalHeight + (listView1.getDividerHeight() * (fundAdapter.getCount() - 1));
//                        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
                    listView1.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList));
                    listView1.setLayoutParams(params);

                    moreFundBut.setText("收起");
                }
                else{
                    int totalHeight = 0;
                    for (int i = 0; i < fundShowDefault; i++) {
                        View listItem = fundAdapter.getView(i, null, listView1);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight();
                    }
                    ViewGroup.LayoutParams params = listView1.getLayoutParams();
                    params.height = totalHeight + (listView1.getDividerHeight() * (fundShowDefault - 1));
//                        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
                    listView1.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList.subList(0,fundShowDefault)));
                    listView1.setLayoutParams(params);

                    moreFundBut.setText("展开更多热点股票");
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestForHot();
            }
        });
    }

    private void initView() {
        listView1 = (ListView) mView.findViewById(R.id.page_fragment1_list1);

        searTab1 = (LinearLayout)mView.findViewById(R.id.sear_tab1);
        searTab2 = (LinearLayout)mView.findViewById(R.id.sear_tab2);
        searTab3 = (LinearLayout)mView.findViewById(R.id.sear_tab3);
        moreFundBut=(Button) mView.findViewById(R.id.page_fragment1_button1);
        swipeRefreshLayout=(SwipeRefreshLayout) mView.findViewById(R.id.pagefrag1_swipe_refresh);
        recyclerView=mView.findViewById(R.id.pagefrag1_recycleview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        List<String> newsTitleList=new ArrayList<>();
        newsTitleList.add("比亚迪：拟对子公司增资17亿美元或等值");
        newsTitleList.add("央行：目前房地产市场风险总体可控 健康发展的整体态势不会改变");
        newsTitleList.add("央行发布2021年第三季度中国货币政策执行报告");
        newsTitleList.add("多股本月涨幅已翻番！汽配板块持续飙涨 下周又有零部件新股要上市");
        newsTitleList.add("13城公布第三批集中供地细则 为防流拍多地降低房企资金门槛");

        newsAdapter newsAdapter=new newsAdapter(newsTitleList);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity2.class);
                //Intent intent=new Intent(getActivity(),MainActivity2.class);
                intent.putExtra("i",0);
                startActivity(intent);
            }
        });
        searTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity2.class);
//                Intent intent=new Intent(getActivity(),MainActivity2.class);
//                intent.putExtra("i",1);
                startActivity(intent);
            }
        });
        searTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity2.class);
                intent.putExtra("i",2);
                startActivity(intent);
            }
        });
    }
    private void requestForHot(){
        String url = "http://localhost:8080/user/lgoin";
        url = "http://43m486x897.yicp.fun/stock/searchStock?id=平安";
        url = "http://43m486x897.yicp.fun/stock/getStockListByHot?wantedNum=30";

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

                        System.out.println(stockBean.getPrice());

                        System.out.println("这上面是 股票的代码、名字、板块集、股价、热度");
                    }

                    stockListNormal=stockBeanList;
                    Message message = new Message();

                    message.what = 1;
                    mHandler.sendMessage(message);
                    Looper.prepare();

                    //Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                else{
                    Looper.prepare();
                    Toast.makeText(getActivity(), "无相关信息", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    public void update(ArrayList<Stock> stockList) {

        System.out.println(123321);
        fundGeneralList.clear();

        int size = stockList.size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            Stock value = stockList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String)value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneral1.setStock(value);
            fundGeneralList.add(fundGeneral1);


        }

        listView1.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList.subList(0,fundShowDefault)));

        if(fundGeneralList.size()==0){
            listView1.setVisibility(View.GONE);
            moreFundBut.setVisibility(View.GONE);
        }
        else{
            listView1.setVisibility(View.VISIBLE);
            moreFundBut.setVisibility(View.VISIBLE);
        }
        //Toast.makeText(getActivity(), "gengaile", Toast.LENGTH_SHORT).show();
//        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
//
//        listView = (ListView) mView.findViewById(R.id.list_search2);
//        listView.setAdapter(fundAdapter);

        //BB.performClick();
        System.out.println(fundGeneralList.size());
        fundAdapter.notifyDataSetChanged();
//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral1);


    }
}