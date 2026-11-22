package com.joeinitiatives.rosary;

import android.content.Context;

import java.util.HashMap;

/** Bilingual user-interface strings. Content lives in JSON assets. */
public final class L {
    private static final HashMap<String, String[]> M = new HashMap<>();

    private static void p(String k, String en, String ml) {
        M.put(k, new String[]{en, ml});
    }

    public static boolean ml(Context c) { return false; }

    public static String t(Context c, String key) {
        String[] v = M.get(key);
        if (v == null) return key;
        return v[0];
    }

    public static String pick(Context c, String en, String ml) {
        return en;
    }

    static {
        p("app_name", "SANCTA MARIA", "SANCTA MARIA");
        p("dedication", "A Gift to Mama Mary", "അമ്മ മറിയത്തിന് ഒരു സമ്മാനം");
        p("dedication_from", "from Joe Initiatives", "ജോ ഇനിഷ്യേറ്റീവ്സിൽനിന്ന്");
        p("welcome", "Welcome to a quiet place of prayer", "പ്രാർത്ഥനയുടെ ശാന്തമായ ഇടത്തേക്ക് സ്വാഗതം");
        p("welcome_sub", "Pray the Rosary, rest in Mary's presence, and walk with her Son.", "ജപമാല പ്രാർത്ഥിക്കുക, മറിയത്തിന്റെ സാന്നിധ്യത്തിൽ വിശ്രമിക്കുക, അവളുടെ പുത്രനോടൊപ്പം നടക്കുക.");
        p("home", "Home", "ഹോം");
        p("rosary", "Holy Rosary", "പരിശുദ്ധ ജപമാല");
        p("mysteries", "Mysteries", "രഹസ്യങ്ങൾ");
        p("devotions", "Marian Devotions", "മറിയ ഭക്തികൾ");
        p("prayers", "Prayers", "പ്രാർത്ഥനകൾ");
        p("todays_mystery", "Today's Mystery", "ഇന്നത്തെ രഹസ്യം");
        p("start_rosary", "Start Holy Rosary", "പരിശുദ്ധ ജപമാല ആരംഭിക്കുക");
        p("continue_rosary", "Continue Rosary", "ജപമാല തുടരുക");
        p("daily_prayer", "Daily Prayer", "ദൈനംദിന പ്രാർത്ഥന");
        p("marian_devotions", "Marian Devotions", "മറിയ ഭക്തികൾ");
        p("kreupasanam", "Kreupasanam Mathavu", "കൃപാസനം മാതാവ്");
        p("st_joseph", "St. Joseph", "വിശുദ്ധ യൗസേപ്പിതാവ്");
        p("prayers_az", "Catholic Prayers", "Catholic Prayers");
        p("history", "Prayer History", "Prayer History");
        p("prayer_history", "Prayer History", "Prayer History");
        p("settings", "Settings", "ക്രമീകരണങ്ങൾ");
        p("about", "About", "വിവരങ്ങൾ");
        p("language", "Language", "ഭാഷ");
        p("english", "English", "ഇംഗ്ലീഷ്");
        p("malayalam", "Malayalam", "മലയാളം");
        p("today", "Today", "ഇന്ന്");
        p("recommended", "Recommended today", "ഇന്ന് ശുപാർശ ചെയ്യുന്നത്");
        p("begin", "Begin", "ആരംഭിക്കുക");
        p("next", "Next", "അടുത്തത്");
        p("previous", "Previous", "മുമ്പത്തേത്");
        p("restart", "Restart", "പുനരാരംഭിക്കുക");
        p("resume", "Resume", "തുടരുക");
        p("decade", "Decade", "ദശകം");
        p("of", "of", "ൽ");
        p("hail_mary", "Hail Mary", "നന്മ നിറഞ്ഞ മറിയമേ");
        p("our_father", "Our Father", "സ്വർഗ്ഗസ്ഥനായ പിതാവേ");
        p("glory_be", "Glory Be", "ത്രിത്വസ്തുതി");
        p("fatima", "Fatima Prayer", "ഫാത്തിമ പ്രാർത്ഥന");
        p("creed", "Apostles' Creed", "വിശ്വാസപ്രമാണം");
        p("sign_cross", "Sign of the Cross", "തിരുക്കുരിശിന്റെ അടയാളം");
        p("hail_holy_queen", "Hail, Holy Queen", "ഏറ്റവും ദയയുള്ള മാതാവേ");
        p("closing_prayer", "Prayer After the Rosary", "ജപമാലയ്ക്കുശേഷമുള്ള പ്രാർത്ഥന");
        p("decade_completed", "Decade Completed", "ദശകം പൂർത്തിയായി");
        p("decade_completed_sub", "A decade offered to Jesus through Mary", "മറിയത്തിലൂടെ യേശുവിന് സമർപ്പിച്ച ഒരു ദശകം");
        p("rosary_completed", "Rosary Completed", "ജപമാല പൂർത്തിയായി");
        p("blessing_msg", "May Mother Mary guide and bless you.", "അമ്മ മറിയം നിങ്ങളെ നയിക്കുകയും അനുഗ്രഹിക്കുകയും ചെയ്യട്ടെ.");
        p("pray_again", "Pray Again", "വീണ്ടും പ്രാർത്ഥിക്കുക");
        p("return_home", "Return Home", "ഹോമിലേക്ക് മടങ്ങുക");
        p("view_history", "View Prayer History", "പ്രാർത്ഥനാ ചരിത്രം കാണുക");
        p("skip", "Skip", "ഒഴിവാക്കുക");
        p("tap_begin", "Tap to begin", "ആരംഭിക്കാൻ സ്പർശിക്കുക");
        p("sound", "Sound", "ശബ്ദം");
        p("animations", "Animations", "അനിമേഷനുകൾ");
        p("vibration", "Vibration", "വൈബ്രേഷൻ");
        p("reminders", "Reminders", "ഓർമ്മപ്പെടുത്തലുകൾ");
        p("morning", "Morning prayer", "പ്രഭാത പ്രാർത്ഥന");
        p("evening", "Evening prayer", "സായാഹ്ന പ്രാർത്ഥന");
        p("marian", "Marian prayer", "മറിയ പ്രാർത്ഥന");
        p("rosary_reminder", "Rosary reminder", "ജപമാല ഓർമ്മപ്പെടുത്തൽ");
        p("save", "Save", "സേവ് ചെയ്യുക");
        p("saved", "Saved", "സേവ് ചെയ്തു");
        p("streak", "Prayer streak", "പ്രാർത്ഥനാ തുടർച്ച");
        p("days", "days", "ദിവസങ്ങൾ");
        p("feast", "Feast", "തിരുനാൾ");
        p("history_bg", "Background", "പശ്ചാത്തലം");
        p("significance", "Spiritual significance", "ആത്മീയ പ്രാധാന്യം");
        p("prayer", "Prayer", "പ്രാർത്ഥന");
        p("personal_intention", "Personal intention", "വ്യക്തിപരമായ ഉദ്ദേശ്യം");
        p("intentions", "Prayer intentions", "പ്രാർത്ഥനാ ഉദ്ദേശ്യങ്ങൾ");
        p("write_intention", "Write your intention here…", "നിങ്ങളുടെ ഉദ്ദേശ്യം ഇവിടെ എഴുതുക…");
        p("approved_apparition", "Approved apparition", "അംഗീകരിക്കപ്പെട്ട പ്രത്യക്ഷീകരണം");
        p("marian_title", "Marian title & devotion", "മറിയ സ്ഥാനപ്പേരും ഭക്തിയും");
        p("daily_verse", "Daily Verse", "ദൈനംദിന വചനം");
        p("mark_complete", "Mark as completed", "പൂർത്തിയായി അടയാളപ്പെടുത്തുക");
        p("completed_today", "Completed today", "ഇന്ന് പൂർത്തിയായി");
        p("choose_mystery", "Choose the mysteries", "രഹസ്യങ്ങൾ തിരഞ്ഞെടുക്കുക");
        p("all_mysteries", "All Mysteries", "എല്ലാ രഹസ്യങ്ങളും");
        p("close", "Close", "അടയ്ക്കുക");
        p("search_prayers", "Search prayers…", "പ്രാർത്ഥനകൾ തിരയുക…");
        p("no_prayers", "No prayers found", "പ്രാർത്ഥനകൾ കണ്ടെത്തിയില്ല");
        p("all", "All", "എല്ലാം");
        p("privacy", "Privacy", "സ്വകാര്യത");
        p("offline", "Works fully offline", "പൂർണ്ണമായും ഓഫ്ലൈനായി പ്രവർത്തിക്കുന്നു");
        p("credits", "Credits & sources", "കടപ്പാടുകളും സ്രോതസ്സുകളും");
        p("version", "Version", "പതിപ്പ്");
        p("reset_progress", "Reset rosary progress", "ജപമാല പുരോഗതി പുനഃസജ്ജമാക്കുക");
        p("cancel", "Cancel", "റദ്ദാക്കുക");
        p("ok", "OK", "ശരി");
        p("enable_reminders", "Enable reminders", "ഓർമ്മപ്പെടുത്തലുകൾ പ്രവർത്തനക്ഷമമാക്കുക");
        p("notification_time", "Reminder time", "ഓർമ്മപ്പെടുത്തൽ സമയം");
        p("today_rosary", "Today's Rosary", "ഇന്നത്തെ ജപമാല");
        p("joyful", "Joyful", "സന്തോഷ");
        p("sorrowful", "Sorrowful", "ദുഃഖ");
        p("glorious", "Glorious", "മഹത്വ");
        p("luminous", "Luminous", "പ്രകാശ");
        p("first", "First", "ഒന്നാം");
        p("second", "Second", "രണ്ടാം");
        p("third", "Third", "മൂന്നാം");
        p("fourth", "Fourth", "നാലാം");
        p("fifth", "Fifth", "അഞ്ചാം");
        p("prayer_language", "Prayer language", "പ്രാർത്ഥനാ ഭാഷ");
        p("scripture", "Scripture", "തിരുവചനം");
        p("reflection", "Reflection", "ധ്യാനം");
        p("description", "Mystery", "രഹസ്യം");
        p("pray_set", "Pray this set", "ഈ രഹസ്യങ്ങൾ പ്രാർത്ഥിക്കുക");
        p("total_rosaries", "Rosaries completed", "പൂർത്തിയാക്കിയ ജപമാലകൾ");
        p("total_decades", "Decades offered", "സമർപ്പിച്ച ദശകങ്ങൾ");
        p("no_history", "Your prayer history will appear here.", "നിങ്ങളുടെ പ്രാർത്ഥനാ ചരിത്രം ഇവിടെ കാണാം.");
        p("daily_progress", "Today's prayer", "ഇന്നത്തെ പ്രാർത്ഥന");
        p("enable_notif_hint", "Allow notifications to receive gentle prayer reminders.", "സൗമ്യമായ പ്രാർത്ഥനാ ഓർമ്മപ്പെടുത്തലുകൾ ലഭിക്കാൻ അറിയിപ്പുകൾ അനുവദിക്കുക.");
        p("notif_title", "A moment with Mary", "മറിയത്തോടൊപ്പം ഒരു നിമിഷം");
        p("notif_rosary", "It is time for the Holy Rosary. Our Lady is waiting for you.", "പരിശുദ്ധ ജപമാലയുടെ സമയമായി. അമ്മ മറിയം നിങ്ങൾക്കായി കാത്തിരിക്കുന്നു.");
        p("notif_morning", "Begin this day with a morning offering to God.", "ദൈവത്തിന് പ്രഭാത സമർപ്പണം നൽകി ഈ ദിവസം ആരംഭിക്കാം.");
        p("notif_evening", "End the day in thanksgiving and quiet prayer.", "നന്ദിയോടും ശാന്തമായ പ്രാർത്ഥനയോടും ദിവസം അവസാനിപ്പിക്കാം.");
        p("notif_marian", "Take a moment to pray with Our Lady today.", "ഇന്ന് അമ്മ മറിയത്തോടൊപ്പം പ്രാർത്ഥിക്കാൻ ഒരു നിമിഷം കണ്ടെത്താം.");
        p("kreupasanam_note", "A local Marian shrine and retreat centre in Kerala. This devotion is presented in communion with the Catholic Church.", "കേരളത്തിലെ ഒരു മറിയ തീർത്ഥാടന കേന്ദ്രവും ധ്യാനകേന്ദ്രവുമാണ് കൃപാസനം. ഈ ഭക്തി കത്തോലിക്കാ സഭയുമായി ഐക്യത്തിൽ അവതരിപ്പിക്കുന്നു.");
        p("joseph_note", "Spouse of Mary, foster father of Jesus, patron of workers, families and a happy death.", "മറിയത്തിന്റെ സഖി, യേശുവിന്റെ പോറ്റുപിതാവ്, തൊഴിലാളികളുടെയും കുടുംബങ്ങളുടെയും സമാധാനപൂർവ്വമായ മരണത്തിന്റെയും മധ്യസ്ഥൻ.");
        p("content_note", "Prayer texts follow traditional Catholic usage. Scripture references are from the Holy Bible. Artwork in this app is an original creation for Joe Initiatives.", "Prayer texts follow traditional Catholic usage. Scripture references are from the Holy Bible. Artwork in this app is an original creation for Joe Initiatives.");
        p("about_text", "Sancta Maria is a quiet, offline companion for prayer. It contains the complete Holy Rosary, the four sets of mysteries, traditional Catholic prayers, Marian devotions, Kreupasanam Mathavu, and prayers to St. Joseph.", "Sancta Maria is a quiet, offline companion for prayer. It contains the complete Holy Rosary, the four sets of mysteries, traditional Catholic prayers, Marian devotions, Kreupasanam Mathavu, and prayers to St. Joseph.");
        p("offline_note", "No account, no tracking, no internet required. All content is stored on your device.", "അക്കൗണ്ടില്ല, ട്രാക്കിംഗില്ല, ഇന്റർനെറ്റ് ആവശ്യമില്ല. എല്ലാ ഉള്ളടക്കവും നിങ്ങളുടെ ഉപകരണത്തിൽ സൂക്ഷിക്കുന്നു.");
        p("view_all", "View all", "എല്ലാം കാണുക");
        p("read_prayer", "Read prayer", "പ്രാർത്ഥന വായിക്കുക");
        p("tap_bead", "Tap a bead to continue", "തുടരാൻ ഒരു മുത്ത് സ്പർശിക്കുക");
        p("completed", "Completed", "പൂർത്തിയായി");
        p("decades", "decades", "ദശകങ്ങൾ");
        p("rosary_saved", "Your progress is saved automatically", "നിങ്ങളുടെ പുരോഗതി സ്വയം സേവ് ചെയ്യുന്നു");
        p("mystery_intro", "Mystery", "രഹസ്യം");
        p("continue_where", "Continue where you left off", "നിർത്തിയിടത്തുനിന്ന് തുടരുക");
        p("new_rosary", "Start a new Rosary", "പുതിയ ജപമാല ആരംഭിക്കുക");
        p("font_size", "Text size", "അക്ഷര വലിപ്പം");
        p("personal_note", "Your intention is kept only on this device.", "നിങ്ങളുടെ ഉദ്ദേശ്യം ഈ ഉപകരണത്തിൽ മാത്രം സൂക്ഷിക്കുന്നു.");
        p("prayer_intentions_note", "You may pause and place your own intention before Our Lady.", "അമ്മ മറിയത്തിന്റെ മുമ്പിൽ നിങ്ങളുടെ സ്വന്തം ഉദ്ദേശ്യം സമർപ്പിക്കാം.");
        p("more", "More", "കൂടുതൽ");
        p("today_set", "Today's set", "ഇന്നത്തെ സമൂഹം");
        p("reset_done", "Rosary progress reset", "ജപമാല പുരോഗതി പുനഃസജ്ജമാക്കി");
        p("notifications_off", "Notifications are disabled for this app", "ഈ ആപ്പിന് അറിയിപ്പുകൾ പ്രവർത്തനരഹിതമാണ്");
        p("notif_enabled", "Reminders enabled", "ഓർമ്മപ്പെടുത്തലുകൾ പ്രവർത്തനക്ഷമമായി");
        p("notif_disabled", "Reminders disabled", "ഓർമ്മപ്പെടുത്തലുകൾ പ്രവർത്തനരഹിതമായി");
        p("ok_amen", "Amen.", "ആമേൻ.");
        p("pray", "Pray", "പ്രാർത്ഥിക്കുക");
        p("mystery_set", "Mystery set", "രഹസ്യ സമൂഹം");
        p("back", "Back", "തിരികെ");
        p("tap_to_expand", "Tap to read", "വായിക്കാൻ സ്പർശിക്കുക");
        p("this_week", "This week", "ഈ ആഴ്ച");
        p("today_completed", "Today's prayer completed", "ഇന്നത്തെ പ്രാർത്ഥന പൂർത്തിയായി");
        p("streak_hint", "A gentle rhythm of prayer, one day at a time.", "ദിവസംപ്രതി ഒരു സൗമ്യമായ പ്രാർത്ഥനാ താളം.");
        p("about_app", "About this app", "ഈ ആപ്പിനെക്കുറിച്ച്");
        p("support", "Made with faith and love", "വിശ്വാസത്തോടും സ്നേഹത്തോടും നിർമ്മിച്ചത്");
    }
}
