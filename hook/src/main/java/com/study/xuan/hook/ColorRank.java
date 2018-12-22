package com.study.xuan.hook;

import android.graphics.Color;

/**
 * Author : xuan.
 * Date : 2018/12/22.
 * Description :the description of this file
 */
public class ColorRank {
    public static final String[] COLOR = new String[]{
            "#66F20303",
            "#66FF703C",
            "#66FFA73C",
            "#66FFDF3C",
            "#66DBFF3C",
            "#667AFF3C",
            "#66439F24",
            "#66286016"
    };

    public static int color(int rank) {
        if (rank >= COLOR.length) {
            return Color.parseColor(COLOR[COLOR.length-1]);
        }else{
            return Color.parseColor(COLOR[rank]);
        }
    }
}
