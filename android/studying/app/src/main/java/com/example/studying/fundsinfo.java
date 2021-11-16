package com.example.studying;

import com.example.studying.entity.FundHeavy;
import com.example.studying.entity.FundHeavyInfo;
import com.example.studying.entity.Stock;
import com.example.studying.utils.HttpGetRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public      class fundsinfo extends AppCompatActivity{

    private Button btn1,btn2, btn3,btn4;

    private TextView textView_fundsname;
    private TextView textcode_style;
    private TextView textView_value_now;
    private TextView title_history;
    private TextView title_heavy;
    private TextView title_;

    private boolean Collection;

    private ImageView imageview;
    ListView listview;
    ListView listview_History;
    private ArrayList<FundHeavy> fundHeavyList=new ArrayList<FundHeavy>();

    Handler mHandler;

    private  String fundsname="";
    //重仓股id
    public List<String>Heavy_id=new ArrayList<>();
    //重仓股名称
    public List<String>Heavy_name=new ArrayList<>();
    //重仓股票的报价
    public List<String>Heavy_price=new ArrayList<>();
    //重仓占比
    public List<String>Heavy_ratio=new ArrayList<>();
    //板块
    public List<String>Heavy_style=new ArrayList<>();


    //日期
    public List<String>History_date=new ArrayList<>();
    //单位净值
    public List<String>History_uni=new ArrayList<>();
    //累计净值
    public List<String>History_cumu=new ArrayList<>();
    //日涨幅
    public List<String>History_incre=new ArrayList<>();




    private int heavystock_num=10;
    private int history_num=5;
    private int heavystyle_num=4;
    private String address="http://40q522n410.qicp.vip/";

    private FundHeavyInfo fundsGet;
    private String addressPL="http://43m486x897.yicp.fun/";
    String code="000031";
    String username="15361022831";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundsinfo_layout);


        btn1 = (Button) this.findViewById(R.id.Button_01);
        btn2 = (Button) this.findViewById(R.id.Button_02);
        btn3 = (Button) this.findViewById(R.id.Button_03);
        btn4 = (Button) this.findViewById(R.id.Button_04);
        listview=(ListView) this.findViewById(R.id.list_view);
        listview_History=(ListView)this.findViewById(R.id.history_view);
        imageview = (ImageView) this.findViewById(R.id.imageView);
        textView_fundsname = (TextView) this.findViewById(R.id.funds_name);
        textcode_style = (TextView) this.findViewById(R.id.code_style);
        textView_value_now = (TextView) this.findViewById(R.id.value_now);
        title_history=(TextView)this.findViewById(R.id.title_history);
        title_heavy=(TextView)this.findViewById(R.id.title_heavy);
        title_=(TextView)this.findViewById(R.id.Title);




        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());


        Intent intent = this.getIntent();
        Bundle bundle=intent.getExtras();
        fundsGet= (FundHeavyInfo)bundle.getSerializable("fundsGet");

        String code=fundsGet.getId();
        Data data = (Data)getApplicationContext();
        username=data.getUsername();

        System.out.println(fundsGet.getId());


        init_info(code);

