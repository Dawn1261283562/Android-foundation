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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;

import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment2 extends androidx.fragment.app.Fragment {
    public static final String SEARCH_HISTORY = "search_history_2";
    private View mView;
    ListView listView;
    FundAdapter fundAdapter;
    private FlowLayout flowLayout2;
    private FlowLayout.Adapter flowAdapter2;
    private LayoutInflater layoutInflater2;
    private ArrayList<String> strList2;
    ImageButton deleteAllHisBut2;
    Group titleGroup;

    private List<FundGeneral> fundGeneralList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment2, container, false);
        }
        initViews();
        initEvents();
        initDate();

        getsearchHistory2();//获取历史搜索记录
        return mView;
    }

    private void initDate() {
        fundGeneralList=new ArrayList<>();
        fundAdapter =new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
        listView.setAdapter(fundAdapter);
        strList2 = new ArrayList<>();
        flowAdapter2=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList2.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater2.inflate(R.layout.historysearch_flow_item,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text_history);
                textView.setText(strList2.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Drawable drawable=textView.getCompoundDrawables()[2];
                        if ((event.getX() > textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() < textView.getWidth()-textView.getPaddingRight())){
                            strList2.remove(position);

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
                            flowLayout2.setAdapter(flowAdapter2);
                        }
                        else if ((event.getX() < textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() > 0)){
                            MainActivity2.editText.setText(strList2.get(position));
                            MainActivity2.editText.setSelection(strList2.get(position).length());
                        }
                        return false;
                    }
                });
                return view;
            }
        };
        flowLayout2.setAdapter(flowAdapter2);
    }

    private void initEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                if(fundGeneralList.get(i).getSelectFund()){
                    fundGeneralList.get(i).setSelectFund(false);
                }
                else{
                    fundGeneralList.get(i).setSelectFund(true);
                }
                fundAdapter.notifyDataSetChanged();
            }
        });
        deleteAllHisBut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY,getActivity().MODE_PRIVATE);
                strList2.clear();
                sp.edit().clear().commit();
                flowLayout2.setAdapter(flowAdapter2);
            }
        });
    }

    private void initViews() {
        listView = (ListView) mView.findViewById(R.id.list_search2);
        flowLayout2 = mView.findViewById(R.id.frag2_history_flow);
        layoutInflater2 = LayoutInflater.from(getActivity());
        deleteAllHisBut2=mView.findViewById(R.id.delete_all_history2);
        titleGroup=mView.findViewById(R.id.sear_frag2_titlegroup);
    }

    public void getsearchHistory2() {
        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] hisArrays = longhistory.split(",");
        if (hisArrays[0]=="") {
            return;
        }
        strList2 = new ArrayList<>();
        for (int i = 0; i < hisArrays.length; i++) {
            strList2.add(hisArrays[i]);
        }
        flowLayout2.setAdapter(flowAdapter2);
    }

    public void saveSearchHistory2(String text){
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

    public void update(ArrayList<Stock> stockList) {

        System.out.println(123321);
        fundGeneralList.clear();

        int size = stockList.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String)value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneral1.setStock(value);
            fundGeneralList.add(fundGeneral1);


        }

//        listView.setAdapter(new FundAdapter(getActivity(),R.layout.fund_item,fundGeneralList));

        if(fundGeneralList.size()==0){
            titleGroup.setVisibility(View.INVISIBLE);
        }
        else{
            titleGroup.setVisibility(View.VISIBLE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}