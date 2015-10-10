package com.jsruyun.eraserdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by cjpx00008 on 2015/9/30.
 */
public class EraserView extends View {
    private static final int MIN_MOVE_DIS = 5;// ��С���ƶ����룺���������ָ����Ļ�ϵ��ƶ�����С�ڴ�ֵ�򲻻����

    private Bitmap fgBitmap, bgBitmap;// ǰ����Ƥ����Bitmap�ͱ������ǵ�ͼ��Bitmap
    private Canvas mCanvas;// ������Ƥ��·���Ļ���
    private Paint mPaint;// ��Ƥ��·������
    private Path mPath;// ��Ƥ������·��
    private Context mContext;

    private int screenW, screenH;// ��Ļ���
    private float preX, preY;// ��¼��һ�������¼���λ������

    public EraserView(Context context, AttributeSet set) {
        super(context, set);
        mContext = context;
        // �������
        cal(context);

        // ��ʼ������
        init(context);
    }

    /**
     * �������
     *
     * @param context
     *            �����Ļ�������
     */
    private void cal(Context context) {
        // ��ȡ��Ļ�ߴ�����
        Point point = getWindowSize();

        // ��ȡ��Ļ���
        screenW = point.x;
        screenH = point.y;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getWindowSize() {
        Point point = new Point();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }

    /**
     * ��ʼ������
     */
    private void init(Context context) {
        // ʵ����·������
        mPath = new Path();

        // ʵ�������ʲ������俹��ݺͿ�����
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        // ���û���͸����Ϊ0�ǹؼ�������Ҫ�û��Ƶ�·����͸���ģ�Ȼ���ø�·����ǰ���ĵ�ɫ��ϡ��١�������·��
        mPaint.setARGB(128, 255, 0, 0);

        // ���û��ģʽΪDST_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        // ���û��ʷ��Ϊ���
        mPaint.setStyle(Paint.Style.STROKE);

        // ����·����ϴ���ʽ
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        // ���ñʴ�����
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // ������߿��
        mPaint.setStrokeWidth(50);

        // ����ǰ��ͼBitmap
        fgBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);

        // ����ע�뻭��
        mCanvas = new Canvas(fgBitmap);

        // ���ƻ�������Ϊ���Ի�
        mCanvas.drawColor(0xFF808080);

        // ��ȡ������ͼBitmap
        bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.test);

        // ���ű�����ͼBitmap����Ļ��С
        bgBitmap = Bitmap.createBitmap(bgBitmap, 0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ���Ʊ���
        canvas.drawBitmap(bgBitmap,  screenW/2 - bgBitmap.getWidth()/2 , screenH/2 - bgBitmap.getHeight()/2, null);

        // ����ǰ��
        canvas.drawBitmap(fgBitmap, 0, 0, null);

        /*
         * ����Ҫע��canvas��mCanvas��������ͬ�Ļ�������
         * ����������Ļ���ƶ���ָ����·��ʱ���·��ͨ��mCanvas���Ƶ�fgBitmap��
         * ÿ��������ָ�ƶ�һ�ξ��Ὣ·��mPath��ΪĿ��ͼ����Ƶ�mCanvas�ϣ�����������������mCanvas�ϻ��������Ի�ɫ
         * ���߻���ΪDST_INģʽ�ļ���ֻ��ʾ���Իң�������ΪmPath��͸�����������ɵĻ��ͼ��Ҳ����͸����
         * �������ǻ�õ�����Ƥ������Ч��
         */
        mCanvas.drawPath(mPath, mPaint);
    }

    /**
     * View���¼�������7/12���
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
         * ��ȡ��ǰ�¼�λ������
         */
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// ��ָ�Ӵ���Ļ����·��
                mPath.reset();
                mPath.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:// ��ָ�ƶ�ʱ����·��
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= MIN_MOVE_DIS || dy >= MIN_MOVE_DIS) {
                    mPath.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                }
                break;
        }

        // �ػ���ͼ
        invalidate();
        return true;
    }
}
