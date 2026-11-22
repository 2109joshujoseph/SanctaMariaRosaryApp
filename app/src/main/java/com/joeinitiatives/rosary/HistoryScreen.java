package com.joeinitiatives.rosary;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/** Prayer history, streak and recent sessions. */
public class HistoryScreen extends BaseScreen {
    public HistoryScreen(MainActivity a) {
        super(a, "history", true);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        HistoryDb db = new HistoryDb(act);
        int rosaries = db.totalRosaries();
        int decades = db.totalDecades();
        int streak = db.streak();
        Set<String> recent = db.recentDays(7);
        java.util.List<HistoryDb.Session> sessions = db.sessions(40);
        db.close();

        LinearLayout header = Widgets.blueCard(act);
        TextView overline = Widgets.overlineLight(act, L.t(act, "history").toUpperCase());
        overline.setTextSize(12f);
        header.addView(overline);

        LinearLayout stats = Ui.row(act);
        stats.addView(stat(rosaries + "", L.t(act, "total_rosaries")), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        stats.addView(stat(decades + "", L.t(act, "total_decades")), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        stats.addView(stat(streak + "", L.t(act, "streak")), new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 16);
        header.addView(stats, slp);
        col.addView(header);

        col.addView(Widgets.section(act, L.t(act, "this_week")));
        col.addView(weekGrid(recent));

        col.addView(Widgets.section(act, "Recent Sessions · " + sessions.size()));
        if (sessions.isEmpty()) {
            TextView empty = Ui.text(act, L.t(act, "no_history"), 14, Ui.MUTED, false);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(0, Ui.dp(act, 30), 0, Ui.dp(act, 30));
            col.addView(empty);
        } else {
            for (HistoryDb.Session s : sessions) {
                col.addView(sessionRow(s));
                Widgets.gap(col, 8);
            }
        }

        TextView reset = Ui.secondaryButton(act, L.t(act, "reset_progress"), new View.OnClickListener() {
            @Override public void onClick(View v) {
                new android.app.AlertDialog.Builder(act)
                        .setTitle(L.t(act, "reset_progress"))
                        .setMessage(L.t(act, "new_rosary"))
                        .setNegativeButton(L.t(act, "cancel"), null)
                        .setPositiveButton(L.t(act, "ok"), new android.content.DialogInterface.OnClickListener() {
                            @Override public void onClick(android.content.DialogInterface d, int w) {
                                Prefs.setRosaryStep(act, 0);
                                Prefs.setRosaryActive(act, false);
                                // Clear history counts in SharedPrefs
                                android.content.SharedPreferences sp = act.getSharedPreferences("rosary_prefs", android.content.Context.MODE_PRIVATE);
                                sp.edit().putInt("total_rosaries", 0).putInt("total_decades", 0).putString("daily_done", "").apply();
                                // Clear history database
                                HistoryDb hdb = new HistoryDb(act);
                                hdb.clearAll();
                                hdb.close();
                                toast(L.t(act, "reset_done"));
                                act.rebuildCurrent();
                            }
                        }).show();
            }
        });
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.topMargin = Ui.dp(act, 16);
        col.addView(reset, rlp);
    }

    private View stat(String value, String label) {
        LinearLayout c = Ui.column(act);
        c.setGravity(Gravity.CENTER);
        TextView v = Ui.text(act, value, 20, Ui.WHITE, true);
        v.setTypeface(Ui.serifBold());
        v.setGravity(Gravity.CENTER);
        c.addView(v);
        TextView l = Ui.text(act, label, 9.5f, 0xFFB9CCE8, false);
        l.setGravity(Gravity.CENTER);
        l.setLineSpacing(Ui.dp(act, 2), 1.06f);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.topMargin = Ui.dp(act, 2);
        c.addView(l, llp);
        return c;
    }

    private View weekGrid(Set<String> recent) {
        LinearLayout row = Ui.row(act);
        row.setWeightSum(7f);
        LocalDate today = LocalDate.now();
        String[] letters = {"M", "T", "W", "T", "F", "S", "S"};
        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            String key = d.toString();
            boolean done = recent.contains(key);
            LinearLayout cell = Ui.column(act);
            cell.setGravity(Gravity.CENTER);
            TextView letter = Ui.text(act, letters[d.getDayOfWeek().getValue() - 1], 9.5f, Ui.MUTED, false);
            letter.setGravity(Gravity.CENTER);
            cell.addView(letter);
            View dot = new View(act);
            dot.setBackground(Ui.bg(act, done ? Ui.GOLD : 0xFFE7DCC3, 12));
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(Ui.dp(act, 24), Ui.dp(act, 24));
            dlp.topMargin = Ui.dp(act, 4);
            cell.addView(dot, dlp);
            if (done) {
                TextView check = Ui.text(act, "✓", 11, Ui.BLUE, true);
                check.setGravity(Gravity.CENTER);
                check.setLayoutParams(new LinearLayout.LayoutParams(Ui.dp(act, 24), Ui.dp(act, 24)));
                // overlay not perfect; keep simple
            }
            row.addView(cell, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        }
        return row;
    }

    private View sessionRow(HistoryDb.Session s) {
        LinearLayout card = Ui.row(act);
        card.setPadding(Ui.dp(act, 14), Ui.dp(act, 12), Ui.dp(act, 14), Ui.dp(act, 12));
        card.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x33C9A227, 16));

        LinearLayout circle = Ui.row(act);
        circle.setGravity(Gravity.CENTER);
        int icon = R.drawable.ic_rosary;
        if ("daily".equals(s.type)) icon = R.drawable.ic_calendar;
        else if ("devotion".equals(s.type)) icon = R.drawable.ic_mary;
        else if ("kreupasanam".equals(s.type) || "st_joseph".equals(s.type)) icon = R.drawable.ic_star;
        circle.setBackground(Ui.bg(act, Ui.GOLD_PALE, 20));
        circle.addView(Ui.icon(act, icon, 17, Ui.BLUE, null));
        card.addView(circle, new LinearLayout.LayoutParams(Ui.dp(act, 38), Ui.dp(act, 38)));

        LinearLayout mid = Ui.column(act);
        String typeLabel = typeLabel(s.type);
        TextView t = Ui.text(act, typeLabel, 14, Ui.BLUE, false);
        t.setTypeface(Ui.sansMedium());
        mid.addView(t);
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM · HH:mm", Locale.getDefault());
        TextView when = Ui.text(act, df.format(new Date(s.ts)), 10.5f, Ui.MUTED, false);
        LinearLayout.LayoutParams wlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wlp.topMargin = Ui.dp(act, 2);
        mid.addView(when, wlp);
        if (s.decades > 0) {
            TextView d = Ui.text(act, s.decades + " " + L.t(act, "decades"), 10.5f, Ui.GOLD, false);
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dlp.topMargin = Ui.dp(act, 2);
            mid.addView(d, dlp);
        }
        LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mlp.leftMargin = Ui.dp(act, 12);
        card.addView(mid, mlp);

        TextView status = Ui.text(act, s.completed ? L.t(act, "completed") : "", 10.5f, Ui.GREEN, false);
        status.setTypeface(Ui.sansMedium());
        card.addView(status);
        return card;
    }

    private String typeLabel(String type) {
        if ("rosary".equals(type)) return L.t(act, "rosary");
        if ("daily".equals(type)) return L.t(act, "daily_prayer");
        if ("devotion".equals(type)) return L.t(act, "marian_devotions");
        if ("kreupasanam".equals(type)) return L.t(act, "kreupasanam");
        if ("st_joseph".equals(type)) return L.t(act, "st_joseph");
        return type == null ? "" : type;
    }
}
