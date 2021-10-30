package com.example.studying;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class AddCompanyActivity extends AppCompatActivity {
    private String setOfAllCompany="华夏基金,中海基金,嘉实基金,西部利得基金,易方达基金,财通基金,景顺长城基金,摩根士丹利华鑫基金," +
            "华富基金,富国基金,长城基金,广发基金,农银汇理基金,工银瑞信基金,中银基金,鹏华基金,建信基金,国联安基金,长盛基金,国海富兰克林基金," +
            "诺安基金,民生加银基金,国投瑞银基金,华安基金,上投摩根基金,天治基金,汇添富基金,博时基金,南方基金,国泰基金,华宸未来基金,金鹰基金," +
            "华宝基金,招商基金,大成基金,中信保诚基金,融通基金,华泰柏瑞基金,银华基金,天弘基金,光大保德信基金管理,泰信基金,宝盈基金,中邮基金," +
            "华润元大基金,华商基金,德邦基金,安信基金,泰达宏利基金,中加基金,平安基金,益民基金,前海开源基金,新华基金,国金基金,英大基金," +
            "东方红资产管理,鑫元基金,长安基金,中信建投基金,国寿安保基金,上银基金,东吴基金,永赢基金,兴业基金,兴证全球基金,江信基金," +
            "信达澳银基金,交银施罗德基金,兴银基金,红塔红土,北信瑞丰,富安达基金,万家基金,华融基金,方正富邦基金,中金基金,东海基金," +
            "圆信永丰基金,中融基金,汇丰晋信基金,九泰基金,中欧基金,浙商证券资管,太平基金,东方基金,申万菱信基金,山西证券,创金合信基金," +
            "嘉合基金,泓德基金,金元顺安基金,泰康资产,东兴基金管理,红土创新基金,新沃基金,海富通基金,国都证券,浙商基金,前海联合,长信基金," +
            "金信基金,中银证券,中科沃土基金,诺德基金,财通资管,浦银安盛基金,长江证券(上海),富荣基金,先锋基金,汇安基金,华泰保兴,中航基金," +
            "银河基金,恒生前海基金,鹏扬基金,华泰证券(上海)资产管理,渤海汇金,南华基金,格林基金,人保资产,东方阿尔法基金,国融基金,恒越基金," +
            "博道基金,合煦智远基金,弘毅远方基金,凯石基金,中庚基金,中泰证券(上海)资管,蜂巢基金,湘财基金,睿远基金,朱雀基金,淳厚基金," +
            "同泰基金,惠升基金,博远基金,东财基金,明亚基金,达诚基金管理,兴华基金管理,瑞达基金管理,汇泉基金管理,百嘉基金管理," +
            "国泰君安资产管理,贝莱德基金管理有限公司,上海海通证券资产管理,上海光大证券资产管理,广发资产管理,招商证券资管,中信证券," +
            "中金公司,国信证券,兴证资管,天风(上海)证券资产管理,安信证券资产,华安证券,方正证券,平安证券,申万宏源证券,中信建投," +
            "信达证券,国海证券,东海证券,东莞证券,东吴证券,国联证券,华鑫证券,长城证券,第一创业,太平洋";

    private EditText editText;
    private Button searchBut;

    private String[] setOfAllCompanyArray;
    private ArrayList<String> companyList;
    private String companySelected;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
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

    }

    private void initDate() {
        editText.setVisibility(View.VISIBLE);
        searchBut.setVisibility(View.VISIBLE);
        editText.setHint("公司名称");

        setOfAllCompanyArray= setOfAllCompany.split(",");
        companyList=new ArrayList(Arrays.asList(setOfAllCompanyArray));

        companySelected = "";

        arrayAdapter=new ArrayAdapter<String>(this,R.layout.type_item,R.id.type_text,companyList);
        listView.setAdapter(arrayAdapter);

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
                for(int j=0;j<setOfAllCompanyArray.length;j++){
                    if(setOfAllCompanyArray[j].contains(wanted)){
                        selectedTypeList.add(setOfAllCompanyArray[j]);
                    }
                }
                companyList.clear();
                companyList.addAll(selectedTypeList);
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                companySelected=companyList.get(i);
                Intent intent=new Intent();
                intent.putExtra("companySelected",companySelected);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    private void initView() {
        editText=findViewById(R.id.search_edit1);
        searchBut=findViewById(R.id.search_but1);

        listView = findViewById(R.id.list_search3_2_3);
    }
    public void clickBack(View view){
        switch (view.getId()){
            case R.id.back_icon:
                finish();
        }
    }
}