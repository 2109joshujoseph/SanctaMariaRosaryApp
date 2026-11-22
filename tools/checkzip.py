import os, zipfile
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\buildtools34.zip"
print("size", os.path.getsize(p))
try:
    z=zipfile.ZipFile(p)
    print("names", len(z.namelist()))
    print("bad", z.testzip())
    print([n for n in z.namelist() if n.endswith("aapt2.exe")][:5])
except Exception as e:
    print("err", e)