//        init_Collection();



    }
    //重仓表的适配器类
    class Heavylist extends BaseAdapter {

        @Override
        public int getCount() {
            return heavystock_num+1;
        }

        @Override
        public Object getItem(int position) {
            if(position==0)
            {
                return "此为属性名称";
            }
            else
            {
                return Heavy_name.get(position-1)+Heavy_price.get(position-1)+Heavy_ratio.get(position-1);
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View v=View.inflate(fundsinfo.this,R.layout.listview_textview,null );
            TextView t1=v.findViewById(R.id.text1);
            TextView t2=v.findViewById(R.id.text2);
            TextView t3=v.findViewById(R.id.text3);
            if(position==0){
                t1.setText("股票名称");
                t2.setText("价格");
                t3.setText("持仓占比");
            return v;}
            else
            {
                t1.setText(fundHeavyList.get(0).get_stock_id()[position-1]);
                t2.setText(fundHeavyList.get(0).get_stock_all_Type()[position-1]);
                t3.setText(fundHeavyList.get(0).get_stock_ratio()[position-1]);
                return v;
            }
        }
    }
    //历史净值得适配器类
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
                return getHistory_date(position-1)+","+getHistory_uni(position-1)+","+getHistory_cumu(position-1)+","+getHistory_incre(position-1);
            }

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View v=View.inflate(fundsinfo.this,R.layout.history_listview,null );
            TextView t1=v.findViewById(R.id.text1);
            TextView t2=v.findViewById(R.id.text2);
            TextView t3=v.findViewById(R.id.text3);
            TextView t4=v.findViewById(R.id.text4);

            if(position!=0){

                t1.setText(getHistory_date(position-1));
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

    //



    //初始化基金信息页函数
    private void init_info(String code) {


        mHandler=new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        fundHeavyList.get(0).get_stock_all_Type();

                        System.out.println(123);
                        fundsinfo.Heavylist adapter=new fundsinfo.Heavylist();

                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            //参数三：位置，即点击的是第几个Item
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //拿到点击的新闻对应的链接

                                Log.v("click_","click"+position);
                                if(position==0)
                                {}
                                else{
//                                    Intent intent=new Intent(fundsinfo.this,PageFragment1.class);
//                                    String stock_id=getHeavy_id(position-1);
//                                    intent.putExtra(stock_id, stock_id);
//                                    startActivity(intent);
                                }

                            }
                        });
                }
            }
        };
        init_History(code);
        init_fundsinfo(code);
        initimage(code);
        button_listen();

    }
    //！！初始化基金信息页函数

    //各类监听函数
    //各类button的监听函数
    public void button_listen() {
        btn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String strURL = "https://j3.dfcfw.com/images/SYL2/"+code+".png?v=163642633545544";
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
                String strURL = "http://j3.dfcfw.com/images/JJJZ2/"+code+".png";
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
                String strURL = "http://image.sinajs.cn/newchart/min/n/sz"+code+".gif";
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
        btn4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String strURL = "http://j4.dfcfw.com/charts/pic6/"+code+".png?v=20211109040103";
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

    //！！各类button的监听函数
    //！！各类监听函数



    //各类局部初始化函数
    //初始化基金信息
    public void init_fundsinfo(String code){

        System.out.println(code);
        String fundsurl="http://43m486x897.yicp.fun/fundHeavy/getById?id="+code+".OF";
        System.out.println(fundsurl);

        HttpGetRequest.sendOkHttpGetRequest(fundsurl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                //Toast.makeText(MainActivity.this, "post请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody data = response.body();
                String strByJson = response.body().string();
                System.out.println(strByJson);
                if(strByJson.equals("[null]"))return;
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

                Gson gson = new Gson();
                ArrayList<FundHeavy> userBeanList = new ArrayList<>();
                System.out.println(strByJson);
                //加强for循环遍历JsonArray
                for (JsonElement fundHeavy : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    FundHeavy userBean = gson.fromJson(fundHeavy, FundHeavy.class);
                    userBeanList.add(userBean);

                    System.out.println("这下面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度（一共十个优质基金）");
                    System.out.println(userBean.id);
                    System.out.println(userBean.name);
                    System.out.println(userBean.getScore());
                    System.out.println(Arrays.toString(userBean._stock_id));
                    System.out.println(Arrays.toString(new String[]{userBean._stock_id[1]}));
                    System.out.println(Arrays.toString(userBean._stock_ratio));
                    System.out.println(Arrays.toString(new String[]{userBean._stock_ratio[1]}));
                    System.out.println(userBean.hits);
                    System.out.println("这上面是 基金代码、名字、评分、十股票代码、十股票比例、二号股票比例、热度");
                }
                fundHeavyList=userBeanList;
                Looper.prepare();

                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);

                System.out.println(data);
                Toast.makeText( fundsinfo.this, strByJson, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
//                    @Override
//                    public void run() {
//    //                    textView_fundsname.setText(res);
//
//
//
////                //        set 所有的重仓股id 形式如000001.sz
//                        setAll_id(res);
//                        Log.v("heavy_id_4",getHeavy_id(9));
//
////                        set 所有的重仓占比
//                        setAll_style_ratio(res);
//
//                        Log.v("heavy_ratio_5",getHeavy_ratio(9));
////                        Log.v("heavy_style",getHeavy_style(3));
//////                        初始化重仓股信息
//                        init_heavystock_info();
//////
//                        init_text(res);
//
//
//                                          }


    }

//    初始化所有textview1信息
    public  void init_text(String funds_res){
        textView_fundsname.setText(Html.fromHtml("<font color=\"#000000\" size=20>"+responce_to_fundsname(funds_res)+"</font>"));
        textcode_style.setText(code+getHeavy_style(0)+getHeavy_style(1)+getHeavy_style(2)+getHeavy_style(3));
        textView_value_now.setText(getHistory_incre(0)+getHistory_date(0)+getHistory_uni(0));

    }
    //初始化历史净值信息
    public void init_History(String code) {

        String hurl= "https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code="+code+"&page=1&sdate=2020-11-12&edate=2021-11-12&per="+history_num;
        HttpGetRequest.sendOkHttpGetRequest(hurl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res=response.body().string();

                Log.v("History_",res);
                setHistory(responce_to_HistoryRecord(res));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        fundsinfo.Historylist adapter_history=new fundsinfo.Historylist();
                        listview_History.setAdapter(adapter_history);


                    }
                });
            }
        });

    }
    //初始化图片
    public void initimage(String code) {

        String image_code=code;
        String strURL = "http://j4.dfcfw.com/charts/pic6/"+image_code+".png?v=20211109040103";
        try {
            Bitmap bitmap = getBitmap(strURL);
            imageview.setImageBitmap(bitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //初始化重仓信息
    public void init_heavystock_info() {

        String url=all_url();

        Log.v("all_url_",all_url());
        HttpGetRequest.sendOkHttpGetRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res=response.body().string();

                setAll_name_price(res);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });

    }

    //！！各类局部初始化函数

    public void init_Collection(){
        setCollection(false);
        String Collection_url=addressPL+"collection/getListByUser?username="+username;
        HttpGetRequest.sendOkHttpGetRequest(Collection_url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res=response.body().string();


                Log.v("Collection_",res);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {

                    }

                });


            }
        });

    }





