package com.example.studying;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.Request;


public      class Stockinfo extends AppCompatActivity{

    private TextView textView;
    private TextView text_price_now,taa,text_price_today,text_price_yest,text_price_h,text_price_l,text_price_total,text_price_num;
    private TextView textView_code;
    private TextView textView2;
    private TextView textView3;
    private ImageView imageview;
    private ListView listView_bargin;

    private String barginString;
    private Stock stockGet;
    private String type="asdasdasd";
    private TabLayout mTabTl;
    private int barginnum=11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stockinfo_layout);


        imageview = (ImageView) this.findViewById(R.id.imageView);
        textView=(TextView) this.findViewById(R.id.text);
        text_price_now=(TextView)this.findViewById(R.id.price_now);
        text_price_today=(TextView)this.findViewById(R.id.price_beginning);
        text_price_yest=(TextView)this.findViewById(R.id.price_yest);
        text_price_h=(TextView)this.findViewById(R.id.h_value);
        text_price_l=(TextView)this.findViewById(R.id.l_value);
        text_price_total=(TextView)this.findViewById(R.id.totalvalue);
        text_price_num=(TextView)this.findViewById(R.id.totalnum);
        taa=(TextView)this.findViewById(R.id.aa);
        mTabTl=(TabLayout)this.findViewById(R.id.Stockinfo_tab) ;

//        textView2=(TextView) this.findViewById(R.id.text2);
//        textView3=(TextView) this.findViewById(R.id.text3);
        textView_code=(TextView) this.findViewById(R.id.code_style);

        listView_bargin=(ListView)this.findViewById(R.id.View_bargin);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        stockGet=(Stock) bundle.getSerializable("stockGet");

        System.out.println(stockGet.getId());
        String stockId=stockGet.getId().toString();
        String temp1=stockId.substring(0,6);
        String temp2=stockId.substring(7,9);
//
//        String temp1="000031";
//        String temp2="sz";

        temp2=temp2.toLowerCase(Locale.ROOT);
//        //System.out.println(temp2+temp1);
        String stockIdFormal=temp2+temp1;
//        //String stockIdFormal=
//        System.out.println(stockIdFormal);
//        //String url="http://hq.sinajs.cn/list=sz000002";//723
        String url="http://hq.sinajs.cn/list="+stockIdFormal;//723
        Log.v("url_",url);
