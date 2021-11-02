package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


public class PageFragment1 extends androidx.fragment.app.Fragment {
    private View mView;
    private LinearLayout searTab1;
    private LinearLayout searTab2;
    private LinearLayout searTab3;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment1, container, false);
        }
        searTab1 = (LinearLayout)mView.findViewById(R.id.sear_tab1);
        searTab2 = (LinearLayout)mView.findViewById(R.id.sear_tab2);
        searTab3 = (LinearLayout)mView.findViewById(R.id.sear_tab3);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity2.class);
                //Intent intent=new Intent(getActivity(),Stockinfo.class);
                intent.putExtra("i",0);
                startActivity(intent);
            }
        });
        searTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity2.class);
                intent.putExtra("i",1);
                startActivity(intent);
            }
        });
        searTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity2.class);
                intent.putExtra("i",2);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}