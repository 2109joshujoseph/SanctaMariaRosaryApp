import os, zipfile
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\buildtools34.zip"
print("size", os.path.getsize(p) if os.path.exists(p) else 0)
try:
    z=zipfile.ZipFile(p); print("names", len(z.namelist())); print("bad", z.testzip())
except Exception as e: print("err", e)
