package com.joeinitiatives.rosary;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Ten Marian devotions, clearly marked as apparitions or titles. */
public class DevotionsScreen extends BaseScreen {
    public DevotionsScreen(MainActivity a) {
        super(a, "devotions", false);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        LinearLayout intro = Widgets.blueCard(act);
        intro.addView(Widgets.overlineLight(act, L.t(act, "marian_devotions").toUpperCase()));
        TextView t = Widgets.headingLight(act, L.t(act, "marian_devotions"));
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 5);
        intro.addView(t, tlp);
        TextView s = Ui.text(act, L.t(act, "welcome_sub"), 12, 0xFFB9CCE8, false);
        s.setLineSpacing(Ui.dp(act, 4), 1.14f);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 8);
        intro.addView(s, slp);
        col.addView(intro);

        int apparitions = 0;
        for (Data.Devotion d : Data.devotions) if (d.apparition()) apparitions++;

        TextView apparitionLabel = Widgets.section(act, L.t(act, "approved_apparition") + " · " + apparitions);
        col.addView(apparitionLabel);
        for (Data.Devotion d : Data.devotions) {
            if (d.apparition()) {
                col.addView(row(d));
                Widgets.gap(col, 9);
            }
        }

        TextView titleLabel = Widgets.section(act, L.t(act, "marian_title") + " · " + (Data.devotions.size() - apparitions));
        col.addView(titleLabel);
        for (Data.Devotion d : Data.devotions) {
            if (!d.apparition()) {
                col.addView(row(d));
                Widgets.gap(col, 9);
            }
        }

        TextView note = Ui.text(act, L.t(act, "content_note"), 10.5f, Ui.MUTED, false);
        note.setLineSpacing(Ui.dp(act, 4), 1.12f);
        LinearLayout.LayoutParams nlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        nlp.topMargin = Ui.dp(act, 18);
        col.addView(note, nlp);
    }

    private View row(final Data.Devotion d) {
        LinearLayout card = Ui.row(act);
        card.setPadding(Ui.dp(act, 10), Ui.dp(act, 10), Ui.dp(act, 12), Ui.dp(act, 10));
        card.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x33C9A227, 18));
        card.setElevation(Ui.dp(act, 1));

        RoundedImage img = Ui.imageName(act, d.image, 78, 14);
        LinearLayout.LayoutParams ilp = new LinearLayout.LayoutParams(Ui.dp(act, 78), Ui.dp(act, 78));
        card.addView(img, ilp);

        LinearLayout mid = Ui.column(act);
        TextView kind = Widgets.pill(act, L.t(act, d.apparition() ? "approved_apparition" : "marian_title"),
                d.apparition() ? Ui.GREEN : Ui.BLUE_MID,
                d.apparition() ? 0xFFE7F0E9 : Ui.BLUE_LIGHT);
        LinearLayout.LayoutParams klp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        klp.bottomMargin = Ui.dp(act, 5);
        mid.addView(kind, klp);

        TextView title = Ui.text(act, L.pick(act, d.titleEn, d.titleMl), 15, Ui.BLUE, true);
        title.setTypeface(Ui.serifBold());
        title.setMaxLines(2);
        mid.addView(title);

        if (d.feastEn != null && !d.feastEn.isEmpty()) {
            TextView feast = Ui.text(act, L.t(act, "feast") + " · " + L.pick(act, d.feastEn, d.feastMl), 10.5f, Ui.MUTED, false);
            LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flp.topMargin = Ui.dp(act, 3);
            mid.addView(feast, flp);
        }

        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mlp.leftMargin = Ui.dp(act, 12);
        card.addView(mid, mlp);
        card.addView(Ui.icon(act, R.drawable.ic_chevron, 16, 0xFF9AA5B5, null));

        card.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { act.push(new DevotionDetailScreen(act, d.id)); }
        });
        Ui.press(card);
        return card;
    }
}
