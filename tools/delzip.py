import os
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\buildtools34.zip"
if os.path.exists(p): os.remove(p); print("removed")
else: print("not found")
