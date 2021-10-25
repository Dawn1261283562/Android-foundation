package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

public class addStockFragment extends androidx.fragment.app.Fragment {
    private View mView;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    private ListView listView;
    private FundAdapter fundAdapter;
    private EditText editText;
    private ArrayList<Stock> stockList1=new ArrayList<Stock>();
    private ArrayList<Stock> stockList=new ArrayList<Stock>();
    public Button BB;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.add_stock_fragment, container, false);
        }
        //获取持仓搜索结果
        //fundSearchResult();

        fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        editText=(EditText)mView.findViewById(R.id.search_edit1);
        BB=mView.findViewById(R.id.search_but1);

        listView = (ListView) mView.findViewById(R.id.list_search2);
        listView.setAdapter(fundAdapter);

        //initbtn_login5();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundGeneral fundGeneral =fundGeneralList.get(i);
                System.out.println(122);
                //fundSearchResult();
                //fundAdapter.notifyDataSetChanged();
                //FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
                //fundGeneralList.add(fundGeneral1);
                stockList.add( fundGeneral.getStock());

                Intent intent = new Intent();

                intent.setClass(getActivity(),MainActivity2.class);

                intent.putExtra("stockList",stockList);

                startActivity(intent);

                fundAdapter.notifyDataSetChanged();
            }
        });


//        BB.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                fundAdapter.notifyDataSetChanged();
//            }
//        });


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemCli
//
//        });

        return mView;
    }




    private void fundSearchResult() {

        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
        fundGeneralList.add(fundGeneral1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    public void update(ArrayList<Stock> stockBeanList) {

        System.out.println(123321);
        fundGeneralList.clear();

        int size = stockBeanList.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockBeanList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneral1.setStock(value);
            fundGeneralList.add(fundGeneral1);


        }

        listView.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList));

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

    public void hasSelectedUpdate(ArrayList<Stock> stockList) {
        System.out.println(123321);
        this.stockList=stockList;
        int size = stockList.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockList.get(i);
            System.out.println(value.getName());
        }
        System.out.println(123321);
    }


//    private void initbtn_login5() {
//        BB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(123);
//                String url = "http://localhost:8080/user/lgoin";
//                url = "http://43m486x897.yicp.fun/stock/searchStock?id=平安";
//                url = "http://43m486x897.yicp.fun/stock/searchStock?id=";
//                //请求传入的参数
//                String urlAdd= editText.getText().toString();
//                RequestBody requestBody = new FormBody.Builder().build();
//                url+=urlAdd;
//
//
//                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                        Looper.prepare();
//                        Toast.makeText(getActivity(), "post请求失败", Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                        ResponseBody data = response.body();
//                        if(response.code()==200){
//                            String strByJson = response.body().string();
//                            JsonParser parser = new JsonParser();
//                            //将JSON的String 转成一个JsonArray对象
//                            JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
//
//                            Gson gson = new Gson();
//                            ArrayList<Stock> stockBeanList = new ArrayList<Stock>();
//
//                            System.out.println(strByJson);
//                            //加强for循环遍历JsonArray
//                            for (JsonElement stock : jsonArray) {
//                                //使用GSON，直接转成Bean对象
//                                Stock stockBean = gson.fromJson(stock, Stock.class);
//                                stockBeanList.add(stockBean);
//
//                                System.out.println("这下面是 股票的代码、名字、板块集、股价、热度");
//                                System.out.println(stockBean.getId());
//                                System.out.println(stockBean.getName());
//                                System.out.println(stockBean.getType());
//                                System.out.println(stockBean.getPrice());
//                                System.out.println(stockBean.getHits());
//                                System.out.println("这上面是 股票的代码、名字、板块集、股价、热度");
//                            }
//                            stockList1 =stockBeanList;
//                            fundGeneralList.clear();
//
//                            int size = stockBeanList.size();
//                            for (int i = 0; i < size; i++) {
//                                Stock value = stockBeanList.get(i);
//                                FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getPrice());
//                                fundGeneralList.add(fundGeneral1);
//
//
//                            }
//                        }
//                        else{
//                            Looper.prepare();
//                            Toast.makeText(getActivity(), "无相关信息", Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                        }
//
//
////                        Looper.prepare();
////                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
////                        Looper.loop();
//                    }
//                });
//                fundAdapter.notifyDataSetChanged();
//
//
//
//            }
//        });
//    }
//    @Override
//    public void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//    }
}