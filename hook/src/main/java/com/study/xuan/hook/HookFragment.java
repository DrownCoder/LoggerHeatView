package com.study.xuan.hook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Author : xuan.
 * Date : 2018/12/21.
 * Description :the description of this file
 */
public class HookFragment extends Fragment implements View.OnClickListener {
    public static final String[] OPTION = new String[]{
            "选择日期","热力图","曲线图"
    };
    private FrameLayout control;
    private Context mContext;
    private HintPopupWindow popupWindow;
    private List<EventLog> viewIds;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        control = new FrameLayout(mContext);
        ShapeBuilder.create().Type(GradientDrawable.OVAL).Solid(Color.BLACK).build(control);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200, 200);
        params.topMargin = 800;
        params.gravity = Gravity.RIGHT;
        getActivity().getWindow().addContentView(control, params);

        initEvent();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initEvent() {
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    popupWindow = new HintPopupWindow(getActivity(), Arrays.asList(OPTION),HookFragment.this);
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
    public void onMessageEvent(List<EventLog> id) {
        viewIds = id;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String day) {
        Log.i("xxxxxxxxx", day);
    }

    @Override
    public void onClick(View v) {
        Integer pos = (Integer) v.getTag();
        switch (pos) {
            case 0:
                showTimer();
                break;
            case 1:
                injectHeat();
                break;
            case 2:
                break;
        }
        popupWindow.dismissPopupWindow();
    }

    private void showTimer() {
        TimerPannel pannel = TimerPannel.newInstance();
        pannel.show(getFragmentManager(),"");
    }

    private void injectHeat() {
        if (viewIds != null) {
            for (final EventLog id : viewIds) {
                final View target = getActivity().findViewById(id.viewId);
                if (id.loggerInfo != null) {
                    target.setBackgroundColor(ColorRank.color(id.loggerInfo.pos));
                    target.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("pv=").append(id.loggerInfo.pv)
                                    .append("\nuv=").append(id.loggerInfo.uv)
                                    .append("(").append(id.loggerInfo.rank).append(")")
                                    .append("\nEventId=").append(id.eventId);
                            if (!TextUtils.isEmpty(id.other)) {
                                builder.append("\nOther:[").append(id.other).append("]");
                            }
                            new SimpleTooltip.Builder(mContext)
                                    .anchorView(v)
                                    .text(builder)
                                    .gravity(Gravity.END)
                                    .animated(true)
                                    .transparentOverlay(false)
                                    .build()
                                    .show();

                            return true;
                        }
                    });
                }
            }
        }
    }

}
