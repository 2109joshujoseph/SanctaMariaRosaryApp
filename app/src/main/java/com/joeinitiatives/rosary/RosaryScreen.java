package com.joeinitiatives.rosary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Guided, bead-by-bead Holy Rosary prayer with rich devotional contemplation.
 * Guarantees prayers are always clearly visible, legible, and interactive.
 */
public class RosaryScreen extends BaseScreen {
    private RosarySession session;
    private TextView setPill, restartBtn;
    private ProgressBar progressBar;
    private TextView progressLabel;
    private LinearLayout decadePillsRow;
    private LinearLayout beadDotsRow;
    private TextView beadStatusBadge;
    private TextView mysteryReminderPill;
    private RoundedImage mysteryImage;
    private TextView prayerTitle;
    private TextView scriptureText;
    private TextView prayerBody;
    private TextView reflectionHeader;
    private TextView reflectionBody;
    private TextView prevBtn, nextBtn;
    private ScrollView cardScrollView;
    private final ArrayList<TextView> decadePills = new ArrayList<>();
    private final ArrayList<TextView> beadDots = new ArrayList<>();
    private boolean finishing;

    public RosaryScreen(MainActivity a) {
        super(a, "rosary", false);
    }

    @Override
    protected void buildContent() {
        int set = Prefs.rosarySet(act);
        int step = Prefs.rosaryActive(act) ? Prefs.rosaryStep(act) : 0;
        session = new RosarySession(set, step);
        if (!Prefs.rosaryActive(act)) {
            Prefs.setRosaryActive(act, true);
            Prefs.setRosaryStart(act, System.currentTimeMillis());
        }

        LinearLayout root = Ui.column(act);
        root.setPadding(Ui.dp(act, 14), Ui.dp(act, 8), Ui.dp(act, 14), Ui.dp(act, 10));

        // 1. TOP BAR: Mystery Set selector + Restart button
        LinearLayout topBar = Ui.row(act);

        setPill = new TextView(act);
        setPill.setTextSize(12f);
        setPill.setTypeface(Ui.sansMedium());
        setPill.setTextColor(Ui.BLUE);
        setPill.setPadding(Ui.dp(act, 14), Ui.dp(act, 7), Ui.dp(act, 14), Ui.dp(act, 7));
        setPill.setBackground(Ui.stroke(act, 0xFFFFFFFF, Ui.GOLD, 18));
        setPill.setClickable(true);
        setPill.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { chooseSet(); }
        });
        topBar.addView(setPill);

        LinearLayout.LayoutParams flex = new LinearLayout.LayoutParams(0, 1, 1f);
        topBar.addView(new View(act), flex);

        restartBtn = Ui.text(act, "Restart", 11.5f, Ui.MUTED, false);
        restartBtn.setPadding(Ui.dp(act, 10), Ui.dp(act, 6), Ui.dp(act, 4), Ui.dp(act, 6));
        restartBtn.setClickable(true);
        restartBtn.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) { confirmRestart(); }
        });
        topBar.addView(restartBtn);

        root.addView(topBar);

        // 2. PROGRESS INDICATOR & DECADE / BEAD TRACKER
        LinearLayout trackerCard = Ui.column(act);
        trackerCard.setPadding(Ui.dp(act, 12), Ui.dp(act, 8), Ui.dp(act, 12), Ui.dp(act, 8));
        trackerCard.setBackground(Ui.stroke(act, 0xEEFFFDF8, 0x33C9A227, 14));
        LinearLayout.LayoutParams trLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        trLp.topMargin = Ui.dp(act, 6);
        trLp.bottomMargin = Ui.dp(act, 6);
        root.addView(trackerCard, trLp);

        // Progress bar + Label
        LinearLayout progRow = Ui.row(act);
        progressBar = new ProgressBar(act, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(session.steps.size() - 1);
        progressBar.setProgressTintList(ColorStateList.valueOf(Ui.GOLD));
        progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(0xFFE5DAC2));
        LinearLayout.LayoutParams pblp = new LinearLayout.LayoutParams(0, Ui.dp(act, 4), 1f);
        progRow.addView(progressBar, pblp);

        progressLabel = Ui.text(act, "0%", 10.5f, Ui.MUTED, false);
        progressLabel.setTypeface(Ui.sansMedium());
        progressLabel.setPadding(Ui.dp(act, 8), 0, 0, 0);
        progRow.addView(progressLabel);
        trackerCard.addView(progRow);

        // 5 Decades Tracker Row
        decadePillsRow = Ui.row(act);
        decadePillsRow.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dprLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dprLp.topMargin = Ui.dp(act, 6);
        trackerCard.addView(decadePillsRow, dprLp);

        decadePills.clear();
        for (int d = 0; d < 5; d++) {
            final int decIdx = d;
            TextView pill = new TextView(act);
            pill.setText("Decade " + (d + 1));
            pill.setTextSize(10.5f);
            pill.setTypeface(Ui.sansMedium());
            pill.setGravity(Gravity.CENTER);
            pill.setPadding(Ui.dp(act, 8), Ui.dp(act, 4), Ui.dp(act, 8), Ui.dp(act, 4));
            pill.setBackground(Ui.stroke(act, 0x55FFFFFF, 0x44C9A227, 12));
            pill.setTextColor(Ui.BLUE);
            pill.setClickable(true);
            pill.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) { jumpToDecade(decIdx); }
            });
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
            if (d > 0) plp.leftMargin = Ui.dp(act, 4);
            decadePillsRow.addView(pill, plp);
            decadePills.add(pill);
        }

        // 10 Hail Mary Bead Dots Row (Visible when in a decade)
        beadDotsRow = Ui.row(act);
        beadDotsRow.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams bdrLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bdrLp.topMargin = Ui.dp(act, 6);
        trackerCard.addView(beadDotsRow, bdrLp);

        beadDots.clear();
        for (int b = 0; b < 10; b++) {
            final int beadIdx = b;
            TextView dot = new TextView(act);
            dot.setText((b + 1) + "");
            dot.setTextSize(9.5f);
            dot.setTypeface(Ui.sansMedium());
            dot.setGravity(Gravity.CENTER);
            dot.setTextColor(Ui.BLUE);
            dot.setBackground(Ui.stroke(act, 0x66FFFFFF, 0x66C9A227, 10));
            LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(Ui.dp(act, 22), Ui.dp(act, 22));
            if (b > 0) dlp.leftMargin = Ui.dp(act, 4);
            dot.setClickable(true);
            dot.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) { jumpToDecadeBead(beadIdx); }
            });
            beadDotsRow.addView(dot, dlp);
            beadDots.add(dot);
        }

        // 3. MAIN PRAYER CARD (Fully Scrollable, Always Prominently Visible)
        cardScrollView = new ScrollView(act);
        cardScrollView.setFillViewport(true);
        cardScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        LinearLayout prayerCard = Ui.column(act);
        prayerCard.setPadding(Ui.dp(act, 18), Ui.dp(act, 16), Ui.dp(act, 18), Ui.dp(act, 18));
        prayerCard.setBackground(Ui.stroke(act, 0xFFFFFDF8, 0x44C9A227, 20));
        prayerCard.setElevation(Ui.dp(act, 2));

        // Status Badge: e.g. "1st Decade · Hail Mary (3 of 10)"
        beadStatusBadge = Ui.label(act, "", Ui.GOLD);
        beadStatusBadge.setTextSize(11f);
        beadStatusBadge.setLetterSpacing(0.08f);
        prayerCard.addView(beadStatusBadge);

        // Mystery Reminder Pill (shown on decade prayers)
        mysteryReminderPill = new TextView(act);
        mysteryReminderPill.setTextSize(11.5f);
        mysteryReminderPill.setTypeface(Ui.sansMedium());
        mysteryReminderPill.setTextColor(Ui.BLUE_MID);
        mysteryReminderPill.setPadding(Ui.dp(act, 10), Ui.dp(act, 4), Ui.dp(act, 10), Ui.dp(act, 4));
        mysteryReminderPill.setBackground(Ui.bg(act, Ui.BLUE_PALE, 12));
        LinearLayout.LayoutParams mrpLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mrpLp.topMargin = Ui.dp(act, 6);
        mrpLp.bottomMargin = Ui.dp(act, 6);
        prayerCard.addView(mysteryReminderPill, mrpLp);

        // Mystery Artwork Image (shown when announcing/reflecting on a mystery)
        mysteryImage = new RoundedImage(act, Ui.dp(act, 16));
        mysteryImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams milp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(act, 170));
        milp.topMargin = Ui.dp(act, 8);
        milp.bottomMargin = Ui.dp(act, 12);
        prayerCard.addView(mysteryImage, milp);

        // Scripture Reference
        scriptureText = Ui.text(act, "", 12f, Ui.GOLD, true);
        scriptureText.setTypeface(Ui.serifBold());
        prayerCard.addView(scriptureText);

        // Prayer / Mystery Title
        prayerTitle = Ui.h2(act, "", Ui.BLUE);
        prayerTitle.setTypeface(Ui.serifBold());
        LinearLayout.LayoutParams ptlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ptlp.topMargin = Ui.dp(act, 4);
        ptlp.bottomMargin = Ui.dp(act, 10);
        prayerCard.addView(prayerTitle, ptlp);

        // Prayer Body Text (Clear, readable, comfortable typography)
        prayerBody = Ui.prayerBody(act, "", Ui.INK);
        prayerBody.setTextIsSelectable(true);
        prayerCard.addView(prayerBody);

        // Reflection Header & Body (for mystery meditation)
        reflectionHeader = Ui.text(act, "Meditation & Fruit of the Mystery", 13f, Ui.GOLD, true);
        reflectionHeader.setTypeface(Ui.serifBold());
        LinearLayout.LayoutParams rhlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rhlp.topMargin = Ui.dp(act, 14);
        rhlp.bottomMargin = Ui.dp(act, 4);
        prayerCard.addView(reflectionHeader, rhlp);

        reflectionBody = Ui.body(act, "", Ui.INK);
        reflectionBody.setTextIsSelectable(true);
        prayerCard.addView(reflectionBody);

        cardScrollView.addView(prayerCard, new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams csvLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        root.addView(cardScrollView, csvLp);

        // 4. BOTTOM NAVIGATION CONTROLS (Fixed at bottom)
        LinearLayout controls = Ui.row(act);
        prevBtn = Ui.secondaryButton(act, "‹ Previous", new OnClickListener() {
            @Override public void onClick(View v) { back(); }
        });
        LinearLayout.LayoutParams prevLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.40f);
        controls.addView(prevBtn, prevLp);

        nextBtn = Ui.primaryButton(act, "Next Bead ›", new OnClickListener() {
            @Override public void onClick(View v) { advance(); }
        });
        LinearLayout.LayoutParams nextLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.60f);
        nextLp.leftMargin = Ui.dp(act, 10);
        controls.addView(nextBtn, nextLp);

        LinearLayout.LayoutParams ctlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ctlp.topMargin = Ui.dp(act, 8);
        root.addView(controls, ctlp);

        addFull(root);
        update();
    }

    private void update() {
        if (session == null) return;
        RosarySession.Step s = session.current();
        int set = session.set;
        setPill.setText(Data.SET_EN[set] + " ▾");

        // Progress calculation
        int progress = session.index;
        int total = session.steps.size() - 1;
        progressBar.setProgress(progress);
        int percent = total > 0 ? (progress * 100 / total) : 0;
        progressLabel.setText(percent + "%");

        // Decade pills update
        for (int d = 0; d < decadePills.size(); d++) {
            TextView p = decadePills.get(d);
            boolean isCurDecade = (s.decade == d);
            boolean isCompleted = (s.decade > d || (s.decade == -1 && session.index > 50));
            if (isCurDecade) {
                p.setBackground(Ui.gradient(act, 0xFF2C5C9E, Ui.BLUE, 12));
                p.setTextColor(Ui.WHITE);
            } else if (isCompleted) {
                p.setBackground(Ui.stroke(act, 0xFFF7EBC8, Ui.GOLD, 12));
                p.setTextColor(Ui.BLUE);
                p.setText("✓ " + (d + 1));
            } else {
                p.setBackground(Ui.stroke(act, 0x66FFFFFF, 0x44C9A227, 12));
                p.setTextColor(Ui.MUTED);
                p.setText("Decade " + (d + 1));
            }
        }

        // Bead dots update
        if (s.decade >= 0) {
            beadDotsRow.setVisibility(View.VISIBLE);
            int curBead = s.hmIndex;
            for (int b = 0; b < beadDots.size(); b++) {
                TextView dot = beadDots.get(b);
                int beadNum = b + 1;
                if (s.kind == RosarySession.HM && beadNum == curBead) {
                    dot.setBackground(Ui.gradient(act, Ui.GOLD_LIGHT, Ui.GOLD, 10));
                    dot.setTextColor(Ui.BLUE);
                } else if (s.kind == RosarySession.HM && beadNum < curBead) {
                    dot.setBackground(Ui.bg(act, Ui.GOLD, 10));
                    dot.setTextColor(Ui.WHITE);
                } else if (s.kind == RosarySession.GLORY || s.kind == RosarySession.FATIMA) {
                    dot.setBackground(Ui.bg(act, Ui.GOLD, 10));
                    dot.setTextColor(Ui.WHITE);
                } else {
                    dot.setBackground(Ui.stroke(act, 0x55FFFFFF, 0x55C9A227, 10));
                    dot.setTextColor(Ui.MUTED);
                }
            }
        } else {
            beadDotsRow.setVisibility(View.GONE);
        }

        // Populate Prayer / Mystery Content
        if (s.kind == RosarySession.MYSTERY && s.mystery != null) {
            beadStatusBadge.setText("MYSTERY ANNOUNCEMENT · DECADE " + (s.decade + 1) + " OF 5");
            mysteryReminderPill.setVisibility(View.GONE);

            mysteryImage.setVisibility(View.VISIBLE);
            int res = Ui.drawableId(act, s.mystery.image);
            if (res != 0) mysteryImage.setImageResource(res);

            scriptureText.setVisibility(View.VISIBLE);
            scriptureText.setText(s.mystery.scripture);

            prayerTitle.setText(s.mystery.titleEn);
            prayerBody.setText(s.mystery.descEn);

            reflectionHeader.setVisibility(View.VISIBLE);
            reflectionBody.setVisibility(View.VISIBLE);
            reflectionBody.setText(s.mystery.reflEn);
        } else {
            mysteryImage.setVisibility(View.GONE);
            scriptureText.setVisibility(View.GONE);
            reflectionHeader.setVisibility(View.GONE);
            reflectionBody.setVisibility(View.GONE);

            // If inside a decade, show mystery reminder pill
            if (s.decade >= 0) {
                Data.Mystery m = Data.mystery(session.set, s.decade + 1);
                if (m != null) {
                    mysteryReminderPill.setVisibility(View.VISIBLE);
                    mysteryReminderPill.setText("Meditation: " + m.titleEn);
                } else {
                    mysteryReminderPill.setVisibility(View.GONE);
                }
            } else {
                mysteryReminderPill.setVisibility(View.GONE);
            }

            beadStatusBadge.setText(counterText(s));

            Data.Prayer p = s.prayerId == null ? null : Data.prayer(s.prayerId);
            if (p != null) {
                prayerTitle.setText(p.titleEn);
                prayerBody.setText(p.bodyEn);
            } else {
                prayerTitle.setText("");
                prayerBody.setText("");
            }
        }

        // Button state
        prevBtn.setEnabled(session.index > 0);
        prevBtn.setAlpha(session.index > 0 ? 1f : 0.4f);
        nextBtn.setText(session.atEnd() ? "Complete Rosary ✓" : "Next Bead ›");

        // Scroll back to top of card on each bead
        cardScrollView.post(new Runnable() {
            @Override public void run() { cardScrollView.scrollTo(0, 0); }
        });
    }

    private String counterText(RosarySession.Step s) {
        String decadeSuffix = s.decade >= 0 ? " · DECADE " + (s.decade + 1) + " OF 5" : "";
        if (s.kind == RosarySession.HM) {
            return "HAIL MARY (" + s.hmIndex + (s.decade >= 0 ? " / 10)" : " / 3)") + decadeSuffix;
        }
        if (s.kind == RosarySession.OF) return "THE LORD'S PRAYER (OUR FATHER)" + decadeSuffix;
        if (s.kind == RosarySession.GLORY) return "GLORY BE TO THE FATHER" + decadeSuffix;
        if (s.kind == RosarySession.FATIMA) return "THE FATIMA PRAYER (O MY JESUS)" + decadeSuffix;
        if (s.kind == RosarySession.SIGN) return "OPENING PRAYER · SIGN OF THE CROSS";
        if (s.kind == RosarySession.FINAL_SIGN) return "CONCLUDING SIGN OF THE CROSS";
        if (s.kind == RosarySession.CREED) return "THE APOSTLES' CREED";
        if (s.kind == RosarySession.HHQ) return "HAIL, HOLY QUEEN (SALVE REGINA)";
        if (s.kind == RosarySession.LITANY) return "LITANY OF LORETO";
        if (s.kind == RosarySession.MEMORARE) return "THE MEMORARE — MOST MERCIFUL PRAYER";
        if (s.kind == RosarySession.CLOSING) return "CONCLUDING ROSARY PRAYER";
        return "";
    }

    private void advance() {
        if (session == null || finishing) return;
        if (session.atEnd()) {
            completeRosary();
            return;
        }
        int from = session.index;
        session.advance();
        Prefs.setRosaryStep(act, session.index);
        Haptics.tap(act);
        int decade = session.decadeCompletedAt(from);
        if (decade >= 0) {
            Prefs.addDecades(act, 1);
            if (Prefs.anim(act)) {
                AngelOverlay.show(act, "Decade Completed", "Decade " + (decade + 1) + " offered to Our Lady", null);
            }
        }
        update();
    }

    private void back() {
        if (session == null || session.index <= 0) return;
        session.index--;
        Prefs.setRosaryStep(act, session.index);
        Haptics.tap(act);
        update();
    }

    private void jumpToDecade(int decadeIdx) {
        if (session == null) return;
        for (int i = 0; i < session.steps.size(); i++) {
            RosarySession.Step s = session.steps.get(i);
            if (s.decade == decadeIdx) {
                session.index = i;
                Prefs.setRosaryStep(act, session.index);
                Haptics.tap(act);
                update();
                return;
            }
        }
    }

    private void jumpToDecadeBead(int beadIdx) {
        if (session == null) return;
        RosarySession.Step cur = session.current();
        int dec = cur.decade >= 0 ? cur.decade : 0;
        int targetHm = beadIdx + 1;
        for (int i = 0; i < session.steps.size(); i++) {
            RosarySession.Step s = session.steps.get(i);
            if (s.decade == dec && s.kind == RosarySession.HM && s.hmIndex == targetHm) {
                session.index = i;
                Prefs.setRosaryStep(act, session.index);
                Haptics.tap(act);
                update();
                return;
            }
        }
    }

    private void chooseSet() {
        final String[] items = {
                "Joyful Mysteries (Monday & Saturday)",
                "Sorrowful Mysteries (Tuesday & Friday)",
                "Glorious Mysteries (Wednesday & Sunday)",
                "Luminous Mysteries (Thursday)"
        };
        new AlertDialog.Builder(act)
                .setTitle("Select Rosary Mysteries")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface d, int which) {
                        Prefs.setRosarySet(act, which);
                        Prefs.setRosaryStep(act, 0);
                        Prefs.setRosaryActive(act, true);
                        Prefs.setRosaryStart(act, System.currentTimeMillis());
                        session = new RosarySession(which, 0);
                        update();
                    }
                }).show();
    }

    private void confirmRestart() {
        new AlertDialog.Builder(act)
                .setTitle("Restart Rosary")
                .setMessage("Start this Holy Rosary again from the beginning?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface d, int w) {
                        session.index = 0;
                        Prefs.setRosaryStep(act, 0);
                        Prefs.setRosaryActive(act, true);
                        Prefs.setRosaryStart(act, System.currentTimeMillis());
                        update();
                    }
                }).show();
    }

    private void completeRosary() {
        if (finishing) return;
        finishing = true;
        Prefs.setRosaryActive(act, false);
        Prefs.setRosaryStep(act, 0);
        Prefs.addRosary(act);
        Prefs.markDaily(act, LocalDate.now().toString());
        HistoryDb db = new HistoryDb(act);
        db.addSession("rosary", 5, true);
        db.markDaily();
        db.close();
        act.push(new CompletionScreen(act));
    }
}
