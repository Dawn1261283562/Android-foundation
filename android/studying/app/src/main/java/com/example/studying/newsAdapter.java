package com.example.studying;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {

    private List<String> newsList;
    static private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View newsView;
        TextView newsTitle;

        public ViewHolder(View view){
            super(view);
            newsView=view;
            newsTitle=(TextView) view.findViewById(R.id.news_item_newstitle);
            context=view.getContext();
        }
    }

    public newsAdapter(List<String>nl){
        newsList=nl;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Intent intent=new Intent(context,WebActivity.class);
                switch (position){
                    case 0:
//                intent.putExtra("StringUrl","http://fund.eastmoney.com/a/202111172184388443.html");
                        intent.putExtra("StringUrl","https://finance.eastmoney.com/a/202111192187490277.html");
                        break;
                    case 1:
                        intent.putExtra("StringUrl","https://finance.eastmoney.com/a/202111192187501390.html");
                        break;
                    case 2:
                        intent.putExtra("StringUrl","http://finance.eastmoney.com/a/202111192187500092.html");
                        break;
                    case 3:
                        intent.putExtra("StringUrl","https://finance.eastmoney.com/a/202111192187473745.html");
                        break;
                    case 4:
                        intent.putExtra("StringUrl","https://finance.eastmoney.com/a/202111192187472238.html");
                        break;
                }
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String news=newsList.get(position);
        holder.newsTitle.setText(news);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
