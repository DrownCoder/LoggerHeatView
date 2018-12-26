package com.study.xuan.hook;

import android.graphics.Color;

/**
 * Author : xuan.
 * Date : 2018/12/22.
 * Description :the description of this file
 */
public class ColorRank {
    public static final String[] COLOR = new String[]{
            "#66D0021B",
            "#66E4142E",
            "#66CE2136",
            "#66E33D51",
            "#66FC0B03",
            "#66FB1911",
            "#66F92C25",
            "#66F8463F",
            "#66FB625C",
            "#66F97873",
            "#66F96706",
            "#66FA7319",
            "#66FB7E2B",
            "#66F9883D",
            "#66FB9652",
            "#66FA9F62"
    };

    public static int color(int rank) {
        if (rank >= COLOR.length) {
            return Color.parseColor(COLOR[COLOR.length-1]);
        }else{
            return Color.parseColor(COLOR[rank]);
        }
    }
}
