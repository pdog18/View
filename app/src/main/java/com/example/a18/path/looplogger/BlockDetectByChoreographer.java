package com.example.a18.path.looplogger;

import android.view.Choreographer;

/**
 * Created by pdog on 2018/1/25.
 */

public class BlockDetectByChoreographer {
    public static void start() {
        Choreographer.getInstance()
            .postFrameCallback(new Choreographer.FrameCallback() {
                @Override
                public void doFrame(long l) {
                    if (LogMonitor.getInstance().isMonitor()) {
                        LogMonitor.getInstance().removeMonitor();
                    }
                    LogMonitor.getInstance().startMonitor();
                    Choreographer.getInstance()
                        .postFrameCallback(this);
                }
            });
    }
}
