import subprocess, os, time
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
url="https://dl.google.com/android/repository/platform-34-ext8_r01.zip"
expected=63201407
for i in range(200):
    size=os.path.getsize(dest) if os.path.exists(dest) else 0
    if size >= expected:
        print("COMPLETE", size, flush=True); break
    print("iter", i, "size", size, flush=True)
    cmd=[CURL,"-L","-C","-","--retry","3","--retry-delay","2","--connect-timeout","30","--max-time","280","-o",dest,url]
    try:
        subprocess.run(cmd, timeout=290)
    except Exception as e:
        print("exc", e, flush=True)
    time.sleep(3)
print("final size", os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
import zipfile
try:
    z=zipfile.ZipFile(dest); print("zip ok", len(z.namelist()))
    for n in z.namelist():
        if n.endswith("android.jar"):
            print("android.jar entry:", n, z.getinfo(n).file_size)
            outdir=os.path.join(os.path.dirname(dest),"platform")
            os.makedirs(outdir, exist_ok=True)
            z.extract(n, outdir)
            # move to platform/android.jar
            src=os.path.join(outdir, n)
            dst=os.path.join(outdir, "android.jar")
            if src != dst:
                os.replace(src, dst)
            print("extracted to", dst)
except Exception as e: print("zip err", e)
