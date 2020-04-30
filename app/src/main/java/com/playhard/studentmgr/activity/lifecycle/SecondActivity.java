package com.playhard.studentmgr.activity.lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.activity.ActivityMain;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    Button back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        back_btn = findViewById(R.id.back);
        back_btn.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
