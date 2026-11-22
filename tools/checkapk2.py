import zipfile
apk=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\dist\SanctaMaria-1.0.0.apk"
z=zipfile.ZipFile(apk)
for n in ["AndroidManifest.xml","classes.dex"]:
    print(n, "compress_type", z.getinfo(n).compress_type)
print("total", len(z.namelist()))
