package com.zhenzhen.dispatchtouchevent.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by zhenzhen on 2016/12/21.
 */

public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        if(event.getAction() == MotionEvent.ACTION_DOWN){
//
//            Log.i(Utils.TAG, "MyButoon" + "---> consume ACTION_DOWN");
//            return true;
//        }

        if(event.getAction() == MotionEvent.ACTION_CANCEL){
            Log.i(Utils.TAG, "MyButoon" + "---> consume ACTION_CANCEL");
            return super.onTouchEvent(event);
        }


        Log.i(Utils.TAG, "MyButoon" + "---> consume Others");

        return super.onTouchEvent(event);
    }
}
