package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.studying.Data;
import com.example.studying.entity.User;
import com.example.studying.utils.HttpGetRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PageFragment2 extends androidx.fragment.app.Fragment {
    private View mView;

    private List<FundGeneral> fundGeneralList;
    private FundAdapter fundAdapter;
    private ListView listView;

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment2, container, false);
        }

        fundGeneralList=new ArrayList<>();
        fundSlected();
        fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
        listView = mView.findViewById(R.id.list_fund_selected);
        listView.setAdapter(fundAdapter);

        initData();

        return mView;

    }

    //Request 请求代码
    private void initData() {
        Data data = (Data)getActivity().getApplication();
        username=data.getUsername();

    }


    private void fundSlected() {
        fundGeneralList.clear();
        FundGeneral fundGeneral1=new FundGeneral("基金1","012345","1.3333,0.0161,1.3334,-0.0104");
        fundGeneralList.add(fundGeneral1);
        FundGeneral fundGeneral2=new FundGeneral("改222","233333","1.4444,-0.0133,1.2333,-0.0122");
        fundGeneralList.add(fundGeneral2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}