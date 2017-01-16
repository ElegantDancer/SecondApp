package com.zhenzhen.dispatchtouchevent.viewgroup;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import org.json.JSONObject;

/**
 * Created by zhenzhen on 2016/12/21.
 */

public class FirstLinearLayout extends LinearLayout {
    public FirstLinearLayout(Context context) {
        super(context);
    }

    public FirstLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(Utils.TAG, "FirstLinearLayout" + "---> dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

//        if(ev.getAction() == MotionEvent.ACTION_MOVE){
//            Log.i(Utils.TAG, "FirstLinearLayout" + "---> onInterceptTouchEvent--->ACTION_MOVE");
//            return true;
//        }
        Log.i(Utils.TAG, "FirstLinearLayout" + "---> onInterceptTouchEvent");


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(Utils.TAG, "FirstLinearLayout" + "---> onTouchEvent");

        return super.onTouchEvent(event);
    }
}
