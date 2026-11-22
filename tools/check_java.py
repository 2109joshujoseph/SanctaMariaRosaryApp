import os, glob
for p in [
    r"C:\Program Files\JetBrains\PyCharm Community Edition 2022.1.1\jbr\bin",
    r"C:\Program Files\JetBrains\PyCharm 2022.1\jbr\bin",
]:
    print("DIR", p, os.path.isdir(p))
    if os.path.isdir(p):
        print(sorted(os.listdir(p)))
