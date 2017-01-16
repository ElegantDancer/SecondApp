package com.imooc.dragviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


public class MainActivity extends Activity {

    private int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Long hah = new Long(30);

        Log.i("Long.valueOf-----> ", String.valueOf(hah));

        Log.i("Long.valueOf-----> ", String.valueOf(hah.valueOf("1234567", 8)));
        Log.i("Long.valueOf-----> ", String.valueOf(Long.valueOf("1234567")));

    }

    public void btnView(View view) {
        startActivity(new Intent(this, DragViewTest.class));
    }

    public void btnViewGroup(View view) {
        startActivity(new Intent(this, DragViewGroupTest.class));
    }

    public void btnScroll(View view){

        view.scrollBy(30, 30);

        Log.i("*********** ", "*********************************************");
        Log.i("view.getLeft-----> ", String.valueOf(view.getLeft()));
        Log.i("view.getX-----> ", String.valueOf(view.getX()));
        Log.i("view.getTop-----> ", String.valueOf(view.getTop()));
        Log.i("view.getY-----> ", String.valueOf(view.getY()));
        Log.i("view.getTanslateX---> ", String.valueOf(view.getTranslationX()));
        Log.i("view.getTanslateY---> ", String.valueOf(view.getTranslationY()));
        Log.i("view.getScrollX---> ", String.valueOf(view.getScrollX()));
        Log.i("view.getScrollY---> ", String.valueOf(view.getScrollY()));
        Log.i("density-----> ", String.valueOf(getResources().getDisplayMetrics().density));
        Log.i("density-----> ", String.valueOf(getResources().getDisplayMetrics().densityDpi));
        Log.i("density-----> ", String.valueOf(getResources().getDisplayMetrics().scaledDensity));
        Log.i("*********** ", "*********************************************");

    }

    public void btnTranslate(View view){

        x += 30;
        view.setTranslationY(x);

        Log.i("***********", "**********************");
        Log.i("view.getLeft-----> ", String.valueOf(view.getLeft()));
        Log.i("view.getX-----> ", String.valueOf(view.getX()));
        Log.i("view.getTop-----> ", String.valueOf(view.getTop()));
        Log.i("view.getY-----> ", String.valueOf(view.getY()));
        Log.i("view.getTanslateX---> ", String.valueOf(view.getTranslationX()));
        Log.i("view.getTanslateY---> ", String.valueOf(view.getTranslationY()));
        Log.i("view.getScrollX---> ", String.valueOf(view.getScrollX()));
        Log.i("view.getScrollY---> ", String.valueOf(view.getScrollY()));

        Log.i("***********", "***********************");

    }

    private int dp2px(float dp){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,  getResources().getDisplayMetrics());
    }

    private int px2dp(float px){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px,  getResources().getDisplayMetrics());
    }

    private int sp2px(float sp){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,  getResources().getDisplayMetrics());
    }
}
