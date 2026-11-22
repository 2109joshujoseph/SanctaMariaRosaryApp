import os
base=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven"
for f in os.listdir(base):
    print(f, os.path.getsize(os.path.join(base,f)))
