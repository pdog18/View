package com.example.a18.path.layoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.a18.path.R;
import com.example.a18.path.SimpleRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new MyLayoutManager());
        recyclerView.setAdapter(new SimpleRecyclerViewAdapter());
    }

}
