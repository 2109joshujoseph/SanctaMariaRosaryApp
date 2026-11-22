import os, glob
p = r"C:\Users\JOSHU JOSEPH\.local\share\opencode\shell"
for root,_,files in os.walk(p):
    for f in files:
        if f.endswith(".out"):
            fp=os.path.join(root,f)
            print("==",fp)
            try:
                print(open(fp,encoding="utf-8",errors="replace").read()[:2000])
            except Exception as e:
                print("err",e)
