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


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidstudy.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //添加顶部的ToolBar
//        Toolbar myToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

//        //ListView 设置左侧侧滑菜单按钮
//        ListView listView = findViewById(R.id.list_view);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1
//                ,new String[]{"Menu1","Menu2"});
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String item = (String) parent.getItemAtPosition(position);
//
//            }
//        });


    }


//    @Override
//    protected void onRestart() {
//        super.onRestart();
////        articleList();
//    }
//
//    public void articleList(){
////        new ArticleApi().articleList();
////
////        new ArticleApi(0,50).get(new ApiListener() {
////            @Override
////            public void success(BaseApi baseApi) {
////                ArticleApi articleApi = (ArticleApi) baseApi;
////                List<Article> list = articleApi.list;
////                ArticleListAdapter articleListAdapter = new ArticleListAdapter(list);
////                contentListView.setAdapter(articleListAdapter);
////                Toast.makeText(MainActivity.this, "页面加载成功!", Toast.LENGTH_SHORT).show();
////            }
////            @Override
////            public void failure(BaseApi baseApi) {
////
////            }
////        });
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
////            case R.id.logout:
//////                final SharedPreferences sharedPreferences = getSharedPreferences("cmsConfig",MODE_PRIVATE);
//////                SharedPreferences.Editor editor = sharedPreferences.edit();
//////                editor.remove("token");
//////                editor.commit();
//////                Intent intent = new Intent(MainActivity.this,MyLoginActivity.class);
//////                startActivity(intent);
////                Toast.makeText(this, "删除Token成功!", Toast.LENGTH_SHORT).show();
////                return true;
//            case R.id.refresh:
//                articleList();
//                Toast.makeText(this, "刷新成功!", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                 return super.onOptionsItemSelected(item);
//        }
//    }
}
