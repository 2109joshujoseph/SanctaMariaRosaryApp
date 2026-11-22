import os, sys, urllib.request, zipfile, shutil, time

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
TC = os.path.join(BASE, ".toolchain")
os.makedirs(TC, exist_ok=True)

def dl(url, dest, label):
    if os.path.exists(dest) and os.path.getsize(dest) > 1000000:
        print("exists", label, os.path.getsize(dest))
        return
    print("downloading", label, url, flush=True)
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
    with urllib.request.urlopen(req, timeout=120) as r, open(dest, "wb") as f:
        total = int(r.headers.get("Content-Length") or 0)
        got = 0
        while True:
            chunk = r.read(1024 * 256)
            if not chunk:
                break
            f.write(chunk)
            got += len(chunk)
            if total and got % (1024 * 1024 * 5) < 1024 * 256:
                print("  %s %.1f/%.1f MB" % (label, got / 1e6, total / 1e6), flush=True)
    print("done", label, os.path.getsize(dest), flush=True)

def unzip(zpath, outdir):
    if os.path.isdir(outdir):
        return
    print("extracting", zpath, flush=True)
    with zipfile.ZipFile(zpath) as z:
        z.extractall(outdir)
    print("extracted", outdir, flush=True)

jdk_zip = os.path.join(TC, "jdk17.zip")
dl("https://api.adoptium.net/v3/binary/latest/17/ga/windows/x64/jdk/hotspot/normal/eclipse", jdk_zip, "JDK17")
unzip(jdk_zip, os.path.join(TC, "jdk"))

bt_zip = os.path.join(TC, "buildtools34.zip")
dl("https://dl.google.com/android/repository/build-tools_r34-windows.zip", bt_zip, "build-tools")
unzip(bt_zip, os.path.join(TC, "buildtools"))

plat_zip = os.path.join(TC, "platform34.zip")
for url in [
    "https://dl.google.com/android/repository/platform-34_r03.zip",
    "https://dl.google.com/android/repository/platform-34_r02.zip",
    "https://dl.google.com/android/repository/platform-34_r01.zip",
]:
    try:
        dl(url, plat_zip, "platform-34")
        break
    except Exception as e:
        print("platform try failed", url, e, flush=True)
unzip(plat_zip, os.path.join(TC, "platform"))

print("TOOLCHAIN READY")
