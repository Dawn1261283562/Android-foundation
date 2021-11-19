package com.example.studying;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private ImageButton helpButton;
    private TextView helpText;
    private  TextView textCompany;
    private ImageButton checkComBtn;
    private FundAdapter fundAdapter;
    private ListView listView;
    private List<FundGeneral> fundGeneralList;
    private FlowLayout.Adapter flowAdapter;
    private ArrayList<FundHeavy> fundHeavyList=new ArrayList<FundHeavy>();
    private String companySelected;

    Handler mHandler;

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
        mHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        hasSelectedUpdate();
                        ((MainActivity2)getActivity()).progressBarGone();
                }
            }
        };
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
        helpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        helpText.setVisibility(View.VISIBLE);
                        helpText.bringToFront();
//                        deleteAllHisBut2.setEnabled(false);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        helpText.setVisibility(View.GONE);
//                        deleteAllHisBut2.setEnabled(true);
                        break;
                    }
                }
                return false;
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
        helpText = mView.findViewById(R.id.sear_frag3_2_helptext);
        helpButton =mView.findViewById(R.id.help_button_sear_frag3_2);
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



                if(typeList.size()==0){

                    Toast.makeText(getActivity(), "请添加板块", Toast.LENGTH_SHORT).show();

                    return;
                }

                ((MainActivity2)getActivity()).progressBarVisible();
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
                        if(response.code()!=200)return;
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

                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);

                        Looper.prepare();
                        System.out.println(data);
                        Toast.makeText(getActivity(), strByJson, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
                /*Thread closeActivity = new Thread(new Runnable() {
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
                closeActivity.run();*/
            }


        });

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
                Intent intent=new Intent(getActivity(),fundsinfo.class);
                intent.putExtra("fundsGet", temp);
                startActivity(intent);




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