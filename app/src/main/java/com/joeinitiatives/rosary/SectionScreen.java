package com.joeinitiatives.rosary;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;

/** Shared layout for the Kreupasanam and St. Joseph devotional sections. */
public class SectionScreen extends BaseScreen {
    private final Data.Section section;
    private final String feastEn, feastMl;

    public SectionScreen(MainActivity a, String titleKey, Data.Section section, String feastEn, String feastMl) {
        super(a, titleKey, true);
        this.section = section;
        this.feastEn = feastEn;
        this.feastMl = feastMl;
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);
        if (section == null) {
            col.addView(Widgets.body(act, L.t(act, "no_prayers")));
            return;
        }

        col.addView(Ui.imageName(act, section.image, 216, 22));

        LinearLayout head = Ui.column(act);
        head.setPadding(Ui.dp(act, 18), Ui.dp(act, 15), Ui.dp(act, 18), Ui.dp(act, 16));
        head.setBackground(Ui.stroke(act, 0xFFFDFAF2, 0x44C9A227, 22));

        LinearLayout top = Ui.row(act);
        top.addView(Widgets.pill(act, L.t(act, "marian_title"), Ui.BLUE_MID, Ui.BLUE_LIGHT));
        if (feastEn != null && !feastEn.isEmpty()) {
            LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flp.leftMargin = Ui.dp(act, 6);
            top.addView(Widgets.pill(act, L.t(act, "feast") + " · " + L.pick(act, feastEn, feastMl), Ui.GOLD, Ui.GOLD_PALE), flp);
        }
        head.addView(top);

        TextView title = Ui.text(act, L.pick(act, section.titleEn, section.titleMl), 25, Ui.BLUE, true);
        title.setTypeface(Ui.serifBold());
        title.setLineSpacing(Ui.dp(act, 3), 1.07f);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 8);
        head.addView(title, tlp);

        TextView intro = Ui.text(act, L.pick(act, section.introEn, section.introMl), 13, Ui.INK, false);
        intro.setLineSpacing(Ui.dp(act, 5), 1.16f);
        LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ilp.topMargin = Ui.dp(act, 9);
        head.addView(intro, ilp);
        col.addView(head);

        String noteKey = "kreupasanam".equals(titleKey()) ? "kreupasanam_note" : "joseph_note";
        LinearLayout note = Widgets.goldCard(act);
        TextView nv = Ui.text(act, L.t(act, noteKey), 11.5f, Ui.INK, false);
        nv.setLineSpacing(Ui.dp(act, 4), 1.14f);
        note.addView(nv);
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nlp.topMargin = Ui.dp(act, 12);
        col.addView(note, nlp);

        if (!section.intentionsEn.isEmpty() || !section.intentionsMl.isEmpty()) {
            col.addView(Widgets.section(act, L.t(act, "intentions")));
            java.util.List<String> items = L.ml(act) && !section.intentionsMl.isEmpty()
                    ? section.intentionsMl : section.intentionsEn;
            for (int i = 0; i < items.size(); i++) {
                LinearLayout row = Ui.row(act);
                row.setPadding(Ui.dp(act, 14), Ui.dp(act, 11), Ui.dp(act, 14), Ui.dp(act, 11));
                row.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x2AC9A227, 16));
                TextView num = Ui.text(act, String.format(java.util.Locale.US, "%02d", i + 1), 11, Ui.GOLD, true);
                num.setTypeface(Ui.serifBold());
                num.setGravity(android.view.Gravity.CENTER);
                num.setLayoutParams(new LinearLayout.LayoutParams(Ui.dp(act, 28), ViewGroup.LayoutParams.WRAP_CONTENT));
                row.addView(num);
                TextView item = Ui.text(act, items.get(i), 13.5f, Ui.INK, false);
                item.setLineSpacing(Ui.dp(act, 4), 1.14f);
                LinearLayout.LayoutParams itlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                itlp.leftMargin = Ui.dp(act, 4);
                row.addView(item, itlp);
                col.addView(row);
                Widgets.gap(col, 7);
            }
        }

        col.addView(Widgets.section(act, L.t(act, "prayer")));
        LinearLayout prayer = Widgets.card(act);
        TextView pv = Ui.prayerBody(act, L.pick(act, section.prayerEn, section.prayerMl), Ui.INK);
        prayer.addView(pv);
        TextView amen = Ui.text(act, L.t(act, "ok_amen"), 12, Ui.GOLD, true);
        amen.setTypeface(Ui.serifBold());
        amen.setPadding(0, Ui.dp(act, 10), 0, 0);
        prayer.addView(amen);
        col.addView(prayer);

        col.addView(Widgets.section(act, L.t(act, "personal_intention")));
        col.addView(Widgets.intentionBox(act, Prefs.intention(act), L.t(act, "personal_intention"), L.t(act, "personal_note")));

        final String type = titleKey();
        final boolean done = Prefs.isDailyDone(act, LocalDate.now().toString());
        TextView mark = done
                ? Ui.secondaryButton(act, L.t(act, "completed_today"), null)
                : Ui.primaryButton(act, L.t(act, "mark_complete"), new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        HistoryDb db = new HistoryDb(act);
                        db.addSession(type, 0, true);
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

        TextView rosary = Ui.secondaryButton(act, L.t(act, "rosary"), new View.OnClickListener() {
            @Override public void onClick(View v) { act.showTab(1); }
        });
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.topMargin = Ui.dp(act, 8);
        col.addView(rosary, rlp);
    }
}
