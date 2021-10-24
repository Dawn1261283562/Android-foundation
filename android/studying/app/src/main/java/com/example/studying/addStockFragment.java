package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.studying.entity.Stock;

import java.util.ArrayList;
import java.util.List;

public class addStockFragment extends androidx.fragment.app.Fragment {
    private View mView;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.add_stock_fragment, container, false);
        }
        //获取持仓搜索结果
        fundSearchResult();

        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);

        listView = (ListView) mView.findViewById(R.id.list_search2);
        listView.setAdapter(fundAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundGeneral fundGeneral =fundGeneralList.get(i);
                System.out.println(122);

            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemCli
//
//        });

        return mView;
    }

    private void fundSearchResult() {
        fundGeneralList.clear();
//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral1);
//        FundGeneral fundGeneral2=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral2);
//        FundGeneral fundGeneral3=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral1);
//        FundGeneral fundGeneral4=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral2);
//        fundGeneralList.add(fundGeneral2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    public void update(ArrayList<Stock> stockBeanList) {
        fundGeneralList.clear();


        int size = stockBeanList.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockBeanList.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneralList.add(fundGeneral1);
            FundGeneral fundGeneral2=new FundGeneral("000000.SZ","平安银行","20.04");
            fundGeneralList.add(fundGeneral2);
        }

//        FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral1);





    }
}