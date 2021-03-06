package com.playhard.studentmgr.ninthHomeWork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.playhard.studentmgr.util.ToastUtil;

/**
 * Created by user on 2020/4/17.
 */

public class StudentDBHelper extends SQLiteOpenHelper {
    public static final String CREATE_USER="create table User(" +
//            "id integer primary key autoincrement," +
            "username text primary key," +
            "password text)";
    private Context context;

    public StudentDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_USER);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("drop table if exists User");
        onCreate(sqLiteDatabase);
    }
}
