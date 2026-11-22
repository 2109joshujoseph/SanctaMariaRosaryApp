package com.joeinitiatives.rosary;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/** The five mysteries of one set, with a direct invitation to pray. */
public class MysterySetScreen extends BaseScreen {
    private static final String[] LETTERS = {"j", "s", "g", "l"};
    private final int set;

    public MysterySetScreen(MainActivity a, int set) {
        super(a, "mysteries", true);
        this.set = set;
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);
        String name = L.pick(act, Data.SET_EN[set], Data.SET_ML[set]);

        LinearLayout banner = Ui.column(act);
        banner.addView(Ui.imageName(act, "myst_" + LETTERS[set] + "1", 172, 22));
        LinearLayout text = Ui.column(act);
        text.setPadding(Ui.dp(act, 18), Ui.dp(act, 14), Ui.dp(act, 18), Ui.dp(act, 16));
        text.setBackground(Ui.stroke(act, 0xFFFDFAF2, 0x44C9A227, 22));
        text.addView(Widgets.overline(act, L.pick(act, Data.DAYS_EN[set], Data.DAYS_ML[set]).toUpperCase()));
        TextView title = Widgets.heading(act, name);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 5);
        text.addView(title, tlp);
        TextView caption = Widgets.caption(act, "5 " + L.t(act, "mysteries") + " · " + L.t(act, "pray_set"));
        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.topMargin = Ui.dp(act, 6);
        text.addView(caption, clp);
        banner.addView(text);
        col.addView(banner);

        TextView section = Widgets.section(act, L.t(act, "all_mysteries"));
        col.addView(section);

        for (int i = 1; i <= 5; i++) {
            final Data.Mystery m = Data.mystery(set, i);
            if (m == null) continue;
            final int number = i;
            col.addView(Widgets.mysteryRow(act, m, i, L.ml(act), new View.OnClickListener() {
                @Override public void onClick(View v) { act.push(new MysteryDetailScreen(act, set, number)); }
            }));
            Widgets.gap(col, 8);
        }

        TextView pray = Ui.primaryButton(act, L.t(act, "pray_set"), new View.OnClickListener() {
            @Override public void onClick(View v) { startSet(); }
        });
        LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plp.topMargin = Ui.dp(act, 14);
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
