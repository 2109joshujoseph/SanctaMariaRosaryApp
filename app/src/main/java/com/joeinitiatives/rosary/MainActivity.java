package com.joeinitiatives.rosary;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    public FrameLayout root;
    public FrameLayout content;
    private LinearLayout nav;
    public final ArrayList<BaseScreen> stack = new ArrayList<>();
    public BaseScreen current;
    private int tab = 0;
    private SoundManager sound;
    private SplashView splash;
    private final ArrayList<ImageView> navIcons = new ArrayList<>();
    private final ArrayList<TextView> navLabels = new ArrayList<>();
    private final ArrayList<View> navBars = new ArrayList<>();
    private final int[] navIconRes = {R.drawable.ic_home, R.drawable.ic_rosary, R.drawable.ic_mysteries, R.drawable.ic_mary, R.drawable.ic_prayers};
    private final String[] navKeys = {"home", "rosary", "mysteries", "devotions", "prayers"};

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        Data.load(this);
        Notifications.createChannel(this);
        // Window styling is optional on older platforms; skip to keep compatibility.
        // getWindow().setStatusBarColor(Ui.IVORY);
        // getWindow().setNavigationBarColor(Ui.IVORY);
        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sound = new SoundManager(this);
        buildRoot();
        showTab(0);
        showSplash();
        Reminders.scheduleAll(this);
        handleIntent(getIntent());
    }

    private void buildRoot() {
        root = new FrameLayout(this);
        root.setBackground(Ui.screenBg(this));

        content = new FrameLayout(this);
        FrameLayout.LayoutParams clp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        clp.bottomMargin = Ui.dp(this, 68);
        root.addView(content, clp);

        View hair = new View(this);
        hair.setBackgroundColor(0x33C9A227);
        FrameLayout.LayoutParams hlp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(this, 1));
        hlp.gravity = Gravity.BOTTOM;
        hlp.bottomMargin = Ui.dp(this, 68);
        root.addView(hair, hlp);

        nav = Ui.row(this);
        nav.setBackgroundColor(0xF2FBF7EE);
        nav.setElevation(Ui.dp(this, 10));
        FrameLayout.LayoutParams nlp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(this, 68));
        nlp.gravity = Gravity.BOTTOM;
        root.addView(nav, nlp);

        for (int i = 0; i < navKeys.length; i++) {
            final int idx = i;
            LinearLayout item = Ui.column(this);
            item.setGravity(Gravity.CENTER);
            item.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            item.setClickable(true);
            item.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) { showTab(idx); }
            });
            Ui.press(item);

            View bar = new View(this);
            bar.setBackgroundColor(0x00C9A227);
            item.addView(bar, new LinearLayout.LayoutParams(Ui.dp(this, 22), Ui.dp(this, 2)));

            ImageView icon = new ImageView(this);
            icon.setImageResource(navIconRes[i]);
            LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(Ui.dp(this, 21), Ui.dp(this, 21));
            ilp.topMargin = Ui.dp(this, 6);
            item.addView(icon, ilp);
            navIcons.add(icon);
            navBars.add(bar);

            TextView label = new TextView(this);
            label.setTextSize(9.5f);
            label.setGravity(Gravity.CENTER);
            label.setTypeface(Ui.sansMedium());
            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tlp.topMargin = Ui.dp(this, 2);
            item.addView(label, tlp);
            navLabels.add(label);

            nav.addView(item);
        }
        setContentView(root);
    }

    private BaseScreen createTab(int i) {
        switch (i) {
            case 1: return new RosaryScreen(this);
            case 2: return new MysteriesScreen(this);
            case 3: return new DevotionsScreen(this);
            case 4: return new PrayersScreen(this);
            default: return new HomeScreen(this);
        }
    }

    public void showTab(int i) {
        tab = i;
        stack.clear();
        BaseScreen s = createTab(i);
        current = s;
        s.create();
        content.removeAllViews();
        content.addView(s, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Ui.fadeIn(s);
        updateNav();
    }

    public void push(BaseScreen s) {
        if (current != null) stack.add(current);
        current = s;
        s.create();
        content.removeAllViews();
        content.addView(s, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        s.setTranslationX(Ui.dp(this, 26));
        s.setAlpha(0f);
        s.animate().translationX(0).alpha(1f).setDuration(220).start();
    }

    public void pop() {
        if (!stack.isEmpty()) {
            BaseScreen prev = stack.remove(stack.size() - 1);
            current = prev;
            content.removeAllViews();
            content.addView(prev, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            prev.setAlpha(1f);
            prev.setTranslationX(0f);
        } else if (tab != 0) {
            showTab(0);
        }
    }

    public void toggleLanguage() {
        Prefs.setLang(this, L.ml(this) ? "en" : "ml");
        for (BaseScreen s : stack) s.rebuild();
        if (current != null) current.rebuild();
        updateNav();
    }

    /** Rebuilds the visible screen in place, preserving the navigation stack. */
    public void rebuildCurrent() {
        if (current != null) current.rebuild();
    }

    public void updateNav() {
        for (int i = 0; i < navKeys.length; i++) {
            boolean sel = i == tab;
            navIcons.get(i).setColorFilter(sel ? Ui.GOLD : 0xFF8A94A6);
            navLabels.get(i).setText(L.t(this, navKeys[i]));
            navLabels.get(i).setTextColor(sel ? Ui.BLUE : 0xFF8A94A6);
            navBars.get(i).setBackgroundColor(sel ? Ui.GOLD : 0x00000000);
        }
    }

    private void showSplash() {
        nav.setVisibility(View.INVISIBLE);
        splash = new SplashView(this, new Runnable() {
            @Override public void run() { endSplash(); }
        });
        root.addView(splash, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        splash.start();
    }

    private void endSplash() {
        if (splash == null) return;
        final SplashView s = splash;
        splash = null;
        s.animate().alpha(0f).setDuration(650).withEndAction(new Runnable() {
            @Override public void run() {
                root.removeView(s);
                nav.setVisibility(View.VISIBLE);
                nav.setAlpha(0f);
                nav.animate().alpha(1f).setDuration(300).start();
                maybeAskNotifications();
                Prefs.setSplashSeen(MainActivity.this);
            }
        }).start();
    }

    private void maybeAskNotifications() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission("android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 10);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);
    }

    public SoundManager sound() { return sound; }

    @Override
    public void onBackPressed() {
        if (splash != null) {
            endSplash();
            return;
        }
        if (!stack.isEmpty()) {
            pop();
        } else if (tab != 0) {
            showTab(0);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null) return;
        String tabExtra = intent.getStringExtra("tab");
        if (tabExtra != null) {
            if ("rosary".equals(tabExtra)) showTab(1);
            else if ("daily".equals(tabExtra)) push(new DailyScreen(this));
            else if ("prayers".equals(tabExtra)) showTab(4);
        }
    }

    @Override
    protected void onDestroy() {
        if (sound != null) sound.release();
        super.onDestroy();
    }
}
