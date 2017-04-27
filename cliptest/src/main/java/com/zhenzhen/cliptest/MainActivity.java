package com.zhenzhen.cliptest;

import android.graphics.Outline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView1 = (TextView) findViewById(R.id.tex1);
        TextView textView2 = (TextView) findViewById(R.id.tex2);

        /**
         * 以view的左上角为坐标原点哦
         */
        ViewOutlineProvider outlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
            }
        };

        ViewOutlineProvider outlineProvider2 = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        };

        //重新设置形状

        textView1.setOutlineProvider(outlineProvider);
        textView2.setOutlineProvider(outlineProvider2);
    }
}
