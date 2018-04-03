package com.example.a18.path.evaluator


import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.text.SpannableStringBuilder
import android.widget.TextView


class IncreaseNumber(private val textView: TextView, private val endValue: Double) {
    var index = 2
    var leftSize = 16
    var rightSize = 16
    var convert: Convert? = null

    fun createAnimator(): ValueAnimator {
        val animator = ObjectAnimator.ofObject(IncreaseEvaluator(index), IncreaseEvaluator.IncreaseText(endValue))
        animator.addUpdateListener { animation ->
            val money = animation.animatedValue as IncreaseEvaluator.IncreaseText
            var content = money.getContent(leftSize, rightSize)
            if (convert != null) {
                content = convert!!.convert(content)
            }
            textView.text = content
        }
        return animator
    }

    interface Convert {
        fun convert(raw: SpannableStringBuilder): SpannableStringBuilder
    }


    companion object {

        fun begin(textView: TextView?, endValue: Double): IncreaseNumber {
            if (textView == null) {
                throw NullPointerException()
            }
            return IncreaseNumber(textView, endValue)
        }
    }

}
