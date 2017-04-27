package com.iqiyi.myrefreshlayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.LinearLayout;

/**
 * Created by zhenzhen on 2017/2/15.
 */

public class MyScrollLinearLayout extends LinearLayout {


    public MyScrollLinearLayout(Context context) {
        this(context, null);
    }

    public MyScrollLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
