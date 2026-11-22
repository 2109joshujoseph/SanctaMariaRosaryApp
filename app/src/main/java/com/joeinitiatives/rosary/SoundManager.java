package com.joeinitiatives.rosary;

import android.content.Context;

/**
 * Audio disabled per requirement ("strictly don't require Malayalam as well as Audio").
 */
public class SoundManager {
    public SoundManager(Context c) {}
    public boolean enabled() { return false; }
    public void playSplash() {}
    public void stopSplash() {}
    public void playAve() {}
    public void playComplete() {}
    public void release() {}
}
