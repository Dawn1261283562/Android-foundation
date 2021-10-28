package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    private View mView;

    private ListView listView1;
    private FundAdapter fundAdapter1;

    private List<FundGeneral> fundGeneralList1=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment1, container, false);
        }

        /*Bundle bundle=getArguments();
        if (bundle != null) {
            fundHeavyInfoList = bundle.getParcelableArrayList("initbtn_login4");
            System.out.println(fundHeavyInfoList.get(0).getId());
        }*/


        fundAdapter1=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList1);
        //获取持仓搜索结果
        //fundSearchResult();

        //System.out.println("okkkkkkkkkkkkkkkkkkk3333");
        listView1 = (ListView) mView.findViewById(R.id.list_search1);
        listView1.setAdapter(fundAdapter1);
        //fundAdapter.notifyDataSetChanged();

        //listView = mView.findViewById(R.id.list_search1);
        //listView.setAdapter(fundAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*Intent intent=new Intent(getActivity(),);
                intent.putExtra("item1",i);
                startActivity(intent);*/

                FundGeneral fundGeneral = fundGeneralList1.get(i);
                Toast.makeText(getContext(), fundGeneral.getFund2().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return mView;
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
        System.out.println("okkkkkkkkkkkkkkkkkkk2222");

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