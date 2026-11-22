import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SacredArtGen {
    static final int W = 900, H = 600;

    public static void main(String[] args) throws Exception {
        String base = args.length > 0 ? args[0] : "app/src/main/res";
        String dr = base + "/drawable";
        new File(dr).mkdirs();

        System.out.println("Generating sacred art for mysteries and devotions...");

        // Joyful Mysteries
        save(renderAnnunciation(), dr + "/myst_j1.jpg");
        save(renderVisitation(), dr + "/myst_j2.jpg");
        save(renderNativity(), dr + "/myst_j3.jpg");
        save(renderPresentation(), dr + "/myst_j4.jpg");
        save(renderFindingInTemple(), dr + "/myst_j5.jpg");

        // Luminous Mysteries
        save(renderBaptism(), dr + "/myst_l1.jpg");
        save(renderCana(), dr + "/myst_l2.jpg");
        save(renderKingdom(), dr + "/myst_l3.jpg");
        save(renderTransfiguration(), dr + "/myst_l4.jpg");
        save(renderEucharist(), dr + "/myst_l5.jpg");

        // Sorrowful Mysteries
        save(renderAgony(), dr + "/myst_s1.jpg");
        save(renderScourging(), dr + "/myst_s2.jpg");
        save(renderCrowning(), dr + "/myst_s3.jpg");
        save(renderCarryingCross(), dr + "/myst_s4.jpg");
        save(renderCrucifixion(), dr + "/myst_s5.jpg");

        // Glorious Mysteries
        save(renderResurrection(), dr + "/myst_g1.jpg");
        save(renderAscension(), dr + "/myst_g2.jpg");
        save(renderPentecost(), dr + "/myst_g3.jpg");
        save(renderAssumption(), dr + "/myst_g4.jpg");
        save(renderCoronation(), dr + "/myst_g5.jpg");

        // Devotions
        save(renderDevFatima(), dr + "/dev_fatima.jpg");
        save(renderDevLourdes(), dr + "/dev_lourdes.jpg");
        save(renderDevGuadalupe(), dr + "/dev_guadalupe.jpg");
        save(renderDevPerpetualHelp(), dr + "/dev_perpetual.jpg");
        save(renderDevMountCarmel(), dr + "/dev_carmel.jpg");
        save(renderDevImmaculateHeart(), dr + "/dev_immaculate.jpg");
        save(renderDevRosary(), dr + "/dev_rosary.jpg");
        save(renderDevSorrows(), dr + "/dev_sorrows.jpg");
        save(renderDevMiraculousMedal(), dr + "/dev_medal.jpg");
        save(renderDevQueenOfHeaven(), dr + "/dev_queen.jpg");

        // Shrine & Saint
        save(renderKreupasanam(), dr + "/kreupasanam.jpg");
        save(renderStJoseph(), dr + "/st_joseph.jpg");

        System.out.println("All sacred artwork generated successfully!");
    }

    private static void save(BufferedImage img, String path) throws Exception {
        ImageIO.write(img, "jpg", new File(path));
    }

    private static Graphics2D createGraphics(BufferedImage img) {
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        return g;
    }

    private static void drawSacredArch(Graphics2D g, String title, String subtitle, Color topColor, Color bottomColor) {
        // Celestial background gradient
        GradientPaint gp = new GradientPaint(0, 0, topColor, 0, H, bottomColor);
        g.setPaint(gp);
        g.fillRect(0, 0, W, H);

        // Radiant divine burst in background
        RadialGradientPaint burst = new RadialGradientPaint(
                new Point2D.Float(W / 2f, H * 0.42f),
                W * 0.55f,
                new float[]{0f, 0.45f, 1f},
                new Color[]{new Color(255, 245, 210, 140), new Color(232, 201, 106, 60), new Color(0, 0, 0, 0)}
        );
        g.setPaint(burst);
        g.fillRect(0, 0, W, H);

        // Subtle rays
        g.setColor(new Color(255, 255, 255, 25));
        for (int i = 0; i < 24; i++) {
            double angle = (Math.PI * 2 / 24) * i;
            Path2D ray = new Path2D.Float();
            ray.moveTo(W / 2f, H * 0.42f);
            ray.lineTo(W / 2f + Math.cos(angle - 0.05) * W, H * 0.42f + Math.sin(angle - 0.05) * W);
            ray.lineTo(W / 2f + Math.cos(angle + 0.05) * W, H * 0.42f + Math.sin(angle + 0.05) * W);
            ray.closePath();
            g.fill(ray);
        }

        // Ornate Gothic Arch Frame
        g.setColor(new Color(201, 162, 39, 180));
        g.setStroke(new BasicStroke(4f));
        g.drawRoundRect(22, 22, W - 44, H - 44, 24, 24);

        g.setColor(new Color(243, 217, 139, 100));
        g.setStroke(new BasicStroke(1.5f));
        g.drawRoundRect(30, 30, W - 60, H - 60, 18, 18);

        // Bottom title ribbon
        GradientPaint ribbon = new GradientPaint(0, H - 74, new Color(15, 25, 45, 225), 0, H, new Color(8, 14, 28, 245));
        g.setPaint(ribbon);
        g.fillRect(24, H - 74, W - 48, 50);

        g.setColor(new Color(201, 162, 39));
        g.setStroke(new BasicStroke(2f));
        g.drawLine(24, H - 74, W - 24, H - 74);

        // Ornate corner filigree
        drawCorner(g, 36, 36, 1, 1);
        drawCorner(g, W - 36, 36, -1, 1);
        drawCorner(g, 36, H - 80, 1, -1);
        drawCorner(g, W - 36, H - 80, -1, -1);

        // Title and subtitle typography
        g.setColor(new Color(255, 240, 190));
        g.setFont(new Font("Serif", Font.BOLD, 22));
        FontMetrics fm = g.getFontMetrics();
        int tw = fm.stringWidth(title);
        g.drawString(title, (W - tw) / 2, H - 46);

        if (subtitle != null && !subtitle.isEmpty()) {
            g.setColor(new Color(212, 188, 140));
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            FontMetrics fmSub = g.getFontMetrics();
            int sw = fmSub.stringWidth(subtitle);
            g.drawString(subtitle, (W - sw) / 2, H - 30);
        }
    }

    private static void drawCorner(Graphics2D g, float x, float y, float sx, float sy) {
        g.setColor(new Color(232, 201, 106, 160));
        g.setStroke(new BasicStroke(2f));
        Path2D p = new Path2D.Float();
        p.moveTo(x, y + 20 * sy);
        p.curveTo(x, y + 6 * sy, x + 6 * sx, y, x + 20 * sx, y);
        g.draw(p);
        g.fillOval((int) (x + 8 * sx - 3), (int) (y + 8 * sy - 3), 6, 6);
    }

    private static void drawHalo(Graphics2D g, float cx, float cy, float r, Color color) {
        RadialGradientPaint rgp = new RadialGradientPaint(
                new Point2D.Float(cx, cy),
                r,
                new float[]{0f, 0.65f, 1f},
                new Color[]{new Color(255, 255, 255, 200), color, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0)}
        );
        g.setPaint(rgp);
        g.fill(new Ellipse2D.Float(cx - r, cy - r, r * 2, r * 2));

        g.setColor(new Color(255, 235, 160, 220));
        g.setStroke(new BasicStroke(3f));
        g.draw(new Ellipse2D.Float(cx - r * 0.72f, cy - r * 0.72f, r * 1.44f, r * 1.44f));
    }

    private static void drawMaryFigure(Graphics2D g, float cx, float cy, float scale, boolean praying, boolean holdingChild) {
        AffineTransform oldTx = g.getTransform();
        g.translate(cx, cy);
        g.scale(scale, scale);

        // Halo
        drawHalo(g, 0, -110, 68, new Color(232, 201, 106, 210));

        // White veil / head covering
        g.setColor(new Color(248, 246, 242));
        Path2D veil = new Path2D.Float();
        veil.moveTo(-28, -85);
        veil.curveTo(-38, -135, 38, -135, 28, -85);
        veil.curveTo(35, -40, 52, 60, 42, 130);
        veil.lineTo(-42, 130);
        veil.curveTo(-52, 60, -35, -40, -28, -85);
        veil.closePath();
        g.fill(veil);
        g.setColor(new Color(201, 162, 39, 140));
        g.setStroke(new BasicStroke(1.8f));
        g.draw(veil);

        // Face
        g.setColor(new Color(246, 222, 198));
        g.fillOval(-18, -115, 36, 44);

        // Soft serene expression features
        g.setColor(new Color(160, 110, 85));
        g.setStroke(new BasicStroke(1.4f));
        g.drawArc(-11, -98, 7, 5, 0, -180); // eye left
        g.drawArc(4, -98, 7, 5, 0, -180);  // eye right
        g.drawLine(0, -96, -1, -88);        // nose
        g.setColor(new Color(195, 95, 90));
        g.drawLine(-4, -82, 4, -82);        // lips

        // Inner robe (ivory/rose/blue)
        g.setColor(new Color(235, 230, 220));
        Path2D robe = new Path2D.Float();
        robe.moveTo(-18, -70);
        robe.curveTo(-22, -30, -30, 40, -36, 130);
        robe.lineTo(36, 130);
        robe.curveTo(30, 40, 22, -30, 18, -70);
        robe.closePath();
        g.fill(robe);

        // Marian Blue Outer Mantle
        g.setColor(new Color(27, 58, 107));
        Path2D mantle = new Path2D.Float();
        mantle.moveTo(-24, -75);
        mantle.curveTo(-42, -30, -56, 50, -48, 130);
        mantle.lineTo(-24, 130);
        mantle.curveTo(-32, 60, -20, 0, -10, -55);
        mantle.closePath();
        g.fill(mantle);

        Path2D mantleRight = new Path2D.Float();
        mantleRight.moveTo(24, -75);
        mantleRight.curveTo(42, -30, 56, 50, 48, 130);
        mantleRight.lineTo(24, 130);
        mantleRight.curveTo(32, 60, 20, 0, 10, -55);
        mantleRight.closePath();
        g.fill(mantleRight);

        // Golden sash at waist
        g.setColor(new Color(201, 162, 39));
        g.fillRect(-18, -20, 36, 8);
        g.drawLine(2, -12, 10, 40);

        if (praying) {
            // Folded hands in prayer
            g.setColor(new Color(246, 222, 198));
            Path2D hands = new Path2D.Float();
            hands.moveTo(0, -42);
            hands.lineTo(-7, -25);
            hands.curveTo(-8, -14, 8, -14, 7, -25);
            hands.closePath();
            g.fill(hands);
            g.setColor(new Color(201, 162, 39, 160));
            g.setStroke(new BasicStroke(1f));
            g.draw(hands);
        } else if (holdingChild) {
            // Child Jesus in her arms
            // Infant halo
            drawHalo(g, 16, -20, 30, new Color(243, 217, 139, 210));

            // Infant head
            g.setColor(new Color(250, 228, 206));
            g.fillOval(4, -32, 24, 25);
            // Infant robe
            g.setColor(new Color(255, 255, 255));
            g.fillOval(0, -10, 34, 45);

            // Mary's arms cradling
            g.setColor(new Color(27, 58, 107));
            g.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawArc(-16, -20, 36, 32, 0, -180);

            // Hand supporting
            g.setColor(new Color(246, 222, 198));
            g.fillOval(12, 16, 12, 10);
        }

        // Celestial clouds at feet
        g.setColor(new Color(255, 255, 255, 180));
        g.fillOval(-60, 115, 50, 26);
        g.fillOval(-25, 110, 50, 30);
        g.fillOval(10, 115, 50, 26);

        g.setTransform(oldTx);
    }

    private static void drawAngelFigure(Graphics2D g, float cx, float cy, float scale, boolean facingRight) {
        AffineTransform oldTx = g.getTransform();
        g.translate(cx, cy);
        if (!facingRight) g.scale(-scale, scale);
        else g.scale(scale, scale);

        // Wings
        g.setColor(new Color(255, 255, 255, 210));
        Path2D wing = new Path2D.Float();
        wing.moveTo(0, 0);
        wing.curveTo(-30, -55, -80, -45, -75, 10);
        wing.curveTo(-50, 5, -20, 15, 0, 10);
        wing.closePath();
        g.fill(wing);

        // Wing highlights
        g.setColor(new Color(232, 201, 106, 160));
        g.setStroke(new BasicStroke(1.5f));
        g.draw(wing);

        // Robe
        g.setColor(new Color(245, 240, 225, 230));
        Path2D r = new Path2D.Float();
        r.moveTo(-10, -20);
        r.curveTo(-15, 20, -25, 60, -20, 85);
        r.lineTo(15, 85);
        r.curveTo(12, 40, 8, 10, 5, -20);
        r.closePath();
        g.fill(r);

        // Head and Halo
        drawHalo(g, 0, -35, 22, new Color(243, 217, 139, 180));
        g.setColor(new Color(248, 226, 204));
        g.fillOval(-9, -44, 18, 20);

        g.setTransform(oldTx);
    }

    // --- JOYFUL MYSTERIES ---

    private static BufferedImage renderAnnunciation() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Annunciation", "The 1st Joyful Mystery · Luke 1:26-38", new Color(18, 38, 76), new Color(10, 20, 42));

        // Angel Gabriel on left
        drawAngelFigure(g, W * 0.28f, H * 0.44f, 1.4f, true);

        // Lily stem held by Gabriel
        g.setColor(new Color(75, 150, 75));
        g.setStroke(new BasicStroke(3f));
        g.drawLine((int) (W * 0.32f), (int) (H * 0.48f), (int) (W * 0.38f), (int) (H * 0.32f));
        g.setColor(Color.WHITE);
        g.fillOval((int) (W * 0.37f), (int) (H * 0.30f), 16, 22);

        // Holy Spirit Dove descending with golden light
        g.setColor(new Color(255, 255, 255, 240));
        float dx = W * 0.50f, dy = H * 0.22f;
        drawHalo(g, dx, dy, 40, new Color(255, 235, 140, 200));
        g.fillOval((int) dx - 12, (int) dy - 8, 24, 16);
        Path2D dw = new Path2D.Float();
        dw.moveTo(dx, dy); dw.lineTo(dx - 22, dy - 18); dw.lineTo(dx + 22, dy - 18); dw.closePath();
        g.fill(dw);

        // Blessed Mother Mary on right kneeling in prayer
        drawMaryFigure(g, W * 0.68f, H * 0.45f, 1.35f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderVisitation() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Visitation", "The 2nd Joyful Mystery · Luke 1:39-56", new Color(24, 52, 92), new Color(14, 28, 52));

        // Hills of Judea in background
        g.setColor(new Color(42, 78, 120, 100));
        g.fillArc((int) (W * 0.1f), (int) (H * 0.4f), (int) (W * 0.5f), (int) (H * 0.5f), 0, 180);
        g.fillArc((int) (W * 0.45f), (int) (H * 0.38f), (int) (W * 0.5f), (int) (H * 0.5f), 0, 180);

        // Mary on left
        drawMaryFigure(g, W * 0.40f, H * 0.45f, 1.3f, true, false);

        // Elizabeth on right embracing Mary
        AffineTransform oldEl = g.getTransform();
        g.translate(W * 0.60f, H * 0.47f);
        g.scale(1.25f, 1.25f);
        drawHalo(g, 0, -110, 58, new Color(212, 175, 55, 180));
        g.setColor(new Color(155, 80, 70)); // Burgundy mantle
        g.fillOval(-35, -80, 70, 200);
        g.setColor(new Color(240, 230, 210)); // Headcloth
        g.fillOval(-24, -120, 48, 50);
        g.setColor(new Color(242, 214, 185)); // Face
        g.fillOval(-16, -108, 32, 36);
        g.setTransform(oldEl);

        g.dispose();
        return img;
    }

    private static BufferedImage renderNativity() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Nativity of Our Lord", "The 3rd Joyful Mystery · Luke 2:1-20", new Color(15, 26, 56), new Color(8, 14, 30));

        // Brilliant Star of Bethlehem
        float sx = W / 2f, sy = H * 0.18f;
        drawHalo(g, sx, sy, 75, new Color(255, 248, 200, 230));
        g.setColor(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            double a = Math.PI / 4 * i;
            float r = (i % 2 == 0) ? 60 : 25;
            g.drawLine((int) sx, (int) sy, (int) (sx + Math.cos(a) * r), (int) (sy + Math.sin(a) * r));
        }

        // Mary holding Infant Jesus
        drawMaryFigure(g, W * 0.46f, H * 0.46f, 1.4f, false, true);

        // Saint Joseph standing reverently by her side
        AffineTransform oldJ = g.getTransform();
        g.translate(W * 0.70f, H * 0.42f);
        g.scale(1.3f, 1.3f);
        drawHalo(g, 0, -115, 52, new Color(201, 162, 39, 160));
        g.setColor(new Color(110, 70, 45)); // Brown robe
        g.fillOval(-26, -80, 52, 200);
        g.setColor(new Color(242, 214, 185));
        g.fillOval(-16, -115, 32, 38);
        g.setColor(new Color(130, 130, 130)); // Beard
        g.fillArc(-14, -92, 28, 24, 0, -180);
        // Lantern
        g.setColor(new Color(255, 215, 100));
        g.fillOval(-35, -20, 16, 22);
        g.setTransform(oldJ);

        g.dispose();
        return img;
    }

    private static BufferedImage renderPresentation() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Presentation in the Temple", "The 4th Joyful Mystery · Luke 2:22-38", new Color(30, 45, 80), new Color(15, 22, 45));

        // Temple Columns in background
        g.setColor(new Color(210, 195, 165, 80));
        g.fillRect((int) (W * 0.16f), (int) (H * 0.15f), 36, (int) (H * 0.65f));
        g.fillRect((int) (W * 0.80f), (int) (H * 0.15f), 36, (int) (H * 0.65f));

        // Mary presenting Child Jesus
        drawMaryFigure(g, W * 0.40f, H * 0.45f, 1.35f, false, true);

        // Simeon the Priest receiving Jesus
        AffineTransform oldSim = g.getTransform();
        g.translate(W * 0.64f, H * 0.44f);
        g.scale(1.3f, 1.3f);
        drawHalo(g, 0, -115, 52, new Color(243, 217, 139, 170));
        g.setColor(new Color(230, 225, 210)); // Temple vestment
        g.fillOval(-28, -80, 56, 200);
        g.setColor(new Color(201, 162, 39)); // Gold stole
        g.fillRect(-12, -75, 24, 140);
        g.setColor(new Color(242, 214, 185));
        g.fillOval(-16, -115, 32, 38);
        g.setColor(new Color(220, 220, 220)); // Venerable white beard
        g.fillArc(-16, -92, 32, 36, 0, -180);
        g.setTransform(oldSim);

        g.dispose();
        return img;
    }

    private static BufferedImage renderFindingInTemple() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Finding the Child Jesus in the Temple", "The 5th Joyful Mystery · Luke 2:41-52", new Color(28, 48, 85), new Color(16, 26, 48));

        // Young Jesus in the center teaching
        float jx = W * 0.52f, jy = H * 0.43f;
        drawHalo(g, jx, jy - 50, 60, new Color(255, 235, 140, 230));
        g.setColor(new Color(250, 250, 250));
        g.fillOval((int) (jx - 24), (int) (jy - 20), 48, 120);
        g.setColor(new Color(250, 226, 202));
        g.fillOval((int) (jx - 15), (int) (jy - 65), 30, 34);

        // Mary entering on left with joyful relief
        drawMaryFigure(g, W * 0.28f, H * 0.45f, 1.3f, true, false);

        // Joseph behind Mary
        drawHalo(g, W * 0.18f, H * 0.32f, 40, new Color(201, 162, 39, 140));

        g.dispose();
        return img;
    }

    // --- LUMINOUS MYSTERIES ---

    private static BufferedImage renderBaptism() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Baptism in the Jordan", "The 1st Luminous Mystery · Matthew 3:13-17", new Color(20, 60, 95), new Color(12, 35, 60));

        // Holy Spirit Dove from heavens
        drawHalo(g, W / 2f, H * 0.20f, 48, new Color(255, 245, 190, 220));
        g.setColor(Color.WHITE);
        g.fillOval((int) (W / 2f - 14), (int) (H * 0.20f - 8), 28, 16);

        // Christ in the Jordan
        drawHalo(g, W * 0.50f, H * 0.40f, 65, new Color(255, 235, 130, 220));
        g.setColor(new Color(246, 224, 198));
        g.fillOval((int) (W * 0.50f - 18), (int) (H * 0.35f), 36, 42);

        // Waters of Jordan
        g.setColor(new Color(65, 130, 195, 180));
        g.fillOval((int) (W * 0.15f), (int) (H * 0.62f), (int) (W * 0.70f), 120);

        // Mary watching in prayer on bank
        drawMaryFigure(g, W * 0.22f, H * 0.46f, 1.25f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderCana() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Wedding at Cana", "The 2nd Luminous Mystery · John 2:1-12", new Color(38, 30, 70), new Color(20, 15, 42));

        // Mother Mary pointing to Christ ("Do whatever He tells you")
        drawMaryFigure(g, W * 0.34f, H * 0.44f, 1.35f, false, false);

        // Christ blessing the water
        float cx = W * 0.65f, cy = H * 0.42f;
        drawHalo(g, cx, cy - 60, 65, new Color(255, 235, 140, 230));
        g.setColor(new Color(155, 40, 45)); // Crimson robe
        g.fillOval((int) cx - 26, (int) cy - 20, 52, 140);
        g.setColor(new Color(246, 224, 198));
        g.fillOval((int) cx - 16, (int) cy - 75, 32, 38);
        // Blessing hand
        g.drawLine((int) cx - 10, (int) cy - 10, (int) cx - 35, (int) cy + 10);

        // 6 Stone Water Jars
        for (int i = 0; i < 4; i++) {
            float jx = W * 0.45f + i * 28;
            g.setColor(new Color(160, 120, 85));
            g.fillRoundRect((int) jx, (int) (H * 0.68f), 22, 38, 8, 8);
            g.setColor(new Color(180, 40, 50)); // Wine rim
            g.fillOval((int) jx + 2, (int) (H * 0.68f), 18, 8);
        }

        g.dispose();
        return img;
    }

    private static BufferedImage renderKingdom() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Proclamation of the Kingdom", "The 3rd Luminous Mystery · Mark 1:14-15", new Color(32, 55, 85), new Color(16, 28, 48));

        // Christ preaching
        float cx = W * 0.52f, cy = H * 0.42f;
        drawHalo(g, cx, cy - 60, 70, new Color(255, 240, 160, 240));
        g.setColor(new Color(250, 250, 250));
        g.fillOval((int) cx - 28, (int) cy - 20, 56, 145);
        g.setColor(new Color(246, 224, 198));
        g.fillOval((int) cx - 18, (int) cy - 78, 36, 42);

        // Mary in audience
        drawMaryFigure(g, W * 0.25f, H * 0.46f, 1.25f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderTransfiguration() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Transfiguration", "The 4th Luminous Mystery · Matthew 17:1-8", new Color(22, 38, 75), new Color(10, 18, 40));

        // Radiance of Christ atop Mount Tabor
        float cx = W / 2f, cy = H * 0.38f;
        drawHalo(g, cx, cy, 110, new Color(255, 255, 220, 255));
        g.setColor(Color.WHITE);
        g.fillOval((int) cx - 28, (int) cy - 30, 56, 140);
        g.setColor(new Color(255, 235, 215));
        g.fillOval((int) cx - 18, (int) cy - 85, 36, 42);

        // Moses and Elijah flanking
        drawHalo(g, W * 0.28f, H * 0.38f, 45, new Color(201, 162, 39, 160));
        drawHalo(g, W * 0.72f, H * 0.38f, 45, new Color(201, 162, 39, 160));

        g.dispose();
        return img;
    }

    private static BufferedImage renderEucharist() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Last Supper & The Eucharist", "The 5th Luminous Mystery · Luke 22:14-20", new Color(38, 22, 45), new Color(18, 10, 24));

        // Blessed Sacrament - Holy Eucharist & Chalice
        float cx = W / 2f, cy = H * 0.42f;
        drawHalo(g, cx, cy - 35, 95, new Color(255, 245, 180, 240));

        // Golden Chalice
        g.setColor(new Color(232, 201, 106));
        Path2D chalice = new Path2D.Float();
        chalice.moveTo(cx - 30, cy + 10);
        chalice.curveTo(cx - 28, cy + 45, cx + 28, cy + 45, cx + 30, cy + 10);
        chalice.closePath();
        g.fill(chalice);
        g.fillRect((int) cx - 6, (int) cy + 45, 12, 35);
        g.fillRoundRect((int) cx - 25, (int) cy + 80, 50, 12, 6, 6);

        // Sacred Host with Cross
        g.setColor(new Color(255, 255, 255));
        g.fillOval((int) cx - 24, (int) cy - 30, 48, 48);
        g.setColor(new Color(201, 162, 39));
        g.setStroke(new BasicStroke(2.5f));
        g.drawOval((int) cx - 24, (int) cy - 30, 48, 48);
        g.drawLine((int) cx, (int) cy - 22, (int) cx, (int) cy + 10);
        g.drawLine((int) cx - 10, (int) cy - 10, (int) cx + 10, (int) cy - 10);

        // Mary Mother of the Church in adoration
        drawMaryFigure(g, W * 0.20f, H * 0.46f, 1.25f, true, false);

        g.dispose();
        return img;
    }

    // --- SORROWFUL MYSTERIES ---

    private static BufferedImage renderAgony() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Agony in the Garden", "The 1st Sorrowful Mystery · Luke 22:39-46", new Color(22, 26, 45), new Color(10, 12, 22));

        // Angel with Cup of Consolation
        drawAngelFigure(g, W * 0.35f, H * 0.38f, 1.25f, true);

        // Christ prostrated in prayer
        float cx = W * 0.60f, cy = H * 0.48f;
        drawHalo(g, cx, cy - 45, 60, new Color(255, 225, 130, 200));
        g.setColor(new Color(145, 35, 45));
        g.fillOval((int) cx - 35, (int) cy - 10, 70, 90);
        g.setColor(new Color(246, 220, 194));
        g.fillOval((int) cx - 18, (int) cy - 58, 36, 40);

        // Olive tree branches
        g.setColor(new Color(45, 60, 40, 140));
        g.fillArc((int) (W * 0.1f), 40, 160, 120, 0, 180);

        g.dispose();
        return img;
    }

    private static BufferedImage renderScourging() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Scourging at the Pillar", "The 2nd Sorrowful Mystery · John 19:1", new Color(38, 20, 25), new Color(18, 8, 12));

        // Stone Pillar
        g.setColor(new Color(150, 140, 135));
        g.fillRoundRect((int) (W / 2f - 20), (int) (H * 0.18f), 40, (int) (H * 0.60f), 8, 8);

        // Christ bound to pillar
        drawHalo(g, W / 2f, H * 0.35f, 65, new Color(255, 230, 140, 210));
        g.setColor(new Color(246, 220, 194));
        g.fillOval((int) (W / 2f - 18), (int) (H * 0.28f), 36, 42);

        // Mother Mary sorrowful on side
        drawMaryFigure(g, W * 0.24f, H * 0.46f, 1.25f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderCrowning() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Crowning with Thorns", "The 3rd Sorrowful Mystery · Matthew 27:27-31", new Color(42, 18, 28), new Color(20, 8, 14));

        float cx = W / 2f, cy = H * 0.44f;
        drawHalo(g, cx, cy - 60, 70, new Color(255, 230, 140, 210));

        // Purple mantle of mockery
        g.setColor(new Color(90, 25, 75));
        g.fillOval((int) cx - 32, (int) cy - 15, 64, 140);
        g.setColor(new Color(246, 220, 194));
        g.fillOval((int) cx - 18, (int) cy - 75, 36, 42);

        // Crown of thorns
        g.setColor(new Color(90, 60, 35));
        g.setStroke(new BasicStroke(3.5f));
        g.drawOval((int) cx - 22, (int) cy - 82, 44, 16);

        // Mary Mother of Sorrows
        drawMaryFigure(g, W * 0.24f, H * 0.46f, 1.25f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderCarryingCross() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Carrying of the Cross", "The 4th Sorrowful Mystery · Luke 23:26-32", new Color(40, 25, 28), new Color(18, 10, 12));

        // Heavy Wooden Cross
        g.setColor(new Color(95, 60, 35));
        g.setStroke(new BasicStroke(16f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g.drawLine((int) (W * 0.35f), (int) (H * 0.65f), (int) (W * 0.72f), (int) (H * 0.22f));
        g.drawLine((int) (W * 0.42f), (int) (H * 0.30f), (int) (W * 0.62f), (int) (H * 0.48f));

        // Jesus meeting His Mother (4th Station)
        drawMaryFigure(g, W * 0.28f, H * 0.46f, 1.3f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderCrucifixion() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Crucifixion & Death of Jesus", "The 5th Sorrowful Mystery · John 19:25-30", new Color(25, 18, 30), new Color(10, 6, 12));

        // The Holy Cross on Golgotha
        float cx = W / 2f, cy = H * 0.36f;
        g.setColor(new Color(90, 55, 30));
        g.fillRect((int) cx - 10, 60, 20, (int) (H * 0.70f));
        g.fillRect((int) cx - 75, 115, 150, 20);

        // Halo of the Crucified Lord
        drawHalo(g, cx, 115, 55, new Color(255, 240, 160, 220));

        // Mother Mary standing at foot of Cross (Stabat Mater)
        drawMaryFigure(g, W * 0.30f, H * 0.50f, 1.35f, true, false);

        // Saint John on right
        drawHalo(g, W * 0.70f, H * 0.44f, 45, new Color(201, 162, 39, 160));

        g.dispose();
        return img;
    }

    // --- GLORIOUS MYSTERIES ---

    private static BufferedImage renderResurrection() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Resurrection of Jesus", "The 1st Glorious Mystery · Matthew 28:1-10", new Color(25, 65, 80), new Color(12, 30, 42));

        // Victorious Christ rising in celestial light
        float cx = W / 2f, cy = H * 0.36f;
        drawHalo(g, cx, cy, 115, new Color(255, 250, 200, 255));
        g.setColor(Color.WHITE);
        g.fillOval((int) cx - 26, (int) cy - 25, 52, 140);
        g.setColor(new Color(255, 230, 205));
        g.fillOval((int) cx - 18, (int) cy - 75, 36, 42);

        // Victory Banner with Red Cross
        g.setColor(new Color(201, 162, 39));
        g.fillRect((int) cx + 22, (int) cy - 85, 4, 160);
        g.setColor(Color.WHITE);
        g.fillRect((int) cx + 26, (int) cy - 85, 36, 24);
        g.setColor(new Color(200, 30, 40));
        g.fillRect((int) cx + 42, (int) cy - 85, 5, 24);
        g.fillRect((int) cx + 26, (int) cy - 75, 36, 5);

        // Joyful Mother Mary
        drawMaryFigure(g, W * 0.22f, H * 0.46f, 1.25f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderAscension() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Ascension of Jesus", "The 2nd Glorious Mystery · Acts 1:6-11", new Color(20, 45, 90), new Color(10, 22, 50));

        // Christ ascending in clouds
        float cx = W / 2f, cy = H * 0.28f;
        drawHalo(g, cx, cy, 85, new Color(255, 245, 180, 240));
        g.setColor(Color.WHITE);
        g.fillOval((int) cx - 24, (int) cy - 20, 48, 110);
        g.setColor(new Color(250, 225, 205));
        g.fillOval((int) cx - 16, (int) cy - 65, 32, 36);

        // Mary in the center looking up in adoration
        drawMaryFigure(g, W * 0.50f, H * 0.52f, 1.3f, true, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderPentecost() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Descent of the Holy Spirit", "The 3rd Glorious Mystery · Acts 2:1-4", new Color(42, 22, 50), new Color(20, 10, 28));

        // Holy Spirit Dove atop with tongues of fire
        float cx = W / 2f, cy = H * 0.22f;
        drawHalo(g, cx, cy, 65, new Color(255, 240, 160, 240));
        g.setColor(Color.WHITE);
        g.fillOval((int) cx - 16, (int) cy - 10, 32, 20);

        // Tongue of Fire over Mary
        g.setColor(new Color(255, 100, 20));
        g.fillOval((int) cx - 8, (int) (H * 0.32f), 16, 26);
        g.setColor(new Color(255, 230, 80));
        g.fillOval((int) cx - 4, (int) (H * 0.32f + 5), 8, 16);

        // Blessed Mother Mary seated in prayer at center of Upper Room
        drawMaryFigure(g, cx, H * 0.46f, 1.4f, true, false);

        // Apostles flanking
        drawHalo(g, W * 0.28f, H * 0.48f, 40, new Color(201, 162, 39, 160));
        drawHalo(g, W * 0.72f, H * 0.48f, 40, new Color(201, 162, 39, 160));

        g.dispose();
        return img;
    }

    private static BufferedImage renderAssumption() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Assumption of Mary", "The 4th Glorious Mystery · Revelation 12:1", new Color(18, 48, 95), new Color(10, 25, 55));

        // Mother Mary assumed into Heaven
        drawMaryFigure(g, W / 2f, H * 0.40f, 1.45f, true, false);

        // Host of Angels carrying Our Lady
        drawAngelFigure(g, W * 0.24f, H * 0.34f, 1.15f, true);
        drawAngelFigure(g, W * 0.76f, H * 0.34f, 1.15f, false);
        drawAngelFigure(g, W * 0.30f, H * 0.54f, 1.0f, true);
        drawAngelFigure(g, W * 0.70f, H * 0.54f, 1.0f, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderCoronation() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Coronation of Our Lady", "The 5th Glorious Mystery · Queen of Heaven", new Color(30, 22, 65), new Color(14, 10, 36));

        // Mother Mary in the center
        drawMaryFigure(g, W / 2f, H * 0.45f, 1.4f, true, false);

        // Crown of 12 Stars and Gold Crown
        float cx = W / 2f, cy = H * 0.26f;
        drawHalo(g, cx, cy, 75, new Color(255, 245, 170, 250));

        // Crown
        g.setColor(new Color(232, 201, 106));
        Path2D crown = new Path2D.Float();
        crown.moveTo(cx - 30, cy + 12);
        crown.lineTo(cx - 34, cy - 10);
        crown.lineTo(cx - 16, cy + 2);
        crown.lineTo(cx, cy - 18);
        crown.lineTo(cx + 16, cy + 2);
        crown.lineTo(cx + 34, cy - 10);
        crown.lineTo(cx + 30, cy + 12);
        crown.closePath();
        g.fill(crown);

        // 12 Stars of Revelation
        g.setColor(new Color(255, 255, 220));
        for (int i = 0; i < 12; i++) {
            double a = (Math.PI * 2 / 12) * i;
            g.fillOval((int) (cx + Math.cos(a) * 48 - 4), (int) (cy + Math.sin(a) * 48 - 4), 8, 8);
        }

        g.dispose();
        return img;
    }

    // --- MARIAN DEVOTIONS ---

    private static BufferedImage renderDevFatima() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of Fatima", "Feast: May 13 · Portugal, 1917", new Color(18, 44, 82), new Color(8, 20, 42));

        drawMaryFigure(g, W / 2f, H * 0.42f, 1.45f, true, false);

        // Holmoak tree at base
        g.setColor(new Color(45, 75, 40));
        g.fillOval((int) (W / 2f - 40), (int) (H * 0.62f), 80, 24);

        // Three shepherd children on side
        g.setColor(new Color(201, 162, 39, 180));
        for (int i = 0; i < 3; i++) {
            g.fillOval((int) (W * 0.22f + i * 22), (int) (H * 0.60f), 16, 28);
        }

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevLourdes() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of Lourdes", "Feast: February 11 · France, 1858", new Color(20, 50, 92), new Color(10, 24, 50));

        // Grotto Arch
        g.setColor(new Color(75, 80, 90, 180));
        g.fillArc((int) (W * 0.30f), (int) (H * 0.12f), (int) (W * 0.40f), (int) (H * 0.70f), 0, 180);

        // Our Lady with blue sash and yellow roses at feet
        drawMaryFigure(g, W / 2f, H * 0.43f, 1.4f, true, false);

        // Yellow roses at feet
        g.setColor(new Color(255, 225, 60));
        g.fillOval((int) (W / 2f - 18), (int) (H * 0.58f), 10, 10);
        g.fillOval((int) (W / 2f + 8), (int) (H * 0.58f), 10, 10);

        // Bernadette kneeling
        drawHalo(g, W * 0.22f, H * 0.54f, 32, new Color(243, 217, 139, 160));

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevGuadalupe() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of Guadalupe", "Feast: December 12 · Patroness of the Americas", new Color(18, 48, 70), new Color(10, 24, 38));

        // Full body golden sunburst mandorla
        float cx = W / 2f, cy = H * 0.43f;
        drawHalo(g, cx, cy, 140, new Color(255, 235, 120, 240));

        // Our Lady with star-studded green mantle
        drawMaryFigure(g, cx, cy, 1.4f, true, false);

        // Crescent moon beneath feet and angel
        g.setColor(new Color(40, 45, 55));
        g.fillArc((int) cx - 40, (int) (H * 0.60f), 80, 26, 0, -180);

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevPerpetualHelp() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of Perpetual Help", "Feast: June 27 · Holy Icon of Grace", new Color(42, 28, 18), new Color(22, 14, 8));

        // Byzantine Gold leaf ground
        drawHalo(g, W / 2f, H * 0.40f, 130, new Color(243, 217, 139, 230));

        // Virgin and Child
        drawMaryFigure(g, W / 2f, H * 0.44f, 1.45f, false, true);

        // Archangels Michael & Gabriel on sides with instruments of Passion
        drawAngelFigure(g, W * 0.24f, H * 0.30f, 0.9f, true);
        drawAngelFigure(g, W * 0.76f, H * 0.30f, 0.9f, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevMountCarmel() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of Mount Carmel", "Feast: July 16 · The Brown Scapular", new Color(35, 26, 48), new Color(18, 12, 26));

        drawMaryFigure(g, W / 2f, H * 0.43f, 1.4f, false, true);

        // Brown Scapular extended from hand
        g.setColor(new Color(105, 65, 40));
        g.fillRect((int) (W * 0.64f), (int) (H * 0.44f), 24, 30);
        g.setColor(new Color(232, 201, 106));
        g.setStroke(new BasicStroke(1.5f));
        g.drawRect((int) (W * 0.64f), (int) (H * 0.44f), 24, 30);
        g.drawLine((int) (W * 0.64f + 12), (int) (H * 0.44f - 18), (int) (W * 0.64f + 12), (int) (H * 0.44f));

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevImmaculateHeart() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Immaculate Heart of Mary", "Feast: Saturday after Sacred Heart", new Color(45, 18, 30), new Color(22, 8, 14));

        drawMaryFigure(g, W / 2f, H * 0.43f, 1.45f, false, false);

        // Radiant Immaculate Heart on breast
        float cx = W / 2f, cy = H * 0.39f;
        drawHalo(g, cx, cy, 40, new Color(255, 235, 140, 240));

        // Heart shape
        g.setColor(new Color(205, 30, 45));
        Path2D h = new Path2D.Float();
        h.moveTo(cx, cy + 14);
        h.curveTo(cx - 18, cy + 2, cx - 18, cy - 14, cx, cy - 4);
        h.curveTo(cx + 18, cy - 14, cx + 18, cy + 2, cx, cy + 14);
        h.closePath();
        g.fill(h);

        // Wreath of white roses around heart
        g.setColor(Color.WHITE);
        g.fillOval((int) cx - 12, (int) cy - 2, 7, 7);
        g.fillOval((int) cx + 5, (int) cy - 2, 7, 7);

        // Piercing sword of sorrow
        g.setColor(new Color(200, 200, 215));
        g.setStroke(new BasicStroke(2.5f));
        g.drawLine((int) cx + 22, (int) cy - 18, (int) cx - 14, (int) cy + 14);

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevRosary() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of the Rosary", "Feast: October 7 · Queen of the Holy Rosary", new Color(18, 42, 75), new Color(8, 20, 38));

        drawMaryFigure(g, W / 2f, H * 0.43f, 1.45f, false, true);

        // Golden Rosary Beads draped
        g.setColor(new Color(232, 201, 106));
        g.setStroke(new BasicStroke(2f));
        g.drawArc((int) (W / 2f - 40), (int) (H * 0.40f), 80, 70, 0, -180);

        // Rosary Cross
        float rx = W / 2f, ry = H * 0.40f + 70;
        g.fillRect((int) rx - 3, (int) ry, 6, 22);
        g.fillRect((int) rx - 9, (int) ry + 5, 18, 5);

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevSorrows() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Our Lady of Sorrows", "Feast: September 15 · Mater Dolorosa", new Color(32, 16, 24), new Color(16, 8, 12));

        drawMaryFigure(g, W / 2f, H * 0.44f, 1.45f, true, false);

        // 7 Swords of Sorrow radiating from heart
        float cx = W / 2f, cy = H * 0.42f;
        g.setColor(new Color(220, 220, 235));
        g.setStroke(new BasicStroke(2.2f));
        for (int i = 0; i < 7; i++) {
            double a = Math.PI / 6 * (i - 3) - Math.PI / 2;
            g.drawLine((int) cx, (int) cy, (int) (cx + Math.cos(a) * 45), (int) (cy + Math.sin(a) * 45));
        }

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevMiraculousMedal() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "The Miraculous Medal", "Feast: November 27 · Graces to the World", new Color(20, 48, 85), new Color(10, 24, 45));

        // Miraculous Medal Oval Frame
        drawHalo(g, W / 2f, H * 0.42f, 135, new Color(243, 217, 139, 210));

        // Mary standing on globe, crushing serpent
        drawMaryFigure(g, W / 2f, H * 0.43f, 1.4f, false, false);

        // Rays of Grace flowing from hands
        g.setColor(new Color(255, 250, 210, 180));
        g.setStroke(new BasicStroke(2f));
        for (int i = -3; i <= 3; i++) {
            g.drawLine((int) (W / 2f - 35), (int) (H * 0.40f), (int) (W * 0.28f + i * 14), (int) (H * 0.64f));
            g.drawLine((int) (W / 2f + 35), (int) (H * 0.40f), (int) (W * 0.72f + i * 14), (int) (H * 0.64f));
        }

        g.dispose();
        return img;
    }

    private static BufferedImage renderDevQueenOfHeaven() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Queen of Heaven", "Feast: August 22 · Regina Caeli", new Color(28, 20, 60), new Color(14, 10, 32));

        drawMaryFigure(g, W / 2f, H * 0.43f, 1.45f, true, false);

        // Glorious Royal Crown and Angels
        float cx = W / 2f, cy = H * 0.25f;
        drawHalo(g, cx, cy, 70, new Color(255, 235, 140, 250));
        g.setColor(new Color(232, 201, 106));
        g.fillRoundRect((int) cx - 28, (int) cy - 5, 56, 18, 6, 6);

        drawAngelFigure(g, W * 0.24f, H * 0.35f, 1.2f, true);
        drawAngelFigure(g, W * 0.76f, H * 0.35f, 1.2f, false);

        g.dispose();
        return img;
    }

    private static BufferedImage renderKreupasanam() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Kreupasanam Mathavu", "Mother of Grace · Marian Shrine, Kalavoor", new Color(20, 48, 88), new Color(10, 24, 48));

        // The golden Seat of Grace & radiance
        drawHalo(g, W / 2f, H * 0.42f, 130, new Color(243, 217, 139, 230));

        // Mother Mary of Kreupasanam holding Child Jesus
        drawMaryFigure(g, W / 2f, H * 0.44f, 1.45f, false, true);

        // Rose garland
        g.setColor(new Color(215, 60, 80));
        for (int i = 0; i < 9; i++) {
            double a = Math.PI * 0.12 * (i - 4) + Math.PI / 2;
            g.fillOval((int) (W / 2f + Math.cos(a) * 75 - 5), (int) (H * 0.44f + Math.sin(a) * 75 - 5), 10, 10);
        }

        g.dispose();
        return img;
    }

    private static BufferedImage renderStJoseph() {
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(img);
        drawSacredArch(g, "Saint Joseph", "Patron of the Universal Church · Terror of Demons", new Color(42, 32, 20), new Color(20, 14, 8));

        float cx = W / 2f, cy = H * 0.43f;
        drawHalo(g, cx, cy - 60, 75, new Color(243, 217, 139, 220));

        // Saint Joseph holding Child Jesus and White Lilies
        g.setColor(new Color(125, 75, 45)); // Brown mantle
        g.fillOval((int) cx - 34, (int) cy - 25, 68, 150);
        g.setColor(new Color(242, 216, 188));
        g.fillOval((int) cx - 18, (int) cy - 80, 36, 42);

        // White beard
        g.setColor(new Color(220, 220, 220));
        g.fillArc((int) cx - 16, (int) cy - 56, 32, 30, 0, -180);

        // White Lilies in hand (Purity)
        g.setColor(new Color(60, 140, 60));
        g.setStroke(new BasicStroke(3.5f));
        g.drawLine((int) cx - 35, (int) cy + 30, (int) cx - 55, (int) cy - 50);
        g.setColor(Color.WHITE);
        g.fillOval((int) cx - 64, (int) cy - 65, 18, 24);
        g.fillOval((int) cx - 50, (int) cy - 58, 20, 16);

        // Child Jesus in arm
        drawHalo(g, cx + 28, cy - 15, 34, new Color(255, 235, 140, 220));
        g.setColor(new Color(255, 255, 255));
        g.fillOval((int) cx + 12, (int) cy - 5, 32, 45);
        g.setColor(new Color(250, 228, 204));
        g.fillOval((int) cx + 18, (int) cy - 28, 20, 24);

        g.dispose();
        return img;
    }
}
