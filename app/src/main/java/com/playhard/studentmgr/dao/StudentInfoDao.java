package com.playhard.studentmgr.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.playhard.studentmgr.domain.StudentInfo;
import com.playhard.studentmgr.domain.User;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2020/3/26.
 */

public class StudentInfoDao {


    public static void createDatabaseForStudentInfo(){
        LitePal.getDatabase();
    }

    static public List<StudentInfo>findAllStudentInfo(){

        return DataSupport.findAll(StudentInfo.class);
    }


    static public void updateStudentInfo(StudentInfo studentInfo){
        studentInfo.updateAll();
    }

    static public void deleteStudentInfo(StudentInfo studentInfo){
       studentInfo.delete();
    }

    public static  void insertStudentInfo(StudentInfo studentInfo){
        studentInfo.save();
    }

}
