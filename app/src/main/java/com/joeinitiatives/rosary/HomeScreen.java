package com.joeinitiatives.rosary;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

/** Premium devotional dashboard. */
public class HomeScreen extends BaseScreen {

    public HomeScreen(MainActivity a) {
        super(a, "app_name", false);
        addAction(Ui.icon(a, R.drawable.ic_settings, 22, Ui.BLUE, new OnClickListener() {
            @Override public void onClick(View v) { act.push(new SettingsScreen(act)); }
        }));
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        HeroView hero = new HeroView(act, R.drawable.mary_main, "", "", L.t(act, "welcome_sub"));
        LinearLayout.LayoutParams hlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(act, 280));
        hlp.bottomMargin = Ui.dp(act, 16);
        col.addView(hero, hlp);

        LinearLayout today = Ui.column(act);
        today.setPadding(Ui.dp(act, 20), Ui.dp(act, 18), Ui.dp(act, 20), Ui.dp(act, 18));
        today.setBackground(Ui.gradient(act, 0xFF22467D, 0xFF142E57, 22));
        today.setElevation(Ui.dp(act, 3));

        int set = Data.todaySet();
        TextView label = Ui.label(act, L.t(act, "todays_mystery"), Ui.GOLD_LIGHT);
        label.setLetterSpacing(0.18f);
        today.addView(label);

        TextView setName = Ui.text(act, L.pick(act, Data.SET_EN[set], Data.SET_ML[set]), 22, Ui.WHITE, true);
        setName.setTypeface(Ui.serifBold());
        LinearLayout.LayoutParams snlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        snlp.topMargin = Ui.dp(act, 6);
        today.addView(setName, snlp);

        TextView day = Ui.text(act, L.pick(act, Data.DAYS_EN[set], Data.DAYS_ML[set]), 11.5f, 0xFFB9CCE8, false);
        LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dlp.topMargin = Ui.dp(act, 3);
        dlp.bottomMargin = Ui.dp(act, 12);
        today.addView(day, dlp);

        Data.Mystery first = Data.mystery(set, 1);
        if (first != null) {
            TextView m = Ui.text(act, L.pick(act, first.titleEn, first.titleMl), 14, 0xFFE8DFC9, true);
            today.addView(m);
        }

        boolean active = Prefs.rosaryActive(act);
        TextView btn = Ui.primaryButton(act, L.t(act, active ? "continue_rosary" : "start_rosary"), new OnClickListener() {
            @Override public void onClick(View v) {
                if (!Prefs.rosaryActive(act)) {
                    Prefs.setRosarySet(act, Data.todaySet());
                    Prefs.setRosaryStep(act, 0);
                    Prefs.setRosaryActive(act, true);
                    Prefs.setRosaryStart(act, System.currentTimeMillis());
                }
                act.showTab(1);
            }
        });
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.topMargin = Ui.dp(act, 16);
        today.addView(btn, blp);
        col.addView(today);

