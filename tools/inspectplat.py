import zipfile
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
z=zipfile.ZipFile(dest)
info=z.getinfo("android-34/android.jar")
print("filename", info.filename)
print("file_size", info.file_size)
print("compress_size", info.compress_size)
print("compress_type", info.compress_type)
print("flag_bits", info.flag_bits)
print("header_offset", info.header_offset)
# try read with different approach
try:
    data=z.read(info)
    print("read ok", len(data))
except Exception as e:
    print("read err", repr(e))
# list a few entries around
for n in z.namelist():
    if "android.jar" in n:
        print("entry", n)
# try extract all? no
print("testzip", z.testzip())
