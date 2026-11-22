import subprocess, os
base=r"C:\Users\JOSHU JOSEPH\OneDrive\Desktop\Mama Mary"
aapt2=os.path.join(base,".toolchain","maven","aapt2","aapt2.exe")
res=os.path.join(base,"app","src","main","res")
out=os.path.join(base,".toolchain","compiled_res.zip")
cmd=[aapt2,"compile","--dir",res,"-o",out]
print(cmd)
p=subprocess.run(cmd, capture_output=True, text=True, timeout=300)
print("rc",p.returncode)
print("stdout",p.stdout[:1000])
print("stderr",p.stderr[:1000])
print("out size", os.path.getsize(out) if os.path.exists(out) else 0)
