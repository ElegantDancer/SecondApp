package com.zhenzhen.secondapp.RoundPrecent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by zhenzhen on 2016/12/15.
 */

public class SelfRoundPrecents extends View {

    // 颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    //饼状图初始绘制角度
    private float mStartAngle = 0;
    //数据
    private ArrayList<PieData> mData;
    //宽高
    private int mHeight, mWidth;
    //画笔
    private Paint mPaint;

    public SelfRoundPrecents(Context context) {
        this(context, null);
    }

    public SelfRoundPrecents(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelfRoundPrecents(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        ArrayList<PieData> list = new ArrayList<>();
        for(int i=1; i< 5; i++){
            PieData date = new PieData("百分比" + String.valueOf(i), i * 20);
            list.add(date);
        }

        setDate(list);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(null == mData){
            return;
        }

        float currentStartAngle = mStartAngle;
//        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-r, -r, r, r);

        for(int i= 0; i < mData.size(); i++){
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF, currentStartAngle, pieData.getAngle(), true, mPaint);
            currentStartAngle += pieData.getAngle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    //设置起始角度
    private void setmStartAngle(int startAngle){
        this.mStartAngle = startAngle;
        invalidate();
    }

    //设置数据
    private void setDate(ArrayList<PieData> date){
        mData = date;
        initDate(date);
    }

    private void initDate(ArrayList<PieData> data) {

        if(data == null || data.size() == 0){
            return;
        }

        float sumValue = 0;
        for(int i=0; i < data.size(); i++){
            PieData pie = data.get(i);
            sumValue += pie.getValue();

            int j = i % mColors.length;
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;

        for(int i=0; i< data.size(); i++){
            PieData pie = data.get(i);

            float percent = pie.getValue() / sumValue;
            float angle = percent * 360;

            pie.setPercentage(percent);
            pie.setAngle(angle);
            sumAngle += angle;
        }
    }

}
