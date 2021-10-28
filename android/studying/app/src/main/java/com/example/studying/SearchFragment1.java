package com.example.studying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
public class SearchFragment1 extends androidx.fragment.app.Fragment {
    public static final String SEARCH_HISTORY = "search_history_1";

    private View mView;

    private ListView listView1;
    private FundAdapter fundAdapter1;
    private FlowLayout flowLayout;
    private FlowLayout.Adapter flowAdapter;
    private LayoutInflater layoutInflater;
    private ArrayList<String> strList;

    private List<FundGeneral> fundGeneralList1;

    private FlowLayout flowLayout1;
    private FlowLayout.Adapter flowAdapter1;
    private LayoutInflater layoutInflater1;
    private ArrayList<String> strList1;
    ImageButton deleteAllHisBut1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment1, container, false);
        }
        initView();
        initEvent();
        initDate();

        getsearchHistory1();//获取历史搜索记录
        return mView;
    }

    private void initDate() {
        fundGeneralList1=new ArrayList<>();
        fundAdapter1=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList1);
        listView1.setAdapter(fundAdapter1);
        strList1 = new ArrayList<>();
        flowAdapter1=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList1.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater1.inflate(R.layout.flow_item3_1,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text3_1);
                textView.setText(strList1.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Drawable drawable=textView.getCompoundDrawables()[2];
                        if ((event.getX() > textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() < textView.getWidth()-textView.getPaddingRight())){
                            strList1.remove(position);

                            SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY,getActivity().MODE_PRIVATE);
                            String longhistory = sp.getString(SEARCH_HISTORY, "");
                            String[] tmpHistory = longhistory.split(",");//用逗号拆分字符串
                            ArrayList<String> history = new ArrayList<String>(Arrays.asList(tmpHistory));
                            history.remove(position);
                            if (history.size() > 0) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < history.size(); i++) {
                                    sb.append(history.get(i) + ",");
                                }
                                sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
                            } else {
                                sp.edit().clear().commit();
                            }
                            flowLayout1.setAdapter(flowAdapter1);
                        }
                        return false;
                    }
                });
                return view;
            }
        };
        flowLayout1.setAdapter(flowAdapter1);
    }

    private void initEvent() {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FundGeneral fundGeneral = fundGeneralList1.get(i);
                Toast.makeText(getContext(), fundGeneral.getFund2().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        deleteAllHisBut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY,getActivity().MODE_PRIVATE);
                strList1.clear();
                sp.edit().clear().commit();
                flowLayout1.setAdapter(flowAdapter1);
            }
        });
    }

    private void initView() {
        listView1 = (ListView) mView.findViewById(R.id.list_search1);
        flowLayout1 = mView.findViewById(R.id.frag1_history_flow);
        layoutInflater1 = LayoutInflater.from(getActivity());
        deleteAllHisBut1=mView.findViewById(R.id.delete_all_history1);
    }
    public void getsearchHistory1() {
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] hisArrays = longhistory.split(",");
        if (hisArrays[0]=="") {
            return;
        }
        strList1 = new ArrayList<>();
        for (int i = 0; i < hisArrays.length; i++) {
            strList1.add(hisArrays[i]);
        }
        flowLayout1.setAdapter(flowAdapter1);
    }
    public void saveSearchHistory1(String text){
        if(text.length()<1) {
            return;
        }
        SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY,getActivity().MODE_PRIVATE);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");//用逗号拆分字符串
        ArrayList<String> history = new ArrayList<String>(Arrays.asList(tmpHistory));
        //这里检查是否有重复
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, text);
        }
        //这里保存数据
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
    }

    public void fundSearchResult() {
        fundGeneralList1.clear();
        MainActivity2 activity2=(MainActivity2)getActivity();
        ArrayList<FundHeavyInfo> temp=activity2.getTemp();
        if(temp!=null){
            for(int i = 0;i < temp.size(); i ++){
                FundGeneral fundGeneral=new FundGeneral(temp.get(i).getId(),temp.get(i).getName(),temp.get(i).getManager());
                System.out.println(temp.get(i).getId()+" "+temp.get(i).getName()+" "+temp.get(i).getManager());
                fundGeneralList1.add(fundGeneral);
            }
            listView1.setAdapter(new  FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList1));
        }

        /*FundGeneral fundGeneral1=new FundGeneral("22222","平安银行平安银行平安银行平安银行","管理者");
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行","管理者");
        fundGeneralList.add(fundGeneral2);*/
    }

    public void update(ArrayList<FundHeavyInfo> fundInfoList) {

        System.out.println(123321);
        fundGeneralList1.clear();

        int size = fundInfoList.size();
        for (int i = 0; i < size; i++) {
            FundHeavyInfo value = fundInfoList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String)value.getId(),(String) value.getName(),(String) value.getManager());
            fundGeneral1.setFundHeavyInfo(value);
            fundGeneralList1.add(fundGeneral1);


        }

        listView1.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList1));

        //Toast.makeText(getActivity(), "gengaile", Toast.LENGTH_SHORT).show();
//        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
//
//        listView = (ListView) mView.findViewById(R.id.list_search2);
//        listView.setAdapter(fundAdapter);

        //BB.performClick();
        System.out.println(fundGeneralList1.size());
        fundAdapter1.notifyDataSetChanged();
//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral1);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}