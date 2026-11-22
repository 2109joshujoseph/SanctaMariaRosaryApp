import os
root=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\app\src\main\java\com\joeinitiatives\rosary"
for fn in os.listdir(root):
    if not fn.endswith(".java"): continue
    p=os.path.join(root,fn)
    src=open(p,encoding="utf-8").read()
    if "OnClickListener" not in src: continue
    if "import android.view.View.OnClickListener;" in src: continue
    if "import android.view.View;" in src:
        src=src.replace("import android.view.View;\n", "import android.view.View;\nimport android.view.View.OnClickListener;\n", 1)
        open(p,"w",encoding="utf-8").write(src)
        print("fixed", fn)
