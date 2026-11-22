import subprocess, os
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\repo.xml"
cmd=[CURL,"-s","-L","--max-time","120","-o",dest,"https://dl.google.com/android/repository/repository2-3.xml"]
print(subprocess.run(cmd, capture_output=True, text=True, timeout=130).returncode)
print("size", os.path.getsize(dest) if os.path.exists(dest) else 0)
