

package com.example.studying;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class addStockActivity extends AppCompatActivity {
    public static final String SEARCH_HISTORY = "search_history_3_1";

    private EditText editText;
    private Button searchBut;
    private Button addMoreBut;
    private Button finishAddBut;
    private ImageButton deleteAllHisBut;

    private ArrayList<Stock> stockList=new ArrayList<Stock>();//选择的股票
    private ArrayList<Stock> stockList1=new ArrayList<Stock>();//搜索到的所有股票
    private ArrayList<FundHeavyInfo> temp;

    private List<FundGeneral> fundGeneralList=new ArrayList<>();

    private ListView listView;
    private FundAdapter fundAdapter;
    private FlowLayout flowLayout;
    private FlowLayout.Adapter flowAdapter;
    private LayoutInflater layoutInflater;
    private ArrayList<String> strList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        stockList=(ArrayList<Stock>) bundle.getSerializable("stockList3_1");
  /*      if(stockList!=null){
            for(int a=0;a<stockList.size();a++){
                Log.d("zzzzzzzzzzzzz", stockList.get(a).getName().toString());
            }
        }*/
        if(stockList==null)stockList=new ArrayList<Stock>();
        initViews();
        initEvents();
        initData();


        Intent intent2=new Intent();
        Bundle bundle2=new Bundle();
        bundle2.putSerializable("stockListAdd", stockList);
        intent2.putExtras(bundle2);
        setResult(Activity.RESULT_OK,intent2);

        fundSearchResult();
        getsearchHistory();
        initbtn_login5();
        //System.out.println(stockList);
    }

    private void initData() {
        fundAdapter=new FundAdapter(addStockActivity.this,R.layout.fund_item,fundGeneralList);

        editText.setVisibility(View.VISIBLE);
        searchBut.setVisibility(View.VISIBLE);
        editText.setHint("股票名称");
        listView.setAdapter(fundAdapter);
        strList = new ArrayList<>();
        flowAdapter=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return strList.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater.inflate(R.layout.historysearch_flow_item,parent,false);
                // 给 View 设置 margin
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text_history);
                textView.setText(strList.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Drawable drawable=textView.getCompoundDrawables()[2];
                        if ((event.getX() > textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() < textView.getWidth()-textView.getPaddingRight())){
                            strList.remove(position);

                            SharedPreferences sp=getSharedPreferences(SEARCH_HISTORY,MODE_PRIVATE);
                            String longhistory = sp.getString(SEARCH_HISTORY, "");
                            String[] tmpHistory = longhistory.split(",");//用逗号拆分字符串
                            ArrayList<String> history = new ArrayList<String>(Arrays.asList(tmpHistory));
                            history.remove(position);
                            if (history.size() > 0) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < history.size(); i++) {
                                    sb.append(history.get(i) + ",");
                                }
                                sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
                            } else {
                                sp.edit().clear().commit();
                            }
                            flowLayout.setAdapter(flowAdapter);
                        }
                        else if ((event.getX() < textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() > 0)){
                            editText.setText(strList.get(position));
                            editText.setSelection(strList.get(position).length());
                        }
                        return false;
                    }
                });
                return view;
            }
        };
        flowLayout.setAdapter(flowAdapter);
    }

    private void initEvents() {
        editText.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundGeneral fundGeneral =fundGeneralList.get(i);
                System.out.println(122);
                //fundSearchResult();
                //fundAdapter.notifyDataSetChanged();
                //FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
                //fundGeneralList.add(fundGeneral1);
/*                stockList.add( fundGeneral.getStock());

                Intent intent = new Intent();

                intent.setClass(addStockActivity.this,MainActivity2.class);*/

                if(fundGeneralList.get(i).getSelectFund()){
                    fundGeneralList.get(i).setSelectFund(false);
                    stockList.remove(fundGeneral.getStock());
                    System.out.println(1223);
                }
                else{
                    fundGeneralList.get(i).setSelectFund(true);
                    stockList.add(fundGeneral.getStock());
                    System.out.println(1224);
                    if(stockList!=null) {
                        System.out.println(stockList.size());
                        for(int j=0;j<stockList.size();j++){
                            System.out.println(stockList.get(j).getName());
                        }
                    }
                }
                fundAdapter.notifyDataSetChanged();
            }
        });

        addMoreBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!(imm.isActive())) {
                    imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
                }
            }
        });
        finishAddBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                FundGeneral fundGeneral =fundGeneralList.get(i);
