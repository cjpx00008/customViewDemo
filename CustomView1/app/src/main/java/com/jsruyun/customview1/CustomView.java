package com.jsruyun.customview1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cjpx00008 on 2015/9/28.
 */
public class CustomView extends View {

    private Paint mPaint;//画笔
    private Context mContext;//上下文环境
    private int mRadiu;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPait();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆环
        Point p = getWindowSize();
        Log.d("test","x:" + p.x/2 + ";y:" + p.y/ 2);
        canvas.drawCircle(p.x/2, p.y/ 2, mRadiu, mPaint);
    }


    private Point getWindowSize() {
        Point point = new Point();
        WindowManager manager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }



    private void initPait() {
        mPaint = new Paint();

        //打开抗锯齿
        mPaint.setAntiAlias(true);

        //设置画笔样式
        mPaint.setStyle(Paint.Style.STROKE);

        //设置画笔颜色
        mPaint.setColor(Color.BLUE);

        /*
         * 设置描边的粗细，单位：像素px
         * 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        mPaint.setStrokeWidth(10);
    }

    public synchronized void setRadiu(int radiu) {
        this.mRadiu = radiu;

        // 重绘
        invalidate();
    }
}
