import os
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven\r8.jar"
print("size", os.path.getsize(p) if os.path.exists(p) else 0)
# check zip validity
import zipfile
try:
    z=zipfile.ZipFile(p); print("zip ok", len(z.namelist()))
except Exception as e: print("zip err", e)
