import os
targets={"aapt2.exe","android.jar"}
root=r"C:\Users\JOSHU JOSEPH"
found=[]
for r,d,f in os.walk(root):
    for x in f:
        if x in targets:
            found.append(os.path.join(r,x))
    d[:] = [x for x in d if x not in (".git","node_modules")]
print("count", len(found))
for x in found: print(x)
