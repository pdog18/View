package com.example.a18.path.evaluator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.a18.path.R;

public class EvaluatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator);

        view = findViewById(R.id.text);
        view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ValueAnimator animator = ObjectAnimator.ofObject(new TextEvaluator(3),new MoneyText(0),new MoneyText(999.554d));
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            MoneyText money = (MoneyText) animation.getAnimatedValue();
            CharSequence content =  money.getContent(62,36);

            view.setText(content);
        });
        animator.start();
    }
}
