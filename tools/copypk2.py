import os, zipfile
src=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
dst=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\plat.zip"
with open(src,"rb") as f, open(dst,"wb") as g:
    while True:
        chunk=f.read(1024*1024)
        if not chunk: break
        g.write(chunk)
print("copied", os.path.getsize(dst))
data=open(dst,"rb").read()
idx=data.find(b"PK\x03\x04")
print("PK at", idx)
open(dst,"wb").write(data[idx:])
print("trimmed size", len(data)-idx)
z=zipfile.ZipFile(dst)
print("zip ok entries", len(z.namelist()))
print("testzip", z.testzip())
info=z.getinfo("android-34/android.jar")
print("entry size", info.file_size)
d=z.read("android-34/android.jar")
print("read", len(d))
outjar=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\android.jar"
open(outjar,"wb").write(d)
print("written", os.path.getsize(outjar))
z2=zipfile.ZipFile(outjar)
print("jar entries", len(z2.namelist()))
print("has Activity", "android/app/Activity.class" in z2.namelist())
