package com.study.xuan.hook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class TimerPannel extends DialogFragment {
    private Context mContext;
    private FrameLayout mRoot;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public static TimerPannel newInstance() {
        TimerPannel fragment = new TimerPannel();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        Window win = getDialog().getWindow();
        if (getDialog().getWindow() != null)
            win.setBackgroundDrawable(new ColorDrawable(0x66000000));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        //params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        // params.windowAnimations = R.style.BottomDialogAnimation;
        win.setAttributes(params);
        mRoot = new FrameLayout(mContext);
        mRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        initView();
        return mRoot;
    }

    private void initView() {
        final SublimePicker picker = new SublimePicker(mContext);
        picker.setBackgroundColor(Color.WHITE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mRoot.addView(picker, params);
        picker.initializePicker(null, new SublimeListenerAdapter() {
            @Override
            public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker, SelectedDate
                    selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker
                                                        .RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {
                Calendar calendar = selectedDate.getFirstDate();
                String day = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + String.valueOf
                        (calendar.get(Calendar.MONTH)) + "-" + String.valueOf(calendar.get
                        (Calendar.DAY_OF_MONTH));
                EventBus.getDefault().post(day);
                dismissAllowingStateLoss();
            }

            @Override
            public void onCancelled() {
                dismissAllowingStateLoss();
            }
        });
    }

    /*public ImageView createPoint(ViewPoint point) {
        ImageView viewPoint = new ImageView(mContext);
        ShapeBuilder.create().Type(GradientDrawable.OVAL).Solid(Color.RED).build(viewPoint);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(150, 150);
        params.topMargin = point.y;
        params.leftMargin = point.x;
        viewPoint.setLayoutParams(params);
        viewPoint.setAlpha(0.3f);
        return viewPoint;
    }*/
}
