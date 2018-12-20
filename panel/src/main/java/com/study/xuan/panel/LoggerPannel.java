package com.study.xuan.panel;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.study.xuan.shapebuilder.shape.ShapeBuilder;

import java.util.ArrayList;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class LoggerPannel extends DialogFragment {
    private Context mContext;
    private FrameLayout mRoot;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public static LoggerPannel newInstance(ArrayList<ViewPoint> points) {

        Bundle args = new Bundle();
        LoggerPannel fragment = new LoggerPannel();
        args.putSerializable("source", points);
        fragment.setArguments(args);
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
        Bundle bundle = getArguments();
        ArrayList<ViewPoint> points = (ArrayList<ViewPoint>) bundle.getSerializable("source");
        for (ViewPoint point : points) {
            mRoot.addView(createPoint(point));
        }
    }

    public ImageView createPoint(ViewPoint point) {
        ImageView viewPoint = new ImageView(mContext);
        ShapeBuilder.create().Type(GradientDrawable.OVAL).Solid(Color.RED).build(viewPoint);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(150, 150);
        params.topMargin = point.y;
        params.leftMargin = point.x;
        viewPoint.setLayoutParams(params);
        viewPoint.setAlpha(0.3f);
        return viewPoint;
    }
}
