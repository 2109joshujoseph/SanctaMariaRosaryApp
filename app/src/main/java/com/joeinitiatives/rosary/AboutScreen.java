package com.joeinitiatives.rosary;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/** About the app, its dedication, credits and privacy. */
public class AboutScreen extends BaseScreen {
    public AboutScreen(MainActivity a) {
        super(a, "about", true);
    }

    @Override
    protected void buildContent() {
        LinearLayout col = scrollBody(18);

        LinearLayout hero = Ui.column(act);
        hero.setBackground(Ui.gradient(act, 0xFF22467D, 0xFF142E57, 24));
        hero.setPadding(Ui.dp(act, 20), Ui.dp(act, 20), Ui.dp(act, 20), Ui.dp(act, 20));

        // Beautifully framed portrait with head, face and hands fully visible
        View maryAbout = new View(act) {
            private final android.graphics.Bitmap bmp = android.graphics.BitmapFactory.decodeResource(getResources(), R.drawable.mary_splash);
            private final android.graphics.Paint p = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG | android.graphics.Paint.FILTER_BITMAP_FLAG);
            private final android.graphics.Rect src = new android.graphics.Rect();
            private final android.graphics.RectF dst = new android.graphics.RectF();
            private final android.graphics.Path clip = new android.graphics.Path();
            private final float rad = Ui.dp(getContext(), 16);

            @Override
            protected void onDraw(android.graphics.Canvas canvas) {
                int w = getWidth(), h = getHeight();
                if (w == 0 || h == 0 || bmp == null) return;
                clip.reset();
                clip.addRoundRect(new android.graphics.RectF(0, 0, w, h), rad, rad, android.graphics.Path.Direction.CW);
                canvas.save();
                canvas.clipPath(clip);

                // Frame Mother Mary from halo top down to holding hands
                float targetHandsY = bmp.getHeight() * 0.465f; // ~446px (hands)
                float targetTopY = bmp.getHeight() * 0.02f;   // ~19px (halo)
                float frameH = targetHandsY - targetTopY;
                float viewAspect = (float) w / h;
                float sw, sh, sx, sy;
                if (viewAspect >= (float) bmp.getWidth() / frameH) {
                    sw = bmp.getWidth();
                    sh = sw / viewAspect;
                    sx = 0;
                    sy = Math.max(0, targetHandsY - sh);
                } else {
                    sh = frameH;
                    sw = sh * viewAspect;
                    sx = Math.max(0, (bmp.getWidth() - sw) / 2f);
                    sy = targetTopY;
                }
                src.set((int) sx, (int) sy, (int) (sx + sw), (int) (sy + sh));
                dst.set(0, 0, w, h);
                canvas.drawBitmap(bmp, src, dst, p);
                canvas.restore();
            }
        };
        hero.addView(maryAbout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Ui.dp(act, 240)));

        TextView title = Ui.text(act, "SANCTA MARIA", 18, Ui.GOLD_LIGHT, true);
        title.setTypeface(Ui.serifBold());
        title.setLetterSpacing(0.14f);
        title.setGravity(android.view.Gravity.CENTER);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tlp.topMargin = Ui.dp(act, 12);
        hero.addView(title, tlp);
        col.addView(hero);

        col.addView(Widgets.section(act, L.t(act, "about_app")));
        LinearLayout about = Widgets.card(act);
        about.addView(Ui.text(act, L.t(act, "about_text"), 14, Ui.INK, false));
        col.addView(about);

        col.addView(Widgets.section(act, L.t(act, "privacy")));
        LinearLayout priv = Widgets.card(act);
        priv.addView(Ui.text(act, L.t(act, "offline"), 13, Ui.INK, false));
        TextView off = Ui.text(act, L.t(act, "offline_note"), 12, Ui.MUTED, false);
        off.setLineSpacing(Ui.dp(act, 4), 1.14f);
        LinearLayout.LayoutParams olp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        olp.topMargin = Ui.dp(act, 6);
        priv.addView(off, olp);
        col.addView(priv);

        col.addView(Widgets.section(act, "Sources & Usage"));
        LinearLayout cn = Widgets.goldCard(act);
        cn.addView(Ui.text(act, "Prayer texts follow traditional Catholic usage. Scripture references are from the Holy Bible. Artwork in this app is an original creation for Joe Initiatives.", 12, Ui.INK, false));
        col.addView(cn);

        col.addView(Widgets.section(act, L.t(act, "credits")));
        LinearLayout cr = Widgets.card(act);
        cr.addView(Ui.text(act, "Sancta Maria is a gift of faith crafted by Joe Initiatives.\nAll prayer content follows the traditional Roman Catholic form.\nSacred artwork and illustrations are original creations for Joe Initiatives.", 13, Ui.INK, false));
        col.addView(cr);

        TextView version = Ui.text(act, L.t(act, "version") + " 1.1.0 · " + L.t(act, "support"), 10.5f, Ui.MUTED, false);
        version.setGravity(android.view.Gravity.CENTER);
        LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vlp.topMargin = Ui.dp(act, 18);
        col.addView(version, vlp);
    }
}