//各类set函数
//setAll类函数
//set所有重仓id
public void setAll_id(String funds_res){
    String res=funds_res;

    Log.v("heacy_id_2",response_to_stock_id(res));
        List<String>list1=Arrays.asList(response_to_stock_id(res).split(",",10));

        for(int i=0;i<10;i++)
        {
            setHeavy_id(i,list1.get(i));
        }
        Log.v("tatata",getHeavy_id(0));
}

    //set所有重仓name,price
    public void setAll_name_price(String heavyinfo_responce){
        String res=heavyinfo_responce;

        List<String>list=Arrays.asList(heavyresponce_to_allstock_displayed(res).split(",",20));

        for(int i=0;i<heavystock_num;i++)
        {
            setHeavy_name(i,list.get(i*2));
            setHeavy_price(i,list.get(i*2+1));
        }

    }

    //set所有重仓name,price
    public void setAll_style_ratio(String funds_res){
        String res=funds_res;
        Log.v("heavy_ratio_1",res);
        List<String>s=Arrays.asList(responce_to_heavystock_ratio(res).split(",",heavystock_num));
        Log.v("heavy_ratio_2",s.get(heavystock_num-1));
        for(int i=0;i<heavystock_num;i++)
        {
            setHeavy_ratio(i,s.get(i));
        }
        List<String>s2=Arrays.asList(responce_to_Heavystyle(res).split(",",heavystyle_num));

        Log.v("heavy_style_0",s2.get(1));
        for(int i=0;i<heavystyle_num;i++)
        {
            setHeavy_style(i,s2.get(i));
        }

        Log.v("heavy_style_1",getHeavy_style(heavystyle_num-1));

    }

    //！！setAll类函数

    //各类set单个数据函数
    //set Heavy_price,Heavy_name,Heavy_ratio,Heavy_id
    public void setHeavy_price(int i,String Heavyprice) {
        String price=Heavyprice;
        int count=i;
        Heavy_price.add(count,price);

    }

    public void setHeavy_name(int i,String Heavyname) {
        String name=Heavyname;
        int count=i;
        Heavy_name.add(count,name);

    }

    public void setHeavy_ratio(int i,String Heavyratio) {
        String ratio=Heavyratio;
        int count=i;
        Heavy_ratio.add(count,ratio);

    }

    public void setHeavy_id(int i,String s) {
        this.Heavy_id.add(i,s);
    }

    public void setHeavy_style(int i,String s) {
        this.Heavy_style.add(i,s);
    }

    public void setCollection(boolean funds_collection){this.Collection=funds_collection;}



