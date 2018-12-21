package com.study.xuan.hook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

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
    private List<Integer> viewIds;
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
    public void onMessageEvent(List<Integer> id) {
        viewIds = id;
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
        final SublimePicker picker = new SublimePicker(mContext);
        getActivity().getWindow().addContentView(picker,new ViewGroup.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        picker.initializePicker(null, new SublimeListenerAdapter() {
            @Override
            public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker, SelectedDate
                    selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                                                        .RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    private void injectHeat() {
        if (viewIds != null) {
            for (int id : viewIds) {
                View target = getActivity().findViewById(id);
                target.setBackgroundColor(Color.RED);
            }
        }
    }

}
