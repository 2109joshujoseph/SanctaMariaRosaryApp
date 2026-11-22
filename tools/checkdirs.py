import os
candidates = [
 r"C:\Users\JOSHU JOSEPH\.gradle",
 r"C:\Users\JOSHU JOSEPH\.android",
 r"C:\Users\JOSHU JOSEPH\AppData\Local\Android",
]
for c in candidates:
    print(c, os.path.exists(c))
    if os.path.exists(c):
        print("  dirs:", os.listdir(c)[:20])
