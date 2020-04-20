package com.example.androidstudy.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.pojo.Article;
import com.example.androidstudy.recyclerpage.ArticlePagedListAdapter;
import com.example.androidstudy.recyclerpage.ArticleViewModel;


public class MainFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView =  view.findViewById(R.id.recycler_view);
        final ArticlePagedListAdapter articlePagedListAdapter = new ArticlePagedListAdapter();
        articlePagedListAdapter.setOnItemClickListener(new ArticlePagedListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {

                Intent intent1 = new Intent(getActivity(),ArticleWebView.class);
                intent1.putExtra("content",article.getViewName());
                startActivity(intent1);
                Toast.makeText(getActivity(), article.getTitle(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("id",article.getId());
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, article.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(articlePagedListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        ArticleViewModel articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
//        articleViewModel.concertList.observe(concertPagedListAdapter::submitList);
        articleViewModel.concertList.observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(PagedList<Article> articles) {
                articlePagedListAdapter.submitList(articles);
            }
        });

        return view;
    }



}
