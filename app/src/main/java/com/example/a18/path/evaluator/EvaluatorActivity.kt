package com.example.a18.path.evaluator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

import com.example.a18.path.R

class EvaluatorActivity : AppCompatActivity(), View.OnClickListener {

    private var view: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluator)

        view = findViewById(R.id.text)
        findViewById<View>(R.id.button).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        IncreaseNumber.begin(view, 9999.55566)
                .setLeftSize(64)
                .setRightSize(36)
                .createAnimator()
                .setDuration(2000)
                .start()
    }
}
