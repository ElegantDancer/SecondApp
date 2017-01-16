package com.zhenzhen.secondapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhenzhen on 2016/12/15.
 */

public class CanvasDemo extends View {
    public CanvasDemo(Context context) {
        this(context, null);
    }

    public CanvasDemo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
    }
}
