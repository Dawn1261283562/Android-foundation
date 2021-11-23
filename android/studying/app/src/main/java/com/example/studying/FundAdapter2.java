package com.example.studying;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class FundAdapter2 extends ArrayAdapter<FundGeneral> {
    private int mResourceId;
    FundGeneral fundGeneral;

    public FundAdapter2(Context context, int textViewResourceId, List<FundGeneral> fundGeneralList){
        super(context, textViewResourceId,fundGeneralList);
        mResourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        fundGeneral=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);

        TextView fund1=(TextView) view.findViewById(R.id.fund1);
        TextView fund2=(TextView) view.findViewById(R.id.fund2);
        TextView fund3=(TextView) view.findViewById(R.id.fund3);


        fund1.setText(fundGeneral.getFund3());
        fund2.setText(fundGeneral.getFund1());
        fund3.setText(fundGeneral.getFund2());

        if(fundGeneral.getSelectFund()){
            view.setBackgroundColor(Color.YELLOW);
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }


}
