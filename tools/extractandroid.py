import zipfile, os
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
out=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform"
os.makedirs(out, exist_ok=True)
z=zipfile.ZipFile(dest)
info=z.getinfo("android-34/android.jar")
print("size", info.file_size)
try:
    data=z.read("android-34/android.jar")
    print("read", len(data))
    open(os.path.join(out,"android.jar"),"wb").write(data)
    print("written")
except Exception as e:
    print("err", e)
    # try extract
    try:
        z.extract("android-34/android.jar", out)
        print("extracted")
    except Exception as e2:
        print("extract err", e2)
