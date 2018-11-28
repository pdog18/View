package pdog18.com.module_materialdesign;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import pdog18.com.base.BaseFragment;
import pdog18.com.module_materialdesign.widget.NumberTextView;


public class NumberTextFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_number_text;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_1 = view.findViewById(R.id.tv_1);
        TextView tv_2 = view.findViewById(R.id.tv_2);
        NumberTextView tv_3 = view.findViewById(R.id.tv_3);
        tv_1.setText("111111");
        tv_2.setText("222222");
        tv_3.setNumber(3333.22f);
    }
}

