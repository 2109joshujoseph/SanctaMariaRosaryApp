p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
data=open(p,"rb").read()
# find PK header
idx=data.find(b"PK\x03\x04")
print("PK at", idx)
if idx>0:
    data=data[idx:]
    open(p,"wb").write(data)
    print("stripped, new size", len(data))
import zipfile
try:
    z=zipfile.ZipFile(p); print("zip ok", len(z.namelist()))
except Exception as e: print("zip err", e)
