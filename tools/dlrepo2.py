import subprocess
CURL=r"C:\Windows\System32\curl.exe"
url="https://dl.google.com/android/repository/repository2-3.xml"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\repo.xml"
cmd=[CURL,"-L","--max-time","90","-o",dest,url]
p=subprocess.run(cmd, capture_output=True, text=True, timeout=100)
print("rc",p.returncode)
print("stderr",p.stderr[:300])
import os
print("size", os.path.getsize(dest) if os.path.exists(dest) else 0)
