package com.example.a18.path.savestate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
    }

   public void onClick(View view) {

       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


       transaction.setCustomAnimations(
           R.animator.base_slide_right_in, R.animator.base_slide_right_in,
           R.animator.base_slide_right_in, R.animator.base_slide_right_in
       );

       transaction.replace(R.id.fl_content, new AnimFragment());

//       transaction.addToBackStack(null);

       transaction.commit();
   }
}
