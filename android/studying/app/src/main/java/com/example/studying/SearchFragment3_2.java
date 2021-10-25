package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studying.entity.FundHeavy;
import com.example.studying.entity.Stock;
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

public class SearchFragment3_2 extends androidx.fragment.app.Fragment {
    private View mView;

    private FlowLayout flowLayout;
    private ArrayList<String> strList;
    private LayoutInflater layoutInflater;
    private ArrayList<String> typeList;
    private Button addBtn;
    private Button searchBtn;
    private FundAdapter fundAdapter;
    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    public SearchFragment3_2(ArrayList<String> typeList) {
        System.out.println(typeList);
        this.typeList=typeList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment3_2, container, false);
        }

        //获取用户选择的板块
        sectorSelected();

        //获取持仓搜索结果
        fundSearchResult();

        fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        addBtn =(Button)mView.findViewById(R.id.frag3_2_but1);
        searchBtn=(Button)mView.findViewById(R.id.frag3_2_but2);
        ListView listView = (ListView) mView.findViewById(R.id.list_search3_2);
        listView.setAdapter(fundAdapter);

        return mView;
    }

    private void sectorSelected() {
        strList = new ArrayList<>();
        strList.add("今天好冷好冷好冷");
        strList.add("好冷好冷好冷好冷好冷好冷");
        strList.add("好冷好冷好冷");
        strList.add("好冷好冷好冷好冷好冷好冷好冷好冷好冷");
        strList.add("好冷好冷好冷");

        flowLayout = (FlowLayout) mView.findViewById(R.id.flowlayout3_2);
        layoutInflater = LayoutInflater.from(getContext());

        flowLayout.setAdapter(new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater.inflate(R.layout.flow_item3_1,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                ((TextView)view.findViewById(R.id.flow_text3_1)).setText(strList.get(position));
                return view;
            }
        });
    }



    private void fundSearchResult(){
        fundGeneralList.clear();
//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","李四");
//        fundGeneralList.add(fundGeneral1);
//        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行平安银行平安银行平安银行","张三");
//        fundGeneralList.add(fundGeneral2);
        if(typeList==null)typeList=new ArrayList<String>();
        int size = typeList.size();
        for (int i = 0; i < size; i++) {
            String value = typeList.get(i);
            FundGeneral fundGeneral1=new FundGeneral("0",(String) value,"0");
            fundGeneral1.setType(value);
            fundGeneralList.add(fundGeneral1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent();

                intent.setClass(getActivity(), addTypeActivity.class);

                if (typeList == null) typeList = new ArrayList<String>();
                intent.putExtra("typeList", typeList);
//                Stock temp =new Stock();temp.setId("写死的假股票");
//                temp.setName("每次添加");
//                temp.setPrice("测试用例用于");
//                stockList.add(temp);
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockAllTypeRadio?num=2&TypeList=军工,计算机";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockAllTypeRadio?num=";
                String urlNext ="&TypeList=";
                int num=typeList.size();
                url=url+num+urlNext;
                for(int i=0;i<num;i++){
                    String id=typeList.get(i).toString();
                    if(i!=num-1){
                        url=url+id+',';
                    }
                    else{
                        url=url+id;
                    }
                }


                //请求传入的参数
                RequestBody requestBody = new FormBody.Builder().build();
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

                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }


        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}