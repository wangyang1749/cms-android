package com.example.androidstudy.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.androidstudy.R;
import com.example.androidstudy.pojo.CmsConst;

public class ArticleWebView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web_view);

        WebView webView =  findViewById(R.id.article_webview);



        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);

        // 设置WebView支持JavaScript
        settings.setJavaScriptEnabled(true);
        //支持自动适配
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(true);  //支持放大缩小
        settings.setBuiltInZoomControls(true); //显示缩放按钮
//        settings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        settings.setAllowFileAccess(true); // 允许访问文件
        final Intent intent = getIntent();
        String content = intent.getStringExtra("content");

        String standard = "<html> \n" +
                "<head> \n" +
                "<style type=\"text/css\"> \n" +
                "body {font-size:13px;margin:5px;}\n" +
                "</style> \n" +
                "</head> \n" +
                "<body>" +
                "<script type='text/javascript'>" +
                "window.onload = function(){\n" +
                "var $img = document.getElementsByTagName('img');\n" +
                "for(var p in  $img){\n" +
                " $img[p].style.width = '100%%';\n" +
                "$img[p].style.height ='auto'\n" +
                "}\n" +
                "}" +
                "</script>" +
                content
                + "</body>" +
                "</html>";
        String url = CmsConst.BASE_URL +"/android/article/load/"+content+".html";
        webView.loadUrl(url);
//        webView.loadData(content,"text/html","UTF-8");
//        webView.loadDataWithBaseURL(null,standard,"text/html","UTF-8",null);
    }


}
