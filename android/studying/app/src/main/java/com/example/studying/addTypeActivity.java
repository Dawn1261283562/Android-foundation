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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

public class addTypeActivity extends AppCompatActivity {
    private String setOfAllType="2025规划,3D玻璃,3D打印,3D摄像头,5G概念,6G概念,AB股,AH股,C2M概念,CAR-T细胞疗法,CRO,EDA概念," +
            "eSIM,ETC,GDR,HIT电池,HS300_,IPO受益,IPv6,LED,MicroLED,MiniLED,MLCC,MSCI中国,NFT概念,OLED,PCB,PPP模式,QFII重仓," +
            "RCEP概念,RCS概念,REITs概念,ST股,UWB概念,VPN,WiFi,阿里概念,阿兹海默,白酒,百度概念,半导体,保险业,北斗导航,北交所概念,北京冬奥," +
            "被动元件,边缘计算,贬值受益,标准普尔,滨海新区,病毒防治,彩票概念,参股保险,参股期货,参股券商,参股新三板,参股银行,餐饮业,仓储业,草甘膦," +
            "超导概念,超级电容,超级品牌,超级真菌,超清视频,车联网,成渝特区,充电桩,宠物经济,抽水蓄能,储能,畜牧业,传感器,创投,创业板综,创业成份," +
            "磁悬浮概念,次新股,大飞机,大数据,代糖概念,单抗概念,氮化镓,刀片电池,道路运输业,地热能,地塞米松,地摊经济,低碳冶金,第三代半导体," +
            "电力、热力生产和供应业,电气机械和器材制造业,电商概念,电信、广播电视和卫星传输服务,电子车牌,电子竞技,电子烟,东北振兴,抖音小店," +
            "独家药品,独角兽,房地产业,房屋建筑业,纺织服装、服饰业,纺织业,非金属矿采选业,非金属矿物制品业,废弃资源综合利用业,分拆预期,风能," +
            "氟化工,辅助生殖,富时罗素,富士康,肝素概念,高送转,工程机械,工业4.0,工业大麻,工业互联,工业母机,工业气体,公共设施管理业,共享经济," +
            "股权激励,股权转让,固态电池,光伏建筑一体化,光刻胶,广播、电视、电影和影视录音制作业,广电,国产软件,国产芯片,国家安防,国企改革,国资云概念," +
            "海工装备,海绵城市,海洋经济,氦气概念,航空运输业,航母概念,航天概念,核能核电,核污染防治,黑色金属矿采选业,黑色金属冶炼和压延加工业,鸿蒙概念," +
            "湖北自贸,互联金融,互联网和相关服务,互联医疗,沪股通,沪企改革,华为概念,华为汽车,华为昇腾,化工原料,化学纤维制造业,化学原料和化学制品制造业," +
            "化妆品概念,换电概念,黄金概念,蝗虫防治,货币金融服务,机动车、电子产品和日用产品修理业,机构重仓,机器视觉,鸡肉概念,基本金属,基金重仓," +
            "基因测序,激光雷达,计算机、通信和其他电子设备制造业,家具制造业,建筑安装业,建筑装饰和其他建筑业,健康中国,降解塑料,教育,节能环保," +
            "金属制品业,进口博览,京东金融,京津冀,精准医疗,酒、饮料和精制茶制造业,举牌,军工,军民融合,开采辅助活动,科技推广和应用服务业,壳资源," +
            "可燃冰,空间站概念,口罩,快递概念,快手概念,垃圾分类,蓝宝石,冷链物流,锂电池,量子通信,林业,磷化工,零售业,流感,蚂蚁概念,盲盒经济," +
            "毛发医疗,茅指数,煤化工,煤炭开采和洗选业,免税概念,免疫治疗,木材加工和木、竹、藤、棕、草制品业,纳米银,钠离子电池,内贸流通," +
            "宁组合,农、林、牧、渔服务业,农副食品加工业,农业,农业种植,批发业,皮革、毛皮、羽毛及其制品和制鞋业,拼多多概念,苹果概念,屏下摄像," +
            "其他金融业,其他制造业,汽车拆解,汽车芯片,汽车制造业,青蒿素,氢能源,区块链,全息技术,券商概念,燃料电池,燃气生产和供应业,人工智能," +
            "人脑工程,人造肉,融资融券,乳业,软件和信息技术服务业,赛马概念,商汤概念,商务服务业,上海自贸,上证180_,上证380,上证50_,社保重仓," +
            "社区团购,深成500,深股通,深圳特区,深证100R,生态保护和环境治理业,生态农业,生物识别,生物疫苗,石墨烯,石油和天然气开采业," +
            "石油加工、炼焦和核燃料加工业,食品安全,食品制造业,手游概念,纾困概念,数据中心,数字货币,数字孪生,数字阅读,水产养殖,水的生产和供应业," +
            "水利建设,水上运输业,送转预期,胎压监测,太阳能,钛白粉,碳化硅,碳基材料,碳交易,特高压,特斯拉,体外诊断,体育,体育产业,天基互联,天然气," +
            "铁路、船舶、航空航天和其他运输设备制造业,铁路基建,铁路运输业,通用航空,通用设备制造业,土地流转,土木工程建筑业,退税商店,万达概念," +
            "网红直播,网络安全,网络游戏,维生素,尾气治理,卫生,文化艺术业,文教、工美、体育和娱乐用品制造业,无人机,无人驾驶,无线充电,无线耳机,物联网," +
            "稀缺资源,稀土永磁,乡村振兴,橡胶和塑料制品业,消毒剂,小金属,小米概念,新材料,新零售,新能源,新能源车,新闻和出版业,雄安新区,虚拟电厂," +
            "虚拟现实,研究和试验发展,盐湖提锂,央视50_,养老概念,养老金,页岩气,一带一路,医废处理,医疗美容,医疗器械,医药制造业,仪器仪表制造业," +
            "移动支付,疫苗冷链,印刷和记录媒介复制业,婴童概念,影视概念,邮政业,油价相关,油气设服,有机硅,有色金属矿采选业,有色金属冶炼和压延加工业," +
            "渔业,预亏预减,预盈预增,元宇宙概念,远程办公,粤港自贸,云计算,云游戏,在线教育,在线旅游,造纸和纸制品业,增强现实,债转股,长江三角,长寿药," +
            "证金持股,知识产权,植物照明,智慧城市,智慧政务,智能穿戴,智能电视,智能电网,智能机器,智能家居,中超概念,中芯概念,中药,中证500,中字头," +
            "猪肉概念,住宿业,注册制次新股,注射器概念,专精特新,专业技术服务业,专用设备制造业,转基因,转债标的,装配建筑,装卸搬运和运输代理业," +
            "资本市场服务,字节概念,综合,租赁业,租售同权";

