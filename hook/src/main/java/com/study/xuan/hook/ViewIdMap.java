package com.study.xuan.hook;

import java.util.HashMap;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class ViewIdMap {
    public static HashMap<String, Integer> ViewMap = new HashMap<>();

    public static void logView(String idName, int id) {
        ViewMap.put(idName, id);
    }
}
