import os, sys, time
print("start", flush=True)
BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
print("base", BASE, flush=True)
TC = os.path.join(BASE, ".toolchain")
print("tc", TC, flush=True)
os.makedirs(TC, exist_ok=True)
print("makedirs ok", flush=True)
print("done", flush=True)
