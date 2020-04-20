package com.example.androidstudy.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.api.ArticleApi;
import com.example.androidstudy.api.ArticleDetailVoApi;
import com.example.androidstudy.api.HttpCallBack;
import com.example.androidstudy.api.base.ApiListener;
import com.example.androidstudy.api.base.BaseApi;
import com.example.androidstudy.pojo.ArticleDetailVO;
import com.example.androidstudy.pojo.EditViewModel;
import com.example.androidstudy.pojo.params.ArticleParams;

public class EditActivity extends AppCompatActivity {

    Button previewBtn,saveBtn;
    EditViewModel editViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final EditText editText = findViewById(R.id.edit_text_id);
        previewBtn = findViewById(R.id.preview_article);
        saveBtn = findViewById(R.id.save_article);
        editViewModel = ViewModelProviders.of(this).get(EditViewModel.class);

        final Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
//        new ArticleDetailVoApi(id).get(new ApiListener() {
//            @Override
//            public void success(BaseApi baseApi) {
//                ArticleDetailVoApi articleDetailVoApi = (ArticleDetailVoApi) baseApi;
//                articleDetailVO = articleDetailVoApi.articleDetailVO;
//                editText.setText(articleDetailVO.getOriginalContent());
//            }
//
//            @Override
//            public void failure(BaseApi baseApi) {
//
//            }
//        });

        new ArticleApi().findArticleDetail(id).setHttpCallBack(new HttpCallBack<ArticleDetailVO>() {
            @Override
            public void success(ArticleDetailVO articleDetailVO) {
                editViewModel.articleDetailVO=articleDetailVO;
                editText.setText(editViewModel.articleDetailVO.getOriginalContent());
            }

            @Override
            public void failure(String message) {

            }
        });
        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditActivity.this,ArticleWebView.class);
                intent1.putExtra("content",editViewModel.articleDetailVO.getFormatContent());
                startActivity(intent1);
                Toast.makeText(EditActivity.this, editViewModel.articleDetailVO.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleParams articleParams = new ArticleParams();
                articleParams.setTitle(editViewModel.articleDetailVO.getTitle());
                articleParams.setCategoryId(editViewModel.articleDetailVO.getCategoryId());
                articleParams.setUserId(editViewModel.articleDetailVO.getUserId());
                articleParams.setOriginalContent(editText.getText().toString());
                articleParams.setSummary(editViewModel.articleDetailVO.getSummary());
                articleParams.setViewName(editViewModel.articleDetailVO.getViewName());

                new ArticleApi().updateArticle(editViewModel.articleDetailVO.getId(),articleParams)
                        .setHttpCallBack(new HttpCallBack<ArticleDetailVO>(){
                            @Override
                            public void success(ArticleDetailVO articleDetailVO) {
                                Toast.makeText(EditActivity.this, "成功更新文章:"+articleDetailVO.getTitle(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(String message) {

                            }
                        });
            }
        });

    }
}
