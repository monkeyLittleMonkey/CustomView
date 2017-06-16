package com.hbh.cl.customview.View;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hbh.cl.customview.model.Panel;
import com.hbh.cl.customview.model.PanelItemModel;
import com.hbh.cl.customview.model.SesameModel;

import java.util.ArrayList;

/**
 * Created by hbh on 2017/6/16.
 */

public class PanelView extends View {

    private RectF panelRectF;
    private RectF progressRectF;
    private int viewWidth;
    private int viewHeight;
    private float progressTotalSweepAngle;
    private float progressSweepAngle;
    private ValueAnimator progressAnimator;
    private SesameModel dataModel;
    private float rAngle = 220/32;
    private int mItemcount = 6;//将表盘分成6份
    private int progressRaduis;
    private int topHeight;
    private String sesameJiFen;
    private Paint linePaint;
    private Paint panelPaint;
    private Paint panelTextPaint;
    private Paint progressPaint;
    private Paint colorPaint;//颜色画笔
    private int color[] = {Color.rgb(102,238,70),Color.rgb(226,246,63),Color.rgb(227,151,75),Color.rgb(255,2,26),Color.rgb(133,13,85),Color.rgb(112,4,36)};
    private float panelStroke = 20;
    private float progressStroke = 5;
    private float startAngle = 160;
    private float sweepAngle = 220;
    private int centerX;
    private int centerY;
    private PanelItemModel model;


    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        dataModel.setFirstText("TEST");
        dataModel.setLevel("轻度污染");
        dataModel.setUpdateTime("2017-6-16");
        dataModel.setTotalMax(500);
        dataModel.setTotalMin(0);
        dataModel.setAQI_value(122);

        panelPaint = new Paint();
        panelPaint.setAntiAlias(true);
        panelPaint.setColor(Color.rgb(220,220,220));
        panelPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.WHITE);

        panelTextPaint = new Paint();
        panelTextPaint.setAntiAlias(true);
        panelTextPaint.setStyle(Paint.Style.FILL);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(10);
        progressPaint.setColor(Color.WHITE);

        colorPaint = new Paint();
        colorPaint.setAntiAlias(true);
        colorPaint.setStyle(Paint.Style.STROKE);
        colorPaint.setStrokeWidth(panelStroke);

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
            progressAnimator.setDuration(2000);
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
        panelPaint.setStrokeWidth(progressStroke);
        progressRectF = new RectF(centerX - progressRaduis, centerY - progressRaduis, centerX + progressRaduis, centerY + progressRaduis);
        canvas.drawArc(progressRectF, startAngle, sweepAngle, false, panelPaint);
        canvas.drawArc(progressRectF, startAngle, progressSweepAngle, false, progressPaint);

        panelPaint.setStrokeWidth(panelStroke);
        int panelRadius = progressRaduis * 9 / 10;
        panelRectF = new RectF(centerX - panelRadius, centerY - panelRadius, centerX + panelRadius, centerY + panelRadius);
        for (int i = 0; i < 6; i++) {
            colorPaint.setColor(color[i]);
            canvas.drawArc(panelRectF, startAngle + 37 * i, 37, false, colorPaint);
        }

        canvas.save();
        //此处将画布旋转-110度。使画布Y轴（垂直）对称。
        canvas.rotate(-110, centerX, centerY);
        drawNumText(canvas);
        canvas.restore();
        drawText(canvas);
    }

    /**
     * 绘制仪表盘数字文本
     * */
    private void drawNumText(Canvas canvas) {
        ArrayList<PanelItemModel> sesameItemdataModels = dataModel.getPanelItemModels();
        for (int i = 0; i < sesameItemdataModels.size(); i++) {
            PanelItemModel sesameItem = sesameItemdataModels.get(i);
            for (int j = 0; j < mItemcount; j++) {
                String itemMin = String.valueOf(sesameItem.getMin());
                String itemMax = String.valueOf(sesameItem.getMax());
                Rect rect = new Rect();
                if (j == 0) {
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemMin, 0, itemMin.length(), rect);
                    canvas.drawText(itemMin, centerX - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
                canvas.rotate(rAngle, centerX, centerY);
                //最后文本
                if (i == sesameItemdataModels.size() - 1 && j == 5) {
                    linePaint.setTextSize(20);
                    linePaint.getTextBounds(itemMax, 0, itemMax.length(), rect);
                    canvas.drawText(itemMax, centerX - rect.width() / 2, panelRectF.top + panelStroke + 15, linePaint);
                }
            }
        }
    }

    /**
     * 绘制仪表盘文字文本
     */
    private void drawText(Canvas canvas) {
        float drawTextY, textSpace = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
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
        panelTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, getResources().getDisplayMetrics()));
        panelTextPaint.setColor(Color.WHITE);
        panelTextPaint.setFakeBoldText(false);
        panelTextPaint.getTextBounds(text, 0, text.length(), rect);
        drawTextY = drawTextY + rect.height() + textSpace;
        canvas.drawText(text, centerX - rect.width() / 2, drawTextY, panelTextPaint);

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
        for (int i = 0; i < list.size(); i++) {
            if (aqi_value > list.get(i).getMax()) {//如果AQI值大于当前区域的最大值
                progressAngle += mItemcount * rAngle;
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
            //开始扫描的值,为起始刻度350
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