//        String url="http://hq.sinajs.cn/list=sz000002";
        Log.v("stock_",url);
        initRequest(url);
        textView_code.setText(temp1);

        initimage(stockIdFormal );

        mTabTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        String strURL0 = "http://image.sinajs.cn/newchart/daily/n/"+stockIdFormal+".gif";
                        try {
                            Bitmap bitmap = getBitmap(strURL0);
                            imageview.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Log.v("funds_name","funds_name");
                        break;
                    case 1:
                        String strURL1 = "http://image.sinajs.cn/newchart/weekly/n/"+stockIdFormal+".gif";
                        Log.i("883333","false");
                        try {
                            Log.i("443333","false");
                            Bitmap bitmap = getBitmap(strURL1);
                            Log.i("553333","false");
                            imageview.setImageBitmap(bitmap);
                            Log.i("663333","false");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.i("333333","false");
                        }
                        Log.v("funds_name","funds_name2");
                        break;
                    case 2:
                        String strURL2 = "http://image.sinajs.cn/newchart/min/n/"+stockIdFormal+".gif";
                        Log.i("883333","false");
                        try {

                            Bitmap bitmap = getBitmap(strURL2);

                            imageview.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.i("333333","false");
                        }
                        Log.v("funds_name","funds_name3");

                        break;
                    case 3:
                        String strURL5 = "http://image.sinajs.cn/newchart/monthly/n/"+stockIdFormal+".gif";
                        Log.i("883333","false");
                        try {
                            Log.i("443333","false");
                            Bitmap bitmap = getBitmap(strURL5);
                            Log.i("553333","false");
                            imageview.setImageBitmap(bitmap);
                            Log.i("663333","false");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.i("333333","false");
                        }
                        Log.v("funds_name","funds_name");
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {



            }

        });


    }


    class bargin extends BaseAdapter {

        @Override
        public int getCount() {
            return barginnum+1;
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
        public boolean isEnabled(int position) {
            if(position==0){
                return false;
            }
            return super.isEnabled(position);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View v=View.inflate(Stockinfo.this,R.layout.stock_bagin_view,null );
            TextView t1=v.findViewById(R.id.text1);
            TextView t2=v.findViewById(R.id.text2);
            TextView t3=v.findViewById(R.id.text3);

            t2.setTextColor(Color.parseColor("#c92027"));
            List<String>bargin_list=Arrays.asList(getBarginString().split(","));
            Log.v("bargin_",bargin_list.get(29));


            if(position!=0){
                if(position<=1){

                    t1.setText("类别");
                    t2.setText("金额/元");
                    t3.setText("股数/手");
                    t1.setTextColor(getResources().getColor(R.color.black));
                    t2.setTextColor(getResources().getColor(R.color.black));
                    t3.setTextColor(getResources().getColor(R.color.black));
                    t1.setTextSize(16);
                    t2.setTextSize(16);
                    t3.setTextSize(16);


                    return v;
                }
                else if(position<=6){

                    t1.setText(position_to_barginstyle(position));
                    t2.setText(bargin_list.get(33-2*position));
                    t3.setText(wrapnum(bargin_list.get(32-2*position)));
                    return v;
                }
                else{

                    t1.setText(position_to_barginstyle(position));
                    t2.setText(bargin_list.get(2*position-3));
                    t3.setText(wrapnum(bargin_list.get(2*position-4)));

                    return v;
                }

            }
            else
            {




                t1.setTextColor(getResources().getColor(R.color.black));
//                t1.setTextColor(andriod.graphics.Color.parseColor("#000000"));
                t1.setTextSize(16);
                t1.setText(position_to_barginstyle(position));
                t2.setText(wrapamount(bargin_list.get(9))+"万元");
                t3.setText(wrapnum(bargin_list.get(8))+"手");

                return  v;
            }
        }
    }
    public Bitmap getBitmap(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    private void initRequest( String URL) {
        String url =URL;

        Log.v("stock_1",url);
        HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("stock_","res");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String res=response.body().string();
                Log.v("stock_",res);
                String res2=res.replace(" ", "");


                setBarginString(res2);
                Log.v("bargin_",getBarginString());
                List<String> list= Arrays.asList(res2.split(","));
                final String res3=list.get(0);

                List<String> list2= Arrays.asList(res3.split("\""));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        Stockinfo.bargin adapter_bargin=new Stockinfo.bargin();
                        listView_bargin.setAdapter(adapter_bargin);



                        final double temp1 =  Double.valueOf(list.get(8)).doubleValue()/100;
                        final double temp2 =  Double.valueOf(list.get(9)).doubleValue()/10000;

                        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");

                        String num1 =myformat.format(temp1);
                        String num2 =myformat.format(temp2);


                        Double price_now=Double.parseDouble(list.get(3));
                        Double price_yest=Double.parseDouble(list.get(2));
                        if(price_now>price_yest){
                            text_price_now.setTextColor(Color.parseColor("#66cc99"));
                        }
                        else{
                            text_price_now.setTextColor(Color.parseColor("#cc0033"));
                        }
                        taa.setText("当前价格:");
                        textView.setText( list2.get(1));
                        text_price_now.setText(list.get(3));


                        if(Double.parseDouble(list.get(1))>Double.parseDouble(list.get(2))){
                            text_price_today.setTextColor(Color.parseColor("#66cc99"));
                        }
                        else{
                            text_price_today.setTextColor(Color.parseColor("#cc0033"));
                        }
                        text_price_today.setText("开盘价:"+list.get(1));
                        text_price_yest.setText("收盘价:"+list.get(2));
                        text_price_h.setText("最高价:"+list.get(4));
                        text_price_l.setText("最低价:"+list.get(5));


//                        text_price_total.setText("总额:"+num2+"万");
//                        text_price_num.setText("总手:"+num1+"百股");
//                        textView2.setText(Html.fromHtml("<font>"
//                                +"今日成交：" +num1+"百股"+"<br>"
//                                +"买一：" +list.get(11)+"股数："+list.get(10) +"股"+"<br>"
//                                +"买二：" +list.get(13)+"股数："+list.get(12) +"股"+"<br>"
//                                +"买三：" +list.get(15)+"股数："+list.get(14) +"股"+"<br>"
//                                +"买四：" +list.get(17)+"股数："+list.get(16) +"股"+"<br>"
//                                +"买五：" +list.get(19)+"股数："+list.get(18) +"股"
//                                + "</font>"));
//                        textView3.setText(Html.fromHtml("<font>"
//                                +"总额："+num2+"万元"+"<br>"
//                                +" 卖一：" +list.get(21)+"股数："+list.get(20)+"<br>"
//                                +" 卖二：" +list.get(23)+"股数："+list.get(22)+"<br>"
//                                +" 卖三：" +list.get(25)+"股数："+list.get(24)+"<br>"
//                                +" 卖四：" +list.get(27)+"股数："+list.get(26)+"<br>"
//                                +" 卖五：" +list.get(29)+"股数："+list.get(28)
//                                + "</font>"));

                    }
                });
            }
        });
    }

    public void initimage(String stockIdFormal) {

        String strURL = "http://image.sinajs.cn/newchart/daily/n/"+stockIdFormal+".gif";
        try {
            Bitmap bitmap = getBitmap(strURL);
            imageview.setImageBitmap(bitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setBarginString(String b){
        this.barginString=b;
    }
    public String getBarginString(){
        return this.barginString;
    }

    public String position_to_barginstyle(int p){
        switch (p){
            case 0:
                return "总成交";
            case 2:
                return "卖五";
            case 3:
                return "卖四";
            case 4:
                return "卖三";
            case 5:
                return "卖二";
            case 6:
                return "卖一";
            case 7:
                return "买一";
            case 8:
                return "买二";
            case 9:
                return "买三";
            case 10:
                return "买四";
            case 11:
                return "买五";
            default:
                return "null";

        }

    }
    public String wrapamount(String a){

        final double temp2 =  Double.valueOf(a).doubleValue()/10000;

        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");


        String num2 =myformat.format(temp2);
        return num2;}
    public String wrapnum(String n){
        final double temp1 =  Double.valueOf(n).doubleValue()/100;


        java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");

        String num1 =myformat.format(temp1);
        return num1;
    };


}

