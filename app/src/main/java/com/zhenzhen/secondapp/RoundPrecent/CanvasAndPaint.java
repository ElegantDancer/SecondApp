package com.zhenzhen.secondapp.RoundPrecent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhenzhen on 2016/12/16.
 */

public class CanvasAndPaint extends View {
    public CanvasAndPaint(Context context) {
        this(context, null);
    }

    public CanvasAndPaint(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CanvasAndPaint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        RectF rectF = new RectF(100, 100, 800, 400);
        canvas.drawArc(rectF, 0, 90, false, paint);
    }
}
