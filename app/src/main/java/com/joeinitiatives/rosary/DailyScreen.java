package com.joeinitiatives.rosary;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Today's verse, mystery and prayer completion. */
public class DailyScreen extends BaseScreen {
    public DailyScreen(MainActivity a) {
        super(a, "daily_prayer", true);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH));
        if (L.ml(act)) {
            // Simple Malayalam-friendly date fallback (English numerals, Malayalam month labels not required).
            date = today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", new Locale("ml", "IN")));
        }
        TextView dateTv = Ui.label(act, date.toUpperCase(), Ui.GOLD);
        dateTv.setLetterSpacing(0.12f);
        col.addView(dateTv);

        TextView lead = Widgets.heading(act, L.t(act, "daily_verse"));
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.topMargin = Ui.dp(act, 6);
        col.addView(lead, llp);

        Data.Verse v = Data.todayVerse();
        if (v != null) {
            LinearLayout card = Widgets.blueCard(act);
            TextView ref = Ui.label(act, L.pick(act, v.refEn, v.refMl).toUpperCase(), Ui.GOLD_LIGHT);
            ref.setLetterSpacing(0.16f);
            card.addView(ref);
            TextView quote = Ui.text(act, "“" + L.pick(act, v.verseEn, v.verseMl) + "”", 17, Ui.WHITE, true);
            quote.setTypeface(Ui.serif());
            quote.setLineSpacing(Ui.dp(act, 6), 1.18f);
            LinearLayout.LayoutParams qlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            qlp.topMargin = Ui.dp(act, 10);
            card.addView(quote, qlp);
            TextView reflection = Ui.text(act, L.pick(act, v.reflEn, v.reflMl), 12.5f, 0xFFDCE7F6, false);
            reflection.setLineSpacing(Ui.dp(act, 5), 1.16f);
            LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.topMargin = Ui.dp(act, 12);
            card.addView(reflection, rlp);
            col.addView(card);
        }

        col.addView(Widgets.section(act, L.t(act, "today_rosary")));
        int set = Data.todaySet();
        LinearLayout rosaryCard = Widgets.card(act);
        rosaryCard.addView(Widgets.pill(act, L.pick(act, Data.DAYS_EN[set], Data.DAYS_ML[set]), Ui.GOLD, Ui.GOLD_PALE));
        TextView setName = Widgets.heading(act, L.pick(act, Data.SET_EN[set], Data.SET_ML[set]));
        LinearLayout.LayoutParams snlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        snlp.topMargin = Ui.dp(act, 6);
        rosaryCard.addView(setName, snlp);
        Data.Mystery first = Data.mystery(set, 1);
        if (first != null) {
            TextView m = Ui.text(act, L.pick(act, first.titleEn, first.titleMl), 12.5f, Ui.MUTED, false);
            LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mlp.topMargin = Ui.dp(act, 4);
            rosaryCard.addView(m, mlp);
        }
        TextView go = Ui.primaryButton(act, L.t(act, "start_rosary"), new View.OnClickListener() {
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
        LinearLayout.LayoutParams glp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        glp.topMargin = Ui.dp(act, 12);
        rosaryCard.addView(go, glp);
        col.addView(rosaryCard);

        col.addView(Widgets.section(act, L.t(act, "daily_progress")));
        HistoryDb db = new HistoryDb(act);
        int streak = db.streak();
        int rosaries = db.totalRosaries();
        db.close();
        LinearLayout streakCard = Widgets.goldCard(act);
        LinearLayout row = Ui.row(act);
        TextView st = Ui.text(act, streak + " " + L.t(act, "days"), 22, Ui.BLUE, true);
        st.setTypeface(Ui.serifBold());
        row.addView(st);
        LinearLayout mid = Ui.column(act);
        TextView hint = Ui.text(act, L.t(act, "streak_hint"), 11, Ui.MUTED, false);
        mid.addView(hint);
        LinearLayout.LayoutParams mlp2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mlp2.leftMargin = Ui.dp(act, 12);
        row.addView(mid, mlp2);
        TextView rc = Ui.text(act, rosaries + "", 22, Ui.BLUE, true);
        rc.setTypeface(Ui.serifBold());
        row.addView(rc);
        streakCard.addView(row);
        col.addView(streakCard);

        final String dayKey = LocalDate.now().toString();
        final boolean done = Prefs.isDailyDone(act, dayKey);
        TextView mark = done
                ? Ui.secondaryButton(act, L.t(act, "completed_today"), null)
                : Ui.primaryButton(act, L.t(act, "mark_complete"), new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        HistoryDb d = new HistoryDb(act);
                        d.addSession("daily", 0, true);
                        d.markDaily();
                        d.close();
                        Prefs.markDaily(act, dayKey);
                        act.rebuildCurrent();
                        toast(L.t(act, "completed_today"));
                    }
                });
        LinearLayout.LayoutParams mlp3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mlp3.topMargin = Ui.dp(act, 14);
        col.addView(mark, mlp3);

        TextView prayers = Ui.secondaryButton(act, L.t(act, "prayers_az"), new View.OnClickListener() {
            @Override public void onClick(View v) { act.showTab(4); }
        });
        LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plp.topMargin = Ui.dp(act, 8);
        col.addView(prayers, plp);
    }
}
