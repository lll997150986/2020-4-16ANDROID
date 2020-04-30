package com.playhard.studentmgr.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.playhard.studentmgr.R;
import com.playhard.studentmgr.activity.lifecycle.FirstActivity;
import com.playhard.studentmgr.custom.MyListAdapter;
import com.playhard.studentmgr.dao.StudentInfoDao;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.util.PreferencesUtil;
import com.playhard.studentmgr.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity  implements  View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener,NavigationView.OnNavigationItemSelectedListener  {


    String username;
    TextView tv_username;

    private final int FALCULTY_LENGTH=2;//硬编码，待完善
    private final int SPECIAL_LENGTH=5;
    private ListView listView;
    private Intent intent = null;
    private Bundle editBundle;
    private StudentInfo info;
    private List<StudentInfo> infoList ;
    private Intent actIntent;
    private android.support.v7.widget.Toolbar toolbar;
    private SearchView searchView;
    private String[] sArray;
    private MyListAdapter myAdapter;
    private static final String TAG = "ActivityMain";
    private DrawerLayout drawerLayout;
    private Intent nav_intent;
    SwipeRefreshLayout swipe_container;
    final ArrayList<StudentInfo> relist = new ArrayList<>();
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
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

        sideNavUsernameInit();//drawerLayout用户名显示
//        navigationView item点击事件
        navigationView.setCheckedItem(R.id.nav_friend);
        navigationView.setNavigationItemSelectedListener(this);
        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        infoList = StudentInfoDao.findAllStudentInfo();
        setMyAdapter(ActivityMain.this,infoList);
        setLongItemListenerPanel(infoList);//
    }

    private void sideNavUsernameInit(){
       // 初始化sidebar的用户名
        View view = LayoutInflater.from(ActivityMain.this).inflate(R.layout.nav_header, null);
        tv_username = view.findViewById(R.id.username);
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                username = bundle.getString("username");
                if (!TextUtils.isEmpty(username)){

                    tv_username.setText(username);
                }
            }
        }

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

//        View view = LayoutInflater.from(ActivityMain.this).inflate(R.layout.search_view,null);
//        searchView = view.findViewById(R.id.sv);
//        searchView.setQueryHint("输入姓名、学院、专业关键字搜索。。");
//
//        arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,sArray );
//        searchView.setOnQueryTextListener(this);
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
                        actIntent = new Intent(ActivityMain.this,ActivityAddFriend.class);
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
                                myAdapter.notifyDataSetChanged();
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
        if (myAdapter==null){
            myAdapter = new MyListAdapter(context,list);
        }
        else{
//            myAdapter = new MyListAdapter(context,list);
            myAdapter.notifyDataSetChanged();
        }

        listView = findViewById(R.id.lvwStudent);
        listView.setAdapter(myAdapter);
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
            case R.id.ic_lifecycle:
                intent = new Intent(ActivityMain.this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.ic_add://暂时使用添加学生替代
                intent = new Intent(ActivityMain.this,ActivityAddFriend.class);
                startActivity(intent);
                break;
            case R.id.ic_group:
              ToastUtil.putMessageCenter(this,"群聊功能尚未完成");
                break;
            case R.id.ic_notice:
                ToastUtil.putMessageCenter(this,"消息通知功能尚未完成");
                break;
//            case R.id.btn_search:
//                if (relist.size()>0){
//                    setMyAdapter(ActivityMain.this,relist);
//                    setLongItemListenerPanel(relist);
//                    Log.d("ActivityMain",relist.toString());
//                }
//                else {
//                    ToastUtil.putMessageCenter(ActivityMain.this,"查询不到结果！");
//                }
//                break;
        }
        return true;
    }







    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //如果直接infoList=StudentInfoDao.findAllStudentInfo();不刷新，notifyDataSetChanged是观察者模式，数据地址变了，不是同一个对象
                infoList.clear();
                List list = StudentInfoDao.findAllStudentInfo();
                infoList.addAll(list);
                Log.d(TAG, "run123: "+list);
                myAdapter.notifyDataSetChanged();
//                setMyAdapter(ActivityMain.this,infoList);
                swipe_container.setRefreshing(false);
                PreferencesUtil.clear(ActivityMain.this);
                ToastUtil.putMessage(ActivityMain.this,"更新完成");
            }
        }, 0); // 0秒后发送消息，停止刷新



    }

    // 当点击搜索按钮时触发该方法
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        relist.clear();
        if (s!=null){
//                    searchList.setFilterText(s);//会出现黑框
            arrayAdapter.getFilter().filter(s);
            for (int j = 0;j < infoList.size(); j++){
                info = infoList.get(j);
                if ((info.getName()+info.getSpecial()+info.getFalculty()).contains(s)){
                    relist.add(info);
                }
            }
        }
        else {
//            searchList.clearTextFilter();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        drawerLayout.closeDrawers();
        if (item.toString().contains("设置")){

            nav_intent = new Intent(ActivityMain.this, ActivityConfig.class);
            startActivity(nav_intent);
        }
        else if (item.toString().contains("好友")){
            nav_intent = new Intent(ActivityMain.this, FriendInfoActivity.class);
            startActivity(nav_intent);
        }
        else if (item.toString().contains("背景")){
        }
        return  true;
    }

}