//                System.out.println(122);
//
//                stockList.add(fundGeneral.getStock());
//
//                fundAdapter.notifyDataSetChanged();
//            }
//        });

        deleteAllHisBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences(SEARCH_HISTORY,MODE_PRIVATE);
                strList.clear();
                sp.edit().clear().commit();
                flowLayout.setAdapter(flowAdapter);
            }
        });
    }

    private void initViews() {
        editText=findViewById(R.id.search_edit1);
        searchBut=findViewById(R.id.search_but1);
        listView = findViewById(R.id.list_search3_1_2);
        addMoreBut= findViewById(R.id.add_stock_but1);
        finishAddBut=findViewById(R.id.add_stock_but2);
        deleteAllHisBut=findViewById(R.id.delete_all_history);
        flowLayout = findViewById(R.id.addstock_history_flow);
        layoutInflater = LayoutInflater.from(this);
    }

    private void getsearchHistory() {
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] hisArrays = longhistory.split(",");
        if (hisArrays[0]=="") {
            return;
        }
        strList = new ArrayList<>();
        for (int i = 0; i < hisArrays.length; i++) {
            strList.add(hisArrays[i]);
        }
        flowLayout.setAdapter(flowAdapter);
    }

    private void saveSearchHistory(String text){
        if(text.length()<1) {
            return;
        }
        SharedPreferences sp=getSharedPreferences(SEARCH_HISTORY,MODE_PRIVATE);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");//用逗号拆分字符串
        ArrayList<String> history = new ArrayList<String>(Arrays.asList(tmpHistory));
        //这里检查是否有重复
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, text);
        }
        //这里保存数据
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
    }

    private void fundSearchResult() {
        /*FundGeneral fundGeneral1=new FundGeneral("000001.SZ","平安银行","20.04");
        fundGeneralList.add(fundGeneral1);*/

        System.out.println(123321);
        fundGeneralList.clear();
//        FundGeneral fundGeneral=new FundGeneral("000001.SZ","平安银行","20.04");
//        fundGeneralList.add(fundGeneral);
        int size = stockList1.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockList1.get(i);
            FundGeneral fundGeneral1=new FundGeneral((String) value.getId(),(String) value.getName(),(String) value.getPrice());
            fundGeneral1.setStock(value);
            fundGeneralList.add(fundGeneral1);
        }

//        listView.setAdapter(new FundAdapter(addStockActivity.this,R.layout.fund_item,fundGeneralList));

        //Toast.makeText(getActivity(), "gengaile", Toast.LENGTH_SHORT).show();
//        FundAdapter fundAdapter=new FundAdapter(getContext(),R.layout.fund_item,fundGeneralList);
//
//        listView = (ListView) mView.findViewById(R.id.list_search2);
//        listView.setAdapter(fundAdapter);

        //BB.performClick();
        System.out.println(fundGeneralList.size());
        fundAdapter.notifyDataSetChanged();
    }

    private void hasSelectedUpdate() {
        System.out.println(123321);
        int size = stockList.size();
        for (int i = 0; i < size; i++) {
            Stock value = stockList.get(i);
            System.out.println(value.getName());
        }
        System.out.println(123321);
    }

    private void initbtn_login5() {
        searchBut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(123);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }

                String url = "http://localhost:8080/user/lgoin";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=平安";
                url = "http://43m486x897.yicp.fun/stock/searchStock?id=";
                //请求传入的参数
                String urlAdd= editText.getText().toString().trim();
                System.out.println(urlAdd);
                saveSearchHistory(urlAdd);
                getsearchHistory();

                RequestBody requestBody = new FormBody.Builder().build();
                url+=urlAdd;
                HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Looper.prepare();
                        //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody data = response.body();
                        if(response.code()==200){
                            String strByJson = response.body().string();
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                            Gson gson = new Gson();
                            ArrayList<Stock> stockBeanList = new ArrayList<Stock>();

                            System.out.println(strByJson);
                            //加强for循环遍历JsonArray
                            for (JsonElement stock : jsonArray) {
                                //使用GSON，直接转成Bean对象
                                Stock stockBean = gson.fromJson(stock, Stock.class);
                                if(stockBean==null){
                                    Looper.prepare();
                                    //addStockFragment.BB.performClick();
                                    Toast.makeText(addStockActivity.this, "无相关信息", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    return;
                                }
                                stockBeanList.add(stockBean);

                                System.out.println("这下面是 股票的代码、名字、板块集、股价、热度");
                                System.out.println(stockBean.getId());
                                System.out.println(stockBean.getName());
                                System.out.println(stockBean.getType());
                                System.out.println(stockBean.getPrice());
                                System.out.println(stockBean.getHits());
                                System.out.println("这上面是 股票的代码、名字、板块集、股价、热度");
                            }
                            System.out.println(stockBeanList);
                            stockList1 =stockBeanList;
                            System.out.println(stockList1);
                            Looper.prepare();
                            Toast.makeText(addStockActivity.this, strByJson, Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        else{
                            Looper.prepare();
                            Toast.makeText(addStockActivity.this, "无相关信息", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });
                Thread closeActivity = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            fundSearchResult();
                            hasSelectedUpdate();
                            // Do some stuff
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }});
                closeActivity.run();

            }
        });
    }

    public void clickBack(View view){
        switch (view.getId()){
            case R.id.back_icon:
                finish();
        }
    }
}