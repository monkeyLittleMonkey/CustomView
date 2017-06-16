package com.hbh.cl.customview.model;

import java.io.Serializable;

/**
 * Created by hbh on 2017/6/16.
 */
public class PanelItemModel implements Serializable {
    private int min;//该级别最小值
    private int max;//该级别最大值

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
