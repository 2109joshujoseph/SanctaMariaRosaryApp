import os
d=r"C:\Program Files\JetBrains\PyCharm Community Edition 2022.1.1\jbr\bin"
print(sorted(f for f in os.listdir(d) if "jar" in f.lower() or f.endswith(".exe"))[:40])
