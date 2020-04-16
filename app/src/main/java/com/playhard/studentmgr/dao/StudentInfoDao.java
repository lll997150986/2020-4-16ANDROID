package com.playhard.studentmgr.dao;

import com.playhard.studentmgr.domain.StudentInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by user on 2020/3/26.
 */

public class StudentInfoDao {

    static public List<StudentInfo> findAll(){
        return DataSupport.findAll(StudentInfo.class);
    }

}
