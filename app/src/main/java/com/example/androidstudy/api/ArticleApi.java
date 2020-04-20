package com.example.androidstudy.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.androidstudy.api.base.ApiListener;
import com.example.androidstudy.api.base.BaseApi;
import com.example.androidstudy.pojo.Article;
import com.example.androidstudy.pojo.ArticleDetailVO;
import com.example.androidstudy.pojo.CmsConst;
import com.example.androidstudy.pojo.params.ArticleParams;
import com.example.androidstudy.utils.OkHttpCallBack;
import com.example.androidstudy.utils.OkHttpUtil;

import java.util.List;

import okhttp3.Call;


public class ArticleApi{

    private HttpCallBack httpCallBack;
    public void setHttpCallBack(HttpCallBack httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    public ArticleApi findArticleDetail(int id){
        String url = CmsConst.BASE_URL+"/api/article/findArticleDetail/"+id;
        OkHttpUtil.get(url, new OkHttpCallBack() {
            @Override
            public void onSuccess(Call call, JSONObject jsonObject) {
                ArticleDetailVO articleDetailVO = jsonObject.getObject("data", ArticleDetailVO.class);
                httpCallBack.success(articleDetailVO);
            }

            @Override
            public void onFailure(Call call, String message) {

            }
        });
        return this;
    }

    public ArticleApi  articleList(int page, int size){
        String url = CmsConst.BASE_URL+ "/android/article?page="+page+"&size="+size;
        OkHttpUtil.get(url, new OkHttpCallBack() {
            @Override
            public void onSuccess(Call call, JSONObject jsonObject) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("content");
                List<Article> articles = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
                httpCallBack.success(articles);
            }

            @Override
            public void onFailure(Call call, String message) {
                httpCallBack.failure(message);
            }
        });
        return this;
    }

    public ArticleApi updateArticle(int id,ArticleParams articleParams){
        String url =CmsConst.BASE_URL+ "/api/article/update/"+id;
        String body = JSONObject.toJSONString(articleParams);
        OkHttpUtil.post(url, body,new OkHttpCallBack() {
            @Override
            public void onSuccess(Call call, JSONObject jsonObject) {
                ArticleDetailVO articleDetailVO = jsonObject.getObject("data", ArticleDetailVO.class);
                httpCallBack.success(articleDetailVO);
            }

            @Override
            public void onFailure(Call call, String message) {
                httpCallBack.failure(message);
            }
        });
        return this;
    }

//    public List<Article> list;
//
////    private String url;
//    public ArticleApi(int page,int size){
////        url = CmsConst.BASE_URL+ "/api/article?page="+page+"&size="+size;
//    }

//    @Override
//    protected void parseDate(JSONObject jsonObject) throws Exception {
//        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("content");
//        list = JSONArray.parseArray(jsonArray.toJSONString(), Article.class);
//    }
//
//    @Override
//    protected String getUrl() {
//        return url;
//    }


}
