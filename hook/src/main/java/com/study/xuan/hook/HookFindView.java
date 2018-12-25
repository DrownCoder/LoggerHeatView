package com.study.xuan.hook;

import android.support.v4.app.FragmentActivity;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class HookFindView {
    public static volatile boolean isCalculating = false;
    public static void hook(final FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction().add(HookFragment.newInstance(),
                "").commitAllowingStateLoss();
    }

}
