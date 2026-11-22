import sys
print(sys.version)
try:
    import PIL
    print("PIL", PIL.__version__)
except Exception as e:
    print("NO PIL", e)
try:
    import numpy
    print("numpy", numpy.__version__)
except Exception as e:
    print("NO numpy", e)
try:
    import fontTools
    print("fontTools", fontTools.version)
except Exception as e:
    print("NO fontTools", e)
