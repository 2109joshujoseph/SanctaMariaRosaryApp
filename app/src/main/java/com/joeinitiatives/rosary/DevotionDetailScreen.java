package com.joeinitiatives.rosary;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;

/** History, significance and prayer for one Marian devotion. */
public class DevotionDetailScreen extends BaseScreen {
    private final String id;

    public DevotionDetailScreen(MainActivity a, String id) {
        super(a, "devotions", true);
        this.id = id;
    }

    @Override
    protected void buildContent() {
        final Data.Devotion d = Data.devotion(id);
        LinearLayout col = scrollBody(18);
        if (d == null) {
            col.addView(Widgets.body(act, L.t(act, "no_prayers")));
            return;
        }

        col.addView(Ui.imageName(act, d.image, 224, 22));

        LinearLayout head = Ui.column(act);
        head.setPadding(Ui.dp(act, 18), Ui.dp(act, 15), Ui.dp(act, 18), Ui.dp(act, 16));
        head.setBackground(Ui.stroke(act, 0xFFFDFAF2, 0x44C9A227, 22));

        LinearLayout badges = Ui.row(act);
        badges.addView(Widgets.pill(act, L.t(act, d.apparition() ? "approved_apparition" : "marian_title"),
                d.apparition() ? Ui.GREEN : Ui.BLUE_MID,
                d.apparition() ? 0xFFE7F0E9 : Ui.BLUE_LIGHT));
        if (d.feastEn != null && !d.feastEn.isEmpty()) {
            LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flp.leftMargin = Ui.dp(act, 6);
            badges.addView(Widgets.pill(act, L.t(act, "feast") + " · " + L.pick(act, d.feastEn, d.feastMl), Ui.GOLD, Ui.GOLD_PALE), flp);
        }
        head.addView(badges);

        TextView title = Ui.text(act, L.pick(act, d.titleEn, d.titleMl), 24, Ui.BLUE, true);
        title.setTypeface(Ui.serifBold());
        title.setLineSpacing(Ui.dp(act, 3), 1.07f);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 8);
        head.addView(title, tlp);

        TextView distinction = Ui.text(act, L.t(act, d.apparition() ? "approved_apparition" : "marian_title"), 11.5f, Ui.MUTED, false);
        LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dlp.topMargin = Ui.dp(act, 5);
        head.addView(distinction, dlp);
        col.addView(head);

        col.addView(Widgets.section(act, L.t(act, "history_bg")));
        col.addView(Ui.prayerBody(act, L.pick(act, d.historyEn, d.historyMl), Ui.INK));

        col.addView(Widgets.section(act, L.t(act, "significance")));
        LinearLayout sig = Widgets.goldCard(act);
        TextView sv = Ui.text(act, L.pick(act, d.significanceEn, d.significanceMl), 14.5f, Ui.INK, true);
        sv.setLineSpacing(Ui.dp(act, 6), 1.16f);
        sig.addView(sv);
        col.addView(sig);

        col.addView(Widgets.section(act, L.t(act, "prayer")));
        LinearLayout prayer = Widgets.card(act);
        TextView pv = Ui.prayerBody(act, L.pick(act, d.prayerEn, d.prayerMl), Ui.INK);
        prayer.addView(pv);
        TextView amen = Ui.text(act, L.t(act, "ok_amen"), 12, Ui.GOLD, true);
        amen.setTypeface(Ui.serifBold());
        amen.setPadding(0, Ui.dp(act, 10), 0, 0);
        prayer.addView(amen);
        col.addView(prayer);

        col.addView(Widgets.section(act, L.t(act, "personal_intention")));
        col.addView(Widgets.intentionBox(act, Prefs.intention(act), L.t(act, "personal_intention"), L.t(act, "personal_note")));

        final boolean done = Prefs.isDailyDone(act, LocalDate.now().toString());
        TextView mark = done
                ? Ui.secondaryButton(act, L.t(act, "completed_today"), null)
                : Ui.primaryButton(act, L.t(act, "mark_complete"), new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        HistoryDb db = new HistoryDb(act);
                        db.addSession("devotion", 0, true);
                        db.markDaily();
                        db.close();
                        Prefs.markDaily(act, LocalDate.now().toString());
                        act.rebuildCurrent();
                        toast(L.t(act, "completed_today"));
                    }
                });
        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mlp.topMargin = Ui.dp(act, 16);
        col.addView(mark, mlp);

        TextView prayRosary = Ui.secondaryButton(act, L.t(act, "rosary"), new View.OnClickListener() {
            @Override public void onClick(View v) { act.showTab(1); }
        });
        LinearLayout.LayoutParams prlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        prlp.topMargin = Ui.dp(act, 8);
        col.addView(prayRosary, prlp);
    }
}
