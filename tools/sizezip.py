import os
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\buildtools34.zip"
print("size", os.path.getsize(p) if os.path.exists(p) else 0)
