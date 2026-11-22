p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\platform34.zip"
data=open(p,"rb").read()
print("len", len(data))
# find all PK headers
pos=0
locs=[]
while True:
    i=data.find(b"PK\x03\x04", pos)
    if i==-1: break
    locs.append(i)
    pos=i+4
print("num PK headers", len(locs))
print("first few", locs[:10])
print("last few", locs[-5:])
# try each as start and test zip
import zipfile, io
for idx in locs:
    try:
        z=zipfile.ZipFile(io.BytesIO(data[idx:]))
        if z.testzip() is None:
            print("VALID start at", idx)
            # save
            open(p,"wb").write(data[idx:])
            print("saved, size", len(data)-idx)
            break
    except Exception as e:
        pass
else:
    print("no valid start found")