//    public void setHistory_num(int num) {
//        this.History_num=num;
//    }

    public void setFundsname(String funds_name){this.fundsname=funds_name;}


    public void setHistory(String History_String){
        String res=History_String;
        List<String>s=Arrays.asList(res.split(",",20));
        for(int i=0;i<5;i++)
        {
            setHistory_date(i,s.get(i*4));
            setHistory_uni(i,s.get(i*4+1));
            setHistory_cumu(i,s.get(i*4+2));
            setHistory_incre(i,s.get(i*4+3));
        }





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





    //！！各类set单个数据函数
    //！！各类set函数

    //各类get函数
    //各类get单数据函数
    //get Heavy_price,Heavy_name,Heavy_ratio,Heavy_id
    public String getHeavy_name(int i) {
        int num=i;
        String stock_name=Heavy_name.get(num);
        return stock_name;

    }

    public String getHeavy_price(int i) {
        int num=i;
        String stock_price=Heavy_price.get(num);
        return stock_price;

    }

    public String getHeavy_ratio(int i) {
        int num=i;
        String stock_ratio=Heavy_ratio.get(num);
        return stock_ratio;

    }

    public String getHeavy_id(int i) {
        return this.Heavy_id.get(i);
    }

    public String getHeavy_style(int i) {
        return this.Heavy_style.get(i);
    }

    public boolean getCollection(){return this.Collection;}

//    public int getHistory_num() {
//        return this.History_num;
//    }

    public String getFundsname(){return this.fundsname;}

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

    //！！各类get单数据函数

    //杂
    //返回一条请求十条重仓股的url
    public String all_url() {

        String all_url= "http://hq.sinajs.cn/list="+stock_id_to_stocklist(Heavy_id.get(0));
        for (int i=1;i<10;i++)
        {
            all_url=all_url+","+stock_id_to_stocklist(Heavy_id.get(i));
        }

        return all_url;
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
    //！！杂
    //！！各类get函数


    //工具函数
    //数据转换类函数由 A to B
    //把请求十只股票的报文转换为重仓股票姓名，今日报价形式的String
    public String heavyresponce_to_allstock_displayed(String heavyresponce){
        String res=heavyresponce;
        res=res.replace(" ","");

        List<String>s1=Arrays.asList(res.split("\"",21));

        String s_all="";

        for(int n=0;n<heavystock_num;n++){

            List<String>s_onestock=Arrays.asList(s1.get(2*n+1).split(",",3));
            s_all=s_all+s_onestock.get(0)+","+s_onestock.get(1);
            if(n!=heavystock_num-1){
                s_all=s_all+",";
            }
        }

        return s_all;
    }

    //转换 id to code ，url ，list
    public String stock_id_to_stocklist(String stock_id) {
        String id=stock_id;
        List<String>list=Arrays.asList(id.split("\\."));
        String Stocklist= list.get(1).toLowerCase() + list.get(0);
        return Stocklist;
    }

    public String stock_id_to_stockcode(String stock_id) {
        String id=stock_id;
        List<String>list=Arrays.asList(id.split("\\."));
        String code=list.get(0);
        return code;
    }

    public String stock_id_to_url(String stock_id) {
        String id=stock_id;


        String stockurl= "http://hq.sinajs.cn/list="+stock_id_to_stocklist(id);
        return stockurl;
    }

    //处理重仓请求的报文返回一个由id和，组成的String
    public String  response_to_stock_id(String heavy) {

        Log.v("heavy_id_3",heavy);
        String res1=heavy;
        final String res2=res1.replace(" ","");
        List<String>list=Arrays.asList(res2.split("\""));

        List<String>list1=Arrays.asList(res2.split("\\["));
        final String res3=list1.get(5);
        Log.v("heavy_id_3",res3);
        List<String>list2=Arrays.asList(res3.split("]"));
        final String res4=list2.get(0).replace("\"","");
        Log.v("heavy_id_3",res4);
        return res4;
    }

    //处理重仓报文返回格式为ratio，ratio，ratio.....的重仓占比String
    public String  responce_to_heavystock_ratio(String heavy) {
        String res=heavy;

        res=res.replace(" ","");
        List<String>s1=Arrays.asList(res.split("\\[",5));

        List<String>s2=Arrays.asList(s1.get(4).split("]",2));
        res=s2.get(0).replace("\"","");
        return res;
    }

    //处理历史净值报文返回净值日期，单位净值，累计净值，日增长率的String字符串
    public String responce_to_HistoryRecord(String Hresponce){

        String res=Hresponce;
        res=res.replace(" ","");
        List<String>s1=Arrays.asList(res.split(":",4));
        List<String>s2=Arrays.asList(s1.get(1).split(">"));
        res="";
        for(int j=0;j<5;j++) {

            for(int i=0;i<4;i++)
            {
                List<String>s3=Arrays.asList(s2.get(22+2*i+16*j).split("<",2));
                if(j<4|i<3) {
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

    public String responce_to_fundsname(String heavy) {
        List<String>s=Arrays.asList(heavy.split("\"",11));
        return s.get(9);
    }

    public String responce_to_Heavystyle(String funds_res) {

        String res = funds_res;

        res = res.replace(" ", "");
        List<String> list = Arrays.asList(res.split("\\[", 7));
        res = list.get(3).replace("\"", "");
        Log.v("heavy_style_2", res);
        List<String> list1 = Arrays.asList(res.split(",", heavystyle_num + 1));
        res = "";
        for (int i = 0; i < heavystyle_num; i++) {
            if (i == 0) {
                res = res + list1.get(i);
            } else {
                res = res + "," + list1.get(i);
            }

        }
        return res;
    }

    public boolean responce_to_Collection_id(String Collection_res){

        String res=Collection_res;

//        int Collection_num=res.length()-res.replace("\\{","").length();
//        Log.v("Collection_num",Collection_num+"");



        return true;
    }


//！！数据转换类函数由 A to B

    //！！数据转换类函数由 A to B
    //工具函数




}