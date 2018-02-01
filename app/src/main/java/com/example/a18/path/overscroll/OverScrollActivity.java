package com.example.a18.path.overscroll;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a18.path.R;
import com.example.a18.path.SimpleRecyclerViewAdapter;

public class OverScrollActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_scroll);


        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.overScrollRecyclerView1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SimpleRecyclerViewAdapter());

    }
}
