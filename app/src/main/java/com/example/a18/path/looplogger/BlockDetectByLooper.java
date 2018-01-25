package com.example.a18.path.looplogger;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by pdog on 2018/1/25.
 */

public class BlockDetectByLooper {
    private static final String FIELD_mQueue = "mQueue";
    private static final String METHOD_next = "next";
    private static final String METHOD_recycleUnchecked = "recycleUnchecked";

    public static void start() {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Looper mainLooper = Looper.getMainLooper();
                final Looper me = mainLooper;
                final MessageQueue queue;
                Field fieldQueue = me.getClass()
                    .getDeclaredField(FIELD_mQueue);
                fieldQueue.setAccessible(true);
                queue = (MessageQueue) fieldQueue.get(me);
                Method methodNext = queue.getClass()
                    .getDeclaredMethod(METHOD_next);
                methodNext.setAccessible(true);
                Binder.clearCallingIdentity();
                for (; ; ) {
                    Message msg = (Message) methodNext.invoke(queue);
                    if (msg == null) {
                        return;
                    }
                    LogMonitor.getInstance().startMonitor();
                    msg.getTarget().dispatchMessage(msg);
                    Binder.clearCallingIdentity();
                    Method recycleUnchecked = Message.class.
                        getDeclaredMethod(METHOD_recycleUnchecked);
                    recycleUnchecked.setAccessible(true);
                    recycleUnchecked.invoke(msg);
                    LogMonitor.getInstance().removeMonitor();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
