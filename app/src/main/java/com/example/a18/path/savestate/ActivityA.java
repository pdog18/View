package com.example.a18.path.savestate;

import android.content.Intent;
import android.os.Bundle;
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
        Intent intent = new Intent();
        intent.putExtra("key", "value");
        intent.setClass(this, ActivityB_NO_SAVE.class);
        startActivity(intent);
    }
}
