package com.playhard.studentmgr.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.dao.StudentInfoDao;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.util.PictureTool;
import com.playhard.studentmgr.util.PreferencesUtil;

import org.litepal.crud.DataSupport;
import org.litepal.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityAddFriend extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private Button btn_submit;
    private EditText name_text,num_text;
    private RadioButton sexMale,sexFemale;
    private Spinner fac_spin,spe_spin;
    private String username, stu_num,sex,falcu,spec;
    private final int HOBBYLENGTH=4;
    private String[] hobby = new  String[HOBBYLENGTH];
    private CheckBox hcheckbox ;
    private ArrayList<String> special = new ArrayList();
    private ArrayAdapter arr_adapter;
    private Intent intent;
    private StudentInfo info;
    private String birth;
    private Button birthBtn;
    private DatePicker datePicker;
    private LinearLayout mLinearLayout;
    private GestureDetector gestureDetector;
    private static final String TAG = "ActivityStudent2";
    private  static SharedPreferences.Editor editor ;
    Button btn_back,openfileBtn;
    TextView title;
    static  String file_path;
    static Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        fac_spin = findViewById(R.id.falculty);
        spe_spin = findViewById(R.id.special);
        name_text = findViewById(R.id.username);
        num_text = findViewById(R.id.student_num);
        sexMale = findViewById(R.id.man);
        sexFemale = findViewById(R.id.woman);
        btn_submit = findViewById(R.id.btnSubmit);
        info = new StudentInfo();
        birthBtn = findViewById(R.id.birthBtn);
        mLinearLayout = findViewById(R.id.addlay);
        btn_back = findViewById(R.id.back);
        title = findViewById(R.id.title_text);

        openfileBtn = findViewById(R.id.file);
        openfileBtn.setOnClickListener(this);


        title.setText("添加学生信息");
