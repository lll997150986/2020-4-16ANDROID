package com.playhard.studentmgr.FifthHomeWork;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.thirdAndFourthHomeWork.ActivityMain;

public class adActivity extends AppCompatActivity {

    private TextView textView;
    private   int ADTIMES = 5;
    private volatile int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        textView = findViewById(R.id.adbtn);
        viewInit();
        eventInit();
    }
//屏幕旋转处理
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_ad);
        viewInit();
        eventInit();
    }

    private void viewInit(){
        textView = findViewById(R.id.adbtn);
    }
//    事件处理
    private void eventInit(){
        final Handler handler = new  Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                synchronized (adActivity.class){
                    if (ADTIMES>0){
                        textView.setText("广告倒计时："+ADTIMES+"秒");
                    }
                    else {
                        Intent intent = new Intent(adActivity.this,ActivityMain.class);
                        startActivity(intent);
                    }
                }
            }
        };
//        线程处理信号量，发送message
         new Thread(new Runnable() {
            @Override
            public void run() {
                while(flag==0){//没有被点击
                    if (ADTIMES>0){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ADTIMES = ADTIMES-1;
                        handler.sendEmptyMessage(0);
                    }
                    else {
                        Thread.currentThread().interrupt();
                    }
                }
                ADTIMES = 0;
                handler.sendEmptyMessage(0);

            }
        }).start();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              flag=1;
            }
        });
    }
}
