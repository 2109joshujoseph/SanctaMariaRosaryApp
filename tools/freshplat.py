import subprocess, os, time
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
url="https://dl.google.com/android/repository/platform-34-ext8_r01.zip"
expected=63201407
# remove if exists
if os.path.exists(dest):
    os.remove(dest)
    print("removed old", flush=True)
for i in range(200):
    size=os.path.getsize(dest) if os.path.exists(dest) else 0
    if size >= expected:
        # verify PK header
        data=open(dest,"rb").read(4)
        if data == b"PK\x03\x04":
            print("COMPLETE and valid", size, flush=True); break
        else:
            print("COMPLETE but bad header", data, flush=True)
            os.remove(dest)
            print("removed bad, retry", flush=True)
            continue
    print("iter", i, "size", size, flush=True)
    cmd=[CURL,"-L","--retry","3","--retry-delay","2","--connect-timeout","30","--max-time","280","-o",dest,url]
    try:
        subprocess.run(cmd, timeout=290)
    except Exception as e:
        print("exc", e, flush=True)
    # if html prefix, remove
    if os.path.exists(dest):
        d=open(dest,"rb").read(100)
        if d[:5] != b"PK\x03\x04" and b"<!DOCTYPE" in d[:200]:
            os.remove(dest)
            print("removed html prefix file", flush=True)
    time.sleep(3)
print("final size", os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
import zipfile
try:
    z=zipfile.ZipFile(dest); print("zip ok", len(z.namelist())); print("testzip", z.testzip())
except Exception as e: print("zip err", e)
