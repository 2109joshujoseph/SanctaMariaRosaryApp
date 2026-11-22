import zipfile
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
z=zipfile.ZipFile(dest)
print("testzip", z.testzip())
print("entries", len(z.namelist()))
# find android.jar
for n in z.namelist():
    if n.endswith("android.jar"):
        print("entry", n, z.getinfo(n).file_size)
        try:
            with z.open(n) as f:
                data=f.read(100)
                print("first bytes", data[:20])
        except Exception as e:
            print("open err", e)
