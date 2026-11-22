import subprocess, os
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\repo.xml"
cmd=[CURL,"-L","--max-time","280","-o",dest,"https://dl.google.com/android/repository/repository2-3.xml"]
p=subprocess.run(cmd, capture_output=True, text=True, timeout=290)
print("rc",p.returncode)
print("stderr",p.stderr[:200])
print("size", os.path.getsize(dest) if os.path.exists(dest) else 0)
