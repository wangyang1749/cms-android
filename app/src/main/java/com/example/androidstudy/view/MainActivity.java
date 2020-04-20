package com.example.androidstudy.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.adapter.ArticleListAdapter;
import com.example.androidstudy.pojo.Article;
import com.example.androidstudy.api.base.ApiListener;
import com.example.androidstudy.api.base.BaseApi;
import com.example.androidstudy.api.ArticleApi;
import com.example.androidstudy.recyclerpage.ArticlePagedListAdapter;
import com.example.androidstudy.recyclerpage.ArticleViewModel;

import java.util.List;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

//    private ArticleViewModel concertViewModel;
//    private TextView mTvResult;
//    private Button openLogin;
//    private RecyclerView recyclerView;
//    ListView contentListView;
//    OkHttpClient okHttpClient = new OkHttpClient();
//    private final String  baseUrl = "http://47.93.201.74:8080";
//    private String [] data = new String[]{"001","002","003","001","001","002","003","001","001","002","003","001","002","003","001","002","003"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        final SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
//        String token = sharedPreferences.getString("token",null);
//        if(token==null){
//            Intent intent = new Intent(MainActivity.this, MyLoginActivity.class);
//            startActivity(intent);
//            MainActivity.this.finish();
//            return;
//        }

        //添加顶部的ToolBar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        RecyclerView recyclerView =  findViewById(R.id.recycler_view);
        final ArticlePagedListAdapter articlePagedListAdapter = new ArticlePagedListAdapter();
        articlePagedListAdapter.setOnItemClickListener(new ArticlePagedListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Article article) {

                Intent intent1 = new Intent(MainActivity.this,ArticleWebView.class);
                intent1.putExtra("content",article.getViewName());
                startActivity(intent1);
                Toast.makeText(MainActivity.this, article.getTitle(), Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("id",article.getId());
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, article.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(articlePagedListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

//        LiveData<PagedList<Article>> liveData = new LivePagedListBuilder<>(new ArticleDataSourceFactory(), 5).build();
//        liveData.observe(this, new Observer<PagedList<Article>>() {
//            @Override
//            public void onChanged(final PagedList<Article> articles) {
//                concertPagedListAdapter.submitList(articles);
//                articles.addWeakCallback(null, new PagedList.Callback() {
//                    @Override
//                    public void onChanged(int position, int count) {
//                        Log.i("article",""+articles);
//                    }
//
//                    @Override
//                    public void onInserted(int position, int count) {
//
//                    }
//
//                    @Override
//                    public void onRemoved(int position, int count) {
//
//                    }
//                });
//            }
//        });


        ArticleViewModel articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
//        articleViewModel.concertList.observe(concertPagedListAdapter::submitList);
        articleViewModel.concertList.observe(this, new Observer<PagedList<Article>>() {
            @Override
            public void onChanged(PagedList<Article> articles) {
                articlePagedListAdapter.submitList(articles);
            }
        });



        //ListView 设置左侧侧滑菜单按钮
        ListView listView = findViewById(R.id.list_view);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
                ,new String[]{"Menu1","Menu2"});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

            }
        });



        //ListView文章列表
//        contentListView = findViewById(R.id.content_list_view);
//        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Article article  = (Article) parent.getItemAtPosition(position);
//                Toast.makeText(MainActivity.this, article.getTitle(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this,EditActivity.class);
//                intent.putExtra("id",article.getId());
//                startActivity(intent);
//
//            }
//        });
//
//        articleList();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        articleList();
    }

    public void articleList(){
//        new ArticleApi().articleList();
//
//        new ArticleApi(0,50).get(new ApiListener() {
//            @Override
//            public void success(BaseApi baseApi) {
//                ArticleApi articleApi = (ArticleApi) baseApi;
//                List<Article> list = articleApi.list;
//                ArticleListAdapter articleListAdapter = new ArticleListAdapter(list);
//                contentListView.setAdapter(articleListAdapter);
//                Toast.makeText(MainActivity.this, "页面加载成功!", Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void failure(BaseApi baseApi) {
//
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
//            case R.id.logout:
////                final SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
////                SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.remove("token");
////                editor.commit();
////                Intent intent = new Intent(MainActivity.this,MyLoginActivity.class);
////                startActivity(intent);
//                Toast.makeText(this, "删除Token成功!", Toast.LENGTH_SHORT).show();
//                return true;
            case R.id.refresh:
                articleList();
                Toast.makeText(this, "刷新成功!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                 return super.onOptionsItemSelected(item);
        }
    }
}
