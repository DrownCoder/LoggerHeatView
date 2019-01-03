package com.study.xuan.hook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.study.xuan.hook.chart.ListViewMultiChartActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Author : xuan.
 * Date : 2018/12/21.
 * Description :the description of this file
 */
public class HookFragment extends Fragment implements View.OnClickListener, DatePickerDialog
        .OnDateSetListener {
    public static final int INIT = -1;
    public static final int REFRESH = 0;
    public static final int HEAT = 1;
    public static final String[] OPTION = new String[]{
            "切换IOS","选择日期", "热力图", "曲线图", "刷新"
    };
    private FrameLayout control;
    private ImageView ivLogo;
    private ProgressBar bar;
    private Context mContext;
    private HintPopupWindow popupWindow;
    private List<LoggerInfo> viewIds;
    public int state = INIT;

    public static HookFragment newInstance() {

        Bundle args = new Bundle();

        HookFragment fragment = new HookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                LogViewAsyncTask task = new LogViewAsyncTask(getActivity());
                task.execute();
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        control = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.control_layout,
                container);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200,200);
        params.topMargin = 800;
        params.gravity = Gravity.RIGHT;
        getActivity().getWindow().addContentView(control, params);
        ivLogo = control.findViewById(R.id.iv_logo);
        bar = control.findViewById(R.id.progress_bar);
        initEvent();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initEvent() {
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HookFindView.isCalculating) {
                    return;
                }
                if (popupWindow == null) {
                    popupWindow = new HintPopupWindow(getActivity(), Arrays.asList(OPTION),
                            HookFragment.this);
                }
                popupWindow.showPopupWindow(control);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<LoggerInfo> id) {
        viewIds = id;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Integer state) {
        switch (state) {
            case LogViewAsyncTask.DO_IN_THREAD:
                ivLogo.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
                break;
            case LogViewAsyncTask.FINISH:
                ivLogo.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
                if (this.state == REFRESH) {
                    injectHeat();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Integer pos = (Integer) v.getTag();
        switch (pos) {
            case 1:
                showTimer();
                break;
            case 2:
                injectHeat();
                break;
            case 3:
                injectChart();
                break;
            case 4:
                refresh();
                break;
        }
        popupWindow.dismissPopupWindow();
    }

    private void injectChart() {

    }

    private void refresh() {
        LogViewAsyncTask task = new LogViewAsyncTask(getActivity());
        task.execute();
        state = REFRESH;
    }

    private void showTimer() {
        Calendar ca = Calendar.getInstance();

        int mYear = ca.get(Calendar.YEAR);
        int mMonth = ca.get(Calendar.MONTH);
        int mDay = ca.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(mContext, this, mYear, mMonth, mDay).show();
    }

    private void injectHeat() {
        if (viewIds != null) {
            for (final LoggerInfo loggerInfo : viewIds) {
                try {
                    state = HEAT;
                    final View target = getActivity().findViewById(loggerInfo.viewId);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        target.setForeground(new ColorDrawable(ColorRank.color(loggerInfo.pos)));
                    }
                    target.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ArrayList<String> list = new ArrayList();
                            list.add("pv=" + loggerInfo.pv);
                            list.add("uv=" + loggerInfo.uv);
                            list.add("EventId=" + loggerInfo.EventId);
                            list.add("排名" + loggerInfo.rank);
                            if (!TextUtils.isEmpty(loggerInfo.other)) {
                                list.add("other=" + loggerInfo.other);
                            }
                            list.add("查看曲线图");
                            /*StringBuilder builder = new StringBuilder();
                            builder.append("pv=").append(loggerInfo.pv)
                                    .append("\nuv=").append(loggerInfo.uv)
                                    .append("(").append(loggerInfo.rank).append(")")
                                    .append("\nEventId=").append(loggerInfo.EventId);
                            if (!TextUtils.isEmpty(loggerInfo.other)) {
                                builder.append("\nOther:[").append(loggerInfo.other).append("]");
                            }*/
                            new HintPopupWindow(getActivity(), list,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getActivity(),
                                                    ListViewMultiChartActivity.class);
                                            startActivity(intent);
                                        }
                                    }).showPopupWindow(target);
                           /* new SimpleTooltip.Builder(mContext)
                                    .anchorView(v)
                                    .text(builder)
                                    .gravity(Gravity.END)
                                    .animated(true)
                                    .transparentOverlay(false)
                                    .build()
                                    .show();*/
                            return true;
                        }
                    });
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i("xxxxxxxx", year + "-" + month + "-" + dayOfMonth);
    }
}
