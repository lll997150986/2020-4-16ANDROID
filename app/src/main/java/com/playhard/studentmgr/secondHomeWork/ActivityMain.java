package com.playhard.studentmgr.secondHomeWork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.adapter.MyListAdapter;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.firstHomeWork.ActivityStudent;
import com.playhard.studentmgr.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 主页
 */
public class ActivityMain extends AppCompatActivity {

    private  Button back_btn,add_btn;
    private ListView listView = null;
    private Intent intent = null;
    private Bundle bundle;
    private StudentInfo info;
    private List<StudentInfo> list ;
    private static final String TAG = "ActivityMain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给两个按钮添加事件处理
        setContentView(R.layout.activity_main2);
        back_btn = findViewById(R.id.backToLogin2);
        addClickListener(back_btn,ActivityStudent.class);
        add_btn = findViewById(R.id.addInfo);
        addClickListener(add_btn,ActivityStudent2.class);
        //查找数据库信息
        list = DataSupport.findAll(StudentInfo.class);
        //每个entry的事件处理
        listView = findViewById(R.id.lvwStudent);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.putMessage(ActivityMain.this,"pos"+i);
                intent = new Intent(ActivityMain.this,ActivityStudent.class);
                startActivity(intent);
            }
        });
        //设置适配器，对Viewlist的更新进行处理
        listView.setAdapter(new MyListAdapter(ActivityMain.this,list));
        Log.d(TAG, "onCreate: 测试");
    }

    /**
     * 单例模式返回list（info记录集合，使每次事件后不覆盖list）
     * @return
     */

    /**
     * 给指定按钮添加事件处理
     * @param btn
     * @param clazz
     */
    private void addClickListener(Button btn, final Class clazz){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               intent = new Intent(ActivityMain.this,clazz);
               startActivity(intent);
                Log.d(TAG, "onClick: 测试");
            }
        });
    }


}
