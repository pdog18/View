package com.example.a18.path.notifycationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.example.a18.path.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn2)
    void btn2() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder2=new Notification.Builder(this);
        Intent intent2=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
        PendingIntent pendingIntent2=PendingIntent.getActivity(this,0,intent2,0);
        builder2.setContentIntent(pendingIntent2);
        builder2.setSmallIcon(R.mipmap.ic_launcher);
        builder2.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder2.setAutoCancel(true);
        builder2.setContentTitle("折叠通知");

        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.activity_notify);
        Notification  notification=builder2.build();
        notification.bigContentView=remoteViews;
        mNotificationManager.notify(1,notification);

    }
    @OnClick(R.id.btn1)
    void btn1() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder=new Notification.Builder(this);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);


        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("普通通知");
        mNotificationManager.notify(1, builder.build());
    }

    @OnClick(R.id.btn3)
    void btn3() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder3=new Notification.Builder(this);
        Intent intent3=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jianshu.com/p/82e249713f1b"));
        PendingIntent pendingIntent3=PendingIntent.getActivity(this,0,intent3,0);
        builder3.setContentIntent(pendingIntent3);
        builder3.setSmallIcon(R.mipmap.ic_launcher);
        builder3.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder3.setAutoCancel(true);
        builder3.setContentTitle("悬挂通知");

        Intent XuanIntent=new Intent();
        XuanIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        XuanIntent.setClass(this,NotifyActivity.class);

        PendingIntent xuanpengdIntent=PendingIntent.getActivity(this,0,XuanIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder3.setFullScreenIntent(xuanpengdIntent,true);
        mNotificationManager.notify(2,builder3.build());

    }
}
