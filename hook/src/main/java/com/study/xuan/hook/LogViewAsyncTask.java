package com.study.xuan.hook;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

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
    private SparseArray<LoggerInfo> ranks;

    public LogViewAsyncTask(FragmentActivity activityRoot) {
        this.activityRoot = new WeakReference<>(activityRoot);
        doRequestLog();
    }

    private void doRequestLog() {
        //请求页面的事件排行
        requestLoggerInfo();
        //请求曲线图数据
    }

    private void requestLoggerInfo() {
        ranks = new SparseArray<>();
        for (int i = 0; i < TempData.EVENID.length; i++) {
            LoggerInfo item = new LoggerInfo();
            item.pos = i;
            item.rank = TempData.RANK[i];
            item.pv = TempData.PV[i];
            item.uv = TempData.UV[i];
            item.EventId = TempData.EVENID[i];
            item.viewName = TempData.ViewId[i];
            ranks.put(item.EventId, item);
        }
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
        List<LoggerInfo> logs = new ArrayList<>();
        for (int i = 0; i < ranks.size(); i++) {
            LoggerInfo item = ranks.valueAt(i);
            item.viewId = ViewIdMap.ViewMap.get(item.viewName);
            logs.add(item);
            /*int[] location = new int[2];
            target.getLocationOnScreen(location);
            ArrayList<ViewPoint> data = new ArrayList<>();
            data.add(new ViewPoint(location[0], location[1]));
            TimerPannel pannel = TimerPannel.newInstance(data);
            pannel.show(activityRoot.get().getSupportFragmentManager(), "");*/
        }
        EventBus.getDefault().post(logs);
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
