package com.jsruyun.paintdemo;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PixelXorXfermode;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cjpx00008 on 2015/9/29.
 */
public class CustomView extends View {
    private Paint mPaint;//画笔
    private Context mContext;//上下文环境
    private Bitmap mBitmap;
    private int x, y, w, h;//位图绘制左上角的起始坐标
    private AvoidXfermode avoidXfermode;// AV模式
    private PixelXorXfermode pixelXorXfermode;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPait();
        initRes();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆环
        canvas.drawBitmap(mBitmap, x, y, mPaint);

        // “染”什么色是由我们自己决定的
        mPaint.setARGB(255, 211, 53, 243);

        // 设置AV模式
        mPaint.setXfermode(pixelXorXfermode);

        // 画一个位图大小一样的矩形
        canvas.drawRect(x, y, w, h, mPaint);
    }


    private Point getWindowSize() {
        Point point = new Point();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }

    private void initRes() {
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test);
        Point point = getWindowSize();
        x = point.x / 2 - mBitmap.getWidth() / 2;
        y = point.y / 2 - mBitmap.getHeight() / 2;
        w = point.x / 2 + mBitmap.getWidth() / 2;
        h = point.y / 2 + mBitmap.getHeight() / 2;
    }


    private void initPait() {
        mPaint = new Paint();

        //打开抗锯齿
        mPaint.setAntiAlias(true);

        //设置画笔样式
        mPaint.setStyle(Paint.Style.FILL);

        //设置画笔颜色
        mPaint.setColor(Color.argb(255, 255, 128, 103));

        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        mPaint.setStrokeWidth(10);

         /*
         * 当画布中有跟0XFFFFFFFF色不一样的地方时候才“染”色
         */
//        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.AVOID);


        pixelXorXfermode = new PixelXorXfermode(Color.BLUE);
        // 生成色彩矩阵
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0, 0, 1, 0, 0,
//                0, 1, 0, 0, 0,
//                1, 0, 0, 0, 0,
//                0, 0, 0, 1, 0,
//        });

//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // 设置颜色过滤
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
        // 设置颜色过滤
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.DARKEN));
    }

}