//       按钮初始化
        btn_back.setOnClickListener(this);
        birthBtn.setOnClickListener(this);
        //下拉框联动实现以及下拉框点击事件监听，获取点击的item值（学院和专业）
        //        获取学院选中值并且联动更新专业列表
        fac_spin.setOnItemSelectedListener(this);
        //        获取专业
        spe_spin.setOnItemSelectedListener(this);
        dataRecovery();
        btn_submit.setOnClickListener(this);
    }

    /**
     * 对表单的数据进行info包装
     */
    private void infoPack() throws FileNotFoundException {
        username = name_text.getText().toString();
        stu_num = num_text.getText().toString();
        if (sexMale.isChecked()){
            sex = sexMale.getText().toString();
        }else {
            sex = sexFemale.getText().toString();
        }
//        爱好
        int[] h ={R.id.liture,R.id.pe,R.id.music,R.id.art};
        getHobby(h);
        Log.d(TAG, "infoPack: username"+username);
        info.setName(username);
        info.setNum(stu_num);
        String hb = "";
        for (int i=0; i<hobby.length;i++){
            if (hobby[i]!=null){
                if (i<hobby.length-1)
                    hb =hb+ hobby[i]+",";
                else hb+=hobby[i];
            }
        }
        info.setHobby(hb);
        info.setSex(sex);
        info.setFalculty(falcu);
        info.setSpecial(spec);
        info.setBirth(birth);
        if (!TextUtils.isEmpty(file_path)){
            info.setPhoto(file_path);
        }

    }

    /**
     * 获取多选框选中的hobby,遍历id数组的view
     * @param id
     */
    private void getHobby(int[] id){
        for (int i=0; i<HOBBYLENGTH;i++){
            hcheckbox = findViewById(id[i]);
            if (hcheckbox.isChecked()){
                hobby[i] = hcheckbox.getText().toString();
            }
        }
    }
    /**
     * 数据初始化，恢复表单数据
     */
    private void dataRecovery(){
        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        username = preferences.getString("username","");
        stu_num = preferences.getString("num","");
        sex = preferences.getString("sex","");
        birth = preferences.getString("birth","");
        falcu = preferences.getString("falcu","");
        spec = preferences.getString("spec","");
        String hb =  preferences.getString("hobby","");
        if (!TextUtils.isEmpty(username)){
            name_text.setText(username);
        }
        if (!TextUtils.isEmpty(stu_num)){
            num_text.setText(stu_num);
        }
        if (!TextUtils.isEmpty(sex)){
            if (sex.contains("男")){
                sexMale.setChecked(true);
            }
            else {
                sexFemale.setChecked(true);
            }
        }
        if (!TextUtils.isEmpty(birth)){
            birthBtn.setText(birth);
        }
        if (!TextUtils.isEmpty(falcu)){
            if (falcu.contains("电气")){
                fac_spin.setSelection(1,true);
            }
            else {
                fac_spin.setSelection(0,true);
            }
        }
        if (!TextUtils.isEmpty(spec)){
            if (falcu.contains("电气")){
                switch (spec){
                    case  "电气工程":
                        spe_spin.setSelection(1, true);
                        break;
                    case "电机工程":
                        spe_spin.setSelection(2, true);
                        break;
                }
            }
            else if (falcu.contains("计算机")){
                switch (spec) {
                    case "软件工程":
                        spe_spin.setSelection(0, true);
                        break;
                    case "信息安全":
                        spe_spin.setSelection(1, true);
                        break;
                    case "物联网":
                        spe_spin.setSelection(2, true);
                        break;
                }
            }
        }
        if (!TextUtils.isEmpty(hb)){
            if (hb.contains("文学")){
                ((CheckBox)findViewById(R.id.liture)).setChecked(true);
            }
            if (hb.contains("体育")){
                ((CheckBox)findViewById(R.id.pe)).setChecked(true);
            }
            if (hb.contains("音乐")){
                ((CheckBox)findViewById(R.id.music)).setChecked(true);
            }
            if (hb.contains("美术")){
                ((CheckBox)findViewById(R.id.art)).setChecked(true);
            }
        }
        PreferencesUtil.clear(ActivityAddFriend.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.btnSubmit:
                try {
                    infoPack();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                List<StudentInfo> temp =  DataSupport.where("num=?",stu_num).find(StudentInfo.class);
                if (temp!=null &&temp.size()>0  && !TextUtils.isEmpty(temp.get(0).getNum())){//更新
                    info.updateAll("num=?",info.getNum());
                }
                else {//添加
                    StudentInfoDao.insertStudentInfo(info);
                }
                PreferencesUtil.clear(ActivityAddFriend.this);
                intent = new Intent(ActivityAddFriend.this,ActivityMain.class);
                startActivity(intent);
                break;
            case R.id.birthBtn://
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                View birthView = LayoutInflater.from(ActivityAddFriend.this).inflate(R.layout.activity_date_chose,null);
                datePicker = birthView.findViewById(R.id.birth);
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        i1+=1;
                        birth=i+"/"+i1+"/"+i2;
                    }
                });
                AlertDialog.Builder  builder= new AlertDialog.Builder(ActivityAddFriend.this );
                builder.setTitle("选择日期：");

                builder.setView(birthView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        birthBtn.setText(birth);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (birthBtn.getText().toString().contains("点击选择")){
                            birth = "";
                        }else {
                            birth = birthBtn.getText().toString();
                        }
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");//设置类型
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
                break;
            default:
                break;
        }
    }

    private void readPicture(){
        Cursor cursor = null;
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor!=null){

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (view.getId()){
            case  R.id.special:
                spec = spe_spin.getSelectedItem().toString();
                break;
            case R.id.falculty:
                falcu =fac_spin.getSelectedItem().toString();
                switch (i){
                    case 0:
                        special.clear();
                        special.add("软件工程");
                        special.add("信息安全");
                        special.add("物联网");
                        break;
                    case 1:
                        special.clear();
                        special.add("电气工程");
                        special.add("电机工程");
                        break;
                }
                //适配器动态展示spinner选项
                //适配器
                arr_adapter= new ArrayAdapter<String>(ActivityAddFriend.this, android.R.layout.simple_spinner_item, special);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                spe_spin.setAdapter(arr_adapter);
                break;
            default:
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode ==Activity.RESULT_OK){
            switch (requestCode){
                case 1:

                    if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
                        Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                        file_path = PictureTool.getRealPathFromUri(this,uri);
                        openfileBtn.setText(file_path);
                        Log.d(TAG, "onActivityResult: filepath:"+file_path);
                    }
                    break;
                default:
                    break;
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
