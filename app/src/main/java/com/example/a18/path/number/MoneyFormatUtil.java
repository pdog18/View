package com.example.a18.path.number;


import java.text.NumberFormat;

/**
 * 对项目中的不同大小的文字进行处理，输入一个数字，返回一个格式化好大小的字符串
 * <p>
 * 10000  -> 10,000
 */
public class MoneyFormatUtil {
    /**
     *
     *  (12345678.845001 , 8)       ->        12,345,678.84500100
     *  (12345678.845001 , 2)       ->        12,345,678.85
     *  (12345678.845001 , 1)       ->        12,345,678.8
     *  (12345678.845001 , 0)       ->        12,345,678
     *
     * @param number    输入的数字
     * @param fractionDigits  小数点后保留的位数
     * @return 输出的字符串
     */
    public static String getFormatMoney(double number, int fractionDigits) {
        numberInstance.setMaximumFractionDigits(fractionDigits);
        numberInstance.setMinimumFractionDigits(fractionDigits);
        return numberInstance.format(number);
    }


    private static NumberFormat numberInstance ;
    static {
        numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMaximumFractionDigits(5);
    }
}
