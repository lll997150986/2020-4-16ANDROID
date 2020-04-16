package com.playhard.studentmgr.seventhHomeWork;

/**
 * Created by user on 2020/4/8.
 */

public interface HttpCallbackListener {
    void onFinshed(String response);
    void onError(Exception e);
}
