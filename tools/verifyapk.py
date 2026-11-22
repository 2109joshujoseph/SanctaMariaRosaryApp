import os, subprocess
base=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary"
apk=os.path.join(base,"dist","Rosary-1.0.0.apk")
print("exists", os.path.exists(apk), "size", os.path.getsize(apk) if os.path.exists(apk) else 0)
aapt2=os.path.join(base,".toolchain","maven","aapt2","aapt2.exe")
# dump badging
p=subprocess.run([aapt2,"dump","badging",apk], capture_output=True, text=True, timeout=120)
print("aapt2 rc", p.returncode)
print(p.stdout[:1500])
print("stderr", p.stderr[:500])
# jarsigner verify
js=r"C:\Users\JOSHU JOSEPH\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.2.v20220201-1208\jre\bin\jarsigner.exe"
p2=subprocess.run([js,"-verify","-verbose","-certs",apk], capture_output=True, text=True, timeout=120)
print("jarsigner rc", p2.returncode)
print(p2.stdout[-1000:])
print("stderr", p2.stderr[-500:])
