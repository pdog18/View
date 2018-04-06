package com.example.a18.path.evaluator

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.TextView
import com.example.a18.path.R
import java.text.NumberFormat

class EvaluatorActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        /* IncreaseNumber.begin(textView, 9.55566)
             .apply {
                 leftSize = 64
                 rightSize = 32
                 index = 2
                 joints = { content, left, right ->
                     content.append("$").append(left).append("👌").append(right).append("%")
                 }
             }
             .createAnimator()
             .setDuration(200000)
             .apply {
                 interpolator = DecelerateInterpolator(3.0f)
             }
             .start()*/


        var fractionDigits = 3
        var leftSize = 64
        var rightSize = 32
        var builder = SpannableStringBuilder()

        val formatter = NumberFormat.getInstance().apply {
            maximumFractionDigits = fractionDigits
            minimumFractionDigits = fractionDigits
        }

        ValueAnimator.ofFloat(99.9f).apply {
            duration = 1000
            addUpdateListener {
                (it.animatedValue is Float).let {
                    val value = formatter.format(this.animatedValue).toString()
                    builder.apply {
                        clear()
                        append(value)
                        setSpan(AbsoluteSizeSpan(leftSize, true), 0, value.length - 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        setSpan(AbsoluteSizeSpan(rightSize, true), value.length - 4, value.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        textView!!.text = builder
                    }
                }
            }
            start()
        }
    }

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluator)

        textView = findViewById(R.id.text)
        findViewById<View>(R.id.button).setOnClickListener(this)
    }

}

