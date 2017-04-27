package com.iqiyi.myrefreshlayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zhenzhen on 2017/2/15.
 */

public class TouchTest extends LinearLayout {

    private static final String TAG = "TouchTest----->";

    private GestureDetector mGestureDetector;

    public TouchTest(Context context) {
        this(context,null);
    }

    public TouchTest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mGestureDetector = new GestureDetector(context, listener);
    }


    private GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        int mainId = event.getPointerId(0);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "action_down");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "非主要手指，action_down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "action_move");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.i(TAG, "非主要手指离开");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "action_up");
                break;


        }
        return true;
    }
}
