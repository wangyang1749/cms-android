package com.example.androidstudy.utils;


import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpUtil {

    public static OkHttpClient okHttpClient = new OkHttpClient();
    private static String token;//="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW5neWFuZyIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU4NTU2OTc0MX0.XbJdagbQ2wNpedMO5zNju5oqEhEoB8ZljadMwOZ4Zt3v-aZUKuFyq3-PkSWidmahttyGVlVleZEXbWs6kLo8Lw";
//    final SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
//    String token = sharedPreferences.getString("token",null);
//        if(token==null){
//        Intent intent = new Intent(MainActivity.this, MyLoginActivity.class);
//        startActivity(intent);
//        return;
//    }

    public static void init(String token_){
        token =token_;
        if(okHttpClient==null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS);
            okHttpClient = builder.build();
        }
    }
//    static {
//        init();
//    }

    /**
     * Get请求传参
     * @param url
     * @param callBack
     * @param urlParams
     */
    public static void get(String url,OkHttpCallBack callBack,Map<String,String> urlParams){
        Call call = null;
        try {
            url= getFinalString(url,urlParams);
            Request request = getBaseRequest()
                    .get()
                    .url(url).build();
            call = okHttpClient.newCall(request);
            call.enqueue(callBack);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Get请求传参
     * @param url
     * @param callBack
     */
    public static void get(String url,OkHttpCallBack callBack){
        get(url,callBack,null);
    }

    public static Request.Builder getBaseRequest(){
        return new Request.Builder()
                .header("Authorization","Bearer "+token);
    }

    public static  String getFinalString(String url, Map<String,String> urlParams){
        if(urlParams==null){
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(HashMap.Entry<String,String> entry:urlParams.entrySet()){
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            try {
                stringBuilder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String paramString = stringBuilder.toString();
        if(url.contains("?")){
            url+="&"+paramString;
        }else {
            url="?"+paramString;
        }
        return url;

    }

    /**
     *
     * @param url
     * @param callBack
     * @param bodyMap
     */
    public static void post(String url,HashMap<String,String> bodyMap, OkHttpCallBack callBack){
//        Call call = null;
//        try {
//
////            FormBody.Builder builder = new FormBody.Builder();
////            for (HashMap.Entry<String,String> entry:bodyMap.entrySet()){
////                builder.add(entry.getKey(),entry.getValue());
////            }
////            RequestBody requestBody = builder.build();
//
//            RequestBody requestBody = RequestBody.create(JSONObject.toJSONString(bodyMap),MediaType.parse("application/json; charset=utf-8"));
//            Request request = getBaseRequest()
//                    .post(requestBody)
//                    .url(url)
//                    .build();
//            call = okHttpClient.newCall(request);
//            call.enqueue(callBack);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        post(url,JSONObject.toJSONString(bodyMap),callBack);
    }


    public static  void post(String url, String body, OkHttpCallBack callBack){
        RequestBody requestBody = RequestBody.create(body,MediaType.parse("application/json; charset=utf-8"));
        Request request = getBaseRequest()
                .post(requestBody)
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callBack);
    }
}
