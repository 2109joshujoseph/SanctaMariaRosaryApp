package com.joeinitiatives.rosary;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

public final class Haptics {
    private Haptics() {}

    public static void tap(Context c) {
        if (!Prefs.vibrate(c)) return;
        // Try View-based haptic feedback first (no special permission needed on modern Android)
        try {
            if (c instanceof android.app.Activity) {
                View root = ((android.app.Activity) c).getWindow().getDecorView();
                root.performHapticFeedback(android.view.HapticFeedbackConstants.KEYBOARD_TAP,
                        android.view.HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
            }
        } catch (Exception ignored) {}
        // Also trigger Vibrator for older devices
        try {
            Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
            if (v == null || !v.hasVibrator()) return;
            if (Build.VERSION.SDK_INT >= 26) {
                v.vibrate(VibrationEffect.createOneShot(18, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(18);
            }
        } catch (Exception ignored) {}
    }
}

