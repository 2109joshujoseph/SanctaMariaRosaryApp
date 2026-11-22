import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProcessImages {
    public static void main(String[] args) throws Exception {
        String base = args.length > 0 ? args[0] : ".";
        File userMary = new File(args[1]);
        File userIcon = new File(args[2]);

        System.out.println("Reading user Mary image from: " + userMary.getAbsolutePath());
        BufferedImage maryImg = ImageIO.read(userMary);
        System.out.println("Mary image: " + maryImg.getWidth() + "x" + maryImg.getHeight());

        System.out.println("Reading user Icon image from: " + userIcon.getAbsolutePath());
        BufferedImage iconImg = ImageIO.read(userIcon);
        System.out.println("Icon image: " + iconImg.getWidth() + "x" + iconImg.getHeight());

        File resDir = new File(base, "app/src/main/res");
        File drDir = new File(resDir, "drawable");
        drDir.mkdirs();

        // 1. Save mary_splash.png
        File splashFile = new File(drDir, "mary_splash.png");
        ImageIO.write(maryImg, "png", splashFile);
        System.out.println("Wrote " + splashFile.getAbsolutePath());

        // 2. Save mary_main.jpg (convert to RGB)
        BufferedImage maryRgb = new BufferedImage(maryImg.getWidth(), maryImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gMary = maryRgb.createGraphics();
        gMary.drawImage(maryImg, 0, 0, null);
        gMary.dispose();
        File mainFile = new File(drDir, "mary_main.jpg");
        ImageIO.write(maryRgb, "jpg", mainFile);
        System.out.println("Wrote " + mainFile.getAbsolutePath());

        // 3. Generate mipmap icons from new circular iconImg
        int[] sizes = {48, 72, 96, 144, 192};
        String[] dirs = {"mipmap-mdpi", "mipmap-hdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi"};

        for (int i = 0; i < sizes.length; i++) {
            int s = sizes[i];
            File dir = new File(resDir, dirs[i]);
            dir.mkdirs();

            // Circular clip with high-quality bicubic resampling
            BufferedImage round = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gR = round.createGraphics();
            gR.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            gR.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            gR.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Inset by 0.5px so anti-aliasing doesn't clip on pixel edges
            gR.setClip(new Ellipse2D.Float(0.5f, 0.5f, s - 1f, s - 1f));
            gR.drawImage(iconImg, 0, 0, s, s, null);
            gR.dispose();

            File fR = new File(dir, "ic_launcher_round.png");
            ImageIO.write(round, "png", fR);

            // Standard ic_launcher (transparent corners with circular medallion)
            BufferedImage square = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gSq = square.createGraphics();
            gSq.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            gSq.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            gSq.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gSq.setClip(new Ellipse2D.Float(0.5f, 0.5f, s - 1f, s - 1f));
            gSq.drawImage(iconImg, 0, 0, s, s, null);
            gSq.dispose();

            File fSq = new File(dir, "ic_launcher.png");
            ImageIO.write(square, "png", fSq);

            System.out.println("Wrote icons in " + dirs[i] + " (" + s + "x" + s + ")");
        }
        System.out.println("All images and icons processed successfully!");
    }
}
