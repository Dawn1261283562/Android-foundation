package com.example.studying;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import java.util.Arrays;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.Request;


public      class Stockinfo extends AppCompatActivity{
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private TextView textView;
    private TextView textView2;
    private ImageView imageview;
    private Stock stockGet;
    private String type="asdasdasd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stockinfo_layout);

        btn1 = (Button) this.findViewById(R.id.Button_01);
        btn2 = (Button) this.findViewById(R.id.Button_02);
        btn3 = (Button) this.findViewById(R.id.Button_03);
        btn4 = (Button) this.findViewById(R.id.Button_04);
        imageview = (ImageView) this.findViewById(R.id.imageView);
        textView=(TextView) this.findViewById(R.id.text);
        textView2=(TextView) this.findViewById(R.id.text2);


        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        stockGet=(Stock) bundle.getSerializable("stockGet");

        System.out.println(stockGet.getId());
        String stockId=stockGet.getId().toString();
        String temp1=stockId.substring(0,6);String temp2=stockId.substring(7,9);
        temp2=temp2.toLowerCase(Locale.ROOT);
        //System.out.println(temp2+temp1);
        String stockIdFormal=temp2+temp1;
        //String stockIdFormal=
        System.out.println(stockIdFormal);
        //String url="http://hq.sinajs.cn/list=sz000002";//723
        String url="http://hq.sinajs.cn/list="+stockIdFormal;//723
        initRequest( url);
        initimage(stockIdFormal );
     btn1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
          String strURL = "http://image.sinajs.cn/newchart/daily/n/"+stockIdFormal+".gif";
       try {
        Bitmap bitmap = getBitmap(strURL);
        imageview.setImageBitmap(bitmap);
       } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
       }
      }
     });
     btn2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
       String strURL = "http://image.sinajs.cn/newchart/weekly/n/"+stockIdFormal+".gif";
       Log.i("883333","false");
       try {
        Log.i("443333","false");
        Bitmap bitmap = getBitmap(strURL);
        Log.i("553333","false");
        imageview.setImageBitmap(bitmap);
        Log.i("663333","false");
       } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        Log.i("333333","false");
       }
      }
     });
     btn3.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
       String strURL = "http://image.sinajs.cn/newchart/min/n/"+stockIdFormal+".gif";
       Log.i("883333","false");
       try {

        Bitmap bitmap = getBitmap(strURL);

        imageview.setImageBitmap(bitmap);

       } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        Log.i("333333","false");
       }
      }
     });
     btn4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String strURL = "http://image.sinajs.cn/newchart/monthly/n/"+stockIdFormal+".gif";
                Log.i("883333","false");
                try {
                    Log.i("443333","false");
                    Bitmap bitmap = getBitmap(strURL);
                    Log.i("553333","false");
                    imageview.setImageBitmap(bitmap);
                    Log.i("663333","false");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.i("333333","false");
                }
            }
        });
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

             HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {

             @Override
             public void onFailure(Call call, IOException e) {
             }

             @Override
             public void onResponse(Call call, final Response response) throws IOException {
                 final String res=response.body().string();
                 final String res2=res.replace(" ", "");

                 List<String> list= Arrays.asList(res2.split(","));
                 final String res3=list.get(0);

                 List<String> list2= Arrays.asList(res3.split("\""));

                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         textView.setText(Html.fromHtml( "<font color=\"#000000\"><big><big>"
                                 + list2.get(1) + "</big></big></font>"
                                 +"<font>"
                                 +"当前价格："+list.get(2)+ "<br>"
                                 +"开盘：" +list.get(1)+"今日最高："+list.get(4)+"<br>"
                                 +"收盘：" +list.get(2)+"今日最低："+list.get(5)
                                 + "</font>"));

                         final double temp1 =  Double.valueOf(list.get(8)).doubleValue()/100;
                         final double temp2 =  Double.valueOf(list.get(9)).doubleValue()/10000;

                         java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");

                         String num1 =myformat.format(temp1);
                         String num2 =myformat.format(temp2);

                         textView2.setText(Html.fromHtml("<font>"
                                 +"今日成交：" +num1+"百股"+"   总额："+num2+"万元"+"<br>"
                                 +"买一：" +list.get(11)+"股数："+list.get(10) +"股"+" 卖一：" +list.get(21)+"股数："+list.get(20)+"<br>"
                                 +"买二：" +list.get(13)+"股数："+list.get(12) +"股"+" 卖二：" +list.get(23)+"股数："+list.get(22)+"<br>"
                                 +"买三：" +list.get(15)+"股数："+list.get(14) +"股"+" 卖三：" +list.get(25)+"股数："+list.get(24)+"<br>"
                                 +"买四：" +list.get(17)+"股数："+list.get(16) +"股"+" 卖四：" +list.get(27)+"股数："+list.get(26)+"<br>"
                                 +"买五：" +list.get(19)+"股数："+list.get(18) +"股"+" 卖五：" +list.get(29)+"股数："+list.get(28)
                                 + "</font>"));

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

 }
