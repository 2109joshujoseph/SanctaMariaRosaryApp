package com.joeinitiatives.rosary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/** Schedules the four optional daily prayer reminders. */
public final class Reminders {
    public static final String[] KEYS = {"morning", "evening", "marian", "rosary"};

    private Reminders() {}

    public static int requestCode(String key) {
        if ("morning".equals(key)) return 1101;
        if ("evening".equals(key)) return 1102;
        if ("marian".equals(key)) return 1103;
        return 1104;
    }

    private static PendingIntent pending(Context c, String key) {
        Intent i = new Intent(c, ReminderReceiver.class);
        i.setAction("com.joeinitiatives.rosary.REMIND");
        i.putExtra("key", key);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= 23) flags |= PendingIntent.FLAG_IMMUTABLE;
        return PendingIntent.getBroadcast(c, requestCode(key), i, flags);
    }

    public static void scheduleAll(Context c) {
        for (String key : KEYS) {
            if (Prefs.reminder(c, key)) schedule(c, key);
            else cancel(c, key);
        }
    }

    public static void schedule(Context c, String key) {
        try {
            AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
            if (am == null) return;
            PendingIntent pi = pending(c, key);
            am.cancel(pi);
            long trigger = nextTrigger(c, key);
            if (Build.VERSION.SDK_INT >= 23) {
                // setExactAndAllowWhileIdle fires even in Doze mode
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pi);
            } else if (Build.VERSION.SDK_INT >= 19) {
                am.setExact(AlarmManager.RTC_WAKEUP, trigger, pi);
            } else {
                am.set(AlarmManager.RTC_WAKEUP, trigger, pi);
            }
        } catch (Exception ignored) {
        }
    }

    public static void cancel(Context c, String key) {
        try {
            AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
            if (am != null) am.cancel(pending(c, key));
        } catch (Exception ignored) {
        }
    }

    public static long nextTrigger(Context c, String key) {
        int minutes = Prefs.time(c, key);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, minutes / 60);
        cal.set(Calendar.MINUTE, minutes % 60);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (cal.getTimeInMillis() <= System.currentTimeMillis()) cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTimeInMillis();
    }

    public static String timeLabel(Context c, String key) {
        int m = Prefs.time(c, key);
        int h = m / 60, min = m % 60;
        String ampm = h < 12 ? "AM" : "PM";
        int h12 = h % 12;
        if (h12 == 0) h12 = 12;
        return String.format(java.util.Locale.US, "%d:%02d %s", h12, min, ampm);
    }
}
