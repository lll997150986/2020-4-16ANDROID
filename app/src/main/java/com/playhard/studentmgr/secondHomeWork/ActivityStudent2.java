package com.playhard.studentmgr.secondHomeWork;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.dao.StudentInfoDao;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.thirdAndFourthHomeWork.ActivityMain;
import com.playhard.studentmgr.util.PictureCuter;
import com.playhard.studentmgr.util.PreferencesUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityStudent2 extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{

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
    private Toolbar addtb;
    private String birth;
    private Button birthBtn;
    private DatePicker datePicker;
    private LinearLayout mLinearLayout;
    private GestureDetector gestureDetector;
    private static final String TAG = "ActivityStudent2";
    private  static SharedPreferences.Editor editor ;



    /**
     * 创建工作
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student2);
        addtb = findViewById(R.id.addTb);
        setSupportActionBar(addtb);
        findViewByIdAll();
//      滑动效果初始化
       scrollPageInit();
//       日期按钮初始化
        setBirthListener();
        //下拉框联动实现以及下拉框点击事件监听，获取点击的item值（学院和专业）
        spinnerFalInit();
        //设置图标大小，注意,drawableLeft图标大小目前只能用java代码修改
        PictureCuter.setDrawable(this,R.drawable.addpeople_fill1,0,0,50,50);
        name_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
        PictureCuter.setDrawable(this,R.drawable.group,0,0,50,50);
        num_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
        //提交事件处理（包括获取表单值，封装和传递）
        dataSubmit();

    }

    private void scrollPageInit(){
        mLinearLayout.setOnTouchListener(this);
        mLinearLayout.setFocusable(true);
        mLinearLayout.setClickable(true);
        mLinearLayout.setLongClickable(true);
        gestureDetector = new GestureDetector(this,this);
    }

    private void dataSubmit(){
//        更新和添加
        dataRecovery();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoPack();
                List<StudentInfo> temp =  DataSupport.select("name")
                        .where("num = ?",stu_num).find(StudentInfo.class);
                if (temp!=null &&temp.size()>0  && !TextUtils.isEmpty(temp.get(0).getNum())){//更新

                    info.updateAll("num=?",info.getNum());
                }
                else {//添加
                    info.save();
                }
                PreferencesUtil.clear(ActivityStudent2.this);
                intent = new Intent(ActivityStudent2.this,ActivityMain.class);
                startActivity(intent);
            }
        });
    }

    private void setBirthListener(){
        birthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                View birthView = LayoutInflater.from(ActivityStudent2.this).inflate(R.layout.activity_date_chose,null);
                datePicker = birthView.findViewById(R.id.birth);
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        i1+=1;
                        birth=i+"/"+i1+"/"+i2;
                    }
                });
                AlertDialog.Builder  builder= new AlertDialog.Builder(ActivityStudent2.this );
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
            }
        });
    }

    /**
     * 下拉框联动实现以及下拉框点击事件监听，获取点击的Falculty item值
     */
    private void spinnerFalInit(){
//        获取学院选中值并且联动更新专业列表
        fac_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
                arr_adapter= new ArrayAdapter<String>(ActivityStudent2.this, android.R.layout.simple_spinner_item, special);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //加载适配器
                spe_spin.setAdapter(arr_adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}

        });
        //获取选中的专业值
        getSpecial();
    }

    /**
     * 获取点击的special item值
     */
    private void getSpecial(){
        //        获取专业
        spe_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spec = spe_spin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void findViewByIdAll()
    {   fac_spin = findViewById(R.id.falculty);
        spe_spin = findViewById(R.id.special);
        name_text = findViewById(R.id.username);
        num_text = findViewById(R.id.student_num);
        sexMale = findViewById(R.id.man);
        sexFemale = findViewById(R.id.woman);
        btn_submit = findViewById(R.id.btnSubmit);
        info = new StudentInfo();
        birthBtn = findViewById(R.id.birthBtn);
        mLinearLayout = findViewById(R.id.addlay);

    }

    //获取表单提交的数据
    private void getSubmitData(){
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
        Log.d(TAG, "getSubmitData: username"+username);
    }

    /**
     * 对表单的数据进行info包装
     */
    private void infoPack(){
        getSubmitData();
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
        Log.d(TAG, "infoPack: "+info.getName());
        Log.d(TAG, "infoPack: "+info.getNum());
        Log.d(TAG, "infoPack: "+info.getSex());
        Log.d(TAG, "infoPack: "+info.getHobby());
        Log.d(TAG, "infoPack: "+info.getBirth());
        Log.d(TAG, "infoPack: "+info.getSpecial());
        Log.d(TAG, "infoPack: "+info.getFalculty());
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
        PreferencesUtil.clear(ActivityStudent2.this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {}

    /**
     * 监听滑动事件
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final  int FLING_MIN_DISTANCE=300;
        final int FLING_MIN_VELOCITY=150;
        if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            intent = new Intent(ActivityStudent2.this,ActivityMain.class);
            PreferencesUtil.clear(ActivityStudent2.this);
            editor = getSharedPreferences("data",MODE_PRIVATE).edit();
            getSubmitData();
            editor.putString("username",name_text.getText().toString());
            editor.putString("num",stu_num);
            editor.putString("sex",sex);
            editor.putString("birth",birth);
            editor.putString("falc",falcu);
            editor.putString("spec",spec);
            editor.putString("hobby",hobby[0]+hobby[1]+hobby[2]+hobby[3]);
            editor.apply();
            startActivity(intent);
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addtoolbar,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_back:
                intent = new Intent(ActivityStudent2.this,ActivityMain.class);
                startActivity(intent);
                break;
        }
        return  true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_student2);
        addtb = findViewById(R.id.addTb);
        setSupportActionBar(addtb);
        findViewByIdAll();
//      滑动效果初始化
        scrollPageInit();
//       日期按钮初始化
        setBirthListener();
        //下拉框联动实现以及下拉框点击事件监听，获取点击的item值（学院和专业）
        spinnerFalInit();
        //设置图标大小，注意,drawableLeft图标大小目前只能用java代码修改
        PictureCuter.setDrawable(this,R.drawable.addpeople_fill1,0,0,50,50);
        name_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
        PictureCuter.setDrawable(this,R.drawable.group,0,0,50,50);
        num_text.setCompoundDrawables(PictureCuter.drawable,null,null,null);
        //提交事件处理（包括获取表单值，封装和传递）
        dataSubmit();
    }


}
