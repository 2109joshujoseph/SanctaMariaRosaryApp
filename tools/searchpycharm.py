import os
root=r"C:\Program Files\JetBrains\PyCharm Community Edition 2022.1.1"
found=[]
for r,d,f in os.walk(root):
    for x in f:
        if "d8" in x.lower() or "r8" in x.lower() or x=="dx.jar":
            found.append(os.path.join(r,x))
print(len(found))
for x in found[:20]: print(x)
