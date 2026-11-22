import zipfile, os
src=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
out=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform"
os.makedirs(out, exist_ok=True)
z=zipfile.ZipFile(src)
print("testzip", z.testzip())
info=z.getinfo("android-34/android.jar")
print("entry size", info.file_size)
data=z.read("android-34/android.jar")
print("read", len(data))
dst=os.path.join(out,"android.jar")
open(dst,"wb").write(data)
print("written", os.path.getsize(dst))
z2=zipfile.ZipFile(dst)
print("jar entries", len(z2.namelist()))
print("has Activity", "android/app/Activity.class" in z2.namelist())
print("has NotificationChannel", "android/app/NotificationChannel.class" in z2.namelist())
