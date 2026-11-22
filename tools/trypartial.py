import zipfile, os
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\buildtools34.zip"
z=zipfile.ZipFile(p)
names=z.namelist()
print("total", len(names))
# find aapt2
for n in names:
    if n.endswith("aapt2.exe"):
        print("aapt2 entry:", n, z.getinfo(n).file_size)
# try extract aapt2
out=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\partial"
os.makedirs(out, exist_ok=True)
for n in names:
    if n.endswith(("aapt2.exe","d8.bat","apksigner.bat","zipalign.exe")):
        try:
            z.extract(n, out)
            print("extracted", n)
        except Exception as e:
            print("fail", n, e)
# list extracted
for root,_,files in os.walk(out):
    for f in files:
        print("FILE", os.path.join(root,f))
