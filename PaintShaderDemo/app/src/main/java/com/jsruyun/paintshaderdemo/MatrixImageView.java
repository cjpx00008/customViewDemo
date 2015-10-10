package com.jsruyun.paintshaderdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by cjpx00008 on 2015/10/9.
 */
public class MatrixImageView extends ImageView {
    private static final int MODE_NONE = 0x00123;// Ĭ�ϵĴ���ģʽ
    private static final int MODE_DRAG = 0x00321;// ��קģʽ
    private static final int MODE_ZOOM = 0x00132;// ����or��תģʽ

    private int mode;// ��ǰ�Ĵ���ģʽ

    private float preMove = 1F;// ��һ����ָ�ƶ��ľ���
    private float saveRotate = 0F;// �����˵ĽǶ�ֵ
    private float rotate = 0F;// ��ת�ĽǶ�

    private float[] preEventCoor;// ��һ�θ�����������꼯��

    private PointF start, mid;// ��㡢�е����
    private Matrix currentMatrix, savedMatrix;// ��ǰ�ͱ����˵�Matrix����
    private Context mContext;// Fuck����

    public MatrixImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        // ��ʼ��
        init(context);
    }

    /**
     * ��ʼ��
     */
    private void init(Context context) {
        /*
         * ʵ��������
         */
        currentMatrix = new Matrix();
        savedMatrix = new Matrix();
        start = new PointF();
        mid = new PointF();

        // ģʽ��ʼ��
        mode = MODE_NONE;
        Point point = getWindowSize(context);

        /*
         * ����ͼƬ��Դ
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        bitmap = Bitmap.createScaledBitmap(bitmap, point.x, point.y, true);
        setImageBitmap(bitmap);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getWindowSize(Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getSize(point);
        return point;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:// ����Ӵ���Ļʱ
                savedMatrix.set(currentMatrix);
                start.set(event.getX(), event.getY());
                mode = MODE_DRAG;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:// �ڶ�����Ӵ���Ļʱ
                preMove = calSpacing(event);
                if (preMove > 10F) {
                    savedMatrix.set(currentMatrix);
                    calMidPoint(mid, event);
                    mode = MODE_ZOOM;
                }
                preEventCoor = new float[4];
                preEventCoor[0] = event.getX(0);
                preEventCoor[1] = event.getX(1);
                preEventCoor[2] = event.getY(0);
                preEventCoor[3] = event.getY(1);
                saveRotate = calRotation(event);
                break;
            case MotionEvent.ACTION_UP:// �����뿪��Ļʱ
            case MotionEvent.ACTION_POINTER_UP:// �ڶ������뿪��Ļʱ
                mode = MODE_NONE;
                preEventCoor = null;
                break;
            case MotionEvent.ACTION_MOVE:// �������ƶ�ʱ
            /*
             * ���㴥����קƽ��
             */
                if (mode == MODE_DRAG) {
                    currentMatrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    currentMatrix.postTranslate(dx, dy);
                }
            /*
             * ���㴥���Ϸ���ת
             */
                else if (mode == MODE_ZOOM && event.getPointerCount() == 2) {
                    float currentMove = calSpacing(event);
                    currentMatrix.set(savedMatrix);
                /*
                 * ָ���ƶ��������10F����
                 */
                    if (currentMove > 10F) {
                        float scale = currentMove / preMove;
                        currentMatrix.postScale(scale, scale, mid.x, mid.y);
                    }
                /*
                 * ��������ʱ��ת
                 */
                    if (preEventCoor != null) {
                        rotate = calRotation(event);
                        float r = rotate - saveRotate;
                        currentMatrix.postRotate(r, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                    }
                }
                break;
        }

        setImageMatrix(currentMatrix);
        return true;
    }

    /**
     * ���������������ľ���
     */
    private float calSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * ����������������е�����
     */
    private void calMidPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * ������ת�Ƕ�
     *
     * @param �¼�����
     * @return �Ƕ�ֵ
     */
    private float calRotation(MotionEvent event) {
        double deltaX = (event.getX(0) - event.getX(1));
        double deltaY = (event.getY(0) - event.getY(1));
        double radius = Math.atan2(deltaY, deltaX);
        return (float) Math.toDegrees(radius);
    }
}