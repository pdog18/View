package com.example.a18.path;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {
    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     * @return String
     */
    public static int getRandColorCode(){
        Random random = new Random();
        return Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }
}
