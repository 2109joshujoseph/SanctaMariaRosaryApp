import os
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\repo.xml"
print("exists", os.path.exists(p), "size", os.path.getsize(p) if os.path.exists(p) else 0)
