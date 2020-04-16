package com.playhard.studentmgr.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.playhard.studentmgr.domain.StudentInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by user on 2020/3/25.
 */

public class MyPagerAdapter extends PagerAdapter {
    List<View> list;
    private List<StudentInfo> infoList ;
    private String[] sArray;
    private final int FALCULTY_LENGTH=2;//硬编码，待完善
    private final int SPECIAL_LENGTH=5;

    public MyPagerAdapter(List<View> list) {
        this.list = list;
    }

    /**
     * ViewPager里面对每个页面的管理是key-value形式的，也就是说每个page都有个对应的id（id是object类型），需要对page操作的时候都是通过id来完成的

     public Object instantiateItem(ViewGroup container, int position)；
     这是pageAdapter里的函数，功能就是往PageView里添加自己需要的page。同时注意它还有个返回值object，这就是那个id。
     public abstract boolean isViewFromObject (View view, Object object)
     这个函数就是用来告诉框架，这个view的id是不是这个object。
     谷歌官方推荐把view当id用，所以常规的instantiateItem（）函数的返回值是你自己定义的view，而isViewFromObject（）的返回值是view == object。
     * @return
     */

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //创建指定位置的视图
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = list.get(position);
        //将即将创建的view视图，加载到容器中
        switch (position){
            case 0:

                break;
            case 1:
                break;
        }





        container.addView(view);
        return view;
    }
    //从容器总移除某个视图
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    private void eventInit(){
        infoList = DataSupport.findAll(StudentInfo.class);
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
    }


}
