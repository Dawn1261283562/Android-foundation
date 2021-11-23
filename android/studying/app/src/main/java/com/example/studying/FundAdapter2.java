package com.example.studying;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
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

        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.fund1=(TextView) view.findViewById(R.id.fund1);
            viewHolder.fund2=(TextView) view.findViewById(R.id.fund2);
            viewHolder.fund3=(TextView) view.findViewById(R.id.fund3);
            view.setTag(viewHolder);
        }
        else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }



        viewHolder.fund1.setText(fundGeneral.getFund3());
        viewHolder.fund2.setText(fundGeneral.getFund1());
        viewHolder.fund3.setText(fundGeneral.getFund2());

        if(position==0){
            viewHolder.fund1.setTextColor(android.graphics.Color.parseColor("#000000"));
            viewHolder.fund2.setTextColor(android.graphics.Color.parseColor("#000000"));
            viewHolder.fund3.setTextColor(android.graphics.Color.parseColor("#000000"));
            viewHolder.fund1.setTextSize(16);
            viewHolder.fund2.setTextSize(16);
            viewHolder.fund3.setTextSize(16);
        }

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        if(position==0){
            return false;
        }
        return super.isEnabled(position);
    }

    class ViewHolder{
        TextView fund1;
        TextView fund2;
        TextView fund3;
    }
}
