package com.joeinitiatives.rosary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Delivers a gentle local prayer reminder. */
public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String key = intent == null ? "rosary" : intent.getStringExtra("key");
        if (key == null) key = "rosary";

        String title = L.t(context, "notif_title");
        String text;
        String tab = "rosary";
        int id;
        if ("morning".equals(key)) {
            text = L.t(context, "notif_morning");
            id = 2101;
        } else if ("evening".equals(key)) {
            text = L.t(context, "notif_evening");
            id = 2102;
        } else if ("marian".equals(key)) {
            text = L.t(context, "notif_marian");
            tab = "prayers";
            id = 2103;
        } else {
            text = L.t(context, "notif_rosary");
            id = 2104;
        }
        Notifications.show(context, title, text, id, tab);
        // Reschedule for the next day since we use one-shot exact alarms.
        if (Prefs.reminder(context, key)) Reminders.schedule(context, key);
    }
}
