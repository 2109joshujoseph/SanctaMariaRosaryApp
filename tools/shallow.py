import os
targets={"r8.jar","d8.jar","classes.dex","dx.jar"}
# check a few roots shallow
roots=["C:\\","C:\\Program Files","C:\\Program Files (x86)","C:\\Users"]
for r in roots:
    try:
        for name in os.listdir(r):
            p=os.path.join(r,name)
            if os.path.isdir(p):
                # one level
                try:
                    for n2 in os.listdir(p):
                        if n2 in targets:
                            print("FOUND", os.path.join(p,n2))
                except Exception: pass
    except Exception: pass
print("done")
