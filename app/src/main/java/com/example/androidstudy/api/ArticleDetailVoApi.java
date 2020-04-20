package com.example.androidstudy.api;

import com.alibaba.fastjson.JSONObject;
import com.example.androidstudy.api.base.BaseApi;
import com.example.androidstudy.pojo.ArticleDetailVO;
import com.example.androidstudy.pojo.CmsConst;

public class ArticleDetailVoApi  extends BaseApi {

    private String url;
    public ArticleDetailVO articleDetailVO;
    public ArticleDetailVoApi(int id){
        url = CmsConst.BASE_URL+"/api/article/findArticleDetail/"+id;
    }
    @Override
    protected void parseDate(JSONObject jsonObject) throws Exception {
        articleDetailVO= jsonObject.getObject("data", ArticleDetailVO.class);
    }

    @Override
    protected String getUrl() {
        return url;
    }
}
