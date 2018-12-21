package com.study.xuan.hook;

import android.content.Context;

/**
 * Author : xuan.
 * Date : 2018/12/21.
 * Description :the description of this file
 */
public class Utils {
    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        if (context == null || context.getResources() == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        if (context == null || context.getResources() == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
