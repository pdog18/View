package com.example.a18.path.savestate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a18.path.R;

import timber.log.Timber;

public class ActivityB_NO_SAVE extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_no__save);


        Timber.d("intent = %s", intent);

        Intent intent = getIntent();
        if (intent != null) {
            String value = intent.getStringExtra("key");
            Timber.d("getIntent() , value = %s", value);
            this.intent = intent;
        }


        if (savedInstanceState != null) {
            String string = savedInstanceState.getString("key");
            Timber.d("savedInstanceState , string = %s", string);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String value = intent.getStringExtra("key");
        Timber.d("value = %s", value);
        outState.putString("key",value);
        super.onSaveInstanceState(outState);
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("key", "value");
        intent.setClass(this, ActivityC_NO_SAVE.class);
        startActivity(intent);
    }
}
