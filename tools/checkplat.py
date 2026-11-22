import os
p=r"C:\Users\JOSHU JOSEPH\.local\share\opencode\shell\aeafa1d40012d7161704828e1a77d40844cf7fe9\sh_1fd027605001x8NpNv080YQtJI.out"
s=open(p,encoding="utf-8",errors="replace").read()
print(s[-1500:])
print("FINAL SIZE CHECK:")
base=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain"
zp=os.path.join(base,"platform34.zip")
print("platform34.zip", os.path.getsize(zp) if os.path.exists(zp) else 0)
# extracted android.jar?
for root,_,files in os.walk(os.path.join(base,"platform")):
    for f in files:
        if f=="android.jar":
            print("android.jar", os.path.join(root,f), os.path.getsize(os.path.join(root,f)))
