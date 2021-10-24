package com.example.studying;

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
    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    ArrayList<FundHeavyInfo> fundHeavyInfoList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment1, container, false);
        }

        Bundle bundle=getArguments();
        if (bundle != null) {
            fundHeavyInfoList = bundle.getParcelableArrayList("initbtn_login4");
        }


        //获取持仓搜索结果
        fundSearchResult();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        ListView listView = (ListView) mView.findViewById(R.id.list_search1);
        listView.setAdapter(fundAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundGeneral fundGeneral=fundGeneralList.get(i);


                Toast.makeText(getContext(),fundGeneral.getFund2().toString(),Toast.LENGTH_SHORT).show();

            }
        });
        return mView;
    }

    private void fundSearchResult() {
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("22222","平安银行平安银行平安银行平安银行","管理者");
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行","管理者");
        fundGeneralList.add(fundGeneral2);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}