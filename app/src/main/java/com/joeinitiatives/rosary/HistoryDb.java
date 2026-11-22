package com.joeinitiatives.rosary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Lightweight local database for prayer sessions and daily completion. */
public class HistoryDb extends SQLiteOpenHelper {
    private static final String NAME = "rosary_history.db";
    private static final int VERSION = 1;

    public static class Session {
        public long id, ts;
        public String type;
        public int decades;
        public boolean completed;
    }

    public HistoryDb(Context c) { super(c.getApplicationContext(), NAME, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sessions (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, ts INTEGER, decades INTEGER, completed INTEGER)");
        db.execSQL("CREATE TABLE daily (day TEXT PRIMARY KEY, ts INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {}

    public void addSession(String type, int decades, boolean completed) {
        ContentValues v = new ContentValues();
        v.put("type", type);
        v.put("ts", System.currentTimeMillis());
        v.put("decades", decades);
        v.put("completed", completed ? 1 : 0);
        getWritableDatabase().insert("sessions", null, v);
    }

    public void markDaily() {
        ContentValues v = new ContentValues();
        v.put("day", LocalDate.now().toString());
        v.put("ts", System.currentTimeMillis());
        getWritableDatabase().insertWithOnConflict("daily", null, v, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public boolean isDailyDone(LocalDate d) {
        Cursor c = getReadableDatabase().rawQuery("SELECT 1 FROM daily WHERE day=?", new String[]{d.toString()});
        boolean b = c.moveToFirst();
        c.close();
        return b;
    }

    public Set<String> recentDays(int n) {
        HashSet<String> out = new HashSet<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT day FROM daily ORDER BY day DESC LIMIT " + Math.max(1, n), null);
        while (c.moveToNext()) out.add(c.getString(0));
        c.close();
        return out;
    }

    public int streak() {
        int n = 0;
        LocalDate d = LocalDate.now();
        if (!isDailyDone(d)) d = d.minusDays(1);
        while (n < 400 && isDailyDone(d)) {
            n++;
            d = d.minusDays(1);
        }
        return n;
    }

    public int totalRosaries() {
        Cursor c = getReadableDatabase().rawQuery("SELECT COUNT(*) FROM sessions WHERE completed=1", null);
        int n = c.moveToFirst() ? c.getInt(0) : 0;
        c.close();
        return n;
    }

    public int totalDecades() {
        Cursor c = getReadableDatabase().rawQuery("SELECT SUM(decades) FROM sessions", null);
        int n = c.moveToFirst() ? c.getInt(0) : 0;
        c.close();
        return n;
    }

    public void clearAll() {
        SQLiteDatabase w = getWritableDatabase();
        w.delete("sessions", null, null);
        w.delete("daily", null, null);
    }

    public List<Session> sessions(int limit) {
        ArrayList<Session> out = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT id,type,ts,decades,completed FROM sessions ORDER BY ts DESC LIMIT " + Math.max(1, limit), null);
        while (c.moveToNext()) {
            Session s = new Session();
            s.id = c.getLong(0);
            s.type = c.getString(1);
            s.ts = c.getLong(2);
            s.decades = c.getInt(3);
            s.completed = c.getInt(4) == 1;
            out.add(s);
        }
        c.close();
        return out;
    }
}
