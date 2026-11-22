import wave, struct, math, os

BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUT = os.path.join(BASE, "app", "src", "main", "res", "raw")
os.makedirs(OUT, exist_ok=True)

def write_wav(path, samples, sr=22050):
    with wave.open(path, "w") as w:
        w.setnchannels(1)
        w.setsampwidth(2)
        w.setframerate(sr)
        for s in samples:
            v = max(-32767, min(32767, int(s * 32767)))
            w.writeframes(struct.pack("<h", v))

def env(t, attack=0.05, decay=1.0):
    if t < attack:
        return t / attack
    return math.exp(-(t - attack) / decay)

# splash_ambient: 12s soft pad (C major chord with slow LFO)
sr = 22050
dur = 12.0
N = int(sr * dur)
samples = []
for i in range(N):
    t = i / sr
    # chord frequencies
    f = [130.81, 196.00, 261.63, 329.63, 392.00]
    val = 0
    for k, freq in enumerate(f):
        val += (0.12 - k*0.015) * math.sin(2*math.pi*freq*t + 0.3*k*math.sin(2*math.pi*0.07*t))
    # gentle swell
    swell = 0.6 + 0.4*math.sin(2*math.pi*0.05*t)
    val *= swell * env(t, 1.2, 4.0) * (1 - t/dur*0.3)
    # soft high shimmer
    val += 0.03 * math.sin(2*math.pi*1046.5*t) * (0.5+0.5*math.sin(2*math.pi*0.13*t)) * env(t, 1.5, 3.0)
    samples.append(val * 0.5)
write_wav(os.path.join(OUT, "splash_ambient.wav"), samples, sr)
print("splash done")

# ave_maria: 3.5s bell arpeggio (original melody)
dur = 3.5
N = int(sr * dur)
samples = []
notes = [(523.25,0.0,0.7),(659.25,0.4,0.7),(783.99,0.8,0.8),(1046.5,1.2,0.9),(783.99,1.7,0.7),(659.25,2.1,0.7),(523.25,2.5,0.9),(392.0,2.9,1.0)]
for i in range(N):
    t = i / sr
    val = 0
    for freq, start, amp in notes:
        dt = t - start
        if 0 <= dt < 0.9:
            # bell envelope
            e = math.exp(-dt*2.4) * (1 - math.exp(-dt*40))
            val += amp * e * (math.sin(2*math.pi*freq*dt) + 0.35*math.sin(2*math.pi*freq*2*dt) + 0.12*math.sin(2*math.pi*freq*3*dt))
    # soft pad underneath
    pad = 0.05*math.sin(2*math.pi*130.81*t)*env(t,0.3,2.0)
    val += pad
    samples.append(val * 0.45)
write_wav(os.path.join(OUT, "ave_maria.wav"), samples, sr)
print("ave done")

# completion: 4.0s ascending chime
dur = 4.0
N = int(sr * dur)
samples = []
chime = [(392.0,0.0),(523.25,0.35),(659.25,0.7),(783.99,1.05),(1046.5,1.4),(1318.5,1.75),(1046.5,2.1),(783.99,2.45),(1318.5,2.8)]
for i in range(N):
    t = i / sr
    val = 0
    for freq, start in chime:
        dt = t - start
        if 0 <= dt < 1.1:
            e = math.exp(-dt*1.9) * (1 - math.exp(-dt*50))
            val += e * (math.sin(2*math.pi*freq*dt) + 0.25*math.sin(2*math.pi*freq*2*dt))
    # warm tail
    val += 0.08*math.sin(2*math.pi*261.63*t)*math.exp(-(t-2.6)/1.0) if t>2.6 else 0
    samples.append(val * 0.4)
write_wav(os.path.join(OUT, "completion.wav"), samples, sr)
print("completion done")
print("all written")
