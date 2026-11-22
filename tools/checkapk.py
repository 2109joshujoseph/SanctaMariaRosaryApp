import zipfile, os
apk=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\dist\Rosary-1.0.0.apk"
z=zipfile.ZipFile(apk)
print("entries", len(z.namelist()))
print("has classes.dex", "classes.dex" in z.namelist())
print("has manifest", "AndroidManifest.xml" in z.namelist())
print("dex size", z.getinfo("classes.dex").file_size if "classes.dex" in z.namelist() else None)
# check alignment? print first few entries
for n in z.namelist()[:10]:
    i=z.getinfo(n)
    print(n, i.file_size, i.compress_type)
