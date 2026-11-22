import json, os, glob
base = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "app", "src", "main", "assets", "content")
for f in sorted(glob.glob(os.path.join(base, "*.json"))):
    with open(f, encoding="utf-8") as fh:
        d = json.load(fh)
    print("==", os.path.basename(f), len(d) if isinstance(d, list) else "object")
    if isinstance(d, list):
        for o in d:
            print("   ", o.get("id", ""), "|", o.get("category", ""), "|", o.get("title_en", "")[:60], "| img:", o.get("image", ""))
    else:
        print("   keys:", ", ".join(d.keys()))
        if "title_en" in d:
            print("   ", d.get("title_en"), "| img:", d.get("image", ""))
