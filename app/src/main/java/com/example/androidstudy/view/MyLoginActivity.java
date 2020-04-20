package com.example.androidstudy.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidstudy.R;
import com.example.androidstudy.api.TokenApi;
import com.example.androidstudy.api.base.ApiListener;
import com.example.androidstudy.api.base.BaseApi;

public class MyLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        final  EditText usernameEditText = findViewById(R.id.username);
        final  EditText passwordEditText = findViewById(R.id.password);
        Button button = findViewById(R.id.login);
        final SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password =passwordEditText.getText().toString();
                new TokenApi(username,password).post(new ApiListener() {
                    @Override
                    public void success(BaseApi baseApi) {
                        TokenApi tokenApi = (TokenApi)baseApi;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token",tokenApi.token);
                        editor.commit();
                        Log.i("token",tokenApi.token);
                        Intent intent = new Intent(MyLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        MyLoginActivity.this.finish();
//                        startActivityForResult();
//                        Toast.makeText(MyLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void failure(BaseApi baseApi) {
//                        Toast.makeText(MyLoginActivity.this, "账号或者密码错误!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
