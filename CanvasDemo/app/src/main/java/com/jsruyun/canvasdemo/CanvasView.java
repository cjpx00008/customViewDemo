package com.jsruyun.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cjpx00008 on 2015/10/10.
 */
public class CanvasView extends View {
    private Rect mRect;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect(0, 0, 500, 500);

        mRect.intersect(250, 250, 750, 750);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);

        canvas.clipRect(mRect);

        canvas.drawColor(Color.RED);
    }
}