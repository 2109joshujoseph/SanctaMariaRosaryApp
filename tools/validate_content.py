import json, os, glob, sys

base = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "app", "src", "main", "assets", "content")
ok = True
for f in sorted(glob.glob(os.path.join(base, "*.json"))):
    try:
        with open(f, "r", encoding="utf-8") as fh:
            data = json.load(fh)
        if isinstance(data, list):
            print("OK", os.path.basename(f), "items:", len(data))
        else:
            print("OK", os.path.basename(f), "keys:", len(data))
    except Exception as e:
        ok = False
        print("ERROR", os.path.basename(f), e)
print("ALL VALID" if ok else "INVALID JSON")
sys.exit(0 if ok else 1)
