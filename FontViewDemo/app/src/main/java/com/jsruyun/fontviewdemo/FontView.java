package com.jsruyun.fontviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by cjpx00008 on 2015/9/30.
 */
public class FontView extends View {

    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";
    private Paint mPaint, mlinePaint;// 画笔
    private Paint.FontMetrics mFontMetrics;// 文本测量对象
    private int baseX,baseY;

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSkewX(0.5f);

        mlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mlinePaint.setStyle(Paint.Style.STROKE);
        mlinePaint.setStrokeWidth(1);
        mlinePaint.setColor(Color.RED);

        mFontMetrics = mPaint.getFontMetrics();

        Log.d("Aige", "ascent：" + mFontMetrics.ascent);
        Log.d("Aige", "top：" + mFontMetrics.top);
        Log.d("Aige", "leading：" + mFontMetrics.leading);
        Log.d("Aige", "descent：" + mFontMetrics.descent);
        Log.d("Aige", "bottom：" + mFontMetrics.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 计算Baseline绘制的起点X轴坐标
        baseX = (int) (canvas.getWidth() / 2 - mPaint.measureText(TEXT) / 2);

        // 计算Baseline绘制的Y坐标
        baseY = (int) ((canvas.getHeight() / 2) - ((mPaint.descent() + mPaint.ascent()) / 2));

        canvas.drawText(TEXT, baseX, baseY, mPaint);

        // 为了便于理解我们在画布中心处绘制一条中线
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, mlinePaint);
    }
}
