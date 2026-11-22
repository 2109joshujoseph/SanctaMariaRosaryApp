import re
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\repo.xml"
s=open(p,encoding="utf-8",errors="replace").read()
for line in s.splitlines():
    if "platform-34" in line and ".zip" in line:
        print(line.strip())
