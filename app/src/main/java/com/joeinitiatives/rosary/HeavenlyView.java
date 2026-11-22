package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

import java.util.Random;

/**
 * Living heavenly sky featuring the Blessed Virgin Mary surrounded by angels
 * flying across and circling around her in celestial adoration.
 */
public class HeavenlyView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mary;
    private final long start = System.currentTimeMillis();
    private boolean running = true;

    // Background stars
    private final float[] starX = new float[52];
    private final float[] starY = new float[52];
    private final float[] starS = new float[52];

    // Swirling light particles surrounding Mary
    private final float[] partAngle = new float[28];
    private final float[] partDist = new float[28];
    private final float[] partSpeed = new float[28];
    private final float[] partSize = new float[28];

    public HeavenlyView(Context c) {
        super(c);
        try {
            mary = BitmapFactory.decodeResource(c.getResources(), R.drawable.mary_splash);
        } catch (Throwable ignored) {}

        Random rnd = new Random(42);
        for (int i = 0; i < starX.length; i++) {
            starX[i] = rnd.nextFloat();
            starY[i] = rnd.nextFloat() * 0.75f;
            starS[i] = 0.6f + rnd.nextFloat() * 1.5f;
        }

        for (int i = 0; i < partAngle.length; i++) {
            partAngle[i] = (float) (rnd.nextFloat() * Math.PI * 2);
            partDist[i] = 0.55f + rnd.nextFloat() * 0.70f;
            partSpeed[i] = 0.7f + rnd.nextFloat() * 0.8f;
            partSize[i] = 1.2f + rnd.nextFloat() * 2.2f;
        }
    }

    public void start() {
        running = true;
        postInvalidateOnAnimation();
    }

    public void stop() {
        running = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        running = false;
        super.onDetachedFromWindow();
    }

    private float t() {
        return (System.currentTimeMillis() - start) / 1000f;
    }

    @Override
    protected void onDraw(Canvas c) {
        float w = getWidth(), h = getHeight();
        if (w == 0 || h == 0) return;
        float t = t();

        // 1. Deep Celestial Blue-to-Gold Sky Gradient
        paint.setShader(new LinearGradient(0, 0, 0, h,
                new int[]{0xFF071224, 0xFF0D2248, 0xFF1B4282, 0xFF658EC4, 0xFFE0C47E},
                new float[]{0f, 0.28f, 0.55f, 0.82f, 1f}, Shader.TileMode.CLAMP));
        c.drawRect(0, 0, w, h, paint);

        // 2. Divine Sunburst / Radiant Halo behind Mary
        float sx = w * 0.5f, sy = h * 0.38f;
        glow.setShader(new RadialGradient(sx, sy, w * 0.75f,
                new int[]{0x99FFF6D8, 0x55FFE8AB, 0x18FFE8AB, 0x00000000},
                new float[]{0f, 0.30f, 0.65f, 1f}, Shader.TileMode.CLAMP));
        c.drawCircle(sx, sy, w * 0.75f, glow);

        // Radiant Rotating Heavenly Light Rays
        c.save();
        c.translate(sx, sy);
        c.rotate((t * 3.5f) % 360f);
        paint.setShader(null);
        paint.setColor(0x15FFFFFF);
        for (int i = 0; i < 20; i++) {
            Path ray = new Path();
            ray.moveTo(0, 0);
            ray.lineTo(-w * 0.04f, -h * 1.1f);
            ray.lineTo(w * 0.04f, -h * 1.1f);
            ray.close();
            c.drawPath(ray, paint);
            c.rotate(18f);
        }
        c.restore();

        // 3. Twinkling Stars
        for (int i = 0; i < starX.length; i++) {
            float a = 0.25f + 0.60f * (0.5f + 0.5f * (float) Math.sin(t * 2.2 + i * 1.3));
            paint.setColor((((int) (a * 255)) << 24) | 0x00FFF8E7);
            c.drawCircle(starX[i] * w, starY[i] * h, Ui.dp(getContext(), starS[i]), paint);
        }

        // 4. Soft Drifting Heaven Clouds
        for (int i = 0; i < 6; i++) {
            float speed = 7f + i * 2.5f;
            float base = (i * 0.21f) * (w + 400) - 200;
            float cx = (base + t * speed) % (w + 400) - 200;
            float cy = h * (0.16f + (i % 4) * 0.12f);
            float cw = w * (0.24f + (i % 3) * 0.10f);
            int alpha = 18 + (i % 4) * 7;
            paint.setColor((alpha << 24) | 0x00FFFFFF);
            c.drawOval(new RectF(cx, cy, cx + cw, cy + cw * 0.32f), paint);
            c.drawOval(new RectF(cx + cw * 0.18f, cy - cw * 0.08f, cx + cw * 0.75f, cy + cw * 0.26f), paint);
        }

        // 5. Angels Flying Across in the Upper Background (Higher altitude)
        // Angel 1: Flying from left to right across upper sky
        float a1x = ((t * 45f) % (w + 200)) - 100;
        float a1y = h * 0.12f + (float) Math.sin(t * 0.8f) * Ui.dp(getContext(), 10);
        drawAngel(c, a1x, a1y, w * 0.052f, 0.75f, t * 3.2f, 1f);

        // Angel 2: Flying from right to left across mid-upper sky
        float a2x = (w + 100) - ((t * 38f) % (w + 200));
        float a2y = h * 0.22f + (float) Math.cos(t * 0.9f) * Ui.dp(getContext(), 12);
        drawAngel(c, a2x, a2y, w * 0.046f, 0.70f, t * 2.8f, -1f);

        // 6. Angels Circling and Surrounding Mother Mary (Background layer)
        float maryCenterX = w / 2f;
        float maryCenterY = h * 0.38f;
        float orbitRx = w * 0.38f;
        float orbitRy = h * 0.18f;

        // Angel 3 (Orbiting behind upper Mary)
        float orbAngle1 = t * 0.55f;
        if (Math.sin(orbAngle1) < 0) {
            float o3x = maryCenterX + (float) Math.cos(orbAngle1) * orbitRx;
            float o3y = maryCenterY + (float) Math.sin(orbAngle1) * orbitRy;
            float dir3 = Math.sin(orbAngle1) < 0 ? -Math.signum((float) Math.sin(orbAngle1)) : 1f;
            drawAngel(c, o3x, o3y, w * 0.042f, 0.65f, t * 3f, dir3);
        }

        // Angel 4 (Orbiting behind on complementary phase)
        float orbAngle2 = t * 0.55f + (float) Math.PI;
        if (Math.sin(orbAngle2) < 0) {
            float o4x = maryCenterX + (float) Math.cos(orbAngle2) * orbitRx;
            float o4y = maryCenterY + (float) Math.sin(orbAngle2) * orbitRy;
            float dir4 = Math.sin(orbAngle2) < 0 ? -Math.signum((float) Math.sin(orbAngle2)) : 1f;
            drawAngel(c, o4x, o4y, w * 0.042f, 0.65f, t * 3f + 1.2f, dir4);
        }

        // 7. OUR LADY - BLESSED MOTHER MARY (Centerpiece)
        float mh = h * 0.56f;
        float mw = mary != null && mary.getHeight() > 0
                ? mh * ((float) mary.getWidth() / mary.getHeight())
                : mh * 0.58f;
        float floatY = (float) Math.sin(t * 1.1f) * Ui.dp(getContext(), 7);
        float mx = w / 2f - mw / 2f;
        float my = h * 0.12f + floatY;

        // Radiant golden aureole around Mary
        glow.setShader(new RadialGradient(w / 2f, my + mh * 0.40f, mw * 0.95f,
                new int[]{0x88FFF2BC, 0x44FFE28A, 0x00FFE28A},
                new float[]{0f, 0.55f, 1f}, Shader.TileMode.CLAMP));
        c.drawCircle(w / 2f, my + mh * 0.40f, mw * 0.95f, glow);

        // Draw Mother Mary Image
        if (mary != null && !mary.isRecycled()) {
            c.drawBitmap(mary, null, new RectF(mx, my, mx + mw, my + mh), paint);
        } else {
            // Fallback silhouette if bitmap pending
            paint.setColor(0xFFF7EEDC);
            c.drawOval(new RectF(mx + mw * 0.2f, my, mx + mw * 0.8f, my + mh * 0.8f), paint);
        }

        // 8. Angels Circling and Surrounding Mother Mary (Foreground layer - in front of Mary)
        if (Math.sin(orbAngle1) >= 0) {
            float o3x = maryCenterX + (float) Math.cos(orbAngle1) * orbitRx;
            float o3y = maryCenterY + (float) Math.sin(orbAngle1) * orbitRy;
            drawAngel(c, o3x, o3y, w * 0.055f, 0.90f, t * 3.4f, 1f);
        }
        if (Math.sin(orbAngle2) >= 0) {
            float o4x = maryCenterX + (float) Math.cos(orbAngle2) * orbitRx;
            float o4y = maryCenterY + (float) Math.sin(orbAngle2) * orbitRy;
            drawAngel(c, o4x, o4y, w * 0.055f, 0.90f, t * 3.4f + 1.2f, -1f);
        }

        // Additional angel swooping close to Mary's right side
        float swoopT = t * 0.45f;
        float a5x = maryCenterX + w * 0.32f + (float) Math.sin(swoopT * 1.8f) * Ui.dp(getContext(), 24);
        float a5y = my + mh * 0.48f + (float) Math.cos(swoopT * 1.5f) * Ui.dp(getContext(), 18);
        drawAngel(c, a5x, a5y, w * 0.050f, 0.85f, t * 3.2f + 2.5f, -1f);

        // Additional angel swooping close to Mary's left side
        float a6x = maryCenterX - w * 0.32f + (float) Math.cos(swoopT * 1.7f) * Ui.dp(getContext(), 24);
        float a6y = my + mh * 0.44f + (float) Math.sin(swoopT * 1.6f) * Ui.dp(getContext(), 18);
        drawAngel(c, a6x, a6y, w * 0.050f, 0.85f, t * 3.2f + 4.1f, 1f);

        // 9. Sparkling Golden Stardust swirling around Our Lady
        paint.setShader(null);
        for (int i = 0; i < partAngle.length; i++) {
            float ang = partAngle[i] + t * partSpeed[i];
            float rx = (mw * 0.65f) * partDist[i];
            float ry = (mh * 0.42f) * partDist[i];
            float px = maryCenterX + (float) Math.cos(ang) * rx;
            float py = (my + mh * 0.45f) + (float) Math.sin(ang) * ry;
            float sparkleAlpha = 0.35f + 0.55f * (0.5f + 0.5f * (float) Math.sin(t * 4.0 + i));
            paint.setColor((((int) (sparkleAlpha * 255)) << 24) | 0x00FFE8A3);
            c.drawCircle(px, py, Ui.dp(getContext(), partSize[i]), paint);
        }

        // 10. Soft Bottom Vignette for Title legibility
        paint.setShader(new LinearGradient(0, h * 0.62f, 0, h,
                new int[]{0x00071224, 0xAA08152B, 0xFA050D1C}, null, Shader.TileMode.CLAMP));
        c.drawRect(0, h * 0.62f, w, h, paint);
        paint.setShader(null);

        if (running) postInvalidateOnAnimation();
    }

    private void drawAngel(Canvas c, float x, float y, float s, float alpha, float t, float dir) {
        c.save();
        c.translate(x, y);
        c.scale(dir, 1f);

        // Angel radiance aura
        int a = (int) (alpha * 255);
        int auraColor = ((int) (alpha * 90) << 24) | 0x00FFF0C0;
        glow.setShader(new RadialGradient(0, 0, s * 2.4f,
                new int[]{auraColor, 0x00FFF0C0}, null, Shader.TileMode.CLAMP));
        c.drawCircle(0, 0, s * 2.4f, glow);

        // Wing flap animation
        float flap = 0.65f + 0.35f * (float) Math.sin(t * 3.5f);
        int color = (a << 24) | 0x00F5E2A8;
        SacredArt.angel(c, paint, 0, 0, s, color, flap);

        c.restore();
    }
}
