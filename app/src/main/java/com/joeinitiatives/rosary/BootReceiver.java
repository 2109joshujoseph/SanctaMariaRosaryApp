package com.joeinitiatives.rosary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Restores prayer reminders after a reboot, clock change or timezone change. */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String a = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(a)
                || Intent.ACTION_TIME_CHANGED.equals(a)
                || Intent.ACTION_TIMEZONE_CHANGED.equals(a)) {
            Reminders.scheduleAll(context);
        }
    }
}
