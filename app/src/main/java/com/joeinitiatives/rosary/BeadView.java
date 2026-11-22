package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/** Traditional rosary layout drawn on canvas, with touchable beads. */
public class BeadView extends View {
    public interface Listener { void onBead(String key); }

    private final Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint chain = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint monogram = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final HashMap<String, float[]> pos = new HashMap<>();
    private final HashMap<String, Float> sizes = new HashMap<>();
    private final HashSet<String> completed = new HashSet<>();
    private String active = "cross";
    private Listener listener;
    private float cx, cy, R, crossY;
    private boolean animate;
    private long start = System.currentTimeMillis();

    public BeadView(Context c) {
        super(c);
        animate = Prefs.anim(c);
        monogram.setTypeface(Ui.serifBold());
        monogram.setTextAlign(Paint.Align.CENTER);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setListener(Listener l) { this.listener = l; }

    public void setState(Set<String> done, String activeKey) {
        completed.clear();
        if (done != null) completed.addAll(done);
        active = activeKey;
        invalidate();
    }

    private void layout() {
        pos.clear();
        sizes.clear();
        float w = getWidth(), h = getHeight();
        if (w <= 0 || h <= 0) return;
        cx = w / 2f;
        cy = h * 0.27f;
        R = Math.min(w * 0.42f, h * 0.27f);
        float small = Math.max(Ui.dp(getContext(), 3.2f), R * 0.045f);
        float large = Math.max(Ui.dp(getContext(), 5.4f), R * 0.075f);

        for (int d = 0; d < 5; d++) {
            float segStart = -90f + d * 72f + 3.5f;
            put("L" + d, segStart, large);
            for (int i = 0; i < 10; i++) {
                put("S" + d + "_" + i, segStart + 6.5f + i * 6.05f, small);
            }
        }

        float top = cy + R;
        float medalY = top + h * 0.050f;
        putPoint("medal", cx, medalY, large * 1.35f);
        float s1 = medalY + h * 0.045f;
        putPoint("p0", cx, s1, small);
        putPoint("p1", cx, s1 + h * 0.042f, small);
        putPoint("p2", cx, s1 + h * 0.084f, small);
        float ofY = s1 + h * 0.084f + h * 0.052f;
        putPoint("pendant1", cx, ofY, large);
        float creedY = ofY + h * 0.055f;
        putPoint("pendant0", cx, creedY, large);
        crossY = creedY + h * 0.066f;
        putPoint("cross", cx, crossY, large * 1.5f);
    }

    private void put(String key, float angleDeg, float r) {
        double a = Math.toRadians(angleDeg);
        putPoint(key, cx + (float) Math.cos(a) * R, cy + (float) Math.sin(a) * R, r);
    }

    private void putPoint(String key, float x, float y, float r) {
        pos.put(key, new float[]{x, y});
        sizes.put(key, r);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        layout();
    }

    @Override
    protected void onDraw(Canvas c) {
        if (pos.isEmpty()) layout();
        if (pos.isEmpty()) return;
        float t = (System.currentTimeMillis() - start) / 1000f;
        boolean pulse = animate && Prefs.anim(getContext());

        chain.setStyle(Paint.Style.STROKE);
        chain.setStrokeWidth(Math.max(1f, Ui.dp(getContext(), 1)));
        chain.setColor(0x55C9A227);
        c.drawCircle(cx, cy, R, chain);

        float top = cy + R;
        float[] medal = pos.get("medal");
        if (medal != null) c.drawLine(cx, top, cx, medal[1], chain);
        float[] cross = pos.get("cross");
        if (cross != null) {
            float[] creed = pos.get("pendant0");
            if (creed != null) c.drawLine(cx, creed[1] + sizes.get("pendant0"), cx, cross[1] - sizes.get("cross"), chain);
        }
        // thread through pendant beads
        String[] pend = {"medal", "p0", "p1", "p2", "pendant1", "pendant0"};
        for (int i = 0; i < pend.length - 1; i++) {
            float[] a = pos.get(pend[i]);
            float[] b = pos.get(pend[i + 1]);
            if (a != null && b != null) c.drawLine(a[0], a[1], b[0], b[1], chain);
        }

        for (String key : pos.keySet()) {
            if ("cross".equals(key)) continue;
            float[] p = pos.get(key);
            float r = sizes.get(key);
            boolean done = completed.contains(key);
            boolean cur = key.equals(active);
            if (cur) {
                float glowR = r * (pulse ? (3.2f + 0.5f * (float) Math.sin(t * 3f)) : 3.4f);
                glow.setShader(new RadialGradient(p[0], p[1], glowR,
                        new int[]{0xAAE8C96A, 0x44E8C96A, 0x00E8C96A}, new float[]{0f, 0.45f, 1f}, Shader.TileMode.CLAMP));
                c.drawCircle(p[0], p[1], glowR, glow);
            }
            fill.setStyle(Paint.Style.FILL);
            fill.setColor(done ? Ui.GOLD : (cur ? Ui.WHITE : 0xFFF6EFDF));
            c.drawCircle(p[0], p[1], r, fill);
            stroke.setStyle(Paint.Style.STROKE);
            stroke.setStrokeWidth(Math.max(1f, Ui.dp(getContext(), cur ? 2.2f : 1f)));
            stroke.setColor(cur ? Ui.BLUE : (done ? 0xFFB08A1E : 0xFFD5C7A6));
            c.drawCircle(p[0], p[1], r, stroke);
            if (cur) {
                stroke.setStrokeWidth(Math.max(1f, Ui.dp(getContext(), 1f)));
                stroke.setColor(0x99C9A227);
                c.drawCircle(p[0], p[1], r + Ui.dp(getContext(), 4), stroke);
            }
        }

        // centre medal with a Marian monogram
        float[] m = pos.get("medal");
        if (m != null) {
            float r = sizes.get("medal");
            fill.setColor(0xFF1B3A6B);
            c.drawCircle(m[0], m[1], r, fill);
            stroke.setColor(Ui.GOLD);
            stroke.setStrokeWidth(Math.max(1f, Ui.dp(getContext(), 1.4f)));
            c.drawCircle(m[0], m[1], r, stroke);
            monogram.setTextSize(r * 1.5f);
            monogram.setColor(0xFFE8C96A);
            c.drawText("M", m[0], m[1] + r * 0.55f, monogram);
            if ("medal".equals(active)) {
                glow.setShader(new RadialGradient(m[0], m[1], r * 3.2f,
                        new int[]{0x88E8C96A, 0x00E8C96A}, null, Shader.TileMode.CLAMP));
                c.drawCircle(m[0], m[1], r * 3.2f, glow);
            }
        }

        // cross
        if (cross != null) {
            boolean cur = "cross".equals(active);
            boolean done = completed.contains("cross");
            float s = sizes.get("cross") * 2.2f;
            if (cur && pulse) {
                glow.setShader(new RadialGradient(cross[0], cross[1], s * 1.5f,
                        new int[]{0x88E8C96A, 0x00E8C96A}, null, Shader.TileMode.CLAMP));
                c.drawCircle(cross[0], cross[1], s * 1.5f, glow);
            }
            fill.setColor(done ? Ui.GOLD : (cur ? Ui.BLUE : 0xFF7C8798));
            SacredArt.cross(c, fill, cross[0], cross[1], s, fill.getColor());
        }

        if (pulse) postInvalidateOnAnimation();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (pos.isEmpty()) layout();
            float best = Float.MAX_VALUE;
            String hit = null;
            float touchSlop = Ui.dp(getContext(), 18);
            for (String key : pos.keySet()) {
                float[] p = pos.get(key);
                float d = (float) Math.hypot(e.getX() - p[0], e.getY() - p[1]);
                if (d < best && d < sizes.get(key) + touchSlop) {
                    best = d;
                    hit = key;
                }
            }
            if (hit != null) {
                performClick();
                if (listener != null) listener.onBead(hit);
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
