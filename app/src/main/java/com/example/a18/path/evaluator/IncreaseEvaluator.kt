package com.example.a18.path.evaluator

import android.animation.TypeEvaluator
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan

class IncreaseEvaluator(index: Int) : TypeEvaluator<IncreaseEvaluator.IncreaseText> {
    private val moneyText: IncreaseText

    init {
        moneyText = IncreaseText(0.0)
        moneyText.setIndex(index)
    }


    override fun evaluate(fraction: Float, startValue: IncreaseText?, endValue: IncreaseText?): IncreaseText {
        return moneyText.update(endValue!!.total, fraction)
    }

    class IncreaseText(total: Double) {
        private var left: Int = 0       //小数点前
        private var right: Int = 0      //小数点以后
        internal var total: Double = 0.toDouble()
        private val content: SpannableStringBuilder = SpannableStringBuilder()
        private var factor: Int = 0
        private var index = 2
        private val flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE

        init {
            update(total, 1f)
        }

        fun update(endTotal: Double, fraction: Float): IncreaseText {

            this.total = endTotal * fraction
            this.left = total.toInt()
            this.right = Math.round((total - left) * factor).toInt()

            return this
        }

        private fun addLastZero(content: SpannableStringBuilder) {
            for (i in 0 until index) {
                content.append("0")
            }
        }

        fun getContent(leftSize: Int, rightSize: Int): SpannableStringBuilder {
            content.clear()       //清空字符串
            // 如果 保留小数点后位数为0，则手动四舍五入，然后直接返回
            if (index == 0) {
                if (right >= factor / 2) {
                    left += 1
                }
                val leftText = Integer.toString(left)
                content.append(leftText)
                        .setSpan(AbsoluteSizeSpan(leftSize, true), 0, content.length, flag)

                return content
            }

            if (right == factor) { //类似0.99995四舍五入后需要向前进一位
                left += 1
                right = 0
            }

            val leftText = Integer.toString(left)
            content.append(leftText)
                    .append(".")
                    .setSpan(AbsoluteSizeSpan(leftSize, true), 0, content.length, flag)

            val beforeLength = content.length

            if (right == 0) {
                addLastZero(content)
            } else {
                val rightText = Integer.toString(right)
                content.append(rightText)
            }

            content.setSpan(AbsoluteSizeSpan(rightSize, true), beforeLength, content.length, flag)

            return content
        }

        /**
         * @param index 保留小数点的位数
         */
        fun setIndex(index: Int) {
            if (index < 0) {
                throw IllegalArgumentException("factor 不能小于0，当前为" + index)
            }

            this.index = index
            this.factor = Math.pow(10.0, index.toDouble()).toInt()
        }
    }
}
