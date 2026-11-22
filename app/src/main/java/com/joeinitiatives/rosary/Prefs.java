package com.joeinitiatives.rosary;

import android.content.Context;
import android.content.SharedPreferences;

/** Small, dependency-free local settings store. */
public final class Prefs {
    private static SharedPreferences sp(Context c) {
        return c.getApplicationContext().getSharedPreferences("rosary_prefs", Context.MODE_PRIVATE);
    }

    public static String lang(Context c) { return sp(c).getString("lang", "en"); }
    public static void setLang(Context c, String v) { sp(c).edit().putString("lang", v).apply(); }

    public static boolean sound(Context c) { return sp(c).getBoolean("sound", true); }
    public static void setSound(Context c, boolean v) { sp(c).edit().putBoolean("sound", v).apply(); }

    public static boolean anim(Context c) { return sp(c).getBoolean("anim", true); }
    public static void setAnim(Context c, boolean v) { sp(c).edit().putBoolean("anim", v).apply(); }

    public static boolean vibrate(Context c) { return sp(c).getBoolean("vibrate", true); }
    public static void setVibrate(Context c, boolean v) { sp(c).edit().putBoolean("vibrate", v).apply(); }

    public static int rosarySet(Context c) { return sp(c).getInt("rosary_set", Data.todaySet()); }
    public static void setRosarySet(Context c, int v) { sp(c).edit().putInt("rosary_set", v).apply(); }

    public static int rosaryStep(Context c) { return sp(c).getInt("rosary_step", 0); }
    public static void setRosaryStep(Context c, int v) { sp(c).edit().putInt("rosary_step", v).apply(); }

    public static boolean rosaryActive(Context c) { return sp(c).getBoolean("rosary_active", false); }
    public static void setRosaryActive(Context c, boolean v) { sp(c).edit().putBoolean("rosary_active", v).apply(); }

    public static long rosaryStart(Context c) { return sp(c).getLong("rosary_start", 0); }
    public static void setRosaryStart(Context c, long v) { sp(c).edit().putLong("rosary_start", v).apply(); }

    public static boolean reminder(Context c, String key) { return sp(c).getBoolean("rem_" + key, false); }
    public static void setReminder(Context c, String key, boolean v) { sp(c).edit().putBoolean("rem_" + key, v).apply(); }

    public static int time(Context c, String key) { return sp(c).getInt("time_" + key, defaultTime(key)); }
    public static void setTime(Context c, String key, int minutes) { sp(c).edit().putInt("time_" + key, minutes).apply(); }

    public static int defaultTime(String key) {
        if ("morning".equals(key)) return 7 * 60;
        if ("evening".equals(key)) return 21 * 60;
        if ("marian".equals(key)) return 12 * 60;
        return 20 * 60;
    }

    public static String intention(Context c) { return sp(c).getString("intention", ""); }
    public static void setIntention(Context c, String v) { sp(c).edit().putString("intention", v).apply(); }

    public static String dailyDone(Context c) { return sp(c).getString("daily_done", ""); }
    public static void markDaily(Context c, String day) {
        String all = dailyDone(c);
        if (!all.contains(day)) {
            all = all.isEmpty() ? day : all + "," + day;
            sp(c).edit().putString("daily_done", all).apply();
        }
    }
    public static boolean isDailyDone(Context c, String day) { return dailyDone(c).contains(day); }

    public static int totalRosaries(Context c) { return sp(c).getInt("total_rosaries", 0); }
    public static void addRosary(Context c) { sp(c).edit().putInt("total_rosaries", totalRosaries(c) + 1).apply(); }
    public static int totalDecades(Context c) { return sp(c).getInt("total_decades", 0); }
    public static void addDecades(Context c, int n) { sp(c).edit().putInt("total_decades", totalDecades(c) + n).apply(); }

    public static boolean splashSeen(Context c) { return sp(c).getBoolean("splash_seen", false); }
    public static void setSplashSeen(Context c) { sp(c).edit().putBoolean("splash_seen", true).apply(); }
}
