package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Reusable devotional cards and rows shared by the content screens. */
public final class Widgets {
    private Widgets() {}

    public static LinearLayout card(Context c) {
        LinearLayout l = Ui.column(c);
        l.setPadding(Ui.dp(c, 18), Ui.dp(c, 18), Ui.dp(c, 18), Ui.dp(c, 18));
        l.setBackground(Ui.stroke(c, 0xFFFDFAF2, 0x33C9A227, 20));
        l.setElevation(Ui.dp(c, 1));
        return l;
    }

    public static LinearLayout goldCard(Context c) {
        LinearLayout l = Ui.column(c);
        l.setPadding(Ui.dp(c, 18), Ui.dp(c, 17), Ui.dp(c, 18), Ui.dp(c, 17));
        l.setBackground(Ui.gradient(c, 0xFFFFF9EC, 0xFFF5EBCF, 20));
        l.setElevation(Ui.dp(c, 1));
        return l;
    }

    public static LinearLayout blueCard(Context c) {
        LinearLayout l = Ui.column(c);
        l.setPadding(Ui.dp(c, 20), Ui.dp(c, 20), Ui.dp(c, 20), Ui.dp(c, 20));
        l.setBackground(Ui.gradient(c, 0xFF22467D, 0xFF142E57, 22));
        l.setElevation(Ui.dp(c, 3));
        return l;
    }

    public static TextView overline(Context c, String s) {
        TextView t = Ui.label(c, s, Ui.GOLD);
        t.setLetterSpacing(0.16f);
        return t;
    }

    public static TextView overlineLight(Context c, String s) {
        TextView t = Ui.label(c, s, Ui.GOLD_LIGHT);
        t.setLetterSpacing(0.18f);
        return t;
    }

    public static TextView heading(Context c, String s) {
        TextView t = Ui.text(c, s, 21, Ui.BLUE, true);
        t.setTypeface(Ui.serifBold());
        t.setLineSpacing(Ui.dp(c, 3), 1.08f);
        return t;
    }

    public static TextView headingLight(Context c, String s) {
        TextView t = Ui.text(c, s, 22, Ui.WHITE, true);
        t.setTypeface(Ui.serifBold());
        t.setLineSpacing(Ui.dp(c, 3), 1.08f);
        return t;
    }

    public static TextView body(Context c, String s) {
        return Ui.body(c, s, Ui.INK);
    }

    public static TextView caption(Context c, String s) {
        return Ui.text(c, s, 11.5f, Ui.MUTED, false);
    }

    public static TextView pill(Context c, String s, int fg, int bg) {
        TextView t = Ui.text(c, s, 10, fg, false);
        t.setTypeface(Ui.sansMedium());
        t.setLetterSpacing(0.06f);
        t.setPadding(Ui.dp(c, 10), Ui.dp(c, 5), Ui.dp(c, 10), Ui.dp(c, 5));
        t.setBackground(Ui.bg(c, bg, 12));
        return t;
    }

    public static View rule(Context c) {
        View v = new View(c);
        v.setBackgroundColor(0x44C9A227);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(c, 1)));
        return v;
    }

    public static void gap(LinearLayout parent, int dp) {
        parent.addView(Ui.spacer(parent.getContext(), dp));
    }

    public static TextView section(Context c, String s) {
        TextView t = Ui.label(c, s, Ui.GOLD);
        t.setTextSize(10.5f);
        t.setLetterSpacing(0.16f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = Ui.dp(c, 22);
        lp.bottomMargin = Ui.dp(c, 8);
        t.setLayoutParams(lp);
        return t;
    }

    public static LinearLayout actionRow(Context c, int iconRes, String title, String subtitle, View.OnClickListener l) {
        LinearLayout row = Ui.row(c);
        row.setPadding(Ui.dp(c, 16), Ui.dp(c, 14), Ui.dp(c, 14), Ui.dp(c, 14));
        row.setBackground(Ui.stroke(c, 0xFFFDFAF3, 0x33C9A227, 18));
        LinearLayout circle = Ui.row(c);
        circle.setGravity(Gravity.CENTER);
        circle.setBackground(Ui.bg(c, Ui.GOLD_PALE, 20));
        circle.addView(Ui.icon(c, iconRes, 17, Ui.BLUE, null));
        circle.setLayoutParams(new LinearLayout.LayoutParams(Ui.dp(c, 38), Ui.dp(c, 38)));
        row.addView(circle);
        LinearLayout mid = Ui.column(c);
        TextView t = Ui.text(c, title, 14, Ui.BLUE, false);
        t.setTypeface(Ui.sansMedium());
        mid.addView(t);
        if (subtitle != null && !subtitle.isEmpty()) {
            TextView s = Ui.text(c, subtitle, 11, Ui.MUTED, false);
            LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            slp.topMargin = Ui.dp(c, 2);
            mid.addView(s, slp);
        }
        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mlp.leftMargin = Ui.dp(c, 12);
        row.addView(mid, mlp);
        row.addView(Ui.icon(c, R.drawable.ic_chevron, 17, 0xFF9AA5B5, null));
        row.setOnClickListener(l);
        Ui.press(row);
        return row;
    }

    public static LinearLayout intentionBox(Context c, String value, String hint, String note) {
        LinearLayout box = goldCard(c);
        TextView h = overline(c, hint);
        box.addView(h);
        EditText et = new EditText(c);
        et.setText(value == null ? "" : value);
        et.setHint(note);
        et.setHintTextColor(0xFF9AA5B5);
        et.setTextColor(Ui.INK);
        et.setTextSize(14);
        et.setTypeface(Ui.sans());
        et.setGravity(Gravity.TOP | Gravity.START);
        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        et.setMinLines(3);
        et.setMaxLines(6);
        et.setBackgroundColor(Color.TRANSPARENT);
        et.setPadding(0, Ui.dp(c, 8), 0, Ui.dp(c, 4));
        et.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int d) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int d) {}
            @Override public void afterTextChanged(Editable s) { Prefs.setIntention(c, s.toString()); }
        });
        box.addView(et, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView n = Ui.text(c, note, 10.5f, Ui.MUTED, false);
        n.setPadding(0, Ui.dp(c, 6), 0, 0);
        box.addView(n);
        return box;
    }

    public static LinearLayout imageBanner(Context c, String image, int heightDp, String overline, String title, String subtitle) {
        LinearLayout wrap = Ui.column(c);
        RoundedImage img = Ui.imageName(c, image, heightDp, 20);
        wrap.addView(img);
        if (title != null && !title.isEmpty()) {
            LinearLayout text = Ui.column(c);
            text.setPadding(Ui.dp(c, 18), Ui.dp(c, 14), Ui.dp(c, 18), Ui.dp(c, 16));
            text.setBackground(Ui.stroke(c, 0xFFFDFAF2, 0x33C9A227, 20));
            if (overline != null && !overline.isEmpty()) text.addView(overline(c, overline));
            TextView t = heading(c, title);
            LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tlp.topMargin = Ui.dp(c, 4);
            text.addView(t, tlp);
            if (subtitle != null && !subtitle.isEmpty()) {
                TextView s = Ui.text(c, subtitle, 12, Ui.MUTED, false);
                LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                slp.topMargin = Ui.dp(c, 6);
                text.addView(s, slp);
            }
            wrap.addView(text);
        }
        return wrap;
    }

    public static LinearLayout mysteryRow(Context c, final Data.Mystery m, int number, boolean isMl, View.OnClickListener l) {
        LinearLayout row = Ui.row(c);
        row.setPadding(Ui.dp(c, 12), Ui.dp(c, 10), Ui.dp(c, 12), Ui.dp(c, 10));
        row.setBackground(Ui.stroke(c, 0xFFFDFAF3, 0x2AC9A227, 16));

        RoundedImage img = Ui.imageName(c, m.image, 62, 12);
        LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(Ui.dp(c, 62), Ui.dp(c, 62));
        row.addView(img, ilp);

        LinearLayout mid = Ui.column(c);
        TextView num = Ui.label(c, String.format(java.util.Locale.US, "%02d", number), Ui.GOLD);
        num.setTextSize(9.5f);
        mid.addView(num);
        TextView t = Ui.text(c, L.pick(c, m.titleEn, m.titleMl), 13.5f, Ui.BLUE, true);
        t.setTypeface(Ui.serifBold());
        t.setMaxLines(2);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(c, 2);
        mid.addView(t, tlp);
        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mlp.leftMargin = Ui.dp(c, 10);
        mlp.rightMargin = Ui.dp(c, 6);
        row.addView(mid, mlp);
        row.addView(Ui.icon(c, R.drawable.ic_chevron, 15, 0xFF9AA5B5, null));
        row.setOnClickListener(l);
        Ui.press(row);
        return row;
    }
}
