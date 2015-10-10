package com.jsruyun.paintshaderdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cjpx00008 on 2015/10/9.
 */
public class ShaderView extends View {
    private static final int RECT_SIZE = 400;// ���γߴ��һ��

    private Paint mPaint;// ����

    private int left, top, right, bottom;// ����������������

    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // ��ȡ��Ļ�ߴ�����
        Point point = getWindowSize(context);

        // ��ȡ��Ļ�е�����
        int screenX = point.x / 2;
        int screenY = point.y / 2;

        // �������������������ֵ
        left = screenX - RECT_SIZE;
        top = screenY - RECT_SIZE;
        right = screenX + RECT_SIZE;
        bottom = screenY + RECT_SIZE;

        // ʵ��������
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // ��ȡλͼ
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        // ������ɫ��
//        mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
//        mPaint.setShader(new LinearGradient(left, top, right - RECT_SIZE, bottom - RECT_SIZE, Color.RED, Color.YELLOW, Shader.TileMode.REPEAT));
//        mPaint.setShader(new LinearGradient(left, top, right, bottom, new int[] { Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE }, new float[] { 0, 0.1F, 0.5F, 0.7F, 0.8F }, Shader.TileMode.REPEAT));
          mPaint.setShader(new SweepGradient(screenX, screenY, Color.RED, Color.YELLOW));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getWindowSize(Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ���ƾ���
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
