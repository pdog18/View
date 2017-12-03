package com.example.a18.path;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<Class> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        initActivities();

        initListView();


    }

    private void initListView() {
        ListView listview = (ListView) findViewById(R.id.lv);
        listview.setAdapter(new BaseAdapter() {
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
                    Intent intent = new Intent(Main2Activity.this, clazz);
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
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            System.out.println(packageInfo.activities.length);
            for (ActivityInfo activity : packageInfo.activities) {
                Class<?> aClass = Class.forName(activity.name);
                if (aClass.equals(Main2Activity.class)) {
                    continue;
                }
                mList.add(0,aClass);
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
