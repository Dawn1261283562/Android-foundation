package com.example.studying;

import static android.provider.MediaStore.Images.Media.getBitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StockPictureFragment extends androidx.fragment.app.Fragment {

    private View mView;

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<ImageView>imageViewsList;
    private ContentPagerAdapter contentAdapter;
    String code;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.stockinfo_tablayout, container, false);
        }
        final Stockinfo activity = (Stockinfo) getActivity();
        mTabTl = (TabLayout) mView.findViewById(R.id.stockinfo_tab);
        mContentVp = (ViewPager) mView.findViewById(R.id.stockinfo_vp);


        Bundle bundle=this.getArguments();
        code=null;
        if(bundle!=null){
            code=bundle.getString("code");

        }


        tabIndicators = new ArrayList<>();
        tabIndicators.add("日k");
        tabIndicators.add("周k");
        tabIndicators.add("分时");
        tabIndicators.add("月k");

        ImageView imageView1=new ImageView(getActivity());
        ImageView imageView2=new ImageView(getActivity());
        ImageView imageView3=new ImageView(getActivity());
        ImageView imageView4=new ImageView(getActivity());


//        String strURL = "http://image.sinajs.cn/newchart/daily/n/"+code+".gif";
        //http://j3.dfcfw.com/images/JJJZ1/006030.png
//        String strURL = "http://j3.dfcfw.com/images/JJJZ1/006030.png";
               String strURL = "http://image.sinajs.cn/newchart/daily/n/"+code+".gif";
        try {
            Bitmap bitmap = getBitmap(strURL);
            imageView1.setImageBitmap(bitmap);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        strURL = "http://image.sinajs.cn/newchart/weekly/n/"+code+".gif";
        Log.i("883333","false");
        try {
            Log.i("443333","false");
            Bitmap bitmap = getBitmap(strURL);
            Log.i("553333","false");
            imageView2.setImageBitmap(bitmap);
            Log.i("663333","false");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("333333","false");
        }
        strURL = "http://image.sinajs.cn/newchart/min/n/"+code+".gif";
        Log.i("883333","false");
        try {
            Log.i("443333","false");
            Bitmap bitmap = getBitmap(strURL);
            Log.i("553333","false");
            imageView3.setImageBitmap(bitmap);
            Log.i("663333","false");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("333333","false");
        }
        strURL = "http://image.sinajs.cn/newchart/monthly/n/"+code+".gif";
        Log.i("883333","false");
        try {
            Log.i("443333","false");
            Bitmap bitmap = getBitmap(strURL);
            Log.i("553333","false");
            imageView4.setImageBitmap(bitmap);
            Log.i("663333","false");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("333333","false");
        }

        imageViewsList=new ArrayList<>();
        imageViewsList.add(imageView1);
        imageViewsList.add(imageView2);
        imageViewsList.add(imageView3);
        imageViewsList.add(imageView4);

//        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.gray), ContextCompat.getColor(getActivity(), R.color.teal_700));
//        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
//        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setTabIndicatorFullWidth(false);

        mTabTl.setupWithViewPager(mContentVp);

        contentAdapter = new ContentPagerAdapter();
        mContentVp.setAdapter(contentAdapter);

        return mView;
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




    class ContentPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
/*            TextView tv = new TextView(TablayoutActivity.this);
            tv.setText("ViewPager"   position);
            tv.setTextSize(30.0f);
            tv.setGravity(Gravity.CENTER);*/
            ((ViewGroup) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }


}
