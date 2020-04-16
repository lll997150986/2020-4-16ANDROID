package com.playhard.studentmgr.firstHomeWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.playhard.studentmgr.adapter.MyListAdapter;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button back_btn = findViewById(R.id.backToLogin);
       back_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,ActivityStudent.class);
               startActivity(intent);
           }
       });
        Button listViewBtn = findViewById(R.id.lvw);
        listViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ListViewActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * listview类，静态内部类，防止重复加载，只在需要用的时候加载一次
     */
    public static class ListViewActivity extends AppCompatActivity {

        private ListView listView;
        private ArrayList<StudentInfo> list;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_view);
            listView = findViewById(R.id.lvw1);
            //设置适配器（重要）--listview的entry添加依赖适配器实现
            listView.setAdapter(new MyListAdapter(ListViewActivity.this,list));
            //每一个entry的事件处理
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(ListViewActivity.this,"pos"+i,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ListViewActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }


    }
}
