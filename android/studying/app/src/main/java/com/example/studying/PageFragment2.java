package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studying.Data;
import com.example.studying.entity.FundHeavy;
import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;
import com.example.studying.entity.User;
import com.example.studying.utils.HttpGetRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PageFragment2 extends androidx.fragment.app.Fragment {
    private View mView;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    FundAdapter2 fundAdapter2;

    private String username;
    private ListView listView;

    private ArrayList<FundHeavy> fundHeavyList=new ArrayList<FundHeavy>();


    Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment2, container, false);
        }
        //获取到用户名
        Data data = (Data)getActivity().getApplicationContext();
        username=data.getUsername();
        //获取到用户选择的基金

        fundAdapter2=new FundAdapter2(getContext(),R.layout.fund_item2,fundGeneralList);

        listView = (ListView) mView.findViewById(R.id.list_fund_selected);
        listView.setAdapter(fundAdapter2);

        mHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:

                        update(fundHeavyList);
                    case 2:

                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FundGeneral fundGeneral = fundGeneralList.get(i);
                Toast.makeText(getContext(), fundGeneral.getFund2().toString(), Toast.LENGTH_SHORT).show();
                //Intent intent=new Intent(getActivity(),MainActivity2.class);

                String id=fundGeneral.getFund1().toString();

                String temp1=id.substring(0,6);String temp2=id.substring(7,9);
                //System.out.println(temp2+temp1);
                // String id_restructure=temp2+temp1;
                String id_restructure=temp1;
                id=id_restructure;
                //System.out.println(id);
                FundHeavyInfo temp=new FundHeavyInfo();
                temp.setId(id);
                temp.setName(fundGeneral.getFund2().toString());
                Intent intent=new Intent(getActivity(),fundsinfo.class);
                intent.putExtra("fundsGet", temp);
                startActivity(intent);




            }
        });




        initData();

        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        fundSlected();
    }

    //Request 请求代码
    private void initData() {
        Data data = (Data)getActivity().getApplication();
        username=data.getUsername();

    }


    public void fundSlected() {
        Data data = (Data)getActivity().getApplicationContext();
        username=data.getUsername();

        if(username==null){
            username="NULL";
            return ;
        }


        String url = "http://localhost:8080/user/lgoin";
        url = "http://43m486x897.yicp.fun/stock/searchStock?id=平安";
        url = "http://43m486x897.yicp.fun/stock/getStockListByHot?wantedNum=30";
        url = "http://43m486x897.yicp.fun/collection/getListByUser?username="+username;


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
                ArrayList<FundHeavy> userBeanList = new ArrayList<>();
                System.out.println(strByJson);
                //加强for循环遍历JsonArray
                for (JsonElement fundHeavy : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    FundHeavy userBean = gson.fromJson(fundHeavy, FundHeavy.class);
                    userBeanList.add(userBean);

                    System.out.println("这下面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度（一共十个优质基金）");
                    System.out.println(userBean.id);
                    System.out.println(userBean.name);
                    System.out.println(userBean.getScore());
                    System.out.println(Arrays.toString(userBean._stock_id));
                    System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
                    System.out.println(Arrays.toString(userBean._stock_ratio));
                    System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
                    System.out.println(userBean.hits);
                    System.out.println("这上面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度");
                }
                fundHeavyList=userBeanList;
                Looper.prepare();

                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);

                System.out.println(data);
                //Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    public void update(ArrayList<FundHeavy> fundInfoList) {

        System.out.println(123321);
        fundGeneralList.clear();

        int size = fundInfoList.size();
        for (int i = 0; i < size; i++) {
            FundHeavy value = fundInfoList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String)value.getId(),(String) value.getName(),i+"");
            fundGeneral1.setFundHeavy(value);
            fundGeneralList.add(fundGeneral1);


        }

        listView.setAdapter(new FundAdapter2(getActivity(),R.layout.fund_item2,fundGeneralList));

        //Toast.makeText(getActivity(), "gengaile", Toast.LENGTH_SHORT).show();
//        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
//
//        listView = (ListView) mView.findViewById(R.id.list_search2);
//        listView.setAdapter(fundAdapter);

        //BB.performClick();
        System.out.println(fundGeneralList.size());
//        fundAdapter.notifyDataSetChanged();
//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral1);


    }

}
