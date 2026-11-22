package com.joeinitiatives.rosary;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/** The four sets of the Rosary, with the day's set recommended first. */
public class MysteriesScreen extends BaseScreen {
    private static final String[] LETTERS = {"j", "s", "g", "l"};

    public MysteriesScreen(MainActivity a) {
        super(a, "mysteries", false);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        TextView lead = Widgets.heading(act, L.t(act, "choose_mystery"));
        col.addView(lead);
        TextView sub = Widgets.caption(act, L.t(act, "recommended") + " · " + L.pick(act, Data.SET_EN[Data.todaySet()], Data.SET_ML[Data.todaySet()]));
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 6);
        slp.bottomMargin = Ui.dp(act, 14);
        col.addView(sub, slp);

        int today = Data.todaySet();
        for (int pass = 0; pass < 2; pass++) {
            for (int set = 0; set < 4; set++) {
                if ((pass == 0) != (set == today)) continue;
                col.addView(setCard(set, set == today));
                Widgets.gap(col, 12);
            }
        }

        TextView note = Ui.text(act, L.t(act, "pray_set"), 11.5f, Ui.MUTED, false);
        note.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nlp.topMargin = Ui.dp(act, 10);
        col.addView(note, nlp);
    }

    private View setCard(final int set, boolean today) {
        LinearLayout card = Ui.column(act);
        card.setBackground(Ui.stroke(act, 0xFFFDFAF2, today ? 0x99C9A227 : 0x33C9A227, 22));
        card.setElevation(Ui.dp(act, today ? 3 : 1));

        RoundedImage img = Ui.imageName(act, "myst_" + LETTERS[set] + "1", 138, 22);
        card.addView(img);

        LinearLayout text = Ui.column(act);
        text.setPadding(Ui.dp(act, 17), Ui.dp(act, 13), Ui.dp(act, 17), Ui.dp(act, 15));

        LinearLayout top = Ui.row(act);
        TextView days = Ui.label(act, L.pick(act, Data.DAYS_EN[set], Data.DAYS_ML[set]).toUpperCase(), Ui.GOLD);
        days.setTextSize(9.5f);
        days.setLetterSpacing(0.14f);
        top.addView(days);
        LinearLayout.LayoutParams flex = new LinearLayout.LayoutParams(0, 1, 1f);
        top.addView(new View(act), flex);
        if (today) {
            top.addView(Widgets.pill(act, L.t(act, "today"), Ui.BLUE, Ui.GOLD_PALE));
        }
        text.addView(top);

        TextView title = Widgets.heading(act, L.pick(act, Data.SET_EN[set], Data.SET_ML[set]));
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 5);
        text.addView(title, tlp);

        Data.Mystery first = Data.mystery(set, 1);
        if (first != null) {
            TextView f = Ui.text(act, L.pick(act, first.titleEn, first.titleMl) + " · 5 " + L.t(act, "mysteries"), 11.5f, Ui.MUTED, false);
            LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flp.topMargin = Ui.dp(act, 4);
            text.addView(f, flp);
        }

        LinearLayout foot = Ui.row(act);
        LinearLayout.LayoutParams flp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flp2.topMargin = Ui.dp(act, 10);
        foot.addView(dots(), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        TextView open = Ui.text(act, L.t(act, "view_all"), 11, Ui.BLUE, false);
        open.setTypeface(Ui.sansMedium());
        foot.addView(open);
        text.addView(foot, flp2);

        card.addView(text);
        card.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { act.push(new MysterySetScreen(act, set)); }
        });
        Ui.press(card);
        return card;
    }

    private LinearLayout dots() {
        LinearLayout row = Ui.row(act);
        for (int i = 0; i < 5; i++) {
            View d = new View(act);
            d.setBackground(Ui.bg(act, i == 0 ? Ui.GOLD : 0xFFE7DCC3, 10));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Ui.dp(act, 6), Ui.dp(act, 6));
            lp.rightMargin = Ui.dp(act, 5);
            row.addView(d, lp);
        }
        return row;
    }
}
