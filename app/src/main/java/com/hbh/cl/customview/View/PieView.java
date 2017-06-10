package com.hbh.cl.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hbh.cl.customview.model.PieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hbh on 2017/1/18.
 */

public class PieView extends View {

    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private float startAngle = 0;//饼状图初始绘制角度
    private List<PieData> mDatas;//数据
    private int mWidth, mHeight;//宽高
    private Paint mPaint = new Paint();


    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);//填充
        mPaint.setAntiAlias(true);//平滑，抗锯齿
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mDatas)
            return;
        float currentStartAngle = startAngle;//当前起始角度
        canvas.translate(mWidth/2, mHeight/2);//将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight)/2 * 0.8);//饼状图半径
        RectF rectF = new RectF(-r,-r,r,r);//饼状图绘制区域

        for (int i = 0; i < mDatas.size(); i++) {
            PieData pie = mDatas.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF,currentStartAngle,pie.getAngle(),true,mPaint);
            currentStartAngle += pie.getAngle();
        }
    }

    //设置起始角度
    public void setStartAngle(int startAngle){
        this.startAngle  = startAngle;
        invalidate();//刷新重绘
    }

    // 设置数据
    public void setData(List<PieData> mData) {
        this.mDatas = mData;
        initData(mData);
        invalidate();//刷新重绘
    }

    private void initData(List<PieData> mdatas){
        if (null == mdatas || mdatas.size() == 0)
            return;
        float sumValue = 0;
        for (int i = 0; i < mdatas.size(); i++) {
            PieData pie = mdatas.get(i);
            sumValue += pie.getValue();
            int j =  i % mColors.length;
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < mdatas.size(); i++) {
            PieData pie = mdatas.get(i);

            float percentage = pie.getValue() / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度

            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(angle);                            // 记录角度大小
            sumAngle += angle;

        }

    }
}
