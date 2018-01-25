package com.example.a18.path.looplogger;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.a18.path.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class LoopLoggerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_logger);

        ButterKnife.bind(this);
//        BlockDetectByLoopPrinter.start();
//        BlockDetectByChoreographer.start();
        BlockDetectByLooper.start();


        toolbar.setTitle("Title");
        toolbar.setSubtitle("SubTitle");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        toolbar.inflateMenu(R.menu.toolbar_menu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.menu_edit:
//                        //业务逻辑
//                        break;
//                }
//                return true;
//            }
//        });


        //refreshActionView有旋转动画，需添加自定义View
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher_round);

        toolbar.getMenu().findItem(R.id.menu_refresh).setActionView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //业务逻辑
            }
        });


    }

    public void ll(View view) {
        SystemClock.sleep(2000);
        Timber.d("view.getWidth() = %s", view.getWidth());
    }
}
