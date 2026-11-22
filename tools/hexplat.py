p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
data=open(p,"rb").read(64)
print(data[:64])
print("len", len(open(p,"rb").read()))
# check local file header signature at offset 0
import struct
print("first 4 bytes", data[:4])
# find PK signature
idx=data.find(b"PK\x03\x04")
print("PK header at", idx)
