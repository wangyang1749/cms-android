package com.example.androidstudy;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.androidstudy.utils.OkHttpUtil;

public class CmsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
//        String token = sharedPreferences.getString("token",null);
//        OkHttpUtil.init(token);
    }
}
