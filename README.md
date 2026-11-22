# Sancta Maria - Rosary & Marian Devotions App

<p align="center">
  <strong>A Sacred, Offline Native Android App for the Holy Rosary & Catholic Devotions</strong>
</p>

---

## Overview

**Sancta Maria** is a dedicated, beautifully crafted native Android application honoring the Blessed Virgin Mary and our Lord Jesus Christ. Designed for daily prayer, contemplation, and spiritual growth, the app provides a complete, distraction-free environment with sacred artwork, scripture readings, ambient chants, and an interactive Rosary bead counter.

---

## ✨ Features

- **The Holy Rosary**:
  - Full meditations for all 20 mysteries:
    - 🕊️ **Joyful Mysteries** (Monday & Saturday)
    - 💧 **Luminous Mysteries** (Thursday)
    - ✝️ **Sorrowful Mysteries** (Tuesday & Friday)
    - 👑 **Glorious Mysteries** (Wednesday & Sunday)
  - Interactive Rosary Bead counter with haptic vibration feedback.
  - Sacred classical Catholic art paired with each mystery.

- **Marian & Catholic Prayers**:
  - Daily Catholic prayers: Morning Offering, Angelus, Regina Caeli, Divine Mercy Chaplet, Evening & Night Prayers.
  - Litany of Loreto (Litany of the Blessed Virgin Mary).
  - Devotions to St. Joseph (Terror of Demons, Seven Sorrows & Joys).
  - Special prayers: Our Lady of Kreupasanam covenant prayer & novena.

- **Audio & Haptics**:
  - Reverent ambient chimes and offline Ave Maria chant audio.
  - Distinct haptic feedback patterns for Our Father, Hail Mary, and Glory Be beads.

- **Daily Reminders & History**:
  - Configurable prayer alarms & notification reminders.
  - Spiritual history tracking to celebrate streaks and consistency.

- **100% Offline & Private**:
  - No ads, no tracking, and no external internet connection required for prayer.

---

## 📱 Application Download

Ready-to-install Android APK packages are available in the [`dist/`](./dist/) folder:
- **Sancta Maria v1.1.0 (Latest)**: [`dist/SanctaMaria-1.1.0.apk`](./dist/SanctaMaria-1.1.0.apk)

### Installation:
1. Download `SanctaMaria-1.1.0.apk` to your Android device.
2. Open the file and allow *Install from unknown sources* if prompted.
3. Launch **Sancta Maria** and begin praying!

---

## 🛠️ Project Structure

```text
Mama Mary/
├── app/
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── assets/content/       # Prayer JSON data & mystery scripts
│           ├── java/com/joeinitiatives/rosary/ # Native Android Java code
│           └── res/                  # Sacred art drawables, audio, layouts, themes
├── dist/                             # Compiled & signed release APKs
├── tools/                            # Python build automation and art generation
├── release.keystore                   # Android release signing key
└── README.md
```

---

## 🔨 Building from Source

The project uses a custom, lightweight, standalone build system driven by Python, removing the overhead of Gradle:

1. Ensure Python 3.x and JDK 17+ are installed.
2. Run the build script:
   ```bash
   python tools/build.py
   ```
3. The signed, zip-aligned release APK will be generated in `dist/SanctaMaria-1.1.0.apk`.

---

## 📜 License

Created with devotion by **Joshu Joseph** (Joe Initiatives).
Dedicated to Our Lady, Sancta Maria, Mother of God.
