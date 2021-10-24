package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.studying.entity.Stock;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment3_1 extends androidx.fragment.app.Fragment {
    private View mView;

    private FlowLayout flowLayout;
    private ArrayList<String> strList;
    private LayoutInflater layoutInflater;
    private Button addBtn;
    private ArrayList<Stock> stockList;
    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.search_fragment3_1, container, false);
        }

        //获取用户选择的股票
        stockSlected();

        //获取持仓搜索结果
        fundSearchResult();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
        addBtn =(Button)mView.findViewById(R.id.frag3_1_but1);
        ListView listView = (ListView) mView.findViewById(R.id.list_search3_1);
        listView.setAdapter(fundAdapter);

        return mView;
    }

    private void stockSlected() {
        strList = new ArrayList<>();
        strList.add("阿里巴巴");
        strList.add("阿巴阿巴");
        strList.add("阿巴巴巴巴");
        strList.add("阿里巴巴阿里巴巴阿里巴巴阿里巴巴阿里巴巴阿里巴巴");
        strList.add("阿巴阿巴阿巴");
        strList.add("阿巴阿");
        strList.add("阿巴阿巴");
        strList.add("阿巴阿巴");
        strList.add("阿巴阿巴");
        strList.add("巴阿巴");
        strList.add("阿巴阿");

        flowLayout = (FlowLayout) mView.findViewById(R.id.flowlayout3_1);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),addStockActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("stockList", stockList);
                intent.putExtras(bundle);
                startActivity(intent);

//                Intent intent=new Intent(getActivity(),addStockActivity.class);
//                intent.putExtra("i",0);
//                startActivity(intent);
            }
        });

    }

    private void fundSearchResult(){
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","李四");
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行平安银行平安银行平安银行","张三");
        fundGeneralList.add(fundGeneral2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}