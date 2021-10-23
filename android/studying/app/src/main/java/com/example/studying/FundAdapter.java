package com.example.studying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class FundAdapter extends ArrayAdapter<FundGeneral> {
    private int mResourceId;
    public FundAdapter(Context context, int textViewResourceId, List<FundGeneral> fundItem){
        super(context,textViewResourceId,fundItem);
        mResourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FundGeneral fundGeneral=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);

        TextView fund1=(TextView) view.findViewById(R.id.fund1);
        TextView fund2=(TextView) view.findViewById(R.id.fund2);
        TextView fund3=(TextView) view.findViewById(R.id.fund3);
        TextView fund4=(TextView) view.findViewById(R.id.fund4);
        TextView fund5=(TextView) view.findViewById(R.id.fund5);
        TextView fund6=(TextView) view.findViewById(R.id.fund6);

        DecimalFormat df=new DecimalFormat(("0.00"));

        fund1.setText(fundGeneral.getFund1());
        fund2.setText(fundGeneral.getFund2());
        fund3.setText(String.valueOf(fundGeneral.getFund3()));
        fund4.setText(String.valueOf(df.format(fundGeneral.getFund4()*100))+"%");
        fund5.setText(String.valueOf(fundGeneral.getFund5()));
        fund6.setText(String.valueOf(df.format(fundGeneral.getFund6()*100))+"%");

        return view;
    }
}
