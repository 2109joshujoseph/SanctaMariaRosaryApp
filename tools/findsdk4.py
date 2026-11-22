import os
root=r"C:\Program Files\JetBrains"
found=[]
for r,d,f in os.walk(root):
    for x in f:
        if x in ("aapt2.exe","android.jar"):
            found.append(os.path.join(r,x))
print("count", len(found))
for x in found: print(x)
