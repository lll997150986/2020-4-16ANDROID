package com.playhard.studentmgr.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.playhard.studentmgr.R;

/**
 * Created by user on 2020/3/11.
 */

public class ToastUtil {
    private static Toast toast;
    private static Toast itoast;
    private  static  View toastview;
    public static  void putMessage(Context context,String message){
        if (toast==null){
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(message);
        }
        toast.show();
    }
    public static void putMessageCenter(Context context,String message){
        View toastview= LayoutInflater.from(context).inflate(R.layout.toast_image_layout,null);
        TextView text = (TextView) toastview.findViewById(R.id.to_message);
        text.setText(message);    //要提示的文本
        if (itoast==null){
             itoast=new Toast(context);   //上下文
        }
        itoast.setGravity(Gravity.CENTER,0,0);   //位置居中
        itoast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示
        itoast.setView(toastview);   //把定义好的View布局设置到Toast里面
        itoast.show();
    }
}
