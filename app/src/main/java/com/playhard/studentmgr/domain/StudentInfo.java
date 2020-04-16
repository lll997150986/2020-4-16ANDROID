package com.playhard.studentmgr.domain;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by user on 2020/3/3.
 */

public class StudentInfo extends DataSupport implements Serializable{
    private String name;
    private String num;
    private String hobby;
    private String special;
    private  String falculty;
    private String sex;
    private String birth;

    public StudentInfo() {
    }

    public StudentInfo(String name, String num, String hobby, String special, String falculty, String sex, String birth) {
        this.name = name;
        this.num = num;
        this.hobby = hobby;
        this.special = special;
        this.falculty = falculty;
        this.sex = sex;
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "学号:" + num  +
                "\t\t\t 性别:" + sex +
                "\t\t\t学院: " + falculty +
                "\t\t\t专业:" + special +
                "\t\t\t爱好:" + hobby;


    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getFalculty() {
        return falculty;
    }

    public void setFalculty(String falculty) {
        this.falculty = falculty;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