        if (active) {
            LinearLayout cont = Ui.row(act);
            cont.setPadding(Ui.dp(act, 14), Ui.dp(act, 10), Ui.dp(act, 14), Ui.dp(act, 10));
            cont.setBackground(Ui.bg(act, Ui.GOLD_PALE, 14));
            ImageView icon = Ui.icon(act, R.drawable.ic_rosary, 16, Ui.GOLD, null);
            cont.addView(icon);
            TextView t = Ui.text(act, L.t(act, "continue_where") + " · " + new RosarySession(Prefs.rosarySet(act), Prefs.rosaryStep(act)).percent() + "%", 12, Ui.BLUE, false);
            t.setPadding(Ui.dp(act, 8), 0, 0, 0);
            cont.addView(t);
            LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            clp.topMargin = Ui.dp(act, 8);
            col.addView(cont, clp);
            cont.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) { act.showTab(1); }
            });
        }

        col.addView(sectionLabel(L.t(act, "daily_verse")));
        Data.Verse verse = Data.todayVerse();
        if (verse != null) {
            LinearLayout vc = Ui.card(act, 0xFFFDFAF2, 18);
            vc.setBackground(Ui.stroke(act, 0xFFFDFAF2, 0x44C9A227, 18));
            TextView v = Ui.text(act, "“" + L.pick(act, verse.verseEn, verse.verseMl) + "”", 14.5f, Ui.INK, true);
            v.setLineSpacing(Ui.dp(act, 5), 1.15f);
            vc.addView(v);
            TextView ref = Ui.text(act, "— " + L.pick(act, verse.refEn, verse.refMl), 11.5f, Ui.GOLD, false);
            LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.topMargin = Ui.dp(act, 8);
            vc.addView(ref, rlp);
            vc.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) { act.push(new DailyScreen(act)); }
            });
            Ui.press(vc);
            col.addView(vc);
        }

        col.addView(sectionLabel(L.t(act, "more")));

        LinearLayout row1 = Ui.row(act);
        row1.addView(quickCard(R.drawable.ic_calendar, L.t(act, "daily_prayer"), L.t(act, "today_rosary"), new Runnable() {
            @Override public void run() { act.push(new DailyScreen(act)); }
        }), weightCard(0, 0));
        row1.addView(quickCard(R.drawable.ic_mary, L.t(act, "marian_devotions"), "10", new Runnable() {
            @Override public void run() { act.showTab(3); }
        }), weightCard(8, 0));
        col.addView(row1);

        LinearLayout row2 = Ui.row(act);
        row2.addView(quickCard(R.drawable.ic_star, L.t(act, "kreupasanam"), "", new Runnable() {
            @Override public void run() { act.push(new KreupasanamScreen(act)); }
        }), weightCard(0, 8));
        row2.addView(quickCard(R.drawable.ic_cross, L.t(act, "st_joseph"), "", new Runnable() {
            @Override public void run() { act.push(new JosephScreen(act)); }
        }), weightCard(8, 8));
        col.addView(row2);

        LinearLayout row3 = Ui.row(act);
        row3.addView(quickCard(R.drawable.ic_prayers, L.t(act, "prayers_az"), "Library", new Runnable() {
            @Override public void run() { act.showTab(4); }
        }), weightCard(0, 0));
        row3.addView(quickCard(R.drawable.ic_history, L.t(act, "history"), "", new Runnable() {
            @Override public void run() { act.push(new HistoryScreen(act)); }
        }), weightCard(8, 0));
        col.addView(row3);

        HistoryDb db = new HistoryDb(act);
        int streak = db.streak();
        int rosaries = db.totalRosaries();
        db.close();
        LinearLayout streakCard = Ui.row(act);
        streakCard.setPadding(Ui.dp(act, 16), Ui.dp(act, 14), Ui.dp(act, 16), Ui.dp(act, 14));
        streakCard.setBackground(Ui.gradient(act, 0xFFFFF9EC, 0xFFF5EBCF, 18));
        ImageView star = Ui.icon(act, R.drawable.ic_star, 22, Ui.GOLD, null);
        streakCard.addView(star);
        LinearLayout mid = Ui.column(act);
        TextView st = Ui.text(act, streak + " " + L.t(act, "days"), 15, Ui.BLUE, true);
        st.setTypeface(Ui.serifBold());
        TextView hint = Ui.text(act, L.t(act, "streak_hint"), 11.5f, Ui.MUTED, false);
        mid.addView(st);
        mid.addView(hint);
        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mlp.leftMargin = Ui.dp(act, 12);
        streakCard.addView(mid, mlp);
        TextView rc = Ui.text(act, rosaries + "", 20, Ui.BLUE, true);
        rc.setTypeface(Ui.serifBold());
        rc.setGravity(Gravity.CENTER);
        streakCard.addView(rc);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 8);
        col.addView(streakCard, slp);

        TextView footer1 = Ui.text(act, "SANCTA MARIA", 15, Ui.GOLD, true);
        footer1.setTypeface(Ui.serifBold());
        footer1.setLetterSpacing(0.12f);
        footer1.setGravity(Gravity.CENTER);
        TextView footer2 = Ui.text(act, "A companion for quiet prayer", 10.5f, Ui.MUTED, false);
        footer2.setLetterSpacing(0.08f);
        footer2.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams f1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        f1.topMargin = Ui.dp(act, 26);
        col.addView(footer1, f1);
        LinearLayout.LayoutParams f2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        f2.topMargin = Ui.dp(act, 3);
        f2.bottomMargin = Ui.dp(act, 10);
        col.addView(footer2, f2);
    }

    private LinearLayout.LayoutParams weightCard(int left, int top) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        lp.leftMargin = Ui.dp(act, left);
        lp.topMargin = Ui.dp(act, top);
        return lp;
    }

    private View quickCard(int iconRes, String title, String meta, final Runnable action) {
        LinearLayout card = Ui.column(act);
        card.setPadding(Ui.dp(act, 14), Ui.dp(act, 15), Ui.dp(act, 14), Ui.dp(act, 14));
        card.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x33C9A227, 18));
        card.setMinimumHeight(Ui.dp(act, 106));
        card.setElevation(Ui.dp(act, 1));

        LinearLayout top = Ui.row(act);
        LinearLayout circle = Ui.row(act);
        circle.setGravity(Gravity.CENTER);
        circle.setBackground(Ui.bg(act, Ui.GOLD_PALE, 20));
        circle.addView(Ui.icon(act, iconRes, 18, Ui.BLUE, null));
        circle.setLayoutParams(new LinearLayout.LayoutParams(Ui.dp(act, 38), Ui.dp(act, 38)));
        top.addView(circle);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(0, 1, 1f);
        top.addView(new View(act), slp);
        if (!meta.isEmpty()) {
            TextView m = Ui.text(act, meta, 10, Ui.GOLD, false);
            top.addView(m);
        }
        card.addView(top);

        TextView t = Ui.text(act, title, 13.5f, Ui.BLUE, false);
        t.setTypeface(Ui.sansMedium());
        t.setMaxLines(2);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 10);
        card.addView(t, tlp);

        card.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { action.run(); }
        });
        Ui.press(card);
        return card;
    }
}
