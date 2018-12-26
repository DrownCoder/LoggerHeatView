package com.study.xuan.hook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import static com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART_INSIDE;

/**
 * Author : xuan.
 * Date : 2018/12/26.
 * Description :the description of this file
 */
public class HookChartFragment extends DialogFragment {
    private View mRoot;
    private Context mContext;
    private LineChart lineChart;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = LayoutInflater.from(mContext).inflate(R.layout.chart_layout, container, false);
        lineChart = mRoot.findViewById(R.id.chart);
        initChart();
        return mRoot;
    }

    private void initChart() {
        //在点击高亮值时回调
        lineChart.setOnChartValueSelectedListener(this);

        //设置整个图表的颜色
        lineChart.setBackgroundResource(R.drawable.bg_line_chart);

        Description description = lineChart.getDescription();
        description.setYOffset(10);
        description.setEnabled(true);
        description.setText("时间");

        //设置标签的位置(如 发电量 实时功率)
        lineChart.getLegend().setPosition(RIGHT_OF_CHART_INSIDE);

        //是否可以缩放、移动、触摸
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);

        //不能让缩放，不然有bug，所以接口也没实现
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(true);

        //设置图表距离上下左右的距离
        lineChart.setExtraOffsets(10, 10, 10, 0);

        //获取左侧侧坐标轴
        YAxis leftAxis = lineChart.getAxisLeft();

        //设置是否显示Y轴的值
        leftAxis.setDrawLabels(true);
        leftAxis.setTextColor(this.getResources().getColor(R.color.homecolor));

        //设置所有垂直Y轴的的网格线是否显示
        leftAxis.setDrawGridLines(true);

        //设置虚线
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        //设置Y极值，我这里没设置最大值，因为项目需要没有设置最大值
        leftAxis.setAxisMinimum(0f);

        //将右边那条线隐藏
        lineChart.getAxisRight().setEnabled(false);
        //获取X轴
        XAxis xAxis = lineChart.getXAxis();
        //设置X轴的位置，可上可下
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //将垂直于X轴的网格线隐藏，将X轴显示
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        //设置X轴上lable颜色和大小
        xAxis.setTextSize(8f);
        xAxis.setTextColor(Color.GRAY);

        //设置X轴高度
        xAxis.setAxisLineWidth(1);
    }
}
