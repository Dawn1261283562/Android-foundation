package com.example.studying;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studying.entity.Stock;

import java.text.DecimalFormat;
import java.util.List;

public class StockAdapter1 extends ArrayAdapter<Stock> {
    private int mResourceId;
    Stock stock;

    public StockAdapter1(Context context, int textViewResourceId, List<Stock> stockList){
        super(context, textViewResourceId,stockList);
        mResourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        stock=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(mResourceId,parent,false);

        TextView stockName=(TextView) view.findViewById(R.id.stock_name);
        TextView stockId=(TextView) view.findViewById(R.id.stock_id);
        EditText stockRadio=(EditText) view.findViewById(R.id.ratio_edit);

        stockName.setText(stock.getName().toString());
        stockId.setText(stock.getId().toString());
        stockRadio.setText(String.valueOf(stock.getExpectRadio()));

        return view;
    }


}
