package com.example.androidstudy.recyclerpage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.androidstudy.pojo.Article;
import com.example.androidstudy.recyclerpage.ArticleDataSourceFactory;

//@Deprecated
public class ArticleViewModel extends ViewModel {
    public final LiveData<PagedList<Article>> concertList;

    // Creates a PagedList object with 50 items per page.
    public ArticleViewModel() {
        concertList =  new LivePagedListBuilder<>(new ArticleDataSourceFactory(),5).build();
    }
}
