package com.example.studying;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
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

public class SearchFragment3_2 extends androidx.fragment.app.Fragment {
    private View mView;

    private FlowLayout flowLayout;
    private LayoutInflater layoutInflater;
    private ArrayList<String> typeList;
    private Button addBtn;
    private Button searchBtn;
    private Button addCompanyBtn;
    private  TextView textCompany;
    private ImageButton checkComBtn;
    private FundAdapter fundAdapter;
    private ListView listView;
    private List<FundGeneral> fundGeneralList;
    private FlowLayout.Adapter flowAdapter;
    private ArrayList<FundHeavy> fundHeavyList=new ArrayList<FundHeavy>();
    private String companySelected;

    public SearchFragment3_2(ArrayList<String> typeList) {
        System.out.println(typeList);
        this.typeList=typeList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment3_2, container, false);
        }
        initViews();
        initEvents();
        initData();

        fundSearchResult();//获取持仓搜索结果
        return mView;
    }

    private void initData() {
        fundGeneralList=new ArrayList<>();
        fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
        listView.setAdapter(fundAdapter);
        typeList = new ArrayList<>();
        companySelected="";
        textCompany.setVisibility(View.GONE);

        layoutInflater = LayoutInflater.from(getContext());
        flowAdapter=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return typeList.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater.inflate(R.layout.flow_item3_1,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text3_1);
                textView.setText(typeList.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Drawable drawable=textView.getCompoundDrawables()[2];
                        if ((event.getX() > textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() < textView.getWidth()-textView.getPaddingRight())){
                            typeList.remove(position);
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
        addCompanyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(checkComBtn.getBackground().getConstantState().equals(getResources().getDrawable(R.mipmap.checkbox1).getConstantState())){
                    checkComBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox2));
                    textCompany.setVisibility(View.VISIBLE);
                    textCompany.setText("当前搜索公司："+companySelected);
                }

                Intent intent=new Intent(getActivity(),AddCompanyActivity.class);
                startActivityForResult(intent,34);
            }
        });
        checkComBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox1));
        checkComBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkComBtn.getBackground().getConstantState().equals(getResources().getDrawable(R.mipmap.checkbox1).getConstantState())){
                    checkComBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox2));
                    textCompany.setVisibility(View.VISIBLE);
                    textCompany.setText("当前搜索公司："+companySelected);

                }
                else{
                    checkComBtn.setBackground(getResources().getDrawable(R.mipmap.checkbox1));
                    textCompany.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initViews() {
        addBtn =(Button)mView.findViewById(R.id.frag3_2_but1);
        searchBtn=(Button)mView.findViewById(R.id.frag3_2_but2);
        checkComBtn=mView.findViewById(R.id.check_company);
        addCompanyBtn=mView.findViewById(R.id.add_compony_but);
        textCompany=mView.findViewById(R.id.text_company_name);
        listView= (ListView) mView.findViewById(R.id.list_search3_2);
        flowLayout = (FlowLayout) mView.findViewById(R.id.flowlayout3_2);
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
                Bundle bundle=new Bundle();
                bundle.putSerializable("typeList",typeList);
                intent.putExtras(bundle);
                intent.setClass(getActivity(),addTypeActivity.class);

                //if(stockList==null)stockList=new ArrayList<Stock>();

                if (typeList == null) typeList = new ArrayList<String>();
                //intent.putExtra("typeList", typeList);
//                Stock temp =new Stock();temp.setId("写死的假股票");
//                temp.setName("每次添加");
//                temp.setPrice("测试用例用于");
//                stockList.add(temp);
                //startActivity(intent);
                startActivityForResult(intent,33);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int checkboxFlag=1;

                if(checkComBtn.getBackground().getConstantState().equals(getResources().getDrawable(R.mipmap.checkbox1).getConstantState())){
                    //普通搜索，不进行公司的板块搜索
                    checkboxFlag=1;
                }
                else{
                    if(companySelected.equals("")){
                        Toast.makeText(getActivity(),"您还没有选择公司",Toast.LENGTH_LONG).show();
                    }
                    else{
                        //进行公司板块搜索
                        checkboxFlag=2;
                    }
                }
                String url = "http://localhost:8080/user/lgoin";
                if(checkboxFlag==1){
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
                }
                else{
                    url = "http://43m486x897.yicp.fun/fundHeavy/getListByCompanyAndTypeRadio?num=2&TypeList=军工,计算机";
                    url = "http://43m486x897.yicp.fun/fundHeavy/getListByCompanyAndTypeRadio?num=";
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
                    String urlNextNext="&company="+companySelected;
                    url=url+num+urlNextNext;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        switch (requestCode){
            case 33:
                if(resultCode== Activity.RESULT_OK){
                    Bundle bundle=intent.getExtras();
                    typeList=(ArrayList<String>) bundle.getSerializable("typeListAdd");

                    typeList =typeListDuplicatesRemove(typeList);

                    flowLayout.setAdapter(flowAdapter);
                }
                break;
            case 34:
                if(resultCode==Activity.RESULT_OK){
                    String temp= intent.getExtras().getString("companySelected");
                    if(!temp.equals("")) {
                        companySelected = temp;
                        textCompany.setText("当前搜索公司："+companySelected);
                    }
                }
                break;
            default:
        }
    }

    private ArrayList<String> typeListDuplicatesRemove(ArrayList<String> typeList){
        HashMap<String,String> stockMap=new HashMap<>() ;

        for(int k=typeList.size()-1;k>=0;k--){

            stockMap.put(typeList.get(k).toString(),typeList.get(k)) ;
        }
        ArrayList<String> stockListNew=new ArrayList<String>();

        for (String value : stockMap.values()) {
            stockListNew.add(value);
        }


        typeList=stockListNew;
        return typeList;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}