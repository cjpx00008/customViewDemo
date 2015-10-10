package com.jsruyun.paintshaderdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cjpx00008 on 2015/10/9.
 */
public class ReflectView extends View {
    private Bitmap mSrcBitmap, mRefBitmap;// 位图
    private Paint mPaint;// 画笔
    private PorterDuffXfermode mXfermode;// 混合模式

    private int x, y;// 位图起点坐标

    public ReflectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化资源
        initRes(context);
    }

    /*
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取源图
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        // 实例化一个矩阵对象
        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);

        // 生成倒影图
        mRefBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

        Point point = getWindowSize(context);

        int screenW = point.x;
        int screenH = point.y;

        x = screenW / 2 - mSrcBitmap.getWidth() / 2;
        y = screenH / 2 - mSrcBitmap.getHeight() / 2;

        // ………………………………
        mPaint = new Paint();
        mPaint.setShader(new LinearGradient(x, y + mSrcBitmap.getHeight(), x, y + mSrcBitmap.getHeight() + mSrcBitmap.getHeight() / 3, 0xAA000000, Color.TRANSPARENT, Shader.TileMode.CLAMP));

        // ………………………………
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
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
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap, x, y, null);

        int sc = canvas.saveLayer(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mRefBitmap, x, y + mSrcBitmap.getHeight(), null);

        mPaint.setXfermode(mXfermode);

        canvas.drawRect(x, y + mSrcBitmap.getHeight(), x + mRefBitmap.getWidth(), y + mSrcBitmap.getHeight() * 2, mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);
    }
}