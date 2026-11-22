package com.joeinitiatives.rosary;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/** Small procedural sacred-art helpers used by the splash and completion animations. */
public final class SacredArt {
    private SacredArt() {}

    public static void angel(Canvas c, Paint p, float cx, float cy, float s, int color, float flap) {
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);

        Path wing = new Path();
        wing.moveTo(cx, cy);
        wing.cubicTo(cx - s * 0.25f, cy - s * 0.95f * flap, cx - s * 1.25f, cy - s * 0.85f * flap, cx - s * 1.45f, cy - s * 0.05f);
        wing.cubicTo(cx - s * 1.05f, cy + s * 0.02f, cx - s * 0.5f, cy + s * 0.16f, cx, cy + s * 0.12f);
        wing.close();
        c.drawPath(wing, p);

        wing.reset();
        wing.moveTo(cx, cy);
        wing.cubicTo(cx + s * 0.25f, cy - s * 0.95f * flap, cx + s * 1.25f, cy - s * 0.85f * flap, cx + s * 1.45f, cy - s * 0.05f);
        wing.cubicTo(cx + s * 1.05f, cy + s * 0.02f, cx + s * 0.5f, cy + s * 0.16f, cx, cy + s * 0.12f);
        wing.close();
        c.drawPath(wing, p);

        Path body = new Path();
        body.moveTo(cx - s * 0.16f, cy - s * 0.15f);
        body.cubicTo(cx - s * 0.34f, cy + s * 0.35f, cx - s * 0.44f, cy + s * 0.75f, cx - s * 0.30f, cy + s * 0.95f);
        body.lineTo(cx + s * 0.30f, cy + s * 0.95f);
        body.cubicTo(cx + s * 0.44f, cy + s * 0.75f, cx + s * 0.34f, cy + s * 0.35f, cx + s * 0.16f, cy - s * 0.15f);
        body.close();
        c.drawPath(body, p);

        c.drawCircle(cx, cy - s * 0.42f, s * 0.17f, p);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(Math.max(1f, s * 0.035f));
        c.drawCircle(cx, cy - s * 0.45f, s * 0.27f, p);
        p.setStyle(Paint.Style.FILL);
    }

    public static void cross(Canvas c, Paint p, float cx, float cy, float s, int color) {
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
        c.drawRect(cx - s * 0.09f, cy - s * 0.55f, cx + s * 0.09f, cy + s * 0.55f, p);
        c.drawRect(cx - s * 0.34f, cy - s * 0.30f, cx + s * 0.34f, cy - s * 0.12f, p);
    }

    public static void star(Canvas c, Paint p, float cx, float cy, float r, int color) {
        p.setColor(color);
        Path path = new Path();
        for (int i = 0; i < 8; i++) {
            double a = Math.PI / 4 * i;
            float rr = (i % 2 == 0) ? r : r * 0.34f;
            float x = (float) (cx + Math.cos(a) * rr);
            float y = (float) (cy + Math.sin(a) * rr);
            if (i == 0) path.moveTo(x, y);
            else path.lineTo(x, y);
        }
        path.close();
        c.drawPath(path, p);
    }
}
