package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

/** Gentle angel fly-past shown when a decade is completed. */
public class AngelOverlay extends FrameLayout {
    private final Runnable onDone;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private boolean closing;

    public static void show(MainActivity act, String title, String subtitle, Runnable onDone) {
        AngelOverlay o = new AngelOverlay(act, title, subtitle, Prefs.anim(act), onDone);
        act.root.addView(o, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        o.animate().alpha(1f).setDuration(260).start();
    }

    private AngelOverlay(Context c, String title, String subtitle, boolean animate, Runnable onDone) {
        super(c);
        this.onDone = onDone;
        setAlpha(0f);
        setClickable(true);
        setBackgroundColor(0x66081424);

        if (animate) {
            addView(new FlightView(c), new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        LinearLayout card = Ui.column(c);
        card.setGravity(Gravity.CENTER_HORIZONTAL);
        card.setPadding(Ui.dp(c, 28), Ui.dp(c, 26), Ui.dp(c, 28), Ui.dp(c, 26));
        card.setBackground(Ui.gradient(c, 0xF21B3A6B, 0xF20E2347, 24));
        card.setElevation(Ui.dp(c, 12));

        View star = new View(c);
        star.setBackgroundColor(0xFFE8C96A);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(Ui.dp(c, 30), Ui.dp(c, 2));
        slp.bottomMargin = Ui.dp(c, 14);
        card.addView(star, slp);

        TextView t = Ui.text(c, title, 25, 0xFFF3D98B, true);
        t.setTypeface(Ui.serifBold());
        t.setGravity(Gravity.CENTER);
        t.setShadowLayer(Ui.dp(c, 14), 0, 0, 0x66E8C96A);
        card.addView(t);

        TextView s = Ui.text(c, subtitle, 12.5f, 0xFFDCE7F6, false);
        s.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams s2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        s2.topMargin = Ui.dp(c, 8);
        card.addView(s, s2);

        FrameLayout.LayoutParams clp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        clp.gravity = Gravity.CENTER;
        clp.leftMargin = Ui.dp(c, 28);
        clp.rightMargin = Ui.dp(c, 28);
        addView(card, clp);
        card.setScaleX(0.94f);
        card.setScaleY(0.94f);
        card.animate().scaleX(1f).scaleY(1f).setDuration(340).start();

        handler.postDelayed(new Runnable() {
            @Override public void run() { dismiss(); }
        }, 3300);
    }

    public void dismiss() {
        if (closing) return;
        closing = true;
        handler.removeCallbacksAndMessages(null);
        animate().alpha(0f).setDuration(380).withEndAction(new Runnable() {
            @Override public void run() {
                if (getParent() != null) ((android.view.ViewGroup) getParent()).removeView(AngelOverlay.this);
                if (onDone != null) onDone.run();
            }
        }).start();
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    /** Golden angels crossing a dark, translucent sky. */
    private static class FlightView extends View {
        private final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final long start = System.currentTimeMillis();
        private final float[] px = new float[30];
        private final float[] py = new float[30];
        private final float[] pr = new float[30];
        private final Random r = new Random(3);

        FlightView(Context c) {
            super(c);
            for (int i = 0; i < px.length; i++) {
                px[i] = r.nextFloat();
                py[i] = r.nextFloat();
                pr[i] = 0.5f + r.nextFloat() * 1.4f;
            }
        }

        @Override
        protected void onDraw(Canvas c) {
            float w = getWidth(), h = getHeight();
            if (w == 0) return;
            float t = (System.currentTimeMillis() - start) / 1000f;

            p.setShader(new RadialGradient(w / 2, h / 2, w * 0.75f,
                    new int[]{0x33FFF0C0, 0x00000000}, null, Shader.TileMode.CLAMP));
            c.drawCircle(w / 2, h / 2, w * 0.75f, p);
            p.setShader(null);

            for (int i = 0; i < px.length; i++) {
                float a = 0.15f + 0.4f * (0.5f + 0.5f * (float) Math.sin(t * 2.2 + i));
                p.setColor((((int) (a * 255)) << 24) | 0x00E8C96A);
                float x = (px[i] * w + t * (6 + i % 5) * 4) % w;
                c.drawCircle(x, py[i] * h, Ui.dp(getContext(), pr[i]), p);
            }

            for (int i = 0; i < 3; i++) {
                float speed = 0.30f + i * 0.055f;
                float x = ((t * speed + i * 0.34f) % 1.25f) * w - w * 0.1f;
                float y = h * (0.22f + i * 0.16f) + (float) Math.sin(t * 1.7f + i) * h * 0.02f;
                float s = w * (0.05f - i * 0.008f);
                float flap = 0.6f + 0.4f * (float) Math.sin(t * 5f + i * 2f);
                int alpha = (int) (200 * Math.min(1f, Math.min(x / (w * 0.2f), (w - x) / (w * 0.2f))));
                alpha = Math.max(0, alpha);
                p.setColor((alpha << 24) | 0x00F3D98B);
                SacredArt.angel(c, p, x, y, s, (alpha << 24) | 0x00F3D98B, flap);
            }
            postInvalidateOnAnimation();
        }
    }
}
