import zipfile
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven\android-4.1.1.4.jar"
z=zipfile.ZipFile(p)
print("entries", len(z.namelist()))
for n in z.namelist():
    if n.endswith("Activity.class") or n.endswith("RippleDrawable.class"):
        print("FOUND", n)
print("has NotificationChannel?", any("NotificationChannel" in n for n in z.namelist()))
