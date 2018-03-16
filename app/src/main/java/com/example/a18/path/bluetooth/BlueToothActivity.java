package com.example.a18.path.bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a18.path.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BlueToothActivity extends AppCompatActivity {

    private static final String TAG = "BlueToothActivity";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BaseQuickAdapter<String, BaseViewHolder> adapter;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //如果捕捉到的是ACTION_BATTERY_CHANGED就运行onBatteryInfoReceiver();将电量显示于新窗口中
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 50);

                onBatteryInfoReceiver(level, scale);
            }
        }
    };

    public void onBatteryInfoReceiver(int intLevel, int intScale) {
        final Dialog d = new Dialog(this);
        d.setTitle("啊啊啊");
        d.setContentView(R.layout.dialog);
        Window window = d.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        TextView mTextView02 = (TextView) d.findViewById(R.id.myTextView02);

        Timber.d("intLevel = %s", intLevel);
        Timber.d("intScale = %s", intScale);


        //取得电池电量显示于Dialog中
        mTextView02.setText("剩余  > " + String.valueOf(intLevel * 100 / intScale) + "%");
        Button b02 = (Button) d.findViewById(R.id.button02);
        b02.setBackgroundColor(Color.RED);
        b02.setText("返回");
        b02.setTextColor(Color.YELLOW);
        b02.setOnClickListener(v -> {
            // 反注册Receiver并关闭窗口
            unregisterReceiver(mBatInfoReceiver);
            d.dismiss();
        });
        d.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        ButterKnife.bind(this, this.getWindow().getDecorView());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.text, item);
            }
        };
        recyclerView.setAdapter(adapter);

        blueTooth();

        registerReceiver(mBatInfoReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    //判断是否开启蓝牙
    public void blueTooth() {
        boolean enable = mBluetoothAdapter.enable(); //返回值表示 是否成功打开了蓝牙功能
        if (enable) {
            Toast.makeText(this, "打开蓝牙功能成功！", Toast.LENGTH_SHORT).show();
            getBlueTooth();
        } else {
            Toast.makeText(this, "打开蓝牙功能失败，请到'系统设置'中手动开启蓝牙功能！", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBlueTooth() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        //如果有配对的设备
        if (pairedDevices.size() > 0) {
            List<String> mArrayAdapter = new ArrayList<>();
            for (BluetoothDevice device : pairedDevices) {
                //通过array adapter在列表中添加设备名称和地址
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Timber.d("bluetooth" + device.getName() + "\n" + device.getAddress());
                BluetoothDevice remoteDevice = mBluetoothAdapter.getRemoteDevice(device.getAddress());
            }

            adapter.setNewData(mArrayAdapter);

        } else {
            Toast.makeText(this, "暂无已配对设备", Toast.LENGTH_SHORT).show();
        }
    }
}
