package com.joeinitiatives.rosary;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Final screen shown when a Rosary is completed.
 * The celestial animation with angels orbiting and passing around Mother Mary
 * plays continuously, and after 3 seconds the three action buttons smoothly glide in below.
 */
public class CompletionScreen extends BaseScreen {
    private HeavenlyView sky;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public CompletionScreen(MainActivity a) {
        super(a, "rosary_completed", true);
    }

    @Override
    protected void buildContent() {
        FrameLayout frame = new FrameLayout(act);

        // 1. Full celestial animation: rotating light rays, twinkling stars,
        // and angels flying across and orbiting around Mother Mary
        sky = new HeavenlyView(act);
        frame.addView(sky, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        sky.start();

        // 2. Info + buttons column pinned at bottom (above the soft sky vignette)
        LinearLayout col = Ui.column(act);
        col.setGravity(Gravity.CENTER_HORIZONTAL);
        col.setPadding(Ui.dp(act, 24), Ui.dp(act, 16), Ui.dp(act, 24), Ui.dp(act, 24));

        View star = new View(act);
        star.setBackgroundColor(Ui.GOLD_LIGHT);
        col.addView(star, new LinearLayout.LayoutParams(Ui.dp(act, 42), Ui.dp(act, 2)));

        TextView title = Ui.text(act, L.t(act, "rosary_completed"), 28, 0xFFF3D98B, true);
        title.setTypeface(Ui.serifBold());
        title.setGravity(Gravity.CENTER);
        title.setShadowLayer(Ui.dp(act, 16), 0, 0, 0xAA000000);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 12);
        col.addView(title, tlp);

        TextView blessing = Ui.text(act, L.t(act, "blessing_msg"), 13.5f, 0xFFDCE7F6, true);
        blessing.setGravity(Gravity.CENTER);
        blessing.setLineSpacing(Ui.dp(act, 4), 1.15f);
        blessing.setShadowLayer(Ui.dp(act, 8), 0, 0, 0xCC000000);
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.topMargin = Ui.dp(act, 6);
        col.addView(blessing, blp);

        HistoryDb db = new HistoryDb(act);
        int rosaries = db.totalRosaries();
        db.close();
        LinearLayout stats = Ui.row(act);
        stats.setGravity(Gravity.CENTER);
        TextView r = Ui.text(act, rosaries + "", 24, 0xFFF3D98B, true);
        r.setTypeface(Ui.serifBold());
        stats.addView(r);
        TextView rl = Ui.text(act, "  " + L.t(act, "total_rosaries").toLowerCase(), 11.5f, 0xFFDCE7F6, false);
        stats.addView(rl);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 8);
        col.addView(stats, slp);

        // 3. Three Action Buttons: initially hidden, smoothly glide in after 3 seconds
        final LinearLayout buttons = Ui.column(act);
        buttons.setAlpha(0f);
        buttons.setTranslationY(Ui.dp(act, 24));

        TextView prayAgain = Ui.primaryButton(act, L.t(act, "pray_again"), new OnClickListener() {
            @Override public void onClick(View v) {
                if (sky != null) sky.stop();
                Prefs.setRosarySet(act, Data.todaySet());
                Prefs.setRosaryStep(act, 0);
                Prefs.setRosaryActive(act, true);
                Prefs.setRosaryStart(act, System.currentTimeMillis());
                act.showTab(1);
            }
        });
        LinearLayout.LayoutParams pal = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pal.topMargin = Ui.dp(act, 16);
        buttons.addView(prayAgain, pal);

        TextView home = Ui.secondaryButton(act, L.t(act, "return_home"), new OnClickListener() {
            @Override public void onClick(View v) {
                if (sky != null) sky.stop();
                act.showTab(0);
            }
        });
        home.setTextColor(Ui.WHITE);
        home.setBackground(Ui.stroke(act, 0x44071324, 0x66E8C96A, 14));
        LinearLayout.LayoutParams hl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hl.topMargin = Ui.dp(act, 8);
        buttons.addView(home, hl);

        TextView history = Ui.secondaryButton(act, L.t(act, "view_history"), new OnClickListener() {
            @Override public void onClick(View v) {
                if (sky != null) sky.stop();
                act.push(new HistoryScreen(act));
            }
        });
        history.setTextColor(Ui.WHITE);
        history.setBackground(Ui.stroke(act, 0x44071324, 0x66E8C96A, 14));
        LinearLayout.LayoutParams hsl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hsl.topMargin = Ui.dp(act, 8);
        buttons.addView(history, hsl);

        col.addView(buttons, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        FrameLayout.LayoutParams clp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.gravity = Gravity.BOTTOM;
        frame.addView(col, clp);
        addFull(frame);

        // Smoothly bring in buttons after 3 seconds of viewing the full angels and Mary animation
        handler.postDelayed(new Runnable() {
            @Override public void run() {
                buttons.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(700)
                        .start();
            }
        }, 3000);

        frame.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override public void onViewAttachedToWindow(View v) {}
            @Override public void onViewDetachedFromWindow(View v) {
                handler.removeCallbacksAndMessages(null);
                if (sky != null) sky.stop();
            }
        });
    }
}
