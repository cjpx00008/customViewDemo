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
    private Paint mPaint;//����
    private Context mContext;//�����Ļ���
    private Bitmap mBitmap;
    private int x, y, w, h;//λͼ�������Ͻǵ���ʼ����
    private AvoidXfermode avoidXfermode;// AVģʽ
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
        // ����Բ��
        canvas.drawBitmap(mBitmap, x, y, mPaint);

        // ��Ⱦ��ʲôɫ���������Լ�������
        mPaint.setARGB(255, 211, 53, 243);

        // ����AVģʽ
        mPaint.setXfermode(pixelXorXfermode);

        // ��һ��λͼ��Сһ���ľ���
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

        //�򿪿����
        mPaint.setAntiAlias(true);

        //���û�����ʽ
        mPaint.setStyle(Paint.Style.FILL);

        //���û�����ɫ
        mPaint.setColor(Color.argb(255, 255, 128, 103));

        /*
         * ������ߵĴ�ϸ����λ������px
         * ע�⣺��setStrokeWidth(0)��ʱ����߿�Ȳ���Ϊ0����ֻռһ������
         */
        mPaint.setStrokeWidth(10);

         /*
         * ���������и�0XFFFFFFFFɫ��һ���ĵط�ʱ��š�Ⱦ��ɫ
         */
//        avoidXfermode = new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.AVOID);


        pixelXorXfermode = new PixelXorXfermode(Color.BLUE);
        // ����ɫ�ʾ���
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0, 0, 1, 0, 0,
//                0, 1, 0, 0, 0,
//                1, 0, 0, 0, 0,
//                0, 0, 0, 1, 0,
//        });

//        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // ������ɫ����
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
        // ������ɫ����
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.BLUE, PorterDuff.Mode.DARKEN));
    }

}
