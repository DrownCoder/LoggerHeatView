package com.study.xuan.hook;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.study.xuan.panel.LoggerPannel;
import com.study.xuan.panel.ViewPoint;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class LogViewAsyncTask extends AsyncTask {
    private WeakReference<FragmentActivity> activityRoot;

    public LogViewAsyncTask(FragmentActivity activityRoot) {
        this.activityRoot = new WeakReference<>(activityRoot);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        //记录id和name的关系
        logViewName(activityRoot.get().getWindow().getDecorView());
        try {
            //等待UI绘制完毕
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //处理event和id的关系
        saveEventView();
        return null;
    }

    private void saveEventView() {
        List<Integer> viewIds = new ArrayList<>();
        for (Map.Entry<EventLog,String> entry:ViewIdMap.EventMap.entrySet()) {
            String name = entry.getValue();
            viewIds.add(ViewIdMap.ViewMap.get(name));
            /*int[] location = new int[2];
            target.getLocationOnScreen(location);
            ArrayList<ViewPoint> data = new ArrayList<>();
            data.add(new ViewPoint(location[0], location[1]));
            LoggerPannel pannel = LoggerPannel.newInstance(data);
            pannel.show(activityRoot.get().getSupportFragmentManager(), "");*/
        }
        EventBus.getDefault().post(viewIds);
    }

    private static List<View> logViewName(View view) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (viewchild.getId() != View.NO_ID) {
                    ViewIdMap.logView(getViewIdName(viewchild), viewchild.getId());
                }
                allchildren.add(viewchild);
                //再次 调用本身（递归）
                allchildren.addAll(logViewName(viewchild));
            }
        }
        return allchildren;
    }


    public static String getViewIdName(View view) {
        return view.getResources().getResourceEntryName(view.getId());
    }
}
