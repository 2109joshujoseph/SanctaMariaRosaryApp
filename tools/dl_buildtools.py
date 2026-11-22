import subprocess, os, time
CURL = r"C:\Windows\System32\curl.exe"
BASE = r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary"
dest = os.path.join(BASE, ".toolchain", "buildtools34.zip")
url = "https://dl.google.com/android/repository/build-tools_r34-windows.zip"
cmd = [CURL, "-L", "--retry", "10", "--retry-delay", "5", "--connect-timeout", "30", "--max-time", "900", "-o", dest, url]
print("starting", flush=True)
subprocess.run(cmd, timeout=920)
print("done", flush=True)
print("size", os.path.getsize(dest) if os.path.exists(dest) else 0, flush=True)
