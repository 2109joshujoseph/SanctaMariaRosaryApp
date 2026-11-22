package com.joeinitiatives.rosary;

import java.util.ArrayList;

/** The complete traditional Rosary sequence, independent of the UI. */
public class RosarySession {
    public static final int SIGN = 0, CREED = 1, OF = 2, HM = 3, GLORY = 4, FATIMA = 5,
            MYSTERY = 6, HHQ = 7, CLOSING = 8, FINAL_SIGN = 9, LITANY = 10, MEMORARE = 11;

    public static class Step {
        public int kind, decade, hmIndex;
        public String bead, prayerId;
        public Data.Mystery mystery;
    }

    public final int set;
    public final ArrayList<Step> steps = new ArrayList<>();
    public int index;

    public RosarySession(int set, int startIndex) {
        this.set = set;
        build();
        index = Math.max(0, Math.min(startIndex, steps.size() - 1));
    }

    private Step add(int kind, String bead, String prayerId, int decade, int hmIndex) {
        Step s = new Step();
        s.kind = kind;
        s.bead = bead;
        s.prayerId = prayerId;
        s.decade = decade;
        s.hmIndex = hmIndex;
        steps.add(s);
        return s;
    }

    private void build() {
        add(SIGN, "cross", "sign_of_cross", -1, 0);
        add(CREED, "pendant0", "apostles_creed", -1, 0);
        add(OF, "pendant1", "our_father", -1, 0);
        for (int i = 0; i < 3; i++) add(HM, "p" + i, "hail_mary", -1, i + 1);
        add(GLORY, "medal", "glory_be", -1, 0);
        add(FATIMA, "medal", "fatima_prayer", -1, 0);

        for (int d = 0; d < 5; d++) {
            Step m = add(MYSTERY, "L" + d, null, d, 0);
            m.mystery = Data.mystery(set, d + 1);
            add(OF, "L" + d, "our_father", d, 0);
            for (int i = 0; i < 10; i++) add(HM, "S" + d + "_" + i, "hail_mary", d, i + 1);
            String connector = d < 4 ? "L" + (d + 1) : "medal";
            add(GLORY, connector, "glory_be", d, 0);
            add(FATIMA, connector, "fatima_prayer", d, 0);
        }

        add(HHQ, "medal", "hail_holy_queen", -1, 0);
        add(LITANY, "medal", "litany_of_loreto", -1, 0);
        add(MEMORARE, "medal", "memorare", -1, 0);
        add(CLOSING, "medal", "after_rosary", -1, 0);
        add(FINAL_SIGN, "cross", "sign_of_cross", -1, 0);
    }

    public Step current() {
        return steps.get(index);
    }

    public boolean atEnd() {
        return index >= steps.size() - 1;
    }

    /** Advances one step. Returns true when the final step has been completed. */
    public boolean advance() {
        if (index < steps.size() - 1) {
            index++;
            return false;
        }
        return true;
    }

    /** The decade number (0-4) that was completed by leaving the given step, or -1. */
    public int decadeCompletedAt(int fromIndex) {
        if (fromIndex < 0 || fromIndex >= steps.size()) return -1;
        Step s = steps.get(fromIndex);
        if (s.kind == FATIMA && s.decade >= 0) return s.decade;
        return -1;
    }

    public int decadesCompleted() {
        int n = 0;
        for (int i = 0; i <= index && i < steps.size(); i++) {
            if (steps.get(i).kind == FATIMA && steps.get(i).decade >= 0) n++;
        }
        return n;
    }

    public int percent() {
        return Math.round(100f * index / Math.max(1, steps.size() - 1));
    }
}
