package com.hbh.cl.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hbh on 2017/6/8.
 */

public class FalseView extends View {

    private Paint mPaint;
    private float mWidth,mHeight;

    public FalseView(Context context) {
        this(context,null);
    }

    public FalseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public FalseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FalseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 初始化画笔
     */
    private void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//初始化画笔、消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为空心
        mPaint.setStrokeWidth(8.0f);//设置空心画笔的宽度
        mPaint.setColor(Color.BLACK);//设置画笔颜色
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
        canvas.translate(mWidth/2, mHeight/2);//平移操作
        RectF rectF = new RectF(-200,-200,200,200);
        for (int i = 0; i < 20; i++) {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rectF,mPaint);
        }

    }
}
