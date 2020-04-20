package com.example.androidstudy.recyclerpage;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.androidstudy.pojo.Article;

public class ArticleDataSourceFactory extends DataSource.Factory<Integer, Article> {

    @NonNull
    @Override
    public DataSource<Integer, Article> create() {
        ArticlePage articlePage = new ArticlePage();

        return articlePage;
    }
}
