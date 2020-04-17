package com.playhard.studentmgr.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.domain.User;
import com.playhard.studentmgr.ninthHomeWork.StudentDBHelper;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2020/3/26.
 */

public class StudentInfoDao {

    private static  StudentDBHelper helper ;
   private static SQLiteDatabase db;
    static public List<StudentInfo> findAll(){
        return DataSupport.findAll(StudentInfo.class);
    }

    public static void createDatabase(Context context){
//        helper = new StudentDBHelper(context,"User.db",null,1);
         helper = new StudentDBHelper(context,"User.db",null,2);
        helper.getWritableDatabase();
    }

    static public List<User>findAllUser(){
        db = helper.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();

        Cursor cursor = db.query("User",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
                User temp = new User();
            do {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                temp.setUsername(username);
                temp.setPassword(password);
                list.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    static public List<User>findByUsername(String username){
        db = helper.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();

        Cursor cursor = db.query("User",null,"username=?",new String[]{username},null,null,null,null);
        if (cursor.moveToFirst()){
            User temp = new User();
            do {
                String password = cursor.getString(cursor.getColumnIndex("password"));
                temp.setUsername(username);
                temp.setPassword(password);
                list.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    static public void updateUser(User user){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        db.update("User",values,"username=?",new String[]{user.getUsername()});
    }

    static public void deleteUser(User user){
        db = helper.getWritableDatabase();
        db.delete("User","username=?",new String[]{user.getUsername()});
    }

    public static  void insertUser(User user){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        db.insert("User",null,values);
        values.clear();
    }


}
