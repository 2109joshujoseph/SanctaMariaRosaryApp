package com.joeinitiatives.rosary;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Loads all offline devotional content from bundled JSON assets. */
public final class Data {
    private static final String TAG = "RosaryData";
    private static boolean loaded = false;

    public static final class Prayer {
        public String id, category, titleEn, titleMl, bodyEn, bodyMl;
    }

    public static final class Mystery {
        public int set, number;
        public String titleEn, titleMl, scripture, descEn, descMl, reflEn, reflMl, image;
    }

    public static final class Devotion {
        public String id, titleEn, titleMl, feastEn, feastMl, kind;
        public String historyEn, historyMl, significanceEn, significanceMl, prayerEn, prayerMl, image;
        public boolean apparition() { return "apparition".equals(kind); }
    }

    public static final class Verse {
        public String refEn, refMl, verseEn, verseMl, reflEn, reflMl;
    }

    public static final class Section {
        public String titleEn, titleMl, introEn, introMl, prayerEn, prayerMl, image;
        public final ArrayList<String> intentionsEn = new ArrayList<>();
        public final ArrayList<String> intentionsMl = new ArrayList<>();
    }

    public static final ArrayList<Prayer> prayers = new ArrayList<>();
    public static final ArrayList<Mystery> mysteries = new ArrayList<>();
    public static final ArrayList<Devotion> devotions = new ArrayList<>();
    public static final ArrayList<Verse> verses = new ArrayList<>();
    public static Section kreupasanam = new Section();
    public static Section joseph = new Section();
    public static String josephFeastEn = "";
    public static String josephFeastMl = "";

    public static final String[] SET_EN = {"Joyful Mysteries", "Sorrowful Mysteries", "Glorious Mysteries", "Luminous Mysteries"};
    public static final String[] SET_ML = {"സന്തോഷ രഹസ്യങ്ങൾ", "ദുഃഖ രഹസ്യങ്ങൾ", "മഹത്വ രഹസ്യങ്ങൾ", "പ്രകാശ രഹസ്യങ്ങൾ"};
    public static final String[] DAYS_EN = {"Monday & Saturday", "Tuesday & Friday", "Wednesday & Sunday", "Thursday"};
    public static final String[] DAYS_ML = {"തിങ്കളാഴ്ചയും ശനിയാഴ്ചയും", "ചൊവ്വാഴ്ചയും വെള്ളിയാഴ്ചയും", "ബുധനാഴ്ചയും ഞായറാഴ്ചയും", "വ്യാഴാഴ്ച"};

    private Data() {}

    public static synchronized void load(Context context) {
        if (loaded) return;
        try {
            AssetManager am = context.getAssets();
            for (String f : new String[]{"content/prayers_core.json", "content/prayers_marian.json",
                    "content/prayers_more.json", "content/prayers_joseph.json"}) {
                JSONArray arr = new JSONArray(read(am, f));
                for (int i = 0; i < arr.length(); i++) prayers.add(parsePrayer(arr.getJSONObject(i)));
            }
            Collections.sort(prayers, new Comparator<Prayer>() {
                @Override public int compare(Prayer a, Prayer b) { return a.titleEn.compareToIgnoreCase(b.titleEn); }
            });

            for (String f : new String[]{"content/mysteries_joyful.json", "content/mysteries_sorrowful.json",
                    "content/mysteries_glorious.json", "content/mysteries_luminous.json"}) {
                JSONArray arr = new JSONArray(read(am, f));
                for (int i = 0; i < arr.length(); i++) mysteries.add(parseMystery(arr.getJSONObject(i)));
            }

            for (String f : new String[]{"content/devotions_one.json", "content/devotions_two.json"}) {
                JSONArray arr = new JSONArray(read(am, f));
                for (int i = 0; i < arr.length(); i++) devotions.add(parseDevotion(arr.getJSONObject(i)));
            }

            JSONArray va = new JSONArray(read(am, "content/daily.json"));
            for (int i = 0; i < va.length(); i++) verses.add(parseVerse(va.getJSONObject(i)));

            kreupasanam = parseSection(new JSONObject(read(am, "content/kreupasanam.json")));
            JSONObject jo = new JSONObject(read(am, "content/joseph.json"));
            joseph = parseSection(jo);
            josephFeastEn = jo.optString("feast_en", "");
            josephFeastMl = jo.optString("feast_ml", "");
            loaded = true;
        } catch (Exception e) {
            Log.e(TAG, "Content load failed", e);
        }
    }

