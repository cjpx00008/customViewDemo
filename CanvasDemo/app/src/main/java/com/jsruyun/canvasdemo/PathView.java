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
    private Path mPath;// ·������
    private Paint mPaint;// ���ʶ���

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*
         * ʵ�������ʲ���������
         */
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.CYAN);

        // ʵ����·��
        mPath = new Path();

        // �ƶ�����[300,300]
        mPath.moveTo(100, 100);

// ����·������
        mPath.lineTo(300, 100);
        mPath.lineTo(400, 200);
        mPath.lineTo(200, 200);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ����·��
        canvas.drawPath(mPath, mPaint);
    }
}
