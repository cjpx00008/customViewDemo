package com.jsruyun.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cjpx00008 on 2015/10/10.
 */
public class PathView extends View {
    private Path mPath;// 路径对象
    private Paint mPaint;// 画笔对象

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*
         * 实例化画笔并设置属性
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.CYAN);

        // 实例化路径
        mPath = new Path();

        // 移动点至[300,300]
        mPath.moveTo(100, 100);

// 连接路径到点
        mPath.lineTo(300, 100);
        mPath.lineTo(400, 200);
        mPath.lineTo(200, 200);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制路径
        canvas.drawPath(mPath, mPaint);
    }
}
