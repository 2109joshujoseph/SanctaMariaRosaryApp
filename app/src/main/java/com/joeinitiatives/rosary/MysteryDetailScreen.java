package com.joeinitiatives.rosary;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/** A single mystery with scripture, meditation and an invitation to pray. */
public class MysteryDetailScreen extends BaseScreen {
    private static final String[] NUM_KEYS = {"first", "second", "third", "fourth", "fifth"};
    private final int set, number;

    public MysteryDetailScreen(MainActivity a, int set, int number) {
        super(a, "mystery_intro", true);
        this.set = set;
        this.number = number;
    }

    @Override
    protected void buildContent() {
        final Data.Mystery m = Data.mystery(set, number);
        LinearLayout col = scrollBody(18);
        if (m == null) {
            col.addView(Widgets.body(act, L.t(act, "no_prayers")));
            return;
        }

        col.addView(Ui.imageName(act, m.image, 212, 22));

        LinearLayout head = Ui.column(act);
        head.setPadding(Ui.dp(act, 18), Ui.dp(act, 15), Ui.dp(act, 18), Ui.dp(act, 16));
        head.setBackground(Ui.stroke(act, 0xFFFDFAF2, 0x44C9A227, 22));

        String over = L.t(act, NUM_KEYS[Math.max(0, Math.min(4, number - 1))]) + " "
                + L.t(act, "mystery_intro").toLowerCase() + " · "
                + L.pick(act, Data.SET_EN[set], Data.SET_ML[set]);
        head.addView(Widgets.overline(act, over));

        TextView title = Ui.text(act, L.pick(act, m.titleEn, m.titleMl), 24, Ui.BLUE, true);
        title.setTypeface(Ui.serifBold());
        title.setLineSpacing(Ui.dp(act, 3), 1.08f);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 6);
        head.addView(title, tlp);

        if (m.scripture != null && !m.scripture.isEmpty()) {
            TextView s = Ui.text(act, m.scripture, 11.5f, Ui.GOLD, true);
            s.setTypeface(Ui.serif());
            LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            slp.topMargin = Ui.dp(act, 7);
            head.addView(s, slp);
        }
        col.addView(head);

        col.addView(Widgets.section(act, L.t(act, "description")));
        TextView desc = Ui.prayerBody(act, L.pick(act, m.descEn, m.descMl), Ui.INK);
        col.addView(desc);

        col.addView(Widgets.section(act, L.t(act, "reflection")));
        LinearLayout refl = Widgets.goldCard(act);
        View bar = new View(act);
        bar.setBackgroundColor(Ui.GOLD);
        refl.addView(bar, new LinearLayout.LayoutParams(Ui.dp(act, 28), Ui.dp(act, 2)));
        TextView r = Ui.text(act, L.pick(act, m.reflEn, m.reflMl), 14.5f, Ui.INK, true);
        r.setLineSpacing(Ui.dp(act, 6), 1.16f);
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.topMargin = Ui.dp(act, 10);
        refl.addView(r, rlp);
        col.addView(refl);

        LinearLayout nav = Ui.row(act);
        TextView prev = Ui.secondaryButton(act, "‹ " + L.t(act, "previous"), new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (number > 1) act.push(new MysteryDetailScreen(act, set, number - 1));
                else act.pop();
            }
        });
        nav.addView(prev, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        TextView next = Ui.secondaryButton(act, L.t(act, "next") + " ›", new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (number < 5) act.push(new MysteryDetailScreen(act, set, number + 1));
                else startSet();
            }
        });
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        nlp.leftMargin = Ui.dp(act, 8);
        nav.addView(next, nlp);
        LinearLayout.LayoutParams navlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        navlp.topMargin = Ui.dp(act, 18);
        col.addView(nav, navlp);

        TextView pray = Ui.primaryButton(act, L.t(act, "pray_set"), new View.OnClickListener() {
            @Override public void onClick(View v) { startSet(); }
        });;
        LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plp.topMargin = Ui.dp(act, 10);
        col.addView(pray, plp);
    }

    private void startSet() {
        Prefs.setRosarySet(act, set);
        Prefs.setRosaryStep(act, 0);
        Prefs.setRosaryActive(act, true);
        Prefs.setRosaryStart(act, System.currentTimeMillis());
        act.showTab(1);
    }
}
