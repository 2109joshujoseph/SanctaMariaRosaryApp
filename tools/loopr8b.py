import subprocess, os, time
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven\r8.jar"
url="https://dl.google.com/dl/android/maven2/com/android/tools/r8/8.2.47/r8-8.2.47.jar"
expected=15298967
for i in range(60):
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
# validate
import zipfile
try:
    z=zipfile.ZipFile(dest); print("zip ok", len(z.namelist()))
except Exception as e: print("zip err", e)
