package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment1 extends androidx.fragment.app.Fragment {
    private View mView;
    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment1, container, false);
        }

        //获取持仓搜索结果
        fundSearchResult();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        ListView listView = (ListView) mView.findViewById(R.id.list_search1);
        listView.setAdapter(fundAdapter);

        return mView;
    }

    private void fundSearchResult() {
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行平安银行平安银行平安银行","管理者");
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