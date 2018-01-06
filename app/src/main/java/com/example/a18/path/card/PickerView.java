package com.example.a18.path.card;

import android.content.Context;
import android.support.v4.widget.Space;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import static com.example.a18.path.Utils.dp2px;

public class PickerView extends ScrollView {

    private LinearLayout linearLayout;
    private PickViewAdapter adapter;

    public PickerView(Context context) {
        this(context, null);
    }

    public PickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(linearLayout);
    }

    private void init() {
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, this);
            linearLayout.addView(view);
        }

        Space space = new Space(getContext());
        space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(640)));
        linearLayout.addView(space);
    }

    public void setAdapter(PickViewAdapter adapter) {
        this.adapter = adapter;
        init();
    }


    /**
     * 设置position
     */
    public void setPosition(int position) {
        int childHeight = linearLayout.getChildAt(0).getHeight();
        post(() -> smoothScrollTo(0, position * childHeight));
    }
}
