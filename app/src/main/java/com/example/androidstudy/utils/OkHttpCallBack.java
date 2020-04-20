package com.example.androidstudy.utils;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class OkHttpCallBack implements Callback {

    public abstract  void onSuccess(final Call call, JSONObject jsonObject);
    public abstract  void onFailure(final Call call, String message);
    @Override
    public  void onFailure(@NotNull Call call, @NotNull IOException e) {
        onFailure(call,e.getMessage());
    }

    public Handler handler  = new Handler(Looper.getMainLooper());

    @Override
    public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {
        if(response!=null){
            if(response.isSuccessful()){
                String str = response.body().string().trim();
                final JSONObject jsonObject = JSONObject.parseObject(str);
                if(jsonObject!=null){
                    if(isPostInMainThread()){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                onSuccess(call,jsonObject);
                            }
                        });
                    }else {
                        onSuccess(call,jsonObject);
                    }

                }else {
                    onFailure(call,"" );
                }
            }else {
                onFailure(call,"");
            }
        }
    }

    protected boolean isPostInMainThread(){
        return true;
    }
}
