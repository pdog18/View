package com.example.a18.path;

/**
 * desc: todo 描述本类功能
 * author: 18
 * email: pdog@qq.com
 * time: 2017/12/4  22 :09
 */

public class Test {
    public static void main(String[] arr){
        double tan = Math.atan2(100, 100);
        double angleA = 180 * tan /Math.PI;
        int angleA1 = (int) angleA;
        System.out.println(angleA1 == 45);

    }
}
