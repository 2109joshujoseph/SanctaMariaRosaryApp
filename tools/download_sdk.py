import os, urllib.request, zipfile

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
TC = os.path.join(BASE, ".toolchain")
os.makedirs(TC, exist_ok=True)

def dl(url, dest, label):
    if os.path.exists(dest) and os.path.getsize(dest) > 1000000:
        print("exists", label, os.path.getsize(dest), flush=True)
        return True
    print("downloading", label, url, flush=True)
    try:
        req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
        with urllib.request.urlopen(req, timeout=60) as r, open(dest, "wb") as f:
            total = int(r.headers.get("Content-Length") or 0)
            got = 0
            while True:
                chunk = r.read(1024 * 256)
                if not chunk:
                    break
                f.write(chunk)
                got += len(chunk)
        print("done", label, os.path.getsize(dest), flush=True)
        return True
    except Exception as e:
        print("FAILED", label, repr(e), flush=True)
        if os.path.exists(dest):
            os.remove(dest)
        return False

def unzip(zpath, outdir):
    if os.path.isdir(outdir) and os.listdir(outdir):
        print("exists dir", outdir, flush=True)
        return
    print("extracting", zpath, flush=True)
    with zipfile.ZipFile(zpath) as z:
        z.extractall(outdir)
    print("extracted", outdir, flush=True)

bt_zip = os.path.join(TC, "buildtools34.zip")
if dl("https://dl.google.com/android/repository/build-tools_r34-windows.zip", bt_zip, "build-tools"):
    unzip(bt_zip, os.path.join(TC, "buildtools"))

plat_zip = os.path.join(TC, "platform34.zip")
for url in [
    "https://dl.google.com/android/repository/platform-34_r03.zip",
    "https://dl.google.com/android/repository/platform-34_r02.zip",
    "https://dl.google.com/android/repository/platform-34_r01.zip",
    "https://dl.google.com/android/repository/platform-33_r03.zip",
]:
    if dl(url, plat_zip, "platform"):
        unzip(plat_zip, os.path.join(TC, "platform"))
        break

for root, dirs, files in os.walk(TC):
    for f in files:
        if f in ("aapt2.exe", "d8.bat", "apksigner.bat", "zipalign.exe", "android.jar"):
            print(os.path.join(root, f))
print("DONE")
