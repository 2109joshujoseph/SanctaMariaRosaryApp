import os
roots=["C:\\Program Files","C:\\Program Files (x86)","C:\\Users"]
found=[]
for r in roots:
    for root,dirs,files in os.walk(r):
        for f in files:
            if f=="jarsigner.exe":
                found.append(os.path.join(root,f))
        dirs[:] = [d for d in dirs if d not in (".git","node_modules")]
print(len(found))
for f in found: print(f)
