import shutil, os
src=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
dst=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\plat.zip"
# read in chunks
with open(src,"rb") as f, open(dst,"wb") as g:
    while True:
        chunk=f.read(1024*1024)
        if not chunk: break
        g.write(chunk)
print("copied", os.path.getsize(dst))
# now find PK and validate
data=open(dst,"rb").read()
idx=data.find(b"PK\x03\x04")
print("PK at", idx)
open(dst,"wb").write(data[idx:])
print("trimmed size", len(data)-idx)
import zipfile
z=zipfile.ZipFile(dst)
print("zip ok entries", len(z.namelist()))
print("testzip", z.testzip())
# extract android.jar
info=z.getinfo("android-34/android.jar")
print("entry size", info.file_size)
data2=z.read("android-34/android.jar")
print("read", len(data2))
open(r"C:\android.jar","wb").write(data2)
print("written", os.path.getsize(r"C:\android.jar"))
