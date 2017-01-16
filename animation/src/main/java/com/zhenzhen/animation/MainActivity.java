package com.zhenzhen.animation;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends Activity  implements View.OnClickListener{

    Button animBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animBtn = (Button) findViewById(R.id.anim_btn);
        animBtn.setOnClickListener(this);
    }

    public void onClick(View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics()));

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int value = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) animBtn.getLayoutParams();
                params.height = value;
                Log.i("----->", "animator is on");
                animBtn.setLayoutParams(params);
            }
        });

        valueAnimator.start();
    }
}
