package com.example.androidstudy.api.base;

import com.alibaba.fastjson.JSONObject;
import com.example.androidstudy.utils.OkHttpCallBack;
import com.example.androidstudy.utils.OkHttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;

public abstract class BaseApi {

    private ApiListener apiListener=null;
    private String status;

    private OkHttpCallBack okHttpCallBack = new OkHttpCallBack() {
        @Override
        protected boolean isPostInMainThread() {
            return isBackInMainThread();
        }

        /**
         * 成功回调
         * @param call
         * @param response
         */
        @Override
        public void onSuccess(Call call, JSONObject response) {
//            status = jsonObject.ge
            if(isSuccess()){
                try {
                    parseDate(response);
                    apiListener.success(BaseApi.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                apiListener.failure(BaseApi.this);
            }
        }

        @Override
        public void onFailure(Call call, String message) {

        }
    };

    protected boolean isBackInMainThread(){
        return true;
    }

    public boolean isSuccess(){
        return true;
//        return "0".equals(status) || "200".equals(status);
    }

    protected  abstract  void parseDate(JSONObject jsonObject) throws Exception;

    protected abstract String getUrl();

    private HashMap<String,String> bodyMap = new HashMap<>();

    public void addParams(String key,String value){
        bodyMap.put(key,value);
    }

    /**
     * Get 请求
     * @param listener
     */
    public void get(ApiListener listener){
        this.apiListener=listener;
        OkHttpUtil.get(getUrl(),okHttpCallBack);
    }

    /**
     * Post请求
     * @param listener
     */
    public void post(ApiListener listener){
        this.apiListener=listener;
        OkHttpUtil.post(getUrl(),bodyMap,okHttpCallBack);
    }
}

