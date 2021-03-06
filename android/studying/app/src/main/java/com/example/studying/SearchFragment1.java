package com.example.studying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;

import com.example.studying.entity.FundHeavyInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment1 extends androidx.fragment.app.Fragment {
    public static final String SEARCH_HISTORY = "search_history_1";

    private View mView;

    private ListView listView1;
    private FundAdapter fundAdapter1;

    private List<FundGeneral> fundGeneralList1;

    private FlowLayout flowLayout1;
    private FlowLayout.Adapter flowAdapter1;
    private ArrayList<String> strList1;

    FlowLayout flowLayout2;
    private FlowLayout.Adapter flowAdapter2;
    private ArrayList<String> strList2;

    Button deleteAllHisBut1;
    ImageButton helpButton;
    TextView helpText;
    Group titleGroup;
    Group titleGroup1;
    Group titleGroup2;

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

        strList2=new ArrayList<>();
        strList2.add("华夏成长混合");
        strList2.add("000001");
        strList2.add("健康");
        strList2.add("000005");


        flowAdapter1=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList1.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
                View view = layoutInflater1.inflate(R.layout.historysearch_flow_item,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text_history);
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
                            changeVisibility1();
                        }
                        else if ((event.getX() < textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() > 0)){
                            MainActivity2.editText.setText(strList1.get(position));
                            MainActivity2.editText.setSelection(strList1.get(position).length());
                        }
                        return false;
                    }
                });
                return view;
            }
        };
        flowAdapter2=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList2.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view = layoutInflater.inflate(R.layout.hot_search_item,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text_history);
                textView.setText(strList2.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        MainActivity2.editText.setText(strList2.get(position));
                        MainActivity2.editText.setSelection(strList2.get(position).length());
                        return false;
                    }
                });
                return view;
            }
        };
        flowLayout1.setAdapter(flowAdapter1);
        flowLayout2.setAdapter(flowAdapter2);
        changeVisibility1();
    }

    private void initEvent() {
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FundGeneral fundGeneral = fundGeneralList1.get(i);
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
        deleteAllHisBut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY,getActivity().MODE_PRIVATE);
                strList1.clear();
                sp.edit().clear().commit();
                flowLayout1.setAdapter(flowAdapter1);
                changeVisibility1();
            }
        });
        helpButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        helpText.setVisibility(View.VISIBLE);
                        helpText.bringToFront();
                        deleteAllHisBut1.setEnabled(false);
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        helpText.setVisibility(View.GONE);
                        deleteAllHisBut1.setEnabled(true);
                        break;
                    }
                }
                return false;
            }
        });

    }

    private void initView() {
        listView1 = (ListView) mView.findViewById(R.id.list_search1);
        flowLayout1 = mView.findViewById(R.id.frag1_history_flow);
        flowLayout2 = mView.findViewById(R.id.frag1_history_flow2);
        deleteAllHisBut1=mView.findViewById(R.id.delete_all_history1);


titleGroup=mView.findViewById(R.id.sear_frag1_titlegroup);
        titleGroup1=mView.findViewById(R.id.sear_frag1_titlegroup1);
        titleGroup2=mView.findViewById(R.id.sear_frag1_titlegroup2);
        helpButton=mView.findViewById(R.id.help_button_sear_frag1);
        helpText=mView.findViewById(R.id.sear_frag1_helptext);
    }
    public void changeVisibility1(){
        if(fundGeneralList1!=null){
            if(fundGeneralList1.size()==0){
                titleGroup.setVisibility(View.INVISIBLE);
                titleGroup2.setVisibility(View.VISIBLE);
                if(flowAdapter1.getCount()==0){
                    titleGroup1.setVisibility(View.GONE);
                }
                else{
                    titleGroup1.setVisibility(View.VISIBLE);
                }
            }else{
                titleGroup.setVisibility(View.VISIBLE);
                titleGroup2.setVisibility(View.GONE);
            }
        }else {
            titleGroup.setVisibility(View.INVISIBLE);
            titleGroup2.setVisibility(View.VISIBLE);
            if(flowAdapter1.getCount()==0){
                titleGroup1.setVisibility(View.GONE);
            }
            else{
                titleGroup1.setVisibility(View.VISIBLE);
            }
        }
    }
    public void clearFundGeneralList(){
        fundGeneralList1.clear();
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
        changeVisibility1();

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

/*    public void fundSearchResult() {
        fundGeneralList1.clear();
        MainActivity2 activity2=(MainActivity2)getActivity();
        ArrayList<FundHeavyInfo> temp=activity2.getTemp();
        if(temp!=null){
            for(int i = 0;i < temp.size(); i ++){
                FundGeneral fundGeneral=new FundGeneral(temp.get(i).getId(),temp.get(i).getName(),temp.get(i).getManager());
                System.out.println(temp.get(i).getId()+" "+temp.get(i).getName()+" "+temp.get(i).getManager());
                fundGeneralList1.add(fundGeneral);
            }
//            listView1.setAdapter(new  FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList1));
            if(fundGeneralList1.size()==0){
                titleGroup.setVisibility(View.INVISIBLE);
            }
            else{
                titleGroup.setVisibility(View.VISIBLE);
            }
        }

        *//*FundGeneral fundGeneral1=new FundGeneral("22222","平安银行平安银行平安银行平安银行","管理者");
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行","管理者");
        fundGeneralList.add(fundGeneral2);*//*
    }*/

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
        changeVisibility1();

//        listView1.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList1));
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