import zipfile, os
src=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\plat.zip"
z=zipfile.ZipFile(src)
info=z.getinfo("android-34/android.jar")
print("info ok", info.file_size)
try:
    with z.open(info) as f:
        out=r"C:\Users\JOSHU JOSEPH\AppData\Local\Temp\opencode\android.jar"
        with open(out,"wb") as g:
            while True:
                chunk=f.read(1024*1024)
                if not chunk: break
                g.write(chunk)
        print("written", os.path.getsize(out))
except Exception as e:
    print("err", repr(e))
    # try seeking manually
    print("header_offset", info.header_offset)
    print("compress_type", info.compress_type)
