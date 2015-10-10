package com.jsruyun.paintshaderdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by cjpx00008 on 2015/10/9.
 */
public class AnimListView extends ListView {
    private Camera mCamera;
    private Matrix mMatrix;

    public AnimListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onDraw(Canvas canvas) {
        mCamera.save();
        mCamera.rotate(30, 0, 0);
        mCamera.getMatrix(mMatrix);
        mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
        canvas.concat(mMatrix);
        super.onDraw(canvas);
        mCamera.restore();
    }
}
