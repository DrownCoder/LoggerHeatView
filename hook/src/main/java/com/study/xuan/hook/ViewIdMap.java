package com.study.xuan.hook;

import android.util.Log;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class ViewIdMap {
    public static HashMap<String, Integer> ViewMap = new HashMap<>();
    public static HashMap<EventLog,String> EventMap = new HashMap<>();

    static {
        EventMap.put(new EventLog(4201), "tv_btn");
        EventMap.put(new EventLog(4202,"floor=查看更多"), "tv_btn1");
    }

    public static void logView(String idName, int id) {
        //Log.i("xxxxxxxx", "name"+idName+id);
        ViewMap.put(idName, id);
    }
}
