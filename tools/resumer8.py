import subprocess, os
CURL=r"C:\Windows\System32\curl.exe"
dest=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary\.toolchain\maven\r8.jar"
url="https://dl.google.com/dl/android/maven2/com/android/tools/r8/8.2.47/r8-8.2.47.jar"
cmd=[CURL,"-L","-C","-","--retry","10","--retry-delay","5","--connect-timeout","30","--max-time","580","-o",dest,url]
print("resume r8", flush=True)
subprocess.run(cmd, timeout=590)
print("size", os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
