package kt.pdog18.com.module_materialdesign;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import kt.pdog18.com.base.BaseFragment;

public class RippleFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gif;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.foreground).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "aaaa", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

