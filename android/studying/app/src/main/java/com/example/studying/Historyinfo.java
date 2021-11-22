package com.example.studying;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studying.entity.FundHeavy;
import com.example.studying.entity.FundHeavyInfo;
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
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Historyinfo extends AddCompanyActivity{

    private ListView history_view;

    private TextView fundsname;


    //日期
    public List<String>History_date=new ArrayList<>();
    //单位净值
    public List<String>History_uni=new ArrayList<>();
    //累计净值
    public List<String>History_cumu=new ArrayList<>();
    //日涨幅
    public List<String>History_incre=new ArrayList<>();
    private String code="";
    private int history_num=20;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.historyinfo_layout);
        history_view = (ListView) this.findViewById(R.id.history_view);

        fundsname=(TextView)this.findViewById(R.id.Funds_name);


        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();

        String name=intent.getStringExtra("fundsname");
        code=intent.getStringExtra("code");


        Log.v("funds_name_",name+code);
        fundsname.setText(name);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        fundsname.setTextColor(Color.BLACK);
        init_History(code);


    }







    class Historylist extends BaseAdapter {

        @Override
        public int getCount() {
            return history_num+1;
        }

        @Override
        public Object getItem(int position) {
            if(position==0)
            {
                return "此为属性名称";
            }
            else
            {
                return "";
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View v=View.inflate(Historyinfo.this,R.layout.historyinfo_view,null );
            TextView t1=v.findViewById(R.id.text1);
            TextView t2=v.findViewById(R.id.text2);
            TextView t3=v.findViewById(R.id.text3);
            TextView t4=v.findViewById(R.id.text4);


            if(position!=0){

//                Log.v("History.t1",getHistory_date(position-1));
//                Log.v("History.t1",getHistory_uni(position-1));
//                Log.v("History.t1",getHistory_cumu(position-1));
//                Log.v("History.t1",getHistory_incre(position-1));


//                Log.v("History_boolean",getHistory_incre(position-1).substring(0,1));
//                Log.v("History.t1","getHistory_date(position-1)");
                if(getHistory_incre(position-1).substring(0,1).equals("-")) {
                    t4.setTextColor(Color.parseColor("#cc0000"));//设置颜色
//                    Log.v("History_boolean",getHistory_incre(position-1).substring(0,1));
//                    Log.v("History_boolean",false+"");
                }
                else{


                    t4.setTextColor(Color.parseColor("#66ff00"));//设置颜色
                    Log.v("History_boolean",true+"");
                }
                

                t1.setTextColor(Color.BLACK);
                t2.setTextColor(Color.BLACK);
                t3.setTextColor(Color.BLACK);

                t1.setText(getHistory_date(position-1).substring(5));
                t2.setText(getHistory_uni(position-1));
                t3.setText(getHistory_cumu(position-1));
                t4.setText(getHistory_incre(position-1));
                return v;}
            else
            {

                t1.setText("日期");
                t2.setText("单价净值");
                t3.setText("累计净值");
                t4.setText("日涨幅");

                return v;
            }
        }
    }

    public void init_History(String code) {

        String hurl= "https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code="+code+"&page=1&sdate=2020-11-12&edate=5555-11-12&per="+history_num;
        HttpGetRequest.sendOkHttpGetRequest(hurl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res=response.body().string();

                Log.v("History_",responce_to_HistoryRecord(res));
                setHistory(responce_to_HistoryRecord(res));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.v("History_main",getHistory_incre(19));
//                        init_text();


                        Historyinfo.Historylist adapter_history=new Historyinfo.Historylist();
                        history_view.setAdapter(adapter_history);



                    }
                });
            }
        });

    }



    public void setHistory(String History_String){
        String res=History_String;
        Log.v("History_res",res);
        List<String>s=Arrays.asList(res.split(",",history_num*4));
        Log.v("History_2",s.get(19));
        for(int i=0;i<history_num;i++)
        {
            setHistory_date(i,s.get(i*4));
            setHistory_uni(i,s.get(i*4+1));
            setHistory_cumu(i,s.get(i*4+2));
            setHistory_incre(i,s.get(i*4+3));
        }
        Log.v("History_79",s.get(79));
//        Log.v("History_get",getHistory_incre(0));





    }

    public void setHistory_date(int i,String Historydate) {
        String date=Historydate;
        int count=i;
        this.History_date.add(count,date);
    }

    public void setHistory_uni(int i,String Historyuni) {
        String uni=Historyuni;
        int count=i;
        History_uni.add(count,uni);
    }

    public void setHistory_cumu(int i,String Historycumu) {
        String cumu=Historycumu;
        int count=i;
        History_cumu.add(count,cumu);
    }

    public void setHistory_incre(int i,String Historyincre) {
        String incre=Historyincre;
        int count=i;
        History_incre.add(count,incre);
    }

    public String getHistory_date(int i) {
        return this.History_date.get(i);
    }

    public String getHistory_uni(int i) {
        return this.History_uni.get(i);
    }

    public String getHistory_cumu(int i) {
        return this.History_cumu.get(i);
    }

    public String getHistory_incre(int i) {
        return this.History_incre.get(i);
    }

    public String responce_to_HistoryRecord(String Hresponce){

        String res=Hresponce;
        Log.v("History_1",res);
        res=res.replace(" ","");
        List<String> s1=Arrays.asList(res.split(":",4));
        List<String>s2=Arrays.asList(s1.get(1).split(">"));
        res="";
        for(int j=0;j<history_num;j++) {

            for(int i=0;i<4;i++)
            {
                List<String>s3=Arrays.asList(s2.get(22+2*i+16*j).split("<",2));
                if(j<history_num-1|i<3) {
                    res = res + s3.get(0) + ",";
                }
                else
                {
                    res = res + s3.get(0) ;
                }
            }
        }


        return res;

    }


}
