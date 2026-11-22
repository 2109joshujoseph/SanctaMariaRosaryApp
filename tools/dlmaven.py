import subprocess, os
CURL=r"C:\Windows\System32\curl.exe"
BASE=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven"
os.makedirs(BASE, exist_ok=True)
jobs = [
 ("aapt2.jar","https://dl.google.com/dl/android/maven2/com/android/tools/build/aapt2/8.2.0-10154469/aapt2-8.2.0-10154469-windows.jar"),
 ("r8.jar","https://dl.google.com/dl/android/maven2/com/android/tools/r8/8.2.47/r8-8.2.47.jar"),
]
for name,url in jobs:
    dest=os.path.join(BASE,name)
    cmd=[CURL,"-L","--retry","5","--connect-timeout","30","--max-time","300","-o",dest,url]
    print("downloading",name,flush=True)
    subprocess.run(cmd, timeout=320)
    print("done",name, os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
# android-all
dest=os.path.join(BASE,"android-all.jar")
cmd=[CURL,"-L","--retry","5","--connect-timeout","30","--max-time","900","-o",dest,"https://repo1.maven.org/maven2/org/robolectric/android-all/13-robolectric-9030017/android-all-13-robolectric-9030017.jar"]
print("downloading android-all", flush=True)
subprocess.run(cmd, timeout=920)
print("done android-all", os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
print("ALL")
