package com.playhard.studentmgr.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.playhard.studentmgr.R;
import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.util.ComponentUtil;
import com.playhard.studentmgr.util.PictureTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 2020/3/3.
 */

public class MyListAdapter extends BaseAdapter {
//    private   final int COUNT = 100;
    private Context mContext;
    private ViewHolder viewHolder = null;
    private List<StudentInfo> list;
    private StudentInfo info;

    public MyListAdapter(Context mContext ,List<StudentInfo> list) {
        this.mContext = mContext;
        this.list=list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }
    /**
     * 静态内部类，每次更新ListView时需要，只加载一次（封装每个entry中的数据）
     */
    static class  ViewHolder{
        public ImageView imageView;
        public TextView tv_name;
        public TextView tv_num;
        public TextView tv_sex;
        public TextView tv_hob;
        public TextView tv_birth;
        public TextView tag_name;
        public TextView tag_birth ;
        public TextView tag_sex ;
        public TextView tag_hob ;
        public TextView tag_num ;
        public View layout;
        public String photo;
    }

    /**
     * 映射view
     * @param i ：加载到第几个entry上
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(  int   i, View view, ViewGroup viewGroup) {
// i就是位置从0开始，convertView是Spinner,ListView中每一项要显示的view
// 通常return 的view也就是convertView
// parent就是父窗体了，也就是Spinner,ListView,GridView了.

        info = list.get(i);//获取i对应的值即新加入的info

        if (view == null){
//            映射viewItem layout
//            布局填充器的实现，即把Xml布局文件解析成View
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item,null);
            //设置entry的值
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.iv);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_num = view.findViewById(R.id.tv_num);
            viewHolder.tv_sex = view.findViewById(R.id.tv_sex);
//            viewHolder.tv_falc = view.findViewById(R.id.tv_falc);
//            viewHolder.tv_spec = view.findViewById(R.id.tv_spec);
            viewHolder.tv_hob = view.findViewById(R.id.tv_hob);
            viewHolder.tv_birth = view.findViewById(R.id.tv_birth);
            viewHolder.layout = view.findViewById(R.id.lay);
            viewHolder.tag_name =view.findViewById(R.id.tag_name);
            viewHolder.tag_birth =view.findViewById(R.id.tag_birth);
//            viewHolder.tag_falc =view.findViewById(R.id.tag_falc);
//            viewHolder.tag_spec =view.findViewById(R.id.tag_spec);
            viewHolder.tag_sex =view.findViewById(R.id.tag_sex);
            viewHolder.tag_hob =view.findViewById(R.id.tag_hob);
            viewHolder.tag_num =view.findViewById(R.id.tag_num);

            viewHolder.tv_name.setText(info.getName().toString());
            viewHolder.tv_num.setText(info.getNum().toString());
            viewHolder.tv_sex.setText(info.getSex().toString());
//            viewHolder.tv_falc.setText(info.getFalculty().toString());
//            viewHolder.tv_spec.setText(info.getSpecial().toString());
            viewHolder.tv_hob.setText(info.getHobby().toString());

            viewHolder.tv_num.setText(info.getNum().toString());
            viewHolder.tv_sex.setText(info.getSex().toString());
//            viewHolder.tv_falc.setText(info.getFalculty().toString());
//            viewHolder.tv_spec.setText(info.getSpecial().toString());
            viewHolder.tv_hob.setText(info.getHobby().toString());
            //default image&&clean cache

            String photoStr = info.getPhoto();

            if (!TextUtils.isEmpty(photoStr)){
                File file = new File(photoStr);
                if (file.exists()){
                    Bitmap bitmap = null;
//                    compress bitmap and replace the file
                    try {
                        bitmap = PictureTool.getImage(photoStr).copy(Bitmap.Config.ARGB_8888,true);
                        Log.d("compress", "before compress-> getView: bitmap.size:width:"+bitmap.getWidth()+",height:"+bitmap.getHeight()+",getByteCount:"+
                                bitmap.getByteCount());
                        if (bitmap!=null && bitmap.getWidth()>96 &&bitmap.getHeight()>96){
                            bitmap.setWidth(96);
                            bitmap.setHeight(96);
                            PictureTool.saveBitmapFile(bitmap,file);
                            Log.d("compress", "after compress->getView: bitmap.size:width:"+bitmap.getWidth()+",height:"+bitmap.getHeight()+",getByteCount:"+
                            bitmap.getByteCount());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    Bitmap bitmap = PictureTool.compressByResolution(photoStr, 96, 96);
                    PictureTool.saveBitmapFile(bitmap,file);
                    viewHolder.imageView.setImageURI(Uri.fromFile(file));
                }
            }
            else {
                Glide.with(mContext).load(R.drawable.cat).into(viewHolder.imageView);
            }


            ComponentUtil.changeTVFontSize(viewHolder.tv_birth,
                                            viewHolder.tv_name,
                                            viewHolder.tv_num,
                                            viewHolder.tv_hob,
                                            viewHolder.tv_sex);
            ComponentUtil.changeTVFontSize(viewHolder.tag_name,
                                            viewHolder.tag_birth,
                                            viewHolder.tag_hob,
                                            viewHolder.tag_sex,
                                            viewHolder.tag_num);
            if (viewHolder.tv_birth!=null){
                viewHolder.tv_birth.setText(info.getBirth().toString());
            }


//            synchronized (MyListAdapter.class){
//                if (i%2==0){
//                    viewHolder.layout.setBackgroundColor(viewHolder.layout.getResources().getColor(R.color.colorGray4));
//                }
//                else {
//                    viewHolder.layout.setBackgroundColor(viewHolder.layout.getResources().getColor(R.color.colorGray5));
//                }
//            }

            //绑定（viewHolder是viewContext的一部分组件）
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }
}
