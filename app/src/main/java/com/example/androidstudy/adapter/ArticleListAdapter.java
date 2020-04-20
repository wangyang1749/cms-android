package com.example.androidstudy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidstudy.R;
import com.example.androidstudy.pojo.Article;

import java.util.List;

public class ArticleListAdapter extends BaseAdapter {

    List<Article> datas;

    public ArticleListAdapter(List<Article> datas){
        this.datas=datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view,null);
        TextView textView = itemView.findViewById(R.id.myText);
        textView.setText(datas.get(position).getTitle());
        return itemView;
    }
}
