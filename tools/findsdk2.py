import os
targets={"aapt2.exe","d8.bat","apksigner.bat","zipalign.exe","android.jar"}
roots=["C:\\Program Files","C:\\Program Files (x86)","C:\\Users","C:\\Android","C:\\sdk","C:\\tools"]
found=[]
for r in roots:
    if not os.path.exists(r): continue
    for root,dirs,files in os.walk(r):
        for f in files:
            if f in targets:
                found.append(os.path.join(root,f))
        dirs[:] = [d for d in dirs if d not in ("WinSxS","node_modules",".git","Cache")]
        if len(found)>100: break
for f in found: print(f)
print("count", len(found))