    private static Prayer parsePrayer(JSONObject o) throws Exception {
        Prayer p = new Prayer();
        p.id = o.getString("id");
        p.category = o.optString("category", "Prayers");
        p.titleEn = o.getString("title_en");
        p.titleMl = o.getString("title_ml");
        p.bodyEn = o.getString("body_en");
        p.bodyMl = o.getString("body_ml");
        return p;
    }

    private static Mystery parseMystery(JSONObject o) throws Exception {
        Mystery m = new Mystery();
        m.set = o.getInt("set");
        m.number = o.getInt("number");
        m.titleEn = o.getString("title_en");
        m.titleMl = o.getString("title_ml");
        m.scripture = o.optString("scripture", "");
        m.descEn = o.getString("desc_en");
        m.descMl = o.getString("desc_ml");
        m.reflEn = o.getString("refl_en");
        m.reflMl = o.getString("refl_ml");
        m.image = o.optString("image", "");
        return m;
    }

    private static Devotion parseDevotion(JSONObject o) throws Exception {
        Devotion d = new Devotion();
        d.id = o.getString("id");
        d.titleEn = o.getString("title_en");
        d.titleMl = o.getString("title_ml");
        d.feastEn = o.optString("feast_en", "");
        d.feastMl = o.optString("feast_ml", "");
        d.kind = o.optString("kind", "title");
        d.historyEn = o.getString("history_en");
        d.historyMl = o.getString("history_ml");
        d.significanceEn = o.getString("significance_en");
        d.significanceMl = o.getString("significance_ml");
        d.prayerEn = o.getString("prayer_en");
        d.prayerMl = o.getString("prayer_ml");
        d.image = o.optString("image", "");
        return d;
    }

    private static Verse parseVerse(JSONObject o) throws Exception {
        Verse v = new Verse();
        v.refEn = o.getString("ref_en");
        v.refMl = o.getString("ref_ml");
        v.verseEn = o.getString("verse_en");
        v.verseMl = o.getString("verse_ml");
        v.reflEn = o.getString("refl_en");
        v.reflMl = o.getString("refl_ml");
        return v;
    }

    private static Section parseSection(JSONObject o) throws Exception {
        Section s = new Section();
        s.titleEn = o.optString("title_en", "");
        s.titleMl = o.optString("title_ml", "");
        s.introEn = o.optString("intro_en", "");
        s.introMl = o.optString("intro_ml", "");
        s.prayerEn = o.optString("prayer_en", "");
        s.prayerMl = o.optString("prayer_ml", "");
        s.image = o.optString("image", "");
        JSONArray a = o.optJSONArray("intentions_en");
        if (a != null) for (int i = 0; i < a.length(); i++) s.intentionsEn.add(a.getString(i));
        JSONArray b = o.optJSONArray("intentions_ml");
        if (b != null) for (int i = 0; i < b.length(); i++) s.intentionsMl.add(b.getString(i));
        return s;
    }

    private static String read(AssetManager am, String path) throws Exception {
        InputStream in = am.open(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
        in.close();
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

    public static Prayer prayer(String id) {
        for (Prayer p : prayers) if (p.id.equals(id)) return p;
        return null;
    }

    public static List<Mystery> mysteriesOf(int set) {
        ArrayList<Mystery> out = new ArrayList<>();
        for (Mystery m : mysteries) if (m.set == set) out.add(m);
        return out;
    }

    public static Mystery mystery(int set, int number) {
        for (Mystery m : mysteries) if (m.set == set && m.number == number) return m;
        return null;
    }

    public static int todaySet() {
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
            case Calendar.SATURDAY: return 0;
            case Calendar.TUESDAY:
            case Calendar.FRIDAY: return 1;
            case Calendar.WEDNESDAY:
            case Calendar.SUNDAY: return 2;
            default: return 3;
        }
    }

    public static Verse todayVerse() {
        if (verses.isEmpty()) return null;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        return verses.get(day % verses.size());
    }

    public static Devotion devotion(String id) {
        for (Devotion d : devotions) if (d.id.equals(id)) return d;
        return null;
    }
}
