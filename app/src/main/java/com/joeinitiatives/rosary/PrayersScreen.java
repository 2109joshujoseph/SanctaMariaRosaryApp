package com.joeinitiatives.rosary;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/** Searchable, categorised library of Catholic prayers. */
public class PrayersScreen extends BaseScreen {
    private LinearLayout listWrap;
    private LinearLayout chipsRow;
    private EditText search;
    private String query = "";
    private String category = "";

    public PrayersScreen(MainActivity a) {
        super(a, "prayers", false);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        TextView lead = Widgets.heading(act, L.t(act, "prayers_az"));
        col.addView(lead);
        TextView count = Widgets.caption(act, Data.prayers.size() + " " + L.t(act, "prayers").toLowerCase());
        LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        clp.topMargin = Ui.dp(act, 4);
        clp.bottomMargin = Ui.dp(act, 12);
        col.addView(count, clp);

        LinearLayout searchBox = Ui.row(act);
        searchBox.setPadding(Ui.dp(act, 12), Ui.dp(act, 2), Ui.dp(act, 12), Ui.dp(act, 2));
        searchBox.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x44C9A227, 16));
        searchBox.addView(Ui.icon(act, R.drawable.ic_prayers, 17, Ui.GOLD, null));
        search = new EditText(act);
        search.setHint(L.t(act, "search_prayers"));
        search.setHintTextColor(0xFF9AA5B5);
        search.setTextColor(Ui.INK);
        search.setTextSize(14);
        search.setSingleLine(true);
        search.setBackgroundColor(Color.TRANSPARENT);
        search.setPadding(Ui.dp(act, 9), Ui.dp(act, 11), 0, Ui.dp(act, 11));
        search.setText(query);
        search.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) { query = s.toString(); renderList(); }
            @Override public void afterTextChanged(Editable s) {}
        });
        searchBox.addView(search, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        col.addView(searchBox);

        HorizontalScrollView hs = new HorizontalScrollView(act);
        hs.setHorizontalScrollBarEnabled(false);
        hs.setClipToPadding(false);
        chipsRow = Ui.row(act);
        int p = Ui.dp(act, 2);
        chipsRow.setPadding(0, p, Ui.dp(act, 8), p);
        hs.addView(chipsRow, new HorizontalScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams hlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hlp.topMargin = Ui.dp(act, 10);
        hlp.bottomMargin = Ui.dp(act, 4);
        col.addView(hs, hlp);

        listWrap = Ui.column(act);
        col.addView(listWrap);

        renderChips();
        renderList();
    }

    private ArrayList<String> categories() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (Data.Prayer p : Data.prayers) if (p.category != null && !p.category.isEmpty()) set.add(p.category);
        return new ArrayList<>(set);
    }

    private void renderChips() {
        if (chipsRow == null) return;
        chipsRow.removeAllViews();
        chipsRow.addView(chip(L.t(act, "all"), ""));
        for (final String c : categories()) chipsRow.addView(chip(c, c));
    }

    private View chip(String label, final String value) {
        TextView t = Ui.chip(act, label, category.equals(value), new OnClickListener() {
            @Override public void onClick(View v) {
                category = value;
                renderChips();
                renderList();
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = Ui.dp(act, 6);
        t.setLayoutParams(lp);
        return t;
    }

    private boolean matches(Data.Prayer p) {
        if (!category.isEmpty() && !category.equals(p.category)) return false;
        if (query == null || query.trim().isEmpty()) return true;
        String q = query.trim().toLowerCase();
        return contains(p.titleEn, q) || contains(p.titleMl, q) || contains(p.bodyEn, q) || contains(p.bodyMl, q);
    }

    private boolean contains(String s, String q) {
        return s != null && s.toLowerCase().contains(q);
    }

    private void renderList() {
        if (listWrap == null) return;
        listWrap.removeAllViews();
        int n = 0;
        for (final Data.Prayer p : Data.prayers) {
            if (!matches(p)) continue;
            n++;
            listWrap.addView(prayerRow(p));
            Widgets.gap(listWrap, 8);
        }
        if (n == 0) {
            TextView empty = Ui.text(act, L.t(act, "no_prayers"), 14, Ui.MUTED, false);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(0, Ui.dp(act, 30), 0, Ui.dp(act, 30));
            listWrap.addView(empty, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private View prayerRow(final Data.Prayer p) {
        LinearLayout card = Ui.column(act);
        card.setPadding(Ui.dp(act, 16), Ui.dp(act, 13), Ui.dp(act, 16), Ui.dp(act, 13));
        card.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x33C9A227, 18));
        card.setElevation(Ui.dp(act, 1));

        LinearLayout top = Ui.row(act);
        TextView cat = Widgets.pill(act, p.category, Ui.GOLD, Ui.GOLD_PALE);
        top.addView(cat);
        LinearLayout.LayoutParams flex = new LinearLayout.LayoutParams(0, 1, 1f);
        top.addView(new View(act), flex);
        top.addView(Ui.icon(act, R.drawable.ic_chevron, 15, 0xFF9AA5B5, null));
        card.addView(top);

        TextView title = Ui.text(act, L.pick(act, p.titleEn, p.titleMl), 16, Ui.BLUE, true);
        title.setTypeface(Ui.serifBold());
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 5);
        card.addView(title, tlp);

        String preview = L.pick(act, p.bodyEn, p.bodyMl).replace('\n', ' ');
        TextView body = Ui.text(act, Ui.ellipsize(preview, 110), 12, Ui.MUTED, false);
        body.setLineSpacing(Ui.dp(act, 4), 1.12f);
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        blp.topMargin = Ui.dp(act, 4);
        card.addView(body, blp);

        card.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { act.push(new PrayerDetailScreen(act, p.id)); }
        });
        Ui.press(card);
        return card;
    }
}
