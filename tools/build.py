import os, subprocess, shutil, sys

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
JBR = r"C:\Progra~1\JetBrains\PYCHAR~2.1\jbr\bin"
TC = os.path.join(BASE, ".toolchain")
MAVEN = os.path.join(TC, "maven")
DIST = os.path.join(BASE, "dist")
BUILD = os.path.join(BASE, "build")
APP = os.path.join(BASE, "app", "src", "main")

def run(cmd, **kw):
    print("RUN", " ".join(str(x) for x in cmd))
    p = subprocess.run(cmd, capture_output=True, text=True, **kw)
    if p.returncode != 0:
        print("STDOUT", p.stdout[-2000:])
        print("STDERR", p.stderr[-4000:])
        raise SystemExit("FAILED: " + " ".join(str(x) for x in cmd))
    return p

def main():
    os.makedirs(DIST, exist_ok=True)
    os.makedirs(BUILD, exist_ok=True)
    # clean
    for f in os.listdir(BUILD):
        fp = os.path.join(BUILD, f)
        if os.path.isfile(fp): os.remove(fp)
        else: shutil.rmtree(fp, ignore_errors=True)

    aapt2 = os.path.join(MAVEN, "aapt2", "aapt2.exe")
    javac = os.path.join(JBR, "javac.exe")
    java = os.path.join(JBR, "java.exe")
    r8jar = os.path.join(MAVEN, "r8.jar")
    androidjar = os.path.join(TC, "platform", "android.jar")

    # 1. compile resources (already done) - recompile
    reszip = os.path.join(TC, "compiled_res.zip")
    run([aapt2, "compile", "--dir", os.path.join(APP, "res"), "-o", reszip])

    # 2. link resources
    manifest = os.path.join(APP, "AndroidManifest.xml")
    assets = os.path.join(APP, "assets")
    linked = os.path.join(BUILD, "linked.apk")
    run([aapt2, "link", "-o", linked, "-I", androidjar,
         "--manifest", manifest, "-A", assets,
         "--no-compress",
         "--java", os.path.join(BUILD, "gen"), reszip])

    # 3. compile java
    srcdir = os.path.join(BASE, "app", "src", "main", "java")
    gen = os.path.join(BUILD, "gen")
    classes = os.path.join(BUILD, "classes")
    os.makedirs(classes, exist_ok=True)
    os.makedirs(gen, exist_ok=True)
    # collect sources
    srcs = []
    for root,_,files in os.walk(srcdir):
        for f in files:
            if f.endswith(".java"): srcs.append(os.path.join(root,f))
    for root,_,files in os.walk(gen):
        for f in files:
            if f.endswith(".java"): srcs.append(os.path.join(root,f))
    run([javac, "-encoding", "UTF-8", "-source", "8", "-target", "8",
         "-bootclasspath", androidjar, "-d", classes] + srcs)

    # 4. dex
    dexdir = os.path.join(BUILD, "dex")
    os.makedirs(dexdir, exist_ok=True)
    # create jar of classes
    jarpath = os.path.join(BUILD, "classes.jar")
    run(["jar", "cf", jarpath, "-C", classes, "."], cwd=classes) if False else None
    # use JBR jar
    # create jar of classes using Python (no jar.exe in JBR)
    import zipfile as _zf
    jarpath = os.path.join(BUILD, "classes.jar")
    with _zf.ZipFile(jarpath, "w", _zf.ZIP_DEFLATED) as zj:
        for root,_,files in os.walk(classes):
            for f in files:
                if f.endswith(".class"):
                    full = os.path.join(root, f)
                    arc = os.path.relpath(full, classes)
                    zj.write(full, arc)
    dexout = os.path.join(BUILD, "classes.dex")
    run([java, "-cp", r8jar, "com.android.tools.r8.D8",
         "--output", dexdir, jarpath])
    # D8 outputs classes.dex in dexdir
    produced = os.path.join(dexdir, "classes.dex")
    shutil.copy(produced, dexout)

    # 5. package unaligned apk with classes.dex stored uncompressed
    import zipfile
    unaligned = os.path.join(BUILD, "unaligned.apk")
    zin = zipfile.ZipFile(linked, "r")
    zout = zipfile.ZipFile(unaligned, "w", zipfile.ZIP_STORED)
    for info in zin.infolist():
        data = zin.read(info.filename)
        ni = zipfile.ZipInfo(info.filename)
        ni.compress_type = zipfile.ZIP_STORED
        zout.writestr(ni, data)
    zi = zipfile.ZipInfo("classes.dex")
    zi.compress_type = zipfile.ZIP_STORED
    with open(dexout, "rb") as f:
        zout.writestr(zi, f.read())
    zin.close()
    zout.close()

    # 6. zipalign (4-byte alignment required by Android)
    zipalign = os.path.join(TC, "bin", "zipalign.exe")
    aligned = os.path.join(BUILD, "aligned.apk")
    run([zipalign, "-p", "-f", "4", unaligned, aligned])

    # 7. sign with APK Signature Scheme v2 & v3 (required by Android 11+)
    outapk = os.path.join(DIST, "SanctaMaria-1.1.0.apk")
    shutil.copy(aligned, outapk)
    ks = os.path.join(BASE, "release.keystore")
    uber_signer = os.path.join(MAVEN, "uber-apk-signer-1.3.0.jar")
    run([java, "-jar", uber_signer, "-a", outapk,
         "--ks", ks, "--ksAlias", "rosary",
         "--ksPass", "Rosary@Joe2026", "--ksKeyPass", "Rosary@Joe2026",
         "--overwrite", "--allowResign", "--skipZipAlign", "--verbose"])

    # 8. verify final APK alignment
    run([zipalign, "-c", "4", outapk])

    # Also keep SanctaMaria-1.0.0.apk in sync for backward compatibility
    legacy_apk = os.path.join(DIST, "SanctaMaria-1.0.0.apk")
    shutil.copy2(outapk, legacy_apk)

    print("DONE", outapk)

if __name__ == "__main__":
    main()
