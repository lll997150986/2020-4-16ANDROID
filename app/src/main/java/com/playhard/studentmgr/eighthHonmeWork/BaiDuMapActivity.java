package com.playhard.studentmgr.eighthHonmeWork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.playhard.studentmgr.R;


public class BaiDuMapActivity extends AppCompatActivity {

    private TextView positionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du_map);

        positionText = findViewById(R.id.position);
    }
}
