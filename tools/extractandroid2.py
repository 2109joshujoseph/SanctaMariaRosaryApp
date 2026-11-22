import zipfile, os
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
out=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform"
os.makedirs(out, exist_ok=True)
z=zipfile.ZipFile(dest)
info=z.getinfo("android-34/android.jar")
print("size", info.file_size)
data=z.read("android-34/android.jar")
print("read", len(data))
open(os.path.join(out,"android.jar"),"wb").write(data)
print("written", os.path.getsize(os.path.join(out,"android.jar")))
# test jar
import zipfile as zf
z2=zf.ZipFile(os.path.join(out,"android.jar"))
print("jar entries", len(z2.namelist()))
print("has Activity", "android/app/Activity.class" in z2.namelist())
