package com.playhard.studentmgr.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.playhard.studentmgr.R;
import com.playhard.studentmgr.dao.StudentInfoDao;
import com.playhard.studentmgr.domain.User;
import com.playhard.studentmgr.util.ToastUtil;

import org.litepal.LitePal;

import java.util.List;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {


    TextView regist_href, fail_href,wechat_href,qq_href,weibo_href;
    private Button back_href,login_btn;
    private static final String TAG = "ActivityLogin";

    private Intent intent;
    EditText username,password;
    private final String XMLPREFERENCEPATH="com.playhard.studentmgr_preferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();


//创建数据库，添加用户，放到注册界面
        StudentInfoDao.createDatabaseForStudentInfo();

    }


    private void init(){
        regist_href = findViewById(R.id.regist_href);
        fail_href = findViewById(R.id.fail_href);
        back_href = findViewById(R.id.back);
        wechat_href= findViewById(R.id.wechat_href);
        qq_href= findViewById(R.id.qq_href);
        weibo_href= findViewById(R.id.weibo_href);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.btnLogin);

        regist_href.setOnClickListener(this);
        fail_href.setOnClickListener(this);
        back_href.setOnClickListener(this);
        wechat_href.setOnClickListener(this);
        qq_href.setOnClickListener(this);
        weibo_href.setOnClickListener(this);
        login_btn.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        //                注册界面尚未完善
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.regist_href:
                intent = new Intent(this,RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.fail_href:
                break;
            case R.id.btnLogin:
                String name = username.getText().toString();
                String passwd = password.getText().toString();
                if ("张三".equals(name) && "123456".equals(passwd)){
                    intent = new Intent(this,ActivityMain.class);
                    intent.putExtra("username",name);
                    startActivity(intent);
                }
                else {
                    ToastUtil.putMessage(this,"账号或密码错误");
                    username.setText("");
                    password.setText("");
                }
                break;
            case R.id.wechat_href:
                break;
            case R.id.qq_href:
                break;
            case R.id.weibo_href:
                break;
        }
    }
}
