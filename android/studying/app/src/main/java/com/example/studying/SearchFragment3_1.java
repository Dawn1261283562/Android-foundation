package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment3_1 extends androidx.fragment.app.Fragment {
    private View mView;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment3_1, container, false);
        }
        //获取持仓搜索结果
        fundSearchResult();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        ListView listView = (ListView) mView.findViewById(R.id.list_search);
        listView.setAdapter(fundAdapter);

        return mView;
    }

    private void fundSearchResult(){
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("某某某基金1","012345",1.3333,0.0161,1.3334,-0.0104);
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("某某某222","233333",1.4444,-0.0133,1.2333,-0.0122);
        fundGeneralList.add(fundGeneral2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}