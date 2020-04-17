package com.playhard.studentmgr.thirdAndFourthHomeWork;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.playhard.studentmgr.FifthHomeWork.adActivity;
import com.playhard.studentmgr.R;
import com.playhard.studentmgr.dao.StudentInfoDao;
import com.playhard.studentmgr.domain.User;
import com.playhard.studentmgr.util.DataUtil;
import com.playhard.studentmgr.util.PictureCuter;
import com.playhard.studentmgr.util.ToastUtil;

import org.litepal.LitePal;

import java.util.List;

public class ActivityLogin extends AppCompatActivity {
    private Button lbtn;
    private ProgressBar bar;
    private static final String TAG = "ActivityLogin";
    private Intent intent;
    private Toolbar loginTb;
    TextView name_text,pass_text;
    private final String XMLPREFERENCEPATH="com.playhard.studentmgr_preferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        createDataBase();
        insertOneAndQueryOne();



        setContentView(R.layout.activity_login);
        loginTb = findViewById(R.id.loginTb);
        setSupportActionBar(loginTb);
        LitePal.getDatabase();
        lbtn = findViewById(R.id.btnLogin);
        bar = findViewById(R.id.lpb);
        bar.setVisibility(View.GONE);
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0,3000);
            }
        });

        name_text = findViewById(R.id.username);
        pass_text = findViewById(R.id.mpass);
        PictureCuter.setDrawable(this,R.drawable.addpeople_fill1,0,0,50,50);
        name_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
        PictureCuter.setDrawable(this,R.drawable.group,0,0,50,50);
        pass_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
    }
    Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ToastUtil.putMessage(ActivityLogin.this,"登录成功");
                intent = new Intent(ActivityLogin.this,adActivity.class);
                startActivity(intent);
            }
    };
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_login);
        loginTb = findViewById(R.id.loginTb);
        setSupportActionBar(loginTb);
        LitePal.getDatabase();
        lbtn = findViewById(R.id.btnLogin);
        bar = findViewById(R.id.lpb);
        bar.setVisibility(View.GONE);
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bar.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0,3000);
            }
        });
        name_text = findViewById(R.id.username);
        pass_text = findViewById(R.id.student_num);
        PictureCuter.setDrawable(this,R.drawable.addpeople_fill1,0,0,50,50);
        name_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
        PictureCuter.setDrawable(this,R.drawable.group,0,0,50,50);
        pass_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] data = {"username","password"};
        DataUtil.dataRecovery(this,XMLPREFERENCEPATH,MODE_PRIVATE,data,name_text,pass_text);
    }


    private void insertOneAndQueryOne(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        StudentInfoDao.insertUser(user);
        List<User> users = StudentInfoDao.findByUsername(user.getUsername());
        ToastUtil.putMessage(this,"插入的用户信息"+users.get(0));
        Log.d(TAG, "insertOneAndQueryOne: user:"+users.get(0));

    }
    private void createDataBase(){
        StudentInfoDao.createDatabase(this);
    }
}
