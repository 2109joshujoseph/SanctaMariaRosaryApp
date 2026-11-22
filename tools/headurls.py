import subprocess, os
CURL = r"C:\Windows\System32\curl.exe"
urls = [
 "https://dl.google.com/android/repository/build-tools_r34.0.0-windows.zip",
 "https://dl.google.com/android/repository/build-tools_r34-windows.zip",
 "https://dl.google.com/android/repository/platform-34_r03.zip",
 "https://dl.google.com/android/repository/platforms/android-34.zip",
 "https://dl.google.com/android/repository/platform-34_r02.zip",
]
for u in urls:
    print("URL", u)
    try:
        r = subprocess.run([CURL, "-sI", "-L", "--max-time", "30", u], capture_output=True, text=True, timeout=40)
        for line in r.stdout.splitlines():
            if line.lower().startswith(("http/", "content-length", "content-type")):
                print("  ", line)
    except Exception as e:
        print("  err", e)
    print()
