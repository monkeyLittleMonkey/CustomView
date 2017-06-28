package com.hbh.cl.customview.View;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.hbh.cl.customview.R;
import com.hbh.cl.customview.model.Panel;
import com.hbh.cl.customview.model.PanelItemModel;
import com.hbh.cl.customview.model.SesameModel;

import java.util.ArrayList;

/**
 * Created by hbh on 2017/6/16.
 */

public class PanelViewTwo extends View {

    private Context context;
    private RectF panelRectF;
    private RectF progressRectF;
    private int viewWidth;
    private int viewHeight;
    private float progressTotalSweepAngle;
    private float progressSweepAngle;
    private ValueAnimator progressAnimator;
    private SesameModel dataModel;
    private float rAngle = 6.1f;//222/32
    private int mItemcount = 6;//将表盘分成6份
    private int progressRaduis;
    private int topHeight;
    private String sesameJiFen;
    private Paint panelPaint;
    private Paint panelTextPaint;
    private Paint progressPaint;
    private Paint colorPaint;//颜色画笔
    private int color[] = {Color.rgb(102,238,70),Color.rgb(226,246,63),Color.rgb(227,151,75),Color.rgb(255,2,26),Color.rgb(133,13,85),Color.rgb(112,4,36)};
    private int drawables[] = {R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l5, R.drawable.l6};
    private int drawable = R.drawable.l1;
    private float panelStroke = 20;
    private float progressStroke = 22;
    private float startAngle = 135;
    private float sweepAngle = 270;
    private int centerX;
    private int centerY;
    private PanelItemModel model;
    private int progressPaintColor;


    public PanelViewTwo(Context context) {
        this(context, null);
    }

