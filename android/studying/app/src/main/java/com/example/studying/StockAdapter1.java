package com.example.studying;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studying.entity.Stock;

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

        TextView stockName=view.findViewById(R.id.stock_name);
        TextView stockId= view.findViewById(R.id.stock_id);
        EditText stockRadio= view.findViewById(R.id.ratio_edit);

        stockName.setText(stock.getName().toString());
        stockId.setText(stock.getId().toString());
        stockRadio.setText(String.valueOf(stock.getExpectRadio()));
        stockRadio.setSelection(stockRadio.getText().length());

        stockRadio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //限制小数点个数
                if (stockRadio.getText().toString().indexOf(".") >= 0) {
                    if (stockRadio.getText().toString().indexOf(".", stockRadio.getText().toString().indexOf(".") + 1) > 0) {
                        stockRadio.setText(stockRadio.getText().toString().substring(0, stockRadio.getText().toString().length() - 1));
                        stockRadio.setSelection(stockRadio.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(stockRadio.getText().toString().length()==0){
                    getItem(position).setExpectRadio(0);
                }
                else getItem(position).setExpectRadio(Double.parseDouble(stockRadio.getText().toString()));
            }
        });
        /*stockRadio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(stockRadio.getText().toString().length()==0){
                        stockRadio.setText("0");
                    }
                    getItem(position).setExpectRadio(Double.parseDouble(stockRadio.getText().toString()));
                }
            }
        });*/

        return view;
    }


}
