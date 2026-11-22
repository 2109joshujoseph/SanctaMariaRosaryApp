import subprocess, os
keytool=r"C:\Progra~1\JetBrains\PYCHAR~2.1\jbr\bin\keytool.exe"
base=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary"
ks=os.path.join(base,"release.keystore")
cmd=[keytool,"-genkeypair","-v","-keystore",ks,"-alias","rosary","-keyalg","RSA","-keysize","2048","-validity","10000","-storepass","Rosary@Joe2026","-keypass","Rosary@Joe2026","-dname","CN=Joe Initiatives, OU=Mobile, O=Joe Initiatives, L=Kerala, ST=Kerala, C=IN"]
print(subprocess.run(cmd, capture_output=True, text=True, timeout=120).stdout)
print(subprocess.run(cmd, capture_output=True, text=True, timeout=120).stderr)
print("exists", os.path.exists(ks), os.path.getsize(ks) if os.path.exists(ks) else 0)
