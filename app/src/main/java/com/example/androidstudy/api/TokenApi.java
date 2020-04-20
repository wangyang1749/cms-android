package com.example.androidstudy.api;

import com.alibaba.fastjson.JSONObject;
import com.example.androidstudy.api.base.BaseApi;
import com.example.androidstudy.pojo.CmsConst;

public class TokenApi extends BaseApi {

    public TokenApi(String username,String password){
        addParams("username",username);
        addParams("password",password);
    }

    public  String token ;
    @Override
    protected void parseDate(JSONObject jsonObject) throws Exception {
        token = jsonObject.getJSONObject("data").getString("id_token");
    }

    @Override
    protected String getUrl() {
        return CmsConst.BASE_URL+"/user/authenticate";
    }
}
