package com.example.a18.path.scrollchart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.internal.NavigationMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.a18.path.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")

public class ScrollC extends FrameLayout {
    public ScrollC(Context context) {
        this(context, null);
    }

    public ScrollC(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollC(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.layout, this, true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        // TODO: 2017/12/9 监听滑动 显示当前的第一个条目的进度文本
        recyclerView.setLayoutManager(manager);


        List<BackMoneyAdapter.Body> bodies = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            BackMoneyAdapter.Body body = new BackMoneyAdapter.Body();
            body.progress = i *2;
            bodies.add(body);
        }
        ChartAdapter adapter = new BackMoneyAdapter(R.layout.progress,bodies);

        recyclerView.setAdapter(adapter);

        adapter.setListener(new ChartAdapter.ClickListener() {
            @Override
            public void onClick(int i) {
                //点击了 进度条，那么现在显示柱状图下方的回款信息
                float xOnUP = ((RV) recyclerView).getMotionEventUp();

            }
        });
    }


    private static final String TAG = "ScrollC";


}
