package com.playhard.studentmgr.eighthHonmeWork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.playhard.studentmgr.R;
import com.playhard.studentmgr.firstHomeWork.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FriendInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView friend_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    EditText editText;
    FloatingActionButton pasteBtn,openfileBtn;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);

         toolbar= findViewById(R.id.toolbar);
         collapsingToolbarLayout = findViewById(R.id.collapsing_bar);
         friend_image = findViewById(R.id.friend_image);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle("好友信息");
        //加载图片
        Glide.with(this).load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1485038566,798705063&fm=26&gp=0.jpg").into(friend_image);

        editText = findViewById(R.id.friend_info_edit);
        editText.setText("在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" +
                "在我们的生活里,曾经有多少个陌生的生命,只是迎面错过,甚至连对望一眼的机会都没有。" );

        openfileBtn = findViewById(R.id.fileopen);
        openfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/plain");//设置类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
            }
        });





        pasteBtn = findViewById(R.id.paste);
        pasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard =
                        (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String text =  clipboard.getText().toString();
                if (!TextUtils.isEmpty(clipboard.getText())){
                    editText.setText("");
                    editText.setText(text);
                }
            }
        });







    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String file_path = actualimagecursor.getString(actual_image_column_index);
            File file = new File(file_path);
            StringBuilder fileContent = new StringBuilder();
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = br.readLine()) != null) {
                    fileContent.append(line);
                }
                fis.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            editText.setText("");
            editText.setText(fileContent);
        }

    }
}
