package com.hbh.cl.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by hbh on 2017/6/13.
 */

public class SpeedView extends View {

    private int mWidth, mHeight;
    private Paint mPaint;

    public SpeedView(Context context) {
        this(context, null);
    }

    public SpeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpeedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int measureSpec){
        int resultSpec = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        int measureMode = MeasureSpec.getMode(measureSpec);
        int measureSize = MeasureSpec.getSize(measureSpec);
        if (measureMode == MeasureSpec.AT_MOST) {
            resultSpec = Math.min(resultSpec, measureSize);
        } else if (measureMode == MeasureSpec.EXACTLY) {
            resultSpec = measureSize;
        }
        return resultSpec;
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
        canvas.translate(mWidth/2, mHeight/2);
        Path path = new Path();
        Path path1 = new Path();
        path.lineTo(100,100);
//        path.moveTo(200,200);
//        path.setLastPoint(100,50);
//        path.lineTo(300,300);
//        path.close();

//        path.addCircle(0, 0, 90, Path.Direction.CW);
//        path1.addRect(-100,-100, 100,100, Path.Direction.CW);

//        path.addPath(path1);
//        path.addPath(path1,0,-100);

//        path.addArc(0, 0, 200, 200, 0, 270);
        path.arcTo(0, 0, 200, 200, 0, 270, true);

        canvas.drawPath(path, mPaint);
    }
}
