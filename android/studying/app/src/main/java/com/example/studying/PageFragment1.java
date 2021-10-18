package com.example.studying;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class PageFragment1 extends androidx.fragment.app.Fragment {
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment1, container, false);
        }
        return mView;
        
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}
