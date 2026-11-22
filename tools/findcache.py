import os
candidates = [
 r"C:\Users\JOSHU JOSEPH\.gradle",
 r"C:\Users\JOSHU JOSEPH\.android",
 r"C:\Users\JOSHU JOSEPH\AppData\Local\Android",
 r"C:\Users\JOSHU JOSEPH\AppData\Roaming",
 r"C:\ProgramData",
]
for c in candidates:
    print("==", c, os.path.exists(c))
    if os.path.exists(c):
        for root,dirs,files in os.walk(c):
            for f in files:
                if f in ("android.jar","aapt2.exe"):
                    print("FOUND", os.path.join(root,f))
            # prune
            dirs[:] = [d for d in dirs if d not in ("caches","Cache",".git")]
            if len(dirs)>200: 
                dirs[:] = dirs[:200]
