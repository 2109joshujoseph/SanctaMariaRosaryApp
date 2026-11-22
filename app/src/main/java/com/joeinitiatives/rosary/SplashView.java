package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The animated opening animation of Mother Mary with angels, smoothly
 * transitioning into the Home page.
 */
public class SplashView extends FrameLayout {
    private final HeavenlyView sky;
    private final Runnable onFinish;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean done;

    public SplashView(Context c, Runnable onFinish) {
        super(c);
        this.onFinish = onFinish;
        sky = new HeavenlyView(c);
        addView(sky, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // Allow tapping anywhere on screen to transition to Home
        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { finish(); }
        });

        // Top wordmark
        TextView word = new TextView(c);
        word.setText("SANCTA MARIA");
        word.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        word.setTextColor(0xFFE8C96A);
        word.setLetterSpacing(0.35f);
        word.setTypeface(Ui.sansMedium());
        LayoutParams wlp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        wlp.gravity = Gravity.TOP | Gravity.START;
        wlp.leftMargin = Ui.dp(c, 24);
        wlp.topMargin = Ui.dp(c, 22);
        addView(word, wlp);

        // Skip button
        TextView skip = new TextView(c);
        skip.setText("Skip");
        skip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        skip.setTextColor(0xFFF6EEDC);
        skip.setPadding(Ui.dp(c, 14), Ui.dp(c, 8), Ui.dp(c, 14), Ui.dp(c, 8));
        skip.setBackground(Ui.stroke(c, 0x33081424, 0x66E8C96A, 18));
        LayoutParams slp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        slp.gravity = Gravity.TOP | Gravity.END;
        slp.rightMargin = Ui.dp(c, 18);
        slp.topMargin = Ui.dp(c, 16);
        skip.setAlpha(0f);
        skip.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { finish(); }
        });
        addView(skip, slp);

        // Bottom Dedication banner
        LinearLayout bottom = Ui.column(c);
        bottom.setGravity(Gravity.CENTER_HORIZONTAL);

        ImageView cross = new ImageView(c);
        cross.setImageResource(R.drawable.ic_cross);
        cross.setColorFilter(0xFFE8C96A);
        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(Ui.dp(c, 18), Ui.dp(c, 18));
        clp.bottomMargin = Ui.dp(c, 8);
        bottom.addView(cross, clp);

        TextView title = new TextView(c);
        title.setText("Sancta Maria");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        title.setTextColor(0xFFF3D98B);
        title.setTypeface(Typeface.create("serif", Typeface.BOLD));
        title.setGravity(Gravity.CENTER);
        title.setShadowLayer(Ui.dp(c, 18), 0, 0, 0x99E8C96A);
        bottom.addView(title);

        View rule = new View(c);
        rule.setBackgroundColor(0x88E8C96A);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(Ui.dp(c, 50), Ui.dp(c, 1));
        rlp.topMargin = Ui.dp(c, 8);
        rlp.bottomMargin = Ui.dp(c, 8);
        bottom.addView(rule, rlp);

        TextView from = new TextView(c);
        from.setText("A gift to Mama Mary from Joe Initiatives");
        from.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        from.setTextColor(0xFFE8DFC9);
        from.setLetterSpacing(0.08f);
        from.setTypeface(Ui.sans());
        from.setGravity(Gravity.CENTER);
        bottom.addView(from);

        LayoutParams blp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        blp.gravity = Gravity.BOTTOM;
        blp.bottomMargin = Ui.dp(c, 44);
        blp.leftMargin = Ui.dp(c, 24);
        blp.rightMargin = Ui.dp(c, 24);
        addView(bottom, blp);

        // Golden progress bar at very bottom
        View progress = new View(c);
        progress.setBackgroundColor(0xFFE8C96A);
        LayoutParams plp = new LayoutParams(LayoutParams.MATCH_PARENT, Ui.dp(c, 2));
        plp.gravity = Gravity.BOTTOM;
        progress.setPivotX(0f);
        progress.setScaleX(0f);
        addView(progress, plp);

        // Entrances and auto-timer
        bottom.setAlpha(0f);
        bottom.setTranslationY(Ui.dp(c, 18));
        bottom.animate().alpha(1f).translationY(0f).setStartDelay(400).setDuration(1000).start();
        skip.animate().alpha(1f).setStartDelay(1000).setDuration(400).start();
        progress.animate().scaleX(1f).setDuration(3600).start();

        handler.postDelayed(new Runnable() {
            @Override public void run() { finish(); }
        }, 3800);
    }

    public void start() {
        sky.start();
    }

    private void finish() {
        if (done) return;
        done = true;
        handler.removeCallbacksAndMessages(null);
        sky.stop();
        onFinish.run();
    }

    @Override
    protected void onDetachedFromWindow() {
        sky.stop();
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }
}
