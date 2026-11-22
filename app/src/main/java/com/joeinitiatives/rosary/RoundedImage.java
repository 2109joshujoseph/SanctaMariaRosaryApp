package com.joeinitiatives.rosary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.widget.ImageView;

/** ImageView with a soft rounded-corner clip. */
public class RoundedImage extends ImageView {
    private final float radius;
    private final Path path = new Path();
    private final RectF rect = new RectF();

    public RoundedImage(Context c, float radiusPx) {
        super(c);
        this.radius = radiusPx;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null || getWidth() == 0) {
            super.onDraw(canvas);
            return;
        }
        rect.set(0, 0, getWidth(), getHeight());
        path.reset();
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path);
        super.onDraw(canvas);
        canvas.restore();
    }
}
