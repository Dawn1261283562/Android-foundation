package com.example.studying;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PageFragment3 extends androidx.fragment.app.Fragment {
    private View mView;

    private TextView textViewUserName1;
    private TextView textViewUserName2;
    private TextView textViewUserMail;
    private Button exitBut;

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.page_fragment3, container, false);
        }

        textViewUserName1=mView.findViewById(R.id.user_name1);
        textViewUserName2=mView.findViewById(R.id.user_name2);
        textViewUserMail=mView.findViewById(R.id.user_mail);
        exitBut=mView.findViewById(R.id.exit_button);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        Data data = (Data)getActivity().getApplicationContext();
        username=data.getUsername();

        if(username!=null){
            textViewUserName1.setText(username);
            textViewUserName2.setText(username);
        }
    }

    private void initEvent() {
        exitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=null;
                textViewUserName1.setText(username);
                textViewUserName2.setText(username);

                Data data = (Data)getActivity().getApplication();
                data.setUsername(null);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }
}
