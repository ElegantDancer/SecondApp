package com.zhenzhen.secondapp.BezierPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhenzhen on 2016/12/16.
 */

public class BezierPath extends View {

    private Paint mPaint;
    private int centerX, centerY;

    private PointF startPiont, endPiont, control;

    public BezierPath(Context context) {
        this(context, null);
    }

    public BezierPath(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierPath(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);
        mPaint.setTextSize(60);

        startPiont = new PointF(0,0);
        endPiont = new PointF(0,0);
        control = new PointF(0,0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        //初始化数据点和控制点
        startPiont.x = centerX - 200;
        startPiont.y = centerY;

        endPiont.x = centerX + 200;
        endPiont.y = centerY;

        control.x = centerX;
        control.y = centerY - 100;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //根据触摸位置更新控制点，并提示重绘
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(startPiont.x, startPiont.y, mPaint);
        canvas.drawPoint(endPiont.x, endPiont.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);

        //绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(startPiont.x, startPiont.y, control.x, control.y, mPaint);
        canvas.drawLine(endPiont.x, endPiont.y, control.x, control.y, mPaint);

        //绘制贝塞尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(startPiont.x, startPiont.y);
        path.quadTo(control.x, control.y, endPiont.x, endPiont.y);
        canvas.drawPath(path, mPaint);
    }
}
