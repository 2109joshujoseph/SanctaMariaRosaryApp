import subprocess, os, time
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven\android-4.1.1.4.jar"
url="https://repo1.maven.org/maven2/com/google/android/android/4.1.1.4/android-4.1.1.4.jar"
expected=12947757
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
import zipfile
try:
    z=zipfile.ZipFile(dest); print("zip ok", len(z.namelist()))
except Exception as e: print("zip err", e)
