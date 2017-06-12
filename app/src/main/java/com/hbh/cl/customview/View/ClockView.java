package com.hbh.cl.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

/**
 * Created by hbh on 2017/6/8.
 */

public class ClockView extends View {

    private Paint mCirclePaint;//表盘画笔
    private Paint mLinePaint;//刻度画笔
    private Paint mTextPaint;//文字画笔
    private Paint mPointPaint;//指针画笔
    private int mCircleWidth;//表盘宽度
    private int mWidth,mHeight;//视图宽高
    private int mLineWidth;//刻度宽度
    private int mLineLeft, mLineTop, mLineRight, mLineBottom;//刻度线左、上、右、下位置
    private Calendar mCalendar;
    private int mHour;//时
    private int mMinute;//分
    private int mSecond;//秒

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initPaint(){
        //表盘宽度
        mCircleWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,8,getResources().getDisplayMetrics());
        //刻度宽度
        mLineWidth =(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());

        //初始化表盘画笔
        mCirclePaint = new Paint();//初始化画笔
        mCirclePaint.setAntiAlias(true);//抗锯齿
        mCirclePaint.setFilterBitmap(true);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);//空心画笔
        mCirclePaint.setStrokeWidth(10.0f);//空心画笔宽度
        mCirclePaint.setColor(Color.rgb(160,82,45));//画笔颜色

        //初始化刻度画笔
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setFilterBitmap(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.WHITE);

        //初始化数字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFilterBitmap(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStrokeWidth(1.0f);
        mTextPaint.setTextSize(48);

        //初始化指针
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setFilterBitmap(true);
        mPointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mLineLeft = mWidth/2 - mLineWidth/2;
        mLineRight = mWidth/2 + mLineWidth/2;
        mLineTop = mCircleWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(onMeasureSpec(widthMeasureSpec), onMeasureSpec(heightMeasureSpec));
    }

    private int onMeasureSpec(int measureSpec){
        int resultSpec = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.AT_MOST) {
            resultSpec = Math.min(resultSpec, specSize);
        } else if (specMode == MeasureSpec.EXACTLY) {
            resultSpec = specSize;
        }
        return resultSpec;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);//绘制表盘
        drawLines(canvas);//绘制刻度
        drawText(canvas);//绘制数字
        drawPoint(canvas);//绘制指针
        invalidate();//重绘
    }

    /**
     * 绘制表盘
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        canvas.drawCircle(mWidth/2, mHeight/2, mWidth/2 - mCircleWidth, mCirclePaint);
    }

    /**
     * 绘制刻度
     * @param canvas
     */
    private void drawLines(Canvas canvas){
        for (int i = 0; i <= 360; i++) {
            if (i % 30 == 0) {
                mLineBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            }else{
                mLineBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
            }

            if (i % 6 == 0) {
                canvas.save();
                canvas.rotate(i, mWidth/2, mHeight/2);
                canvas.drawRect(mLineLeft, mLineTop, mLineRight, mLineBottom, mLinePaint);
                canvas.restore();
            }

//            if(i != 0 && i % 30 == 0){
//                canvas.save();
//                canvas.rotate(i, mWidth/2, mHeight/2);
//                canvas.drawText(i/30 + "", mLineLeft, mLineBottom + 20, mTextPaint);
//                canvas.restore();
//            }
        }
    }

    /**
     * 绘制时间数字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        canvas.drawText("ⅩⅡ", mLineLeft - mLineWidth, mLineBottom + mCircleWidth, mTextPaint);
        canvas.drawText("Ⅰ", mWidth/2 + ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(30))), mHeight/2 - (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(30)), mTextPaint);
        canvas.drawText("Ⅱ", mWidth/2 + ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(60))), mHeight/2 - (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(60)), mTextPaint);
        canvas.drawText("Ⅲ", mWidth - mLineBottom - mCirclePaint.getStrokeWidth()- mCircleWidth, mHeight/2 + mLineWidth, mTextPaint);
        canvas.drawText("Ⅳ", mWidth/2 + ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(60))), mHeight/2 + (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(60)), mTextPaint);
        canvas.drawText("Ⅴ", mWidth/2 + ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(30))), mHeight/2 + (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(30)), mTextPaint);
        canvas.drawText("Ⅵ", mWidth/2, mHeight - mLineBottom - mCirclePaint.getStrokeWidth(), mTextPaint);
        canvas.drawText("Ⅶ", mWidth/2 - ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(30))), mHeight/2 + (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(30)), mTextPaint);
        canvas.drawText("Ⅷ", mWidth/2 - ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(60))), mHeight/2 + (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(60)), mTextPaint);
        canvas.drawText("Ⅸ", mLineBottom + mCirclePaint.getStrokeWidth(), mHeight/2 + mLineWidth, mTextPaint);
        canvas.drawText("Ⅹ", mWidth/2 - ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(60))), mHeight/2 - (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(60)), mTextPaint);
        canvas.drawText("ⅩⅠ", mWidth/2 - ((mWidth/2  - mCircleWidth - mLineBottom)* (float)Math.sin(Math.toRadians(30))), mHeight/2 - (mWidth/2 - mCircleWidth - mLineBottom)* (float)Math.cos(Math.toRadians(30)), mTextPaint);
    }

    /**
     * 绘制指针
     */
    private void drawPoint(Canvas canvas){
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mSecond = mCalendar.get(Calendar.SECOND);

        //绘制时针
        mPointPaint.setColor(Color.rgb(222,184,135));
        mPointPaint.setStrokeWidth(12.0f);
        canvas.save();
        canvas.rotate(mHour * 30 + mMinute * 6 / 12, mWidth/2, mWidth/2);
        canvas.drawLine(mWidth/2, mHeight/2, mWidth/2 , mWidth/4, mPointPaint);
        canvas.drawLine(mWidth/2, mHeight/2, mWidth/2 , mWidth/2 + mWidth/10, mPointPaint);
        canvas.restore();
        //绘制分针
        mPointPaint.setStrokeWidth(9.0f);
        canvas.save();
        canvas.rotate(mMinute * 6, mWidth/2, mWidth/2);
        canvas.drawLine(mWidth/2, mHeight/2, mWidth/2 , mWidth/6, mPointPaint);
        canvas.drawLine(mWidth/2, mHeight/2, mWidth/2 , mWidth/2 + mWidth/10, mPointPaint);
        canvas.restore();
        //绘制秒针
        canvas.drawCircle(mWidth/2, mHeight/2, 14, mLinePaint);//绘制表针交点
        mPointPaint.setColor(Color.WHITE);
        mPointPaint.setStrokeWidth(6.0f);
        canvas.save();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        canvas.rotate(mSecond * 6, mWidth/2, mWidth/2);
        canvas.drawLine(mWidth/2, mHeight/2, mWidth/2 , mHeight/8, mPointPaint);
        canvas.drawLine(mWidth/2, mHeight/2, mWidth/2 , mWidth/2 + mWidth/10, mPointPaint);
        canvas.restore();

    }
}
