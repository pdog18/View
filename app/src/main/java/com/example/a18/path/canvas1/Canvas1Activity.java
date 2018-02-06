package com.example.a18.path.canvas1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.a18.path.R;

public class Canvas1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas1);
        final ImageView imageView = findViewById(R.id.img);
        imageView.post(new Runnable() {
            @Override
            public void run() {

                String simpleName = imageView.getContext().getClass().getSimpleName();
                System.out.println(simpleName);
                System.out.println(imageView.getContext() == Canvas1Activity.this);

                $3D $3D = new $3D(imageView.getContext(),0, 360, imageView.getWidth() / 2, imageView.getHeight() / 2, 0, true);
                $3D.setDuration(3000);
                $3D.setInterpolator(new LinearInterpolator());
                $3D.setRepeatCount(Animation.INFINITE);
                imageView.startAnimation($3D);
            }
        });



    }
}
