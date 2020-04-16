package com.playhard.studentmgr.firstHomeWork;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.playhard.studentmgr.secondHomeWork.ActivityMain;
import com.playhard.studentmgr.R;

public class ActivityStudent extends AppCompatActivity {

    private RadioGroup sexRadioGrp;
    private Button login;
    private TextView wrongHref;
    Drawable drawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        sexRadioGrp = findViewById(R.id.sex_first);
        sexRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton button = radioGroup.findViewById(i);
                Toast.makeText(ActivityStudent.this,button.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        setDrawable(R.id.username_first,R.drawable.addpeople_fill1,0,0,50,50);
        setDrawable(R.id.student_num_first,R.drawable.group,0,0,50,50);

    }


    private void setDrawable(int parent,int id,int x, int y, int w, int h){
        drawable=getResources().getDrawable(id);//获取drawable
        drawable.setBounds(x,y,w,h);
        EditText text = findViewById(parent);
        text.setCompoundDrawables(drawable,null,null,null);

    }


    public void setListeners(View view){
        if (view.getId()==R.id.loginbtn_first){
            login = findViewById(R.id.loginbtn_first);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(ActivityStudent.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityStudent.this,ActivityMain.class);
                    startActivity(intent);
                }
            });
        }
            if (view.getId()==R.id.fail){
            wrongHref= findViewById(R.id.fail);
            wrongHref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ActivityStudent.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else return;
    }
}