    private EditText editText;
    private Button searchBut;
    private Button addMoreBut;
    private Button finishAddBut;
    private ImageView searchImageView;
    private ImageButton clearTextButton;

    private String[] setOfAllTypeArray;
    private ArrayList<String> typeList;
    private ArrayList<String> selectedTypeList;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private FlowLayout flowLayout;
    private FlowLayout.Adapter flowAdapter;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_type);
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

        initView();
        initEvent();
        initDate();

        Intent intent2=new Intent();
        Bundle bundle2=new Bundle();
        bundle2.putSerializable("typeListAdd", selectedTypeList);
        intent2.putExtras(bundle2);
        setResult(Activity.RESULT_OK,intent2);
    }

    private void initDate() {
        editText.setVisibility(View.VISIBLE);
        searchBut.setVisibility(View.VISIBLE);
        searchImageView.setVisibility(View.VISIBLE);
        editText.setHint("板块名称");

        setOfAllTypeArray= setOfAllType.split(",");
        typeList=new ArrayList(Arrays.asList(setOfAllTypeArray));

        Bundle bundle=getIntent().getExtras();
        selectedTypeList = (ArrayList<String>) bundle.getSerializable("typeList");
        if(selectedTypeList==null)selectedTypeList=new ArrayList<String>();

        arrayAdapter=new ArrayAdapter<String>(this,R.layout.type_item,R.id.type_text,typeList);
        listView.setAdapter(arrayAdapter);

        layoutInflater = LayoutInflater.from(getApplicationContext());
        flowAdapter=new FlowLayout.Adapter() {
            @Override
            public int getCount() {
                return selectedTypeList.size();
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                View view = layoutInflater.inflate(R.layout.flow_item3_1,parent,false);
                ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                mlp.setMargins(5, 5, 5, 5);
                view.setLayoutParams(mlp);
                TextView textView= (TextView)view.findViewById(R.id.flow_text3_1);
                textView.setText(selectedTypeList.get(position));
                textView.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event){
                        Drawable drawable=textView.getCompoundDrawables()[2];
                        if ((event.getX() > textView.getWidth()-drawable.getIntrinsicWidth()-textView.getPaddingRight())
                                &&(event.getX() < textView.getWidth()-textView.getPaddingRight())){
                            selectedTypeList.remove(position);
                            flowLayout.setAdapter(flowAdapter);
                            if(selectedTypeList.size()==0){
                                flowLayout.setVisibility(View.GONE);
                            }
                            else{
                                flowLayout.setVisibility(View.VISIBLE);
                            }
                        }
                        return false;
                    }
                });
                return view;
            }
        };
        flowLayout.setAdapter(flowAdapter);
        if(selectedTypeList.size()==0){
            flowLayout.setVisibility(View.GONE);
        }
        else{
            flowLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initEvent() {
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
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                arrayAdapter.getFilter().filter(charSequence);
                String wanted= charSequence.toString();

                ArrayList<String>selectedTypeList=new ArrayList<String>();
                for(int j=0;j<setOfAllTypeArray.length;j++){
                    if(setOfAllTypeArray[j].contains(wanted)){
                        selectedTypeList.add(setOfAllTypeArray[j]);
                    }
                }
                typeList.clear();
                typeList.addAll(selectedTypeList);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editText.getText().length()>0){
                    clearTextButton.setVisibility(View.VISIBLE);
                }
                else{
                    clearTextButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        clearTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String typeSelected=typeList.get(i);
                boolean hasSelect=false;
                for(int j=0;j<selectedTypeList.size();j++){
                    if(selectedTypeList.get(j).equals(typeSelected)){
                        hasSelect=true;
                        Toast.makeText(getApplicationContext(),"板块已添加",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(!hasSelect){
                    selectedTypeList.add(typeSelected);
                    flowLayout.setAdapter(flowAdapter);
                    if(selectedTypeList.size()==0){
                        flowLayout.setVisibility(View.GONE);
                    }
                    else{
                        flowLayout.setVisibility(View.VISIBLE);
                    }
                }

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
    }

    private void initView() {
        editText=findViewById(R.id.search_edit1);
        searchBut=findViewById(R.id.search_but1);
        addMoreBut= findViewById(R.id.add_stock_but1);
        finishAddBut=findViewById(R.id.add_stock_but2);
        searchImageView=findViewById(R.id.search_icon);
        clearTextButton=findViewById(R.id.clear_icon);

        listView = findViewById(R.id.list_search3_2_2);
        flowLayout = findViewById(R.id.add_type_flow);
    }

    public void clickBack(View view){
        switch (view.getId()){
            case R.id.back_icon:
                finish();
        }
    }
}