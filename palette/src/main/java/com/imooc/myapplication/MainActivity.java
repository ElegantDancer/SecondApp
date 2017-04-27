package com.imooc.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test);
        // 创建Palette对象

        /**
         * 通过这个方法  能把
         */
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
           @Override
           public void onGenerated(Palette palette) {

               /**
                * action bar的背景颜色
                */
               Palette.Swatch swatch = palette.getVibrantSwatch();
               getActionBar().setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));

               /**
                * 最上面的statusbar
                */
               Window window = getWindow();
               window.setStatusBarColor(swatch.getRgb());
           }
       });
    }
}
