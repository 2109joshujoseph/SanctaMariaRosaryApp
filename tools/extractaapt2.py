import zipfile, os
base=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven"
jar=os.path.join(base,"aapt2.jar")
out=os.path.join(base,"aapt2")
os.makedirs(out, exist_ok=True)
z=zipfile.ZipFile(jar)
print("entries", len(z.namelist()))
for n in z.namelist():
    if n.endswith(("aapt2.exe","aapt2")):
        print("AAPT2:", n)
        z.extract(n, out)
print("extracted")
# find
for root,_,files in os.walk(out):
    for f in files:
        if "aapt2" in f:
            print("FILE", os.path.join(root,f))
