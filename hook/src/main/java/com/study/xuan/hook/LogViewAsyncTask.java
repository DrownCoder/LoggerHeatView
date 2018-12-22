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
        LoggerInfo info = new LoggerInfo();
        info.EventId = 4201;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info.pos = 0;
        ranks.put(info.EventId, info);
        LoggerInfo info1 = new LoggerInfo();
        info1.EventId = 4202;
        info1.rank = "31.85%";
        info1.pv = "20,835,447";
        info1.uv = "597,283";
        info1.pos = 1;
        ranks.put(info1.EventId, info1);
        LoggerInfo info2 = new LoggerInfo();
        info2.EventId = 4203;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info2.pos = 2;
        ranks.put(info2.EventId, info2);
        LoggerInfo info3 = new LoggerInfo();
        info3.EventId = 4204;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info3.pos = 3;
        ranks.put(info3.EventId, info3);
        LoggerInfo info4 = new LoggerInfo();
        info4.EventId = 4205;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info4.pos = 4;
        ranks.put(info4.EventId, info4);
        LoggerInfo info5 = new LoggerInfo();
        info5.EventId = 4206;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info5.pos = 5;
        ranks.put(info5.EventId, info5);
        LoggerInfo info6 = new LoggerInfo();
        info6.EventId = 4207;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info6.pos = 6;
        ranks.put(info6.EventId, info6);
        LoggerInfo info7 = new LoggerInfo();
        info7.EventId = 4208;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info7.pos = 7;
        ranks.put(info7.EventId, info7);
        LoggerInfo info8 = new LoggerInfo();
        info7.EventId = 4209;
        info.rank = "31.85%";
        info.pv = "20,835,447";
        info.uv = "597,283";
        info8.pos = 8;
        ranks.put(info8.EventId, info8);
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
        List<EventLog> viewIds = new ArrayList<>();
        for (Map.Entry<EventLog, String> entry : ViewIdMap.EventMap.entrySet()) {
            EventLog eventLog = entry.getKey();
            String name = entry.getValue();
            eventLog.loggerInfo = ranks.get(eventLog.eventId);
            eventLog.viewId = ViewIdMap.ViewMap.get(name);
            viewIds.add(eventLog);
            /*int[] location = new int[2];
            target.getLocationOnScreen(location);
            ArrayList<ViewPoint> data = new ArrayList<>();
            data.add(new ViewPoint(location[0], location[1]));
            TimerPannel pannel = TimerPannel.newInstance(data);
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