    public PanelViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initData();
    }

    public PanelViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PanelViewTwo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initData(){
        ArrayList<PanelItemModel> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            model = new PanelItemModel();
            if (i == 0) {//优
                model.setMax(50);
                model.setMin(0);
            }
            if (i == 1) {//良
                model.setMax(100);
                model.setMin(50);
            }
            if (i == 2) {//轻度污染
                model.setMax(150);
                model.setMin(100);
            }
            if (i == 3) {//中度污染
                model.setMax(200);
                model.setMin(150);
            }
            if (i == 4) {//重度污染
                model.setMax(300);
                model.setMin(200);
            }
            if (i == 5) {//严重污染
                model.setMax(500);
                model.setMin(300);
            }
            list.add(model);
        }

        dataModel = new SesameModel();
        dataModel.setPanelItemModels(list);
        dataModel.setFirstText("AQI指数");
        dataModel.setLevel("轻度污染");
        dataModel.setUpdateTime("06-27 15时");
        dataModel.setTotalMax(500);
        dataModel.setTotalMin(0);
        dataModel.setAQI_value(120);

        if (dataModel.getLevel().equals("--")) {
            progressPaintColor = Color.rgb(255,255,255);
        } else if (dataModel.getLevel().equals("优")) {
            drawable = drawables[0];
            progressPaintColor = Color.rgb(102,238,70);
        } else if (dataModel.getLevel().equals("良")) {
            drawable = drawables[1];
            progressPaintColor = Color.rgb(226,246,63);
        } else if (dataModel.getLevel().equals("轻度污染")) {
            drawable = drawables[2];
            progressPaintColor = Color.rgb(227,151,75);
        } else if (dataModel.getLevel().equals("中度污染")) {
            drawable = drawables[3];
            progressPaintColor = Color.rgb(255,2,26);
        } else if (dataModel.getLevel().equals("重度污染")) {
            drawable = drawables[4];
            progressPaintColor = Color.rgb(133,13,85);
        } else if (dataModel.getLevel().equals("严重污染")) {
            drawable = drawables[5];
            progressPaintColor = Color.rgb(112,4,36);
        }

        panelPaint = new Paint();
        panelPaint.setAntiAlias(true);
        panelPaint.setColor(Color.rgb(220,220,220));
        panelPaint.setStyle(Paint.Style.STROKE);

        panelTextPaint = new Paint();
        panelTextPaint.setAntiAlias(true);
        panelTextPaint.setStyle(Paint.Style.FILL);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressStroke);
        progressPaint.setColor(progressPaintColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        colorPaint = new Paint();
        colorPaint.setAntiAlias(true);
        colorPaint.setStyle(Paint.Style.STROKE);
        colorPaint.setColor(Color.rgb(128,205,255));
        colorPaint.setStrokeWidth(panelStroke);
        colorPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (dataModel != null && dataModel.getPanelItemModels() != null && dataModel.getPanelItemModels().size() != 0) {
            viewWidth = w;
            viewHeight = h;
            progressRaduis = (w / 2) * 7 / 10;
            topHeight = (w / 2) * 3 / 10;
            centerX = viewWidth / 2;
            centerY = viewHeight / 2;
            sesameJiFen = String.valueOf(dataModel.getTotalMin());

            progressTotalSweepAngle = computeProgressAngle();
            Panel startPanel = new Panel();
            startPanel.setStartSweepAngle(1);//扫描开始角度
            startPanel.setStartSweepValue(dataModel.getTotalMin());//扫描开始过程对应的值

            Panel endPanel = new Panel();
            endPanel.setEndSweepAngle(progressTotalSweepAngle);//扫描结束角度
            endPanel.setEndSweepValue(dataModel.getAQI_value());//扫描结束过程对应的值

            progressAnimator = ValueAnimator.ofObject(new CreditEvaluator(), startPanel, endPanel);
            progressAnimator.setDuration(1500);
            progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Panel panel = (Panel) animation.getAnimatedValue();
                    //更新进度值
                    progressSweepAngle = panel.getSesameSweepAngle();
                    sesameJiFen = String.valueOf(panel.getSesameSweepValue());
                    invalidate();
                }
            });

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressAnimator.start();
                }
            }, 500);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPanel(canvas);
    }

    /**
     * 绘制仪表盘
     */
    private void drawPanel(Canvas canvas) {

        panelPaint.setStrokeWidth(panelStroke);
        int panelRadius = progressRaduis;
        panelRectF = new RectF(centerX - panelRadius, centerY - panelRadius, centerX + panelRadius, centerY + panelRadius);
        canvas.drawArc(panelRectF, startAngle, sweepAngle, false, colorPaint);

        progressRectF = new RectF(centerX - progressRaduis, centerY - progressRaduis, centerX + progressRaduis, centerY + progressRaduis);
        canvas.drawArc(progressRectF, startAngle, progressSweepAngle, false, progressPaint);

        drawText(canvas);
    }

    /**
     * 绘制仪表盘文字文本
     */
    private void drawText(Canvas canvas) {
        float drawTextY, textSpace = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
        Rect rect = new Rect();

        String text = dataModel.getFirstText();
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.parseColor("#e5e5e5"));
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = centerY - panelRectF.height() / 2 * 0.45f;
        canvas.drawText(text, centerX - rect.width() / 2, drawTextY, panelTextPaint);

        text = sesameJiFen;
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 34, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(true);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerX - rect.width() / 2, drawTextY, panelTextPaint);

        text = dataModel.getLevel();
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerX - rect.width()/2, drawTextY, panelTextPaint);
        if (!text.equals("--")) {
            Bitmap bitmap = ((BitmapDrawable)getResources().getDrawable(drawable)).getBitmap();
            canvas.drawBitmap(bitmap,centerX - rect.width()/2 - rect.width()/text.length() - 20 , drawTextY - rect.height(), panelTextPaint);
        }
        text = dataModel.getUpdateTime();
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerX - rect.width() / 2, drawTextY, panelTextPaint);
    }

    /**
     * 计算当前污染指数计算所占的角度
     */
    private float computeProgressAngle() {
        ArrayList<PanelItemModel> list = dataModel.getPanelItemModels();
        int aqi_value = dataModel.getAQI_value();
        float progressAngle = 0;
        if(aqi_value%50 == 0 && aqi_value <= 200){
            progressAngle = 45*(aqi_value/50);
        }else if(aqi_value == 300){
            progressAngle = 45*5;
        }else if(aqi_value == 500){
            progressAngle = 45*6;
        }else{
            for (int i = 0; i < list.size(); i++) {
                if (aqi_value > list.get(i).getMax()) {//如果AQI值大于当前区域的最大值
//                    progressAngle += mItemcount * rAngle;
                    progressAngle += 45;
                    continue;
                }
                int blance = aqi_value - list.get(i).getMin();
                float areaItem = (list.get(i).getMax() - list.get(i).getMin()) / mItemcount;
                progressAngle += (blance / areaItem) * rAngle;
                if (blance % areaItem != 0) {
                    blance -= (blance / areaItem) * areaItem;
                    float percent = (blance / areaItem);
                    progressAngle += (int) (percent * rAngle);
                }
                break;
            }
        }
        return progressAngle;
    }

    private class CreditEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Panel resultPanel = new Panel();
            Panel startPanel = (Panel) startValue;
            Panel endPanel = (Panel) endValue;
            //开始扫描角度,从1度开始扫描
            float startSweepAngle = startPanel.getStartSweepAngle();
            //结束扫描的角度,为计算出来的污染指数在仪表盘上扫描过的角度
            float endSweepAngle = endPanel.getEndSweepAngle();
            float sesameSweepAngle = startSweepAngle + fraction * (endSweepAngle - startSweepAngle);
            //计算出来进度条变化时变化的角度
            resultPanel.setSesameSweepAngle(sesameSweepAngle);
            //开始扫描的值,为起始刻度0
            float startSweepValue = startPanel.getStartSweepValue();
            //结束扫描的值,为污染指数值
            float endSweepValue = endPanel.getEndSweepValue();
            //计算出进度条在变化的时候污染指数的值
            float sesameSweepValue = startSweepValue + fraction * (endSweepValue - startSweepValue);
            resultPanel.setSesameSweepValue((int) sesameSweepValue);
            return resultPanel;
        }
    }
}
