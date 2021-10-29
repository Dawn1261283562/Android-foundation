package com.example.studying;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SearchFragment3_1 extends androidx.fragment.app.Fragment {
    private View mView;

    private FundAdapter fundAdapter;
    private FlowLayout flowLayout;
    private ArrayList<String> strList;
    private LayoutInflater layoutInflater;
    private Button addBtn;
    private Button searchBtn;
    private Button prorateBtn;
    private ImageButton checkProBtn;
    private ArrayList<Stock> stockList=new ArrayList<Stock>();
    private List<FundGeneral> fundGeneralList=new ArrayList<>();
    private ArrayList<FundHeavy> fundHeavyList=new ArrayList<FundHeavy>();

    private ListView listView;
    private FlowLayout.Adapter flowAdapter;

    public SearchFragment3_1(ArrayList<Stock> stockList) {
        System.out.println(stockList);
        this.stockList=stockList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment3_1, container, false);
        }

        initViews();
        initEvents();
        initData();

        //获取持仓搜索结果
        fundSearchResult();

        return mView;
    }

    private void initData() {
        fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
        listView.setAdapter(fundAdapter);
        strList = new ArrayList<>();
        flowAdapter=new FlowLayout.Adapter() {
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
                TextView textView= (TextView)view.findViewById(R.id.flow_text3_1);
                textView.setText(strList.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Drawable drawable=textView.getCompoundDrawables()[2];
                        if ((event.getX() > textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() < textView.getWidth()-textView.getPaddingRight())){
                            strList.remove(position);
                            stockList.remove(position);
                            flowLayout.setAdapter(flowAdapter);
                        }
                        return false;
                    }
                });
                return view;
            }
        };
        flowLayout.setAdapter(flowAdapter);
    }

    private void initEvents() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("stockList3_1",stockList);
                intent.putExtras(bundle);
                intent.setClass(getActivity(),addStockActivity.class);

                if(stockList==null)stockList=new ArrayList<Stock>();
//                Stock temp =new Stock();temp.setId("写死的假股票");
//                temp.setName("每次添加");
//                temp.setPrice("测试用例用于");
//                stockList.add(temp);
                startActivityForResult(intent,31);

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "http://localhost:8080/user/lgoin";
                //url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockList?num=4&stockIdList=000001.SZ,000858.SZ,002475.SZ,002050.SZ";
                url = "http://43m486x897.yicp.fun/fundHeavy/getListByStockList?num=";
                String urlNext="&stockIdList=";
                int num=stockList.size();
                url=url+num+urlNext;
                for(int i=0;i<num;i++){
                    String id=stockList.get(i).getId().toString();
                    if(i!=num-1){
                        url=url+id+',';
                    }
                    else{
                        url=url+id;
                    }
                }

                //请求传入的参数
//                Vector<Thread>threadVector=new Vector<Thread>();
//                Thread childThread =new Thread(new Runnable()){
//
//                }

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
                        fundHeavyList=userBeanList;
                        Looper.prepare();

                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1450);
                            hasSelectedUpdate();
                            // Do some stuff
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }});
                closeActivity.run();
                //fundSearchResult();

            }
        });
        prorateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkProBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox2));
                /*
                这里不知道干嘛stockList是空的，null
                */
                /*if(null!=stockList){
                    for(int a=0;a<stockList.size();a++){
                        System.out.println(stockList.get(a).getName().toString());
                    }
                }*/

//                Stock stock=new Stock();
//                stock.setExpectRadio(1);
//                stock.setName("平安银行");
//                stock.setHits(23);
//                stock.setId("000001.sz");
//                stock.setPrice("343");
//                stock.setType("w2e");
//                stockList.add(stock);

                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("stockListToProrate",stockList);
                intent.putExtras(bundle);
                intent.setClass(getActivity(),ProrateActivity.class);
                startActivityForResult(intent,32);
            }
        });
        checkProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkProBtn.getBackground().getConstantState().equals(getResources().getDrawable(R.mipmap.checkbox1).getConstantState())){
                    checkProBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox2));
                }
                else{
                    checkProBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox1));
                }
            }
        });
    }


    private void hasSelectedUpdate() {
    /*FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
        fundGeneralList.add(fundGeneral1);*/

        System.out.println(123321);
        fundGeneralList.clear();
        //        FundGeneral fundGeneral=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral);
        int size = fundHeavyList.size();
        for (int i = 0; i < size; i++) {
            FundHeavy value = fundHeavyList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getScore());
            fundGeneral1.setFundHeavy(value);
            fundGeneralList.add(fundGeneral1);
        }

