package com.joeinitiatives.rosary;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/** Language, sound, vibration, reminders and progress reset. */
public class SettingsScreen extends BaseScreen {
    public SettingsScreen(MainActivity a) {
        super(a, "settings", true);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        LinearLayout header = Widgets.blueCard(act);
        header.addView(Widgets.overlineLight(act, L.t(act, "settings").toUpperCase()));
        TextView title = Widgets.headingLight(act, L.t(act, "settings"));
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 6);
        header.addView(title, tlp);
        TextView sub = Ui.text(act, L.t(act, "offline_note"), 11.5f, 0xFFB9CCE8, false);
        sub.setLineSpacing(Ui.dp(act, 4), 1.14f);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 8);
        header.addView(sub, slp);
        col.addView(header);

        col.addView(Widgets.section(act, "Preferences"));
        col.addView(toggleRow(L.t(act, "animations"), L.t(act, "animations"), Prefs.anim(act), new Toggle() {
            @Override public void onChange(boolean v) { Prefs.setAnim(act, v); act.rebuildCurrent(); }
        }));
        col.addView(toggleRow(L.t(act, "vibration"), L.t(act, "vibration"), Prefs.vibrate(act), new Toggle() {
            @Override public void onChange(boolean v) { Prefs.setVibrate(act, v); }
        }));

        col.addView(Widgets.section(act, L.t(act, "reminders")));
        TextView hint = Ui.text(act, L.t(act, "enable_notif_hint"), 11, Ui.MUTED, false);
        hint.setLineSpacing(Ui.dp(act, 4), 1.12f);
        LinearLayout.LayoutParams hlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hlp.bottomMargin = Ui.dp(act, 8);
        col.addView(hint, hlp);

        for (String key : Reminders.KEYS) {
            col.addView(reminderRow(key));
            Widgets.gap(col, 8);
        }

        col.addView(Widgets.section(act, L.t(act, "more")));
        col.addView(Widgets.actionRow(act, R.drawable.ic_star, L.t(act, "about"), L.t(act, "about_app"), new View.OnClickListener() {
            @Override public void onClick(View v) { act.push(new AboutScreen(act)); }
        }));
        Widgets.gap(col, 8);
        col.addView(Widgets.actionRow(act, R.drawable.ic_history, L.t(act, "history"), L.t(act, "view_history"), new View.OnClickListener() {
            @Override public void onClick(View v) { act.push(new HistoryScreen(act)); }
        }));
        Widgets.gap(col, 8);

        TextView reset = Ui.secondaryButton(act, L.t(act, "reset_progress"), new View.OnClickListener() {
            @Override public void onClick(View v) {
                new android.app.AlertDialog.Builder(act)
                        .setTitle(L.t(act, "reset_progress"))
                        .setMessage(L.t(act, "new_rosary"))
                        .setNegativeButton(L.t(act, "cancel"), null)
                        .setPositiveButton(L.t(act, "ok"), new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface d, int w) {
                                Prefs.setRosaryStep(act, 0);
                                Prefs.setRosaryActive(act, false);
                                android.content.SharedPreferences sp = act.getSharedPreferences("rosary_prefs", android.content.Context.MODE_PRIVATE);
                                sp.edit().putInt("total_rosaries", 0).putInt("total_decades", 0).putString("daily_done", "").apply();
                                HistoryDb hdb = new HistoryDb(act);
                                hdb.clearAll();
                                hdb.close();
                                toast(L.t(act, "reset_done"));
                            }
                        }).show();
            }
        });
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.topMargin = Ui.dp(act, 14);
        col.addView(reset, rlp);

        TextView version = Ui.text(act, L.t(act, "version") + " 1.1.0", 10.5f, Ui.MUTED, false);
        version.setGravity(android.view.Gravity.CENTER);
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vlp.topMargin = Ui.dp(act, 18);
        col.addView(version, vlp);
    }

    private TextView langButton(final String code, String label, boolean selected) {
        TextView t = Ui.secondaryButton(act, label, new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!code.equals(Prefs.lang(act))) {
                    Prefs.setLang(act, code);
                    for (BaseScreen s : act.stack) s.rebuild();
                    if (act.current != null) act.current.rebuild();
                    act.updateNav();
                }
            }
        });
        if (selected) {
            t.setBackground(Ui.bg(act, Ui.BLUE, 14));
            t.setTextColor(Ui.WHITE);
        }
        return t;
    }

    private View toggleRow(String label, String sub, boolean value, final Toggle onChange) {
        LinearLayout row = Ui.row(act);
        row.setPadding(Ui.dp(act, 15), Ui.dp(act, 12), Ui.dp(act, 12), Ui.dp(act, 12));
        row.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x33C9A227, 16));
        LinearLayout mid = Ui.column(act);
        TextView t = Ui.text(act, label, 14, Ui.INK, false);
        t.setTypeface(Ui.sansMedium());
        mid.addView(t);
        if (sub != null && !sub.isEmpty() && !sub.equals(label)) {
            TextView s = Ui.text(act, sub, 10.5f, Ui.MUTED, false);
            LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            slp.topMargin = Ui.dp(act, 2);
            mid.addView(s, slp);
        }
        row.addView(mid, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        final Switch sw = new Switch(act);
        sw.setChecked(value);
        sw.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                onChange.onChange(isChecked);
            }
        });
        row.addView(sw);
        return row;
    }

    private View reminderRow(final String key) {
        LinearLayout row = Ui.row(act);
        row.setPadding(Ui.dp(act, 15), Ui.dp(act, 12), Ui.dp(act, 12), Ui.dp(act, 12));
        row.setBackground(Ui.stroke(act, 0xFFFDFAF3, 0x33C9A227, 16));

        LinearLayout mid = Ui.column(act);
        TextView t = Ui.text(act, L.t(act, key), 14, Ui.INK, false);
        t.setTypeface(Ui.sansMedium());
        mid.addView(t);
        TextView s = Ui.text(act, Reminders.timeLabel(act, key), 11, Ui.MUTED, false);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        slp.topMargin = Ui.dp(act, 2);
        mid.addView(s, slp);
        row.addView(mid, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        final Switch sw = new Switch(act);
        sw.setChecked(Prefs.reminder(act, key));
        sw.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                Prefs.setReminder(act, key, isChecked);
                if (isChecked) Reminders.schedule(act, key);
                else Reminders.cancel(act, key);
                toast(isChecked ? L.t(act, "notif_enabled") : L.t(act, "notif_disabled"));
                act.rebuildCurrent();
            }
        });
        row.addView(sw);

        TextView time = Ui.text(act, "⏱", 13, Ui.GOLD, false);
        time.setPadding(Ui.dp(act, 8), Ui.dp(act, 4), Ui.dp(act, 2), Ui.dp(act, 4));
        time.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int mins = Prefs.time(act, key);
                new TimePickerDialog(act, new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        Prefs.setTime(act, key, hourOfDay * 60 + minute);
                        if (Prefs.reminder(act, key)) Reminders.schedule(act, key);
                        act.rebuildCurrent();
                    }
                }, mins / 60, mins % 60, false).show();
            }
        });
        row.addView(time);
        return row;
    }

    private interface Toggle { void onChange(boolean v); }
}
