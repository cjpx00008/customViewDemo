package com.jsruyun.fontviewdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cjpx00008 on 2015/10/8.
 */
public class MaskFilterView extends View {
    private static final int RECT_SIZE = 400;
    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private int x,y;
    private int left, top, right, bottom;//
    private Bitmap srcBitmap,shadowBitmap;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 记得设置模式为SOFTWARE
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);
    }

    /**
     * 初始化画笔 
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setColor(0xFF603811);

        mPaint.setColor(Color.DKGRAY);
        // 设置画笔遮罩滤镜
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL));
    }

    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         */
        Point point = getWindowSize();


        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.test);
        shadowBitmap = srcBitmap.extractAlpha();
        x = point.x / 2 - srcBitmap.getWidth() / 2;
        y = point.y / 2 - srcBitmap.getHeight() / 2;
        left = point.x / 2 - RECT_SIZE / 2;
        top = point.y / 2 - RECT_SIZE / 2;
        right = point.x / 2 + RECT_SIZE / 2;
        bottom = point.y / 2 + RECT_SIZE / 2;
        Log.d("test", "left:" + left + ";top:" + top + ";right:" + right + ";bottom:" + bottom);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getWindowSize() {
        Point point = new Point();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        canvas.drawBitmap(shadowBitmap,x,y,mPaint);

        canvas.drawBitmap(srcBitmap, x, y , null);
        // 画一个矩形
//        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
