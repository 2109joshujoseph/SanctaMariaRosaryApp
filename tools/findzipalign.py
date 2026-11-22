import os
roots=["C:\\"]
found=[]
for r in roots:
    for root,dirs,files in os.walk(r):
        for f in files:
            if f.lower()=="zipalign.exe":
                found.append(os.path.join(root,f))
        dirs[:] = [d for d in dirs if d not in (".git","node_modules","WinSxS")]
        if len(found)>5: break
print(len(found))
for f in found: print(f)
