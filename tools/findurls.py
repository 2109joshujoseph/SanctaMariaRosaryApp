import re
p=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\repo.xml"
s=open(p,encoding="utf-8",errors="replace").read()
for m in re.finditer(r'<url>([^<]*platform[^<]*\.zip)</url>', s):
    print("PLATFORM:", m.group(1))
for m in re.finditer(r'<url>([^<]*build-tools[^<]*windows[^<]*\.zip)</url>', s):
    print("BUILDTOOLS:", m.group(1))
# print any 34
for line in s.splitlines():
    if "34" in line and ".zip" in line:
        print("ZIP34:", line.strip()[:200])
        break
