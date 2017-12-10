package com.example.a18.path;

import android.content.Context;


public class Utils {

    public  static float dp(Context context,float px){
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (px / scale + 0.5f);
    }

    public static Context getApp() {
        return App.getApp();
    }
}
