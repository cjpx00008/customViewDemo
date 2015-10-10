package com.jsruyun.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cjpx00008 on 2015/10/10.
 */
public class BitmapMeshView2 extends View {
    private static final int WIDTH = 9, HEIGHT = 9;// �ָ���
    private static final int COUNT = (WIDTH + 1) * (HEIGHT + 1);// ������

    private Bitmap mBitmap;// λͼ����

    private float[] matrixOriganal = new float[COUNT * 2];// ��׼����������
    private float[] matrixMoved = new float[COUNT * 2];// �任�����������

    private float clickX, clickY;// ������Ļʱ��ָ��xy����

    private Paint origPaint, movePaint, linePaint;// ��׼�㡢�任����߶εĻ���Paint

    public BitmapMeshView2(Context context, AttributeSet set) {
        super(context, set);
        setFocusable(true);

        // ʵ�����ʲ�������ɫ
        origPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        origPaint.setColor(0x660000FF);
        movePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        movePaint.setColor(0x99FF0000);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFFFFB00);

        // ��ȡλͼ��Դ
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);

        // ��ʼ����������
        int index = 0;
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = mBitmap.getHeight() * y / HEIGHT;

            for (int x = 0; x <= WIDTH; x++) {
                float fx = mBitmap.getWidth() * x / WIDTH;
                setXY(matrixMoved, index, fx, fy);
                setXY(matrixOriganal, index, fx, fy);
                index += 1;
            }
        }
    }

    /**
     * ������������
     *
     * @param array
     *            ��������
     * @param index
     *            ��ʶֵ
     * @param x
     *            x����
     * @param y
     *            y����
     */
    private void setXY(float[] array, int index, float x, float y) {
        array[index * 2 + 0] = x;
        array[index * 2 + 1] = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // ��������λͼ
        canvas.drawBitmapMesh(mBitmap, WIDTH, HEIGHT, matrixMoved, 0, null, 0, null);

        // ���Ʋο�Ԫ��
        drawGuide(canvas);
    }

    /**
     * ���Ʋο�Ԫ��
     *
     * @param canvas
     *            ����
     */
    private void drawGuide(Canvas canvas) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float x = matrixOriganal[i + 0];
            float y = matrixOriganal[i + 1];
            canvas.drawCircle(x, y, 4, origPaint);

            float x1 = matrixOriganal[i + 0];
            float y1 = matrixOriganal[i + 1];
            float x2 = matrixMoved[i + 0];
            float y2 = matrixMoved[i + 1];
            canvas.drawLine(x1, y1, x2, y2, origPaint);
        }

        for (int i = 0; i < COUNT * 2; i += 2) {
            float x = matrixMoved[i + 0];
            float y = matrixMoved[i + 1];
            canvas.drawCircle(x, y, 4, movePaint);
        }

        canvas.drawCircle(clickX, clickY, 6, linePaint);
    }

    /**
     * ����任��������
     */
    private void smudge() {
        for (int i = 0; i < COUNT * 2; i += 2) {

            float xOriginal = matrixOriganal[i + 0];
            float yOriginal = matrixOriganal[i + 1];

            float dist_click_to_origin_x = clickX - xOriginal;
            float dist_click_to_origin_y = clickY - yOriginal;

            float kv_kat = dist_click_to_origin_x * dist_click_to_origin_x + dist_click_to_origin_y * dist_click_to_origin_y;

            float pull = (float) (1000000 / kv_kat / Math.sqrt(kv_kat));

            if (pull >= 1) {
                matrixMoved[i + 0] = clickX;
                matrixMoved[i + 1] = clickY;
            } else {
                matrixMoved[i + 0] = xOriginal + dist_click_to_origin_x * pull;
                matrixMoved[i + 1] = yOriginal + dist_click_to_origin_y * pull;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        smudge();
        invalidate();
        return true;
    }
}