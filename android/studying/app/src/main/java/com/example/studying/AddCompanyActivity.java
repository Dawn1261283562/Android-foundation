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
    private String setOfAllCompany="清华大学、北京大学、浙江大学、厦门大学、武汉大学、复旦大学、华中科技大学、中南大学、中山大学、电子科技大学、四川大学、同济大学、山东大学、湖南大学";

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

        setOfAllCompanyArray= setOfAllCompany.split("、");
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