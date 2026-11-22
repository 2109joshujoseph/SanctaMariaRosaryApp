package com.joeinitiatives.rosary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Shared visual language for the whole application. */
public final class Ui {
    public static final int IVORY = 0xFFFBF7EE;
    public static final int IVORY_DEEP = 0xFFF4EDDD;
    public static final int BLUE = 0xFF1B3A6B;
    public static final int BLUE_MID = 0xFF2C5C9E;
    public static final int BLUE_LIGHT = 0xFFDCE7F6;
    public static final int BLUE_PALE = 0xFFEEF4FC;
    public static final int GOLD = 0xFFC9A227;
    public static final int GOLD_LIGHT = 0xFFE8C96A;
    public static final int GOLD_PALE = 0xFFF7EBC8;
    public static final int INK = 0xFF22304A;
    public static final int MUTED = 0xFF6E7A8F;
    public static final int LINE = 0xFFE5DAC2;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int ROSE = 0xFFB25C6A;
    public static final int GREEN = 0xFF4E7C66;

    private static Typeface serif, serifBold, sans, sansMedium;

    private Ui() {}

    public static Typeface serif() {
        if (serif == null) serif = Typeface.create("serif", Typeface.NORMAL);
        return serif;
    }

    public static Typeface serifBold() {
        if (serifBold == null) serifBold = Typeface.create("serif", Typeface.BOLD);
        return serifBold;
    }

    public static Typeface sans() {
        if (sans == null) sans = Typeface.create("sans-serif", Typeface.NORMAL);
        return sans;
    }

    public static Typeface sansMedium() {
        if (sansMedium == null) sansMedium = Typeface.create("sans-serif-medium", Typeface.NORMAL);
        return sansMedium;
    }

    public static int dp(Context c, float v) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, c.getResources().getDisplayMetrics()));
    }

    public static int screenWidth(Context c) {
        return c.getResources().getDisplayMetrics().widthPixels;
    }

    public static GradientDrawable bg(Context c, int color, float radiusDp) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(c, radiusDp));
        return d;
    }

    public static GradientDrawable stroke(Context c, int fill, int strokeColor, float radiusDp) {
        GradientDrawable d = bg(c, fill, radiusDp);
        d.setStroke(dp(c, 1), strokeColor);
        return d;
    }

    public static GradientDrawable gradient(Context c, int top, int bottom, float radiusDp) {
        GradientDrawable d = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{top, bottom});
        d.setCornerRadius(dp(c, radiusDp));
        return d;
    }

    public static GradientDrawable gradient(Context c, int[] colors, GradientDrawable.Orientation o, float radiusDp) {
        GradientDrawable d = new GradientDrawable(o, colors);
        d.setCornerRadius(dp(c, radiusDp));
        return d;
    }

    public static GradientDrawable screenBg(Context c) {
        return new GradientDrawable(GradientDrawable.Orientation.TL_BR,
                new int[]{0xFFFFFDF8, IVORY, 0xFFF0F5FC});
    }

    public static TextView text(Context c, CharSequence s, float sp, int color, boolean display) {
        TextView t = new TextView(c);
        t.setText(s);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        t.setTextColor(color);
        t.setTypeface(display ? serif() : sans());
        t.setLineSpacing(dp(c, 4), 1.12f);
        t.setIncludeFontPadding(false);
        return t;
    }

    public static TextView title(Context c, CharSequence s, int color) {
        TextView t = text(c, s, 26, color, true);
        t.setLineSpacing(dp(c, 3), 1.05f);
        return t;
    }

    public static TextView h2(Context c, CharSequence s, int color) {
        return text(c, s, 20, color, true);
    }

    public static TextView body(Context c, CharSequence s, int color) {
        TextView t = text(c, s, 15, color, false);
        t.setLineSpacing(dp(c, 6), 1.18f);
        return t;
    }

    public static TextView prayerBody(Context c, CharSequence s, int color) {
        TextView t = text(c, s, 17, color, false);
        t.setLineSpacing(dp(c, 8), 1.22f);
        return t;
    }

    public static TextView label(Context c, CharSequence s, int color) {
        TextView t = text(c, s, 11, color, false);
        t.setTypeface(sansMedium());
        t.setAllCaps(false);
        t.setLetterSpacing(0.12f);
        return t;
    }

    public static LinearLayout column(Context c) {
        LinearLayout l = new LinearLayout(c);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    public static LinearLayout row(Context c) {
        LinearLayout l = new LinearLayout(c);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER_VERTICAL);
        return l;
    }

    public static LinearLayout card(Context c, int bgColor, float radiusDp) {
        LinearLayout l = column(c);
        l.setBackground(bg(c, bgColor, radiusDp));
        l.setPadding(dp(c, 18), dp(c, 18), dp(c, 18), dp(c, 18));
        l.setElevation(dp(c, 2));
        return l;
    }

    public static View spacer(Context c, int h) {
        View v = new View(c);
        v.setLayoutParams(new LinearLayout.LayoutParams(1, dp(c, h)));
        return v;
    }

    public static View divider(Context c) {
        View v = new View(c);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(c, 1));
        lp.topMargin = dp(c, 12);
        lp.bottomMargin = dp(c, 12);
        v.setLayoutParams(lp);
        v.setBackgroundColor(LINE);
        return v;
    }

    public static TextView primaryButton(Context c, CharSequence s, View.OnClickListener l) {
        TextView t = new TextView(c);
        t.setText(s);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        t.setTextColor(BLUE);
        t.setTypeface(sansMedium());
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(c, 18), dp(c, 15), dp(c, 18), dp(c, 15));
        GradientDrawable g = gradient(c, GOLD_LIGHT, GOLD, 14);
        t.setBackground(g);
        t.setClickable(true);
        t.setOnClickListener(l);
        press(t);
        return t;
    }

    public static TextView secondaryButton(Context c, CharSequence s, View.OnClickListener l) {
        TextView t = new TextView(c);
        t.setText(s);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        t.setTextColor(BLUE);
        t.setTypeface(sansMedium());
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(c, 16), dp(c, 13), dp(c, 16), dp(c, 13));
        GradientDrawable g = stroke(c, 0x00FFFFFF, 0x66C9A227, 14);
        t.setBackground(g);
        t.setClickable(true);
        t.setOnClickListener(l);
        press(t);
        return t;
    }

    public static TextView chip(Context c, CharSequence s, boolean selected, View.OnClickListener l) {
        TextView t = new TextView(c);
        t.setText(s);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.5f);
        t.setTextColor(selected ? WHITE : BLUE);
        t.setTypeface(sansMedium());
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(c, 14), dp(c, 9), dp(c, 14), dp(c, 9));
        t.setBackground(selected ? bg(c, BLUE, 20) : stroke(c, 0x88FFFFFF, 0x55C9A227, 20));
        t.setClickable(true);
        t.setOnClickListener(l);
        press(t);
        return t;
    }

    public static ImageView icon(Context c, int res, int sizeDp, int tint, View.OnClickListener l) {
        ImageView iv = new ImageView(c);
        iv.setImageResource(res);
        iv.setColorFilter(tint);
        int s = dp(c, sizeDp);
        iv.setLayoutParams(new LinearLayout.LayoutParams(s, s));
        if (l != null) {
            iv.setOnClickListener(l);
            iv.setClickable(true);
            iv.setBackground(null);
            iv.setPadding(dp(c, 4), dp(c, 4), dp(c, 4), dp(c, 4));
        }
        return iv;
    }

    public static int drawableId(Context c, String name) {
        if (name == null || name.isEmpty()) return 0;
        return c.getResources().getIdentifier(name, "drawable", c.getPackageName());
    }

    public static RoundedImage image(Context c, int res, int heightDp, float radiusDp) {
        RoundedImage iv = new RoundedImage(c, dp(c, radiusDp));
        if (res != 0) iv.setImageResource(res);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(c, heightDp)));
        return iv;
    }

    public static RoundedImage imageName(Context c, String name, int heightDp, float radiusDp) {
        return image(c, drawableId(c, name), heightDp, radiusDp);
    }

    public static void press(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    view.animate().scaleX(0.975f).scaleY(0.975f).setDuration(90).start();
                } else if (e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.animate().scaleX(1f).scaleY(1f).setDuration(140).start();
                }
                return false;
            }
        });
    }

    public static void margin(View v, int l, int t, int r, int b) {
        ViewGroup.LayoutParams p = v.getLayoutParams();
        if (p instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) p;
            lp.setMargins(dp(v.getContext(), l), dp(v.getContext(), t), dp(v.getContext(), r), dp(v.getContext(), b));
            v.setLayoutParams(lp);
        }
    }

    public static LinearLayout.LayoutParams lp(int w, int h) {
        return new LinearLayout.LayoutParams(w, h);
    }

    public static LinearLayout.LayoutParams lpWeight(float weight) {
        return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
    }

    public static void fadeIn(View v) {
        v.setAlpha(0f);
        v.animate().alpha(1f).setDuration(320).start();
    }

    public static TextView switchLabel(Context c, String s) {
        TextView t = text(c, s, 15, INK, false);
        t.setTypeface(sansMedium());
        return t;
    }

    public static String ellipsize(String s, int max) {
        return TextUtils.isEmpty(s) ? "" : (s.length() <= max ? s : s.substring(0, max - 1) + "…");
    }

    public static int blend(int a, int b, float t) {
        int ar = Color.red(a), ag = Color.green(a), ab = Color.blue(a);
        int br = Color.red(b), bg = Color.green(b), bb = Color.blue(b);
        return Color.rgb((int) (ar + (br - ar) * t), (int) (ag + (bg - ag) * t), (int) (ab + (bb - ab) * t));
    }
}
