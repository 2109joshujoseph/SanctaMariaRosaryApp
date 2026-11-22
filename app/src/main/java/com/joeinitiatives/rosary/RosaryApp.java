package com.joeinitiatives.rosary;

import android.app.Application;
import android.util.Log;

public class RosaryApp extends Application {
    private static final String TAG = "SanctaMaria";

    @Override
    public void onCreate() {
        super.onCreate();
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "FATAL CRASH in thread: " + t.getName(), e);
                if (defaultHandler != null) {
                    defaultHandler.uncaughtException(t, e);
                }
            }
        });
        try {
            Data.load(this);
            Notifications.createChannel(this);
        } catch (Throwable t) {
            Log.e(TAG, "Init error", t);
        }
    }
}
