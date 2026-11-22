import subprocess, os
jar=r"C:\Program Files\JetBrains\PyCharm Community Edition 2022.1.1\jbr\bin\jar.exe"
zipf=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\plat.zip"
out=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\extracted"
os.makedirs(out, exist_ok=True)
# list
p=subprocess.run([jar,"tf",zipf], capture_output=True, text=True, timeout=120)
print("rc list", p.returncode)
print([l for l in p.stdout.splitlines() if "android.jar" in l])
# extract just android-34/android.jar
p2=subprocess.run([jar,"xf",zipf,"android-34/android.jar"], cwd=out, capture_output=True, text=True, timeout=300)
print("rc extract", p2.returncode)
print("stderr", p2.stderr[:500])
src=os.path.join(out,"android-34","android.jar")
dst=os.path.join(out,"android.jar")
if os.path.exists(src):
    os.replace(src, dst)
    print("moved, size", os.path.getsize(dst))
else:
    print("not found after extract")
    # list extracted
    for root,_,files in os.walk(out):
        for f in files:
            if f.endswith(".jar"):
                print("JAR", os.path.join(root,f))
