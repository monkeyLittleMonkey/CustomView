package com.hbh.cl.customview.model;

/**
 * Created by hbh on 2017/1/18.
 */

public class PieData {

    private String name;//名称
    private float value;//数值
    private float percentage;//百分比

    private int color = 0;//颜色
    private float angle = 0;//角度

    public PieData() {

    }

    public PieData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
