package com.hbh.cl.customview.model;

import java.io.Serializable;
import java.lang.String;import java.util.ArrayList;

/**
 * Created by hbh on 2017/6/16.
 */
public class SesameModel implements Serializable {
    private int AQI_value;//AQI
    private String level;//污染级别
    private int totalMin;//区间最小值
    private int totalMax;//区间最大值
    private String firstText;//第一个文本值:BETA
    private String updateTime;//更新时间
    private ArrayList<PanelItemModel> panelItemModels;

    public String getFirstText() {
        return firstText;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getAQI_value() {
        return AQI_value;
    }

    public void setAQI_value(int AQI_value) {
        this.AQI_value = AQI_value;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTotalMin() {
        return totalMin;
    }

    public void setTotalMin(int totalMin) {
        this.totalMin = totalMin;
    }

    public int getTotalMax() {
        return totalMax;
    }

    public void setTotalMax(int totalMax) {
        this.totalMax = totalMax;
    }

    public ArrayList<PanelItemModel> getPanelItemModels() {
        return panelItemModels;
    }

    public void setPanelItemModels(ArrayList<PanelItemModel> panelItemModels) {
        this.panelItemModels = panelItemModels;
    }
}
