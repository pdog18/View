package com.example.a18.path.evaluator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import com.example.a18.path.R

class EvaluatorActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

        IncreaseNumber.begin(textView, 9.55566)
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
            .start()
    }

    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluator)

        textView = findViewById(R.id.text)
        findViewById<View>(R.id.button).setOnClickListener(this)
    }

}

