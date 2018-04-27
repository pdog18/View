package kt.pdog18.com.module_evaluator


import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_INTERMEDIATE
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView


class IncreaseNumber(private val textView: TextView, private val endValue: Double) {
    /**
     * 实例: 999.545
     * [index] 保留两位小数时 999.55
     * [leftSize] 999 字体大小
     * [rightSize]  .545 字体大小
     *
     * content 都包含了字体大小
     * [content] 999.545的字符
     * [leftContent] 999
     * [rightContent] .545
     *
     * [convert] 传入两个值分别为 999 和 545,根据这两个值进行生成对应的SpannableStringBuilder
     * [joints] 传入由 convert 传入的三个SpannableStringBuilder对象，可以通过重写它来插入对应的元素
     */
    var index = 2
    var leftSize = 16
    var rightSize = 16
    val content = SpannableStringBuilder()
    val leftContent = SpannableStringBuilder()
    val rightContent = SpannableStringBuilder()

    var flag = SPAN_INTERMEDIATE
    var convert: (body: IncreaseEvaluator.IncreaseBody) -> SpannableStringBuilder
        = { body ->

        leftContent.clear()
        rightContent.clear()
        content.clear()

        val left = body.left
        val right = body.right

        val leftText = Integer.toString(left)
        leftContent.append(leftText)
            .setSpan(AbsoluteSizeSpan(leftSize, true), 0, leftText.length, flag)
        // 如果 保留小数点后位数为0，则手动四舍五入，然后直接返回
        if (index != 0) {

            if (right == 0 ) {
                addLastZero(rightContent, index)
            } else {
                rightContent.append(".${Integer.toString(right)}")
            }
            rightContent.setSpan(AbsoluteSizeSpan(rightSize, true), 0, rightContent.length, flag)
        }

        joints(content, leftContent, rightContent)
    }

    /**
     * [content] 此时为空
     */
    var joints: (content: SpannableStringBuilder,
                 left: SpannableStringBuilder,
                 right: SpannableStringBuilder) -> SpannableStringBuilder
        = { content, left, right ->

        content.append(left).append(right)
    }


    fun createAnimator(): ValueAnimator {
        val endBody = IncreaseEvaluator.IncreaseBody(endValue)
        val increaseEvaluator = IncreaseEvaluator(index)
        val animator = ObjectAnimator.ofObject(increaseEvaluator, endBody)

        animator.addUpdateListener { _ ->
            textView.text = convert(increaseEvaluator.increasingBody)
        }
        return animator
    }

    private fun addLastZero(content: SpannableStringBuilder, index: Int) {
        //todo 例如保留4位小数时，只有2位数时需要补充0

        content.append(".")
        for (i in 0 until index) {
            content.append("0")
        }
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
