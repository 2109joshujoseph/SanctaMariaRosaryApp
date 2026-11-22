package com.joeinitiatives.rosary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/** Local, offline prayer notifications. No network, no tracking. */
public final class Notifications {
    public static final String CHANNEL = "prayer_reminders";

    private Notifications() {}

    public static void createChannel(Context c) {
        if (Build.VERSION.SDK_INT < 26) return;
        try {
            Class<?> nch = Class.forName("android.app.NotificationChannel");
            Object channel = nch.getConstructor(String.class, CharSequence.class, int.class)
                    .newInstance(CHANNEL, c.getString(R.string.channel_name), 3); // IMPORTANCE_DEFAULT=3
            nch.getMethod("setDescription", String.class).invoke(channel, c.getString(R.string.channel_desc));
            nch.getMethod("enableVibration", boolean.class).invoke(channel, true);
            NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm != null) nm.getClass().getMethod("createNotificationChannel", nch).invoke(nm, channel);
        } catch (Exception ignored) {}
    }

    public static void show(Context c, String title, String text, int id, String tab) {
        try {
            NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm == null) return;

            Intent i = new Intent(c, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (tab != null) i.putExtra("tab", tab);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= 23) flags |= 0x04000000; // FLAG_IMMUTABLE
            PendingIntent pi = PendingIntent.getActivity(c, id, i, flags);

            Notification.Builder b = new Notification.Builder(c);
            // set channel id on API 26+
            if (Build.VERSION.SDK_INT >= 26) {
                try { b.getClass().getMethod("setChannelId", String.class).invoke(b, CHANNEL); } catch (Exception ignored) {}
            }
            b.setSmallIcon(R.drawable.ic_cross)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setStyle(new Notification.BigTextStyle().bigText(text))
                    .setContentIntent(pi)
                    .setAutoCancel(true);
            if (Build.VERSION.SDK_INT < 26) b.setPriority(Notification.PRIORITY_DEFAULT);
            nm.notify(id, b.build());
        } catch (Exception ignored) {
        }
    }
}
