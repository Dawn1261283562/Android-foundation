package com.example.studying;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studying.entity.Stock;

import java.util.ArrayList;

public class ProrateActivity extends AppCompatActivity {

    private ArrayList<Stock> stockList;//选择的股票
    private TextView titleTex;
    private ListView listView;
    private StockAdapter1 stockAdapter;

    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prorate);

        initView();
        initEvent();
        initDate();

//        Stock stock =new Stock();
//        stock.setId(1111);
//        stock.setPrice(111);
//        stock.setName(111);
//        stockList.add(stock);

        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable("stockListProrated",stockList);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);

    }

    private void initDate() {
        titleTex.setVisibility(View.VISIBLE);
        titleTex.setText("自选比例");

        Bundle bundle=getIntent().getExtras();
        stockList=(ArrayList<Stock>)bundle.getSerializable("stockListToProrate");
        if(stockList==null) stockList = new ArrayList<Stock>();

        stockAdapter =new StockAdapter1(this,R.layout.prorate_item,stockList);
        listView.setAdapter(stockAdapter);

    }

    private void initEvent() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        titleTex = findViewById(R.id.title_text);
        listView = findViewById(R.id.prorate_list);
        buttonBack= findViewById(R.id.but_prorateback);
    }

    public void clickBack(View view){
        switch (view.getId()){
            case R.id.back_icon:
                finish();
        }
    }
}