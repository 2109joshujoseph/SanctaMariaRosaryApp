import os, subprocess, zipfile, time, sys

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
TC = os.path.join(BASE, ".toolchain")
os.makedirs(TC, exist_ok=True)
CURL = r"C:\Windows\System32\curl.exe"

def valid_zip(path, min_size=1000000):
    if not os.path.exists(path) or os.path.getsize(path) < min_size:
        return False
    try:
        with zipfile.ZipFile(path) as z:
            bad = z.testzip()
            return bad is None
    except Exception:
        return False

def fetch(url, dest, label):
    for attempt in range(1, 31):
        if valid_zip(dest):
            print("VALID", label, os.path.getsize(dest), flush=True)
            return True
        print("fetch", label, "attempt", attempt, "size", os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
        cmd = [CURL, "-L", "--retry", "5", "--retry-delay", "2", "-C", "-",
               "--connect-timeout", "30", "--max-time", "900", "-o", dest, url]
        try:
            subprocess.run(cmd, timeout=960)
        except Exception as e:
            print("curl exception", e, flush=True)
        time.sleep(2)
    return valid_zip(dest)

def unzip(zpath, outdir):
    if os.path.isdir(outdir) and os.listdir(outdir):
        print("exists", outdir, flush=True)
        return
    with zipfile.ZipFile(zpath) as z:
        z.extractall(outdir)
    print("extracted", outdir, flush=True)

ok1 = fetch("https://dl.google.com/android/repository/build-tools_r34-windows.zip",
            os.path.join(TC, "buildtools34.zip"), "build-tools")
ok2 = fetch("https://dl.google.com/android/repository/platform-34_r03.zip",
            os.path.join(TC, "platform34.zip"), "platform-34")

if ok1:
    unzip(os.path.join(TC, "buildtools34.zip"), os.path.join(TC, "buildtools"))
if ok2:
    unzip(os.path.join(TC, "platform34.zip"), os.path.join(TC, "platform"))

for root, dirs, files in os.walk(TC):
    for f in files:
        if f in ("aapt2.exe", "d8.bat", "apksigner.bat", "zipalign.exe", "android.jar"):
            print("FOUND", os.path.join(root, f), flush=True)
print("FETCH DONE", ok1, ok2, flush=True)
