package com.zhenzhen.dispatchtouchevent.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zhenzhen on 2016/12/21.
 */

public class SecondLinearLayout extends LinearLayout {
    public SecondLinearLayout(Context context) {
        super(context);
    }

    public SecondLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecondLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i(Utils.TAG, "SecondLinearLayout" + "---> onInterceptTouchEvent");
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            Log.i(Utils.TAG, "SecondLinearLayout" + "---> onInterceptTouchEvent--->ACTION_MOVE");
            return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(Utils.TAG, "SecondLinearLayout--> onTouchEvent--->" + String.valueOf(event.getAction()));
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i(Utils.TAG, "SecondLinearLayout---> dispatchTouchEvent" + String.valueOf(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }
}