//        listView.setAdapter(new FundAdapter(addStockActivity.this,R.layout.fund_item,fundGeneralList));

        //Toast.makeText(getActivity(), "gengaile", Toast.LENGTH_SHORT).show();
//        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
//
//        listView = (ListView) mView.findViewById(R.id.list_search2);
//        listView.setAdapter(fundAdapter);

        //BB.performClick();
        System.out.println(fundGeneralList.size());
        fundAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        addBtn =mView.findViewById(R.id.frag3_1_but1);
        searchBtn=mView.findViewById(R.id.frag3_1_but2);
        prorateBtn=mView.findViewById(R.id.prorate_but);
        prorateBtn=mView.findViewById(R.id.prorate_but);
        checkProBtn=mView.findViewById(R.id.check_prorate);
        listView =  mView.findViewById(R.id.list_search3_1);
        flowLayout = mView.findViewById(R.id.flowlayout3_1);
        layoutInflater = LayoutInflater.from(getContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 31:
                if(resultCode==Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    stockList=(ArrayList<Stock>) bundle.getSerializable("stockListAdd");

                    stockList =stockListDuplicatesRemove(stockList);

                    //获取用户选择的股票
                    stockSlected();
                }
                break;
            case 32:
                if(resultCode==Activity.RESULT_OK){
                    Bundle bundle=data.getExtras();
                    stockList=(ArrayList<Stock>) bundle.getSerializable("stockListProrated");

                    stockList =stockListDuplicatesRemove(stockList);
                    for(int a=0;a<stockList.size();a++){
                        System.out.println(String.valueOf(stockList.get(a).getExpectRadio()));
                    }
                    //获取用户选择的股票
                    stockSlected();
                }
            default:
        }
    }

    private ArrayList<Stock> stockListDuplicatesRemove(ArrayList<Stock> stockList){
        HashMap<String,Stock> stockMap=new HashMap<>() ;

        for(int k=stockList.size()-1;k>=0;k--){

            stockMap.put(stockList.get(k).getName().toString(),stockList.get(k)) ;
        }
        ArrayList<Stock> stockListNew=new ArrayList<Stock>();

        for (Stock value : stockMap.values()) {
            stockListNew.add(value);
        }


        stockList=stockListNew;
        return stockList;
    }

    private void stockSlected() {
        strList = new ArrayList<>();
        for(Stock stock:stockList){
            strList.add(stock.getName().toString());
        }
        /*strList.add("阿里巴巴");
        strList.add("阿巴阿巴");
        strList.add("阿巴巴巴巴");
        strList.add("阿里巴巴阿里巴巴阿里巴巴阿里巴巴阿里巴巴阿里巴巴");
        strList.add("阿巴阿巴阿巴");
        strList.add("阿巴阿");
        strList.add("阿巴阿巴");
        strList.add("阿巴阿巴");
        strList.add("阿巴阿巴");
        strList.add("巴阿巴");
        strList.add("阿巴阿");*/

        flowLayout.setAdapter(flowAdapter);
    }

    private void fundSearchResult(){
        fundGeneralList.clear();
//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","李四");
//        fundGeneralList.add(fundGeneral1);
//        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行平安银行平安银行平安银行","张三");
//        fundGeneralList.add(fundGeneral2);
        if(stockList==null)stockList=new ArrayList<Stock>();
        int size = stockList.size();
        System.out.println(12);
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            Stock value = stockList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneral1.setStock(value);
            fundGeneralList.add(fundGeneral1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    void updateStockList(ArrayList<Stock> stockList, Activity Avt){
        fundGeneralList.clear();
        System.out.println(77);
        int size = stockList.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneral1.setStock(value);
            fundGeneralList.add(fundGeneral1);
        }
        //ListView listView = (ListView) mView.findViewById(R.id.list_search3_1);
        //listView.setAdapter(fundAdapter);
        //listView.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList));

        listView.setAdapter(new FundAdapter(Avt,R.layout.fund_item,fundGeneralList));
        //listView.setAdapter(new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList));
        //listView.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList));
    }
}