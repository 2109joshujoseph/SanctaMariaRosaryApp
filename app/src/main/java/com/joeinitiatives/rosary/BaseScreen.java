package com.joeinitiatives.rosary;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/** Common scaffold: translucent header, language switch, scrollable body. */
public abstract class BaseScreen extends FrameLayout {
    protected final MainActivity act;
    protected final LinearLayout content;
    protected final LinearLayout header;
    private final String titleKey;
    private final boolean back;
    private final LinearLayout rightActions;
    private TextView titleView;
    private boolean built;

    public BaseScreen(MainActivity a, String titleKey, boolean back) {
        super(a);
        this.act = a;
        this.titleKey = titleKey;
        this.back = back;
        setBackground(Ui.screenBg(a));

        header = Ui.row(a);
        header.setPadding(Ui.dp(a, 10), Ui.dp(a, 6), Ui.dp(a, 10), Ui.dp(a, 6));
        header.setBackgroundColor(0xCCFBF7EE);

        FrameLayout.LayoutParams hlp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(a, 58));
        addView(header, hlp);

        View backBtn = Ui.icon(a, R.drawable.ic_back, 24, Ui.BLUE, new OnClickListener() {
            @Override public void onClick(View v) { act.pop(); }
        });
        LinearLayout backWrap = Ui.row(a);
        backWrap.setLayoutParams(new LinearLayout.LayoutParams(Ui.dp(a, 42), Ui.dp(a, 42)));
        backWrap.setGravity(Gravity.CENTER);
        if (back) {
            backWrap.addView(backBtn);
            backWrap.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) { act.pop(); }
            });
        }
        header.addView(backWrap);

        titleView = Ui.label(a, L.t(a, titleKey), Ui.BLUE);
        titleView.setTextSize(12.5f);
        titleView.setTypeface(Ui.sansMedium());
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        tlp.leftMargin = Ui.dp(a, 6);
        titleView.setLayoutParams(tlp);
        header.addView(titleView);

        rightActions = Ui.row(a);
        header.addView(rightActions);

        View hair = new View(a);
        hair.setBackgroundColor(0x33C9A227);
        FrameLayout.LayoutParams hairLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(a, 1));
        hairLp.gravity = Gravity.TOP;
        hairLp.topMargin = Ui.dp(a, 58);
        addView(hair, hairLp);

        content = new LinearLayout(a);
        content.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams clp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        clp.topMargin = Ui.dp(a, 59);
        addView(content, clp);
    }

    public void create() {
        if (built) return;
        built = true;
        buildContent();
    }

    public void rebuild() {
        content.removeAllViews();
        titleView.setText(L.t(act, titleKey));
        buildContent();
    }

    protected abstract void buildContent();

    public String titleKey() { return titleKey; }

    protected void addAction(View v) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = Ui.dp(act, 4);
        rightActions.addView(v, lp);
    }

    /** A vertically scrolling padded column that fills the body. */
    protected LinearLayout scrollBody(int paddingDp) {
        ScrollView sv = new ScrollView(act);
        sv.setFillViewport(true);
        sv.setClipToPadding(false);
        sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        LinearLayout col = Ui.column(act);
        int p = Ui.dp(act, paddingDp);
        col.setPadding(p, Ui.dp(act, 12), p, Ui.dp(act, 28));
        sv.addView(col, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content.addView(sv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        return col;
    }

    protected void addFull(View v) {
        content.addView(v, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    protected TextView sectionLabel(String text) {
        TextView t = Ui.label(act, text, Ui.GOLD);
        t.setTextSize(10.5f);
        t.setLetterSpacing(0.14f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = Ui.dp(act, 20);
        lp.bottomMargin = Ui.dp(act, 8);
        t.setLayoutParams(lp);
        return t;
    }

    protected TextView sectionTitle(String text) {
        TextView t = Ui.h2(act, text, Ui.BLUE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = Ui.dp(act, 10);
        t.setLayoutParams(lp);
        return t;
    }

    protected View goldRule() {
        View v = new View(act);
        v.setBackgroundColor(0x66C9A227);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Ui.dp(act, 34), Ui.dp(act, 2));
        lp.bottomMargin = Ui.dp(act, 14);
        v.setLayoutParams(lp);
        return v;
    }

    protected void toast(String s) {
        android.widget.Toast.makeText(act, s, android.widget.Toast.LENGTH_SHORT).show();
    }
}
