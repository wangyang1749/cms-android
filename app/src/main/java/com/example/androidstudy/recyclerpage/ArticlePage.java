package com.example.androidstudy.recyclerpage;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.androidstudy.api.ArticleApi;
import com.example.androidstudy.api.HttpCallBack;
import com.example.androidstudy.api.base.ApiListener;
import com.example.androidstudy.api.base.BaseApi;
import com.example.androidstudy.pojo.Article;

import java.util.List;

public class ArticlePage extends PageKeyedDataSource<Integer, Article> {


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Article> callback) {

        new ArticleApi().articleList(0,params.requestedLoadSize)
                .setHttpCallBack(new HttpCallBack<List<Article>>() {
                    @Override
                    public void success(List<Article> articles) {
                        callback.onResult(articles,null,1);
                    }

                    @Override
                    public void failure(String message) {

                    }
                });

//        new ArticleApi(0,params.requestedLoadSize).get(new ApiListener() {
//            @Override
//            public void success(BaseApi baseApi) {
//                ArticleApi articleApi = (ArticleApi)baseApi;
//                List<Article> articles = articleApi.list;
//                callback.onResult(articles,null,1);
//            }
//
//            @Override
//            public void failure(BaseApi baseApi) {
//
//            }
//        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        new ArticleApi().articleList(params.key,params.requestedLoadSize)
                .setHttpCallBack(new HttpCallBack<List<Article>>() {
                    @Override
                    public void success(List<Article> articles) {
                        callback.onResult(articles,params.key+1);
                    }

                    @Override
                    public void failure(String message) {

                    }
                });
//        new ArticleApi(params.key,params.requestedLoadSize).get(new ApiListener() {
//            @Override
//            public void success(BaseApi baseApi) {
//                ArticleApi articleApi = (ArticleApi)baseApi;
//                List<Article> articles = articleApi.list;
//                callback.onResult(articles,params.key+1);
//            }
//
//            @Override
//            public void failure(BaseApi baseApi) {
//
//            }
//        });
    }
}
