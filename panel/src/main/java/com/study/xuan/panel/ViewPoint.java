package com.study.xuan.panel;

import java.io.Serializable;

/**
 * Author : xuan.
 * Date : 2018/12/20.
 * Description :the description of this file
 */
public class ViewPoint implements Serializable {
    public int x;
    public int y;
    public int evenid;
    public String other;
    public int pv;
    public int uv;

    public ViewPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
