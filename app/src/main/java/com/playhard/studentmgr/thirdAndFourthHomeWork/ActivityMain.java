package com.playhard.studentmgr.thirdAndFourthHomeWork;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.playhard.studentmgr.eighthHonmeWork.ActivityConfig;
import com.playhard.studentmgr.R;
import com.playhard.studentmgr.SixthHomeWork.FirstActivity;
import com.playhard.studentmgr.adapter.MyListAdapter;
import com.playhard.studentmgr.dao.StudentInfoDao;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.secondHomeWork.ActivityStudent2;
import com.playhard.studentmgr.seventhHomeWork.ActivityPhonePlace;
import com.playhard.studentmgr.util.PreferencesUtil;
import com.playhard.studentmgr.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity  implements View.OnTouchListener ,GestureDetector.OnGestureListener{

    private final int FALCULTY_LENGTH=2;//硬编码，待完善
    private final int SPECIAL_LENGTH=5;
    private ListView listView,searchList;
    private Intent intent = null;
    private Bundle editBundle;
    private StudentInfo info;
    private List<StudentInfo> infoList ;
    private Intent actIntent;
    private android.support.v7.widget.Toolbar toolbar;
    private  Button searchBtn;
    private SearchView searchView;
    private String[] sArray;
    private MyListAdapter myAdapter;
    private  GestureDetector gestureDetector;
    LinearLayout mLinearLayout;
    private static final String TAG = "ActivityMain";
    private DrawerLayout drawerLayout;
    private Intent nav_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //启用v7 toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.category);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setCheckedItem(R.id.nav_friend);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                if (item.toString().contains("设置")){

                    nav_intent = new Intent(ActivityMain.this, ActivityConfig.class);
                    startActivity(nav_intent);
                }
//                else if (item.toString().contains("位置")){
//                    nav_intent = new Intent(ActivityMain.this, MapsActivity.class);
//                    startActivity(nav_intent);
//                }
                return  true;
            }
        });


//        滑动页面初始化和设置
        scrollPageInit();
        infoList = StudentInfoDao.findAll();
        searchPanelInit();
        setMyAdapter(ActivityMain.this,infoList);
        setLongItemListenerPanel(infoList);//代码局部分离，方便查询时复用
    }




    private void searchPanelInit(){
        int len = infoList.size();
        sArray = new String[len + FALCULTY_LENGTH + SPECIAL_LENGTH];//硬编码
        int i;
        for ( i=0; i<len;i++){
            sArray[i] = infoList.get(i).getName();
        }
        sArray[i] =  "软件工程";
        sArray[i+1] =  "信息安全";
        sArray[i+2] =  "物联网";
        sArray[i+3] =  "电气工程";
        sArray[i+4] =  "电机工程";
        sArray[i+5] =  "计算机学院";
        sArray[i+6] =  "电气学院";
        //每个entry的事件处理
        editBundle = new Bundle();
    }

    private void scrollPageInit(){
//        滑动页面实现
        gestureDetector = new GestureDetector(this,this);
        mLinearLayout = findViewById(R.id.list_layout);
        mLinearLayout.setOnTouchListener(this);
        mLinearLayout.setFocusable(true);
        mLinearLayout.setClickable(true);
        mLinearLayout.setLongClickable(true);
        listView = findViewById(R.id.lvwStudent);
//        listview冲突问题--被拦截
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }

    private void setLongItemListenerPanel(final List<StudentInfo> listAbout){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int j, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
                builder.setTitle("进行你的操作");
                builder.setPositiveButton("编辑", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        actIntent = new Intent(ActivityMain.this,ActivityStudent2.class);
                        PreferencesUtil.clear(ActivityMain.this);
                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putString("username",listAbout.get(j).getName());
                        editor.putString("num",listAbout.get(j).getNum());
                        editor.putString("sex",listAbout.get(j).getSex());
                        editor.putString("falc",listAbout.get(j).getFalculty());
                        editor.putString("spec",listAbout.get(j).getSpecial());
                        editor.putString("birth",listAbout.get(j).getBirth());
                        editor.putString("hobby",listAbout.get(j).getHobby());
                        editor.apply();
                        startActivity(actIntent);
                    }
                });
                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder delDiag = new AlertDialog.Builder(ActivityMain.this);
                        delDiag.setTitle("确认删除吗？");
                        delDiag.setCancelable(false);
                        delDiag.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DataSupport.deleteAll(StudentInfo.class,"num=?", listAbout.get(j).getNum());
                                listAbout.remove(j);
                                ToastUtil.putMessageCenter(ActivityMain.this,"删除成功");
