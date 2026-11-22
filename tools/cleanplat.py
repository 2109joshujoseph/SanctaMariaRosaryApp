import os
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
if os.path.exists(p):
    data=open(p,"rb").read(100)
    if data[:5] != b"PK\x03\x04" and b"<!DOCTYPE" in data[:200]:
        os.remove(p)
        print("removed html-prefixed file")
    else:
        print("file ok or not html, size", os.path.getsize(p))
else:
    print("not exists")
# also remove temp plat
tp=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\plat.zip"
if os.path.exists(tp): os.remove(tp); print("removed temp")
