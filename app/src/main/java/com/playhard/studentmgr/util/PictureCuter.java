package com.playhard.studentmgr.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

/**
 * Created by user on 2020/3/26.
 */

public class PictureCuter {
    public static Drawable drawable;


    /**
     * 设置图标大小
     */
    public static void setDrawable( Context context, int id, int x, int y, int w, int h){
        drawable=context.getResources().getDrawable(id);//获取drawable
        drawable.setBounds(x,y,w,h);
    }


}
