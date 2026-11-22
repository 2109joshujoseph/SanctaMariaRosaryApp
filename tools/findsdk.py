import os
targets={"aapt2.exe","d8.bat","apksigner.bat","zipalign.exe","android.jar"}
found=[]
for root,dirs,files in os.walk("C:\\"):
    for f in files:
        if f in targets:
            found.append(os.path.join(root,f))
    # prune huge dirs
    dirs[:] = [d for d in dirs if d not in ("WinSxS","node_modules",".git")]
    if len(found)>50: break
for f in found: print(f)
print("count", len(found))
