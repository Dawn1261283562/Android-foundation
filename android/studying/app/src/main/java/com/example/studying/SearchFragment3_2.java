package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment3_2 extends androidx.fragment.app.Fragment {
    private View mView;

    private FlowLayout flowLayout;
    private ArrayList<String> strList;
    private LayoutInflater layoutInflater;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment3_2, container, false);
        }

        //获取用户选择的板块
        sectorSelected();

        //获取持仓搜索结果
        fundSearchResult();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        ListView listView = (ListView) mView.findViewById(R.id.list_search3_2);
        listView.setAdapter(fundAdapter);

        return mView;
    }

    private void sectorSelected() {
        strList = new ArrayList<>();
        strList.add("今天好冷好冷好冷");
        strList.add("好冷好冷好冷好冷好冷好冷");
        strList.add("好冷好冷好冷");
        strList.add("好冷好冷好冷好冷好冷好冷好冷好冷好冷");
        strList.add("好冷好冷好冷");

        flowLayout = (FlowLayout) mView.findViewById(R.id.flowlayout3_2);
        layoutInflater = LayoutInflater.from(getContext());

        flowLayout.setAdapter(new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater.inflate(R.layout.flow_item3_1,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                ((TextView)view.findViewById(R.id.flow_text3_1)).setText(strList.get(position));
                return view;
            }
        });
    }


    private void fundSearchResult(){
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("某某某基金1","012345",1.3333,0.0161,1.3334,-0.0104);
        fundGeneralList.add(fundGeneral1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}