package com.example.androidstudy.recyclerpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidstudy.R;
import com.example.androidstudy.pojo.Article;

public class ArticlePagedListAdapter extends PagedListAdapter<Article, ArticlePagedListAdapter.MyViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Article  article);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener= listener;
    }
    static class  MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.myText);
        }
    }

    public ArticlePagedListAdapter() {
        super(new DiffUtil.ItemCallback<Article>() {
            @Override
            public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.getId()==newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_text_view,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = getItem(viewHolder.getAdapterPosition());
                onItemClickListener.onItemClick(article);
                
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Article article = getItem(position);
        if(article==null){
            holder.textView.setText("loading");
        }else {
            holder.textView.setText(article.getTitle());
        }
    }
}
