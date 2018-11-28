package com.pdog.support

import java.math.RoundingMode
import java.text.NumberFormat

/**
 * 对项目中的不同大小的文字进行处理，输入一个数字，返回一个格式化好大小的字符串
 *
 * 10000  -> 10,000
 */
private val numberInstance: NumberFormat by lazy {
    NumberFormat.getNumberInstance().apply {
        maximumFractionDigits = 2
        roundingMode = RoundingMode.HALF_UP
    }
}

/**
 * (12345678.845001 , 8)       ->        12,345,678.84500100
 * (12345678.845001 , 2)       ->        12,345,678.85
 * (12345678.845001 , 1)       ->        12,345,678.8
 * (12345678.845001 , 0)       ->        12,345,678
 *
 * [fractionDigits] 小数点后保留的位数
 * [nullReturn] 空值时返回
 * @return 输出的字符串
 */
@Synchronized
fun Double?.formatMoney(fractionDigits: Int = 2, nullReturn: String = "-"): String {
    return if (this == null) {
        nullReturn
    } else {
        numberInstance.maximumFractionDigits = fractionDigits
        numberInstance.minimumFractionDigits = fractionDigits
        val result = numberInstance.format(this)
        if (result == "-0") {
            "0"
        } else {
            result
        }
    }
}

fun Double?.formatMoneyTenThousand(fractionDigits: Int = 2,
                                   nullReturn: String = "-",
                                   prefix: String = "",
                                   postfix: String = "万元"): String {
    return if (this == null) {
        nullReturn
    } else {
        "$prefix${(this / 10000).formatMoney(fractionDigits, nullReturn)}$postfix"
    }
}