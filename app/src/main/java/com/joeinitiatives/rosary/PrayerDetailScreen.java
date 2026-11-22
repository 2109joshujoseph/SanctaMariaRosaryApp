package com.joeinitiatives.rosary;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Full text of a single prayer. */
public class PrayerDetailScreen extends BaseScreen {
    private final String id;

    public PrayerDetailScreen(MainActivity a, String id) {
        super(a, "prayers", true);
        this.id = id;
    }

    @Override
    protected void buildContent() {
        Data.Prayer p = Data.prayer(id);
        LinearLayout col = scrollBody(18);
        if (p == null) {
            col.addView(Widgets.body(act, L.t(act, "no_prayers")));
            return;
        }

        LinearLayout head = Ui.column(act);
        head.setPadding(Ui.dp(act, 20), Ui.dp(act, 20), Ui.dp(act, 20), Ui.dp(act, 20));
        head.setBackground(Ui.gradient(act, 0xFF22467D, 0xFF142E57, 22));
        head.setElevation(Ui.dp(act, 2));
        head.addView(Widgets.overlineLight(act, p.category.toUpperCase()));
        TextView title = Ui.text(act, L.pick(act, p.titleEn, p.titleMl), 24, Ui.WHITE, true);
        title.setTypeface(Ui.serifBold());
        title.setLineSpacing(Ui.dp(act, 3), 1.08f);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 7);
        head.addView(title, tlp);
        col.addView(head);

        LinearLayout body = Widgets.card(act);
        TextView pv = Ui.prayerBody(act, L.pick(act, p.bodyEn, p.bodyMl), Ui.INK);
        pv.setTextIsSelectable(true);
        body.addView(pv);
        TextView amen = Ui.text(act, L.t(act, "ok_amen"), 12, Ui.GOLD, true);
        amen.setTypeface(Ui.serifBold());
        amen.setPadding(0, Ui.dp(act, 12), 0, 0);
        body.addView(amen);
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.topMargin = Ui.dp(act, 12);
        col.addView(body, blp);

        TextView note = Ui.text(act, L.t(act, "content_note"), 10.5f, Ui.MUTED, false);
        note.setLineSpacing(Ui.dp(act, 4), 1.12f);
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nlp.topMargin = Ui.dp(act, 14);
        col.addView(note, nlp);

        TextView rosary = Ui.primaryButton(act, L.t(act, "rosary"), new View.OnClickListener() {
            @Override public void onClick(View v) { act.showTab(1); }
        });
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.topMargin = Ui.dp(act, 14);
        col.addView(rosary, rlp);
    }
}
