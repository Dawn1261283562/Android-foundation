package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment2 extends androidx.fragment.app.Fragment {
    private View mView;
    ListView listView;
    FundAdapter fundAdapter;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment2, container, false);
        }
        initViews();
        initEvents();
        initDate();

        //获取持仓搜索结果
        fundSearchResult();
        return mView;
    }

    private void initDate() {
        fundAdapter =new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        listView.setAdapter(fundAdapter);
    }

    private void initEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundGeneral fundGeneral=fundGeneralList.get(i);
                Toast.makeText(getContext(),fundGeneral.getFund2().toString(),Toast.LENGTH_SHORT).show();

                if(fundGeneralList.get(i).getSelectFund()){
                    fundGeneralList.get(i).setSelectFund(false);
                    System.out.println(22222);
                }
                else{
                    fundGeneralList.get(i).setSelectFund(true);
                    System.out.println(33333);
                }
                fundAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initViews() {
        listView = (ListView) mView.findViewById(R.id.list_search2);
    }

    private void fundSearchResult() {
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行平安银行","20.04");
        fundGeneralList.add(fundGeneral2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}