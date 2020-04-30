package com.playhard.studentmgr.custom;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.playhard.studentmgr.dao.StudentDBHelper;

public class StudentContentProvider extends ContentProvider {
    public static final int STUDENT_DIR = 0;
    public static final int STUDENT_ITEM = 1;
    public static UriMatcher uriMatcher;
    private StudentDBHelper dbHelper;

    private static String AUTHORITY = "com.playhard.studentmgr.provider";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "studentinfo", STUDENT_DIR);
        uriMatcher.addURI(AUTHORITY, "studentinfo/#", STUDENT_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new StudentDBHelper(getContext(), "Student.db", null, 6);
//        StudentInfoDao.createDatabaseForStudentInfo();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case STUDENT_DIR:
                cursor = db.query("studentinfo", strings, s, strings1, null, null, s1);
                break;
            case STUDENT_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = db.query("studentinfo", strings, "num=?", new String[]{id}, null, null, s1);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case STUDENT_DIR:
                return "vnd.android.cursor.dir/vnd.com.playhard.studentmgr.provider.Student";
            case STUDENT_ITEM:
                return "vnd.android.cursor.item/vnd.com.playhard.studentmgr.provider.Student";
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case STUDENT_DIR:
            case STUDENT_ITEM:
                long newId = db.insert("studentinfo", null, contentValues);
//                uriReturn = Uri.parse("content://" + AUTHORITY + "/Student/" + newId);
                break;
            default:
                break;
        }
//        return uriReturn;
        return  null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENT_DIR:
                deleteRows = db.delete("studentinfo", s, strings);
                break;
            case STUDENT_ITEM:
                String id = uri.getPathSegments().get(1);
                deleteRows = db.delete("studentinfo", "num=?", new String[]{id});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = 0;
        switch (uriMatcher.match(uri)) {
            case STUDENT_DIR:
                rows = db.update("studentinfo", contentValues, s, strings);
                break;
            case STUDENT_ITEM:
                String id = uri.getPathSegments().get(1);
                rows = db.update("studentinfo", contentValues, "num=?", new String[]{id});
                break;
            default:
                break;
        }
        return rows;
    }
}
