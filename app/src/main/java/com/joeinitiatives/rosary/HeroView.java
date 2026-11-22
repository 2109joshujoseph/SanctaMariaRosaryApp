package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

/** Cinematic hero card with an image, a Marian-blue scrim and gold typography. */
public class HeroView extends View {
    private Bitmap bmp;
    private final String label, title, subtitle;
    private final Paint imgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private final Paint scrimPaint = new Paint();
    private final Paint goldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint labelPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint titlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint subPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final Rect src = new Rect();
    private final RectF dst = new RectF();
    private final Path clip = new Path();
    private final float radius;

    public HeroView(Context c, int res, String label, String title, String subtitle) {
        super(c);
        this.label = label;
        this.title = title;
        this.subtitle = subtitle;
        this.radius = Ui.dp(c, 24);
        if (res != 0) bmp = BitmapFactory.decodeResource(c.getResources(), res);
        labelPaint.setTypeface(Ui.sansMedium());
        labelPaint.setTextSize(Ui.dp(c, 10));
        labelPaint.setColor(Ui.GOLD_LIGHT);
        labelPaint.setLetterSpacing(0.16f);
        titlePaint.setTypeface(Ui.serifBold());
        titlePaint.setTextSize(Ui.dp(c, 26));
        titlePaint.setColor(Ui.WHITE);
        subPaint.setTypeface(Ui.sans());
        subPaint.setTextSize(Ui.dp(c, 12.5f));
        subPaint.setColor(0xFFDCE7F6);
        goldPaint.setColor(0x99E8C96A);
        goldPaint.setStyle(Paint.Style.STROKE);
        goldPaint.setStrokeWidth(Ui.dp(c, 1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth(), h = getHeight();
        if (w == 0 || h == 0) return;
        clip.reset();
        clip.addRoundRect(new RectF(0, 0, w, h), radius, radius, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(clip);
        if (bmp != null) {
            // Frame Mother Mary from top of radiant halo down to her holding hands together
            // In 549x960 image: halo top is ~y=25, face is ~y=250-325, holding hands are ~y=330-415.
            // Target bottom at y=445 ensures holding hands together are fully and clearly visible.
            float targetHandsY = bmp.getHeight() * 0.465f; // ~446px
            float targetTopY = bmp.getHeight() * 0.02f;   // ~19px (halo)
            float frameH = targetHandsY - targetTopY;     // ~427px

            float viewAspect = (float) w / h;
            float sw, sh, sx, sy;
            if (viewAspect >= (float) bmp.getWidth() / frameH) {
                // Wide view: fill full image width, anchor bottom to just past holding hands
                sw = bmp.getWidth();
                sh = sw / viewAspect;
                sx = 0;
                sy = Math.max(0, targetHandsY - sh);
            } else {
                // Tall view: fit exactly from halo to holding hands, crop sides symmetrically
                sh = frameH;
                sw = sh * viewAspect;
                sx = Math.max(0, (bmp.getWidth() - sw) / 2f);
                sy = targetTopY;
            }

            src.set((int) sx, (int) sy, (int) (sx + sw), (int) (sy + sh));
            dst.set(0, 0, w, h);
            canvas.drawBitmap(bmp, src, dst, imgPaint);
        } else {
            canvas.drawColor(Ui.BLUE);
        }

        // Soft, subtle shadow only at very bottom edge (16%) so holding hands remain bright and clear
        scrimPaint.setShader(new LinearGradient(0, h * 0.82f, 0, h,
                new int[]{0x00000000, 0x4408152B, 0xBB050D1C}, new float[]{0f, 0.45f, 1f}, Shader.TileMode.CLAMP));
        canvas.drawRect(0, h * 0.82f, w, h, scrimPaint);

        // Subtitle centered neatly at the bottom edge with text shadow for crisp legibility
        float bottom = h - Ui.dp(getContext(), 12);
        subPaint.setTextAlign(Paint.Align.CENTER);
        subPaint.setShadowLayer(Ui.dp(getContext(), 4), 0, 1, 0xDD000000);
        canvas.drawText(subtitle == null ? "" : subtitle, w / 2f, bottom, subPaint);

        canvas.drawRoundRect(new RectF(0.5f, 0.5f, w - 0.5f, h - 0.5f), radius, radius, goldPaint);
        canvas.restore();
    }
}
