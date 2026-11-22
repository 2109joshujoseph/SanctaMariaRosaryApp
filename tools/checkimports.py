import os, re
root=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\app\src\main\java\com\joeinitiatives\rosary"
for fn in os.listdir(root):
    if not fn.endswith(".java"): continue
    p=os.path.join(root,fn)
    src=open(p,encoding="utf-8").read()
    uses = "OnClickListener" in src
    imports = "import android.view.View.OnClickListener;" in src or "import android.view.View.*" in src
    if uses and not imports:
        print("MISSING IMPORT:", fn)