//                                myAdapter.notifyDataSetChanged();//通知adapter更新数据，而不需要手动跳转
                                intent = new Intent(ActivityMain.this,ActivityMain.class);
                                startActivity(intent);
                            }
                        });
                        delDiag.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        delDiag.show();
                    }
                }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void setMyAdapter(Context context, List list){
        myAdapter = new MyListAdapter(context,list);
        listView.setAdapter(myAdapter);
    }

    private  void setSearchHandler(final List<StudentInfo> list){
        final AlertDialog.Builder searchPanel = new AlertDialog.Builder(ActivityMain.this);
        View view = LayoutInflater.from(ActivityMain.this).inflate(R.layout.search_view,null);
//               View view = getLayoutInflater().inflate(R.layout.search_view,null);
        searchPanel.setView(view);
        searchPanel.show();
        searchList = view.findViewById(R.id.lv);
        searchView = view.findViewById(R.id.sv);
        searchBtn = view.findViewById(R.id.btn_search);
        final ArrayList<StudentInfo> relist = new ArrayList<>();
        final int size = list.size();
        searchView.setQueryHint("输入姓名、学院、专业关键字搜索。。");
        searchList.setTextFilterEnabled(true);
        final ArrayAdapter arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,sArray );
        searchList.setAdapter(arrayAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String s) {
                relist.clear();
                if (s!=null){
//                    searchList.setFilterText(s);//会出现黑框
                    arrayAdapter.getFilter().filter(s);
                    for (int j = 0;j < size; j++){
                        info = list.get(j);
                        if ((info.getName()+info.getSpecial()+info.getFalculty()).contains(s)){
                            relist.add(info);
                        }
                    }
                }
                else {
                    searchList.clearTextFilter();
                }
                return true;
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relist.size()>0){
                    setMyAdapter(ActivityMain.this,relist);
                    setLongItemListenerPanel(relist);
                    Log.d("ActivityMain",relist.toString());
                    System.out.println("*******************************");
                    System.out.println(relist);
                }
                else {
                    ToastUtil.putMessageCenter(ActivityMain.this,"查询不到结果！");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.cloud_upload:
//                ToastUtil.putMessage(ActivityMain.this,"not realize.");
                intent = new Intent(ActivityMain.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.ic_add:
                intent = new Intent(ActivityMain.this,ActivityStudent2.class);
                startActivity(intent);
                break;
            case R.id.ic_refresh:
                infoList = DataSupport.findAll(StudentInfo.class);
                setMyAdapter(this,infoList);
                PreferencesUtil.clear(ActivityMain.this);
                ToastUtil.putMessageCenter(ActivityMain.this,"已最新");
                break;
            case R.id.ic_back:
                intent = new Intent(ActivityMain.this,ActivityLogin.class);
                startActivity(intent);
                break;
            case R.id.ic_search:
              setSearchHandler(infoList);
                break;
            case R.id.ic_notice:
//                noticeOnce();
                ToastUtil.putMessageCenter(this,"通知功能尚未完成");
                break;
            case R.id.ic_num_search:
//                noticeOnce();
                intent = new Intent(ActivityMain.this, ActivityPhonePlace.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    private void noticeOnce(){

//        get notificationmanger
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("first notification title")
                .setContentText("first notification content")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_cloud))
                .build();
        manager.notify(2,notification);
    }


//    把touchEvent传递给gestureDetector
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final  int FLING_MIN_DISTANCE=300;
        final  int FLING_MIN_VELOCITY=150;

        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling left
            intent = new Intent(ActivityMain.this,ActivityStudent2.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main3);
        //启用v7 toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        滑动页面初始化和设置
        scrollPageInit();
        infoList = StudentInfoDao.findAll();
        searchPanelInit();
        setMyAdapter(ActivityMain.this,infoList);
        setLongItemListenerPanel(infoList);//代码局部分离，方便查询时复用
    }
}
