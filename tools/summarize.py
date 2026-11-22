import subprocess, os
p=subprocess.run(["python","tools/verifyapk.py"], capture_output=True, text=True, timeout=300)
out=p.stdout
for line in out.splitlines():
    if any(k in line for k in ["application-label","sdkVersion","targetSdkVersion","jar verified","exists True"]):
        print(line)
print("size line:")
for line in out.splitlines():
    if "exists True" in line: print(line)
