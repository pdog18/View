package com.example.a18.path.looplogger;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import timber.log.Timber;

public class LogMonitor {
    private final static String TAG = LogMonitor.class.getSimpleName();
    private static HandlerThread mLogThread = new HandlerThread("log");
    private static LogMonitor sInstance = new LogMonitor();
    private  Handler mIoHandler;
    private static final long TIME_BLOCK = 1000L;

    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = mLogThread.getStackTrace();

            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString()).append("\n");
            }
            Log.e(TAG, sb.toString());
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }

    public boolean isMonitor() {
        Class handlerClass = null;
        try {
            handlerClass = Class.forName("android.os.Handler");
            Method method = handlerClass.getDeclaredMethod("hasCallbacks", Runnable.class);
            method.setAccessible(true);
            return (boolean) method.invoke(mIoHandler,mLogRunnable);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
