package com.example.a18.path;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<Class> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initActivities();

        initListView();
    }

    private void initListView() {
        listView = findViewById(R.id.lv);
        Timber.d("listView = %s", listView);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                final Class clazz = mList.get(i);
                TextView textView = new TextView(viewGroup.getContext());
                ViewGroup.LayoutParams params = new ViewPager.LayoutParams();
                params.height = 150;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                textView.setLayoutParams(params);
                textView.setText(clazz.getSimpleName());

                textView.setGravity(Gravity.CENTER);

                textView.setOnClickListener(view1 -> {
                    Intent intent = new Intent(MainActivity.this, clazz);
                    startActivity(intent);
                });
                return textView;
            }
        });

    }

    private void initActivities() {
        mList.clear();

        try {
            PackageManager packageManager = getPackageManager();
            Timber.d("getPackageName() = %s", getPackageName());
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            System.out.println(packageInfo.activities.length);
            for (ActivityInfo activity : packageInfo.activities) {
                Class<?> aClass;
                try {
                    aClass = Class.forName(activity.name);
                } catch (ClassNotFoundException e) {
                    continue;
                }
                if (aClass.equals(MainActivity.class)) {
                    continue;
                }

                if (aClass.getSimpleName().contains("NO_SAVE")) {
                    continue;
                }

                Timber.d("aClass.getSimpleName() = %s", aClass.getSimpleName());
                mList.add(0, aClass);
            }
        } catch (PackageManager.NameNotFoundException  e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
