import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class ArtGen {
    static final int W = 900, H = 600;
    static Random RND = new Random(42);

    public static void main(String[] args) throws Exception {
        String base = args.length > 0 ? args[0] : "app/src/main/res";
        String dr = base + "/drawable";
        String mm = base + "/mipmap-mdpi";
        new File(dr).mkdirs();
        new File(mm).mkdirs();
        for (String d : new String[]{"mipmap-hdpi","mipmap-xhdpi","mipmap-xxhdpi","mipmap-xxxhdpi"}) new File(base+"/"+d).mkdirs();
        // mary
        save(imgMaryMain(), dr + "/mary_main.jpg");
        savePng(imgMarySplash(), dr + "/mary_splash.png");
        // mysteries
        String[] sets = {"j","s","g","l"};
        String[][] titles = {
            {"Annunciation","Visitation","Nativity","Presentation","Finding"},
            {"Agony","Scourging","Crowning","Carrying","Crucifixion"},
            {"Resurrection","Ascension","Pentecost","Assumption","Coronation"},
            {"Baptism","Cana","Kingdom","Transfiguration","Eucharist"}
        };
        Color[][] palettes = {
            {new Color(0x1B3A6B), new Color(0x5C8FD6), new Color(0xF3D98B)},
            {new Color(0x3A1B2E), new Color(0x8B3A5C), new Color(0xE8C96A)},
            {new Color(0x1B4A3A), new Color(0x4E9E7C), new Color(0xF3D98B)},
            {new Color(0x2E2A1B), new Color(0x9E8A4E), new Color(0xF3D98B)}
        };
        for (int si = 0; si < sets.length; si++) {
            for (int n = 1; n <= 5; n++) {
                save(sceneMystery(sets[si], n, palettes[si]), dr + "/myst_" + sets[si] + n + ".jpg");
            }
        }
        // devotions
        String[] devs = {"fatima","lourdes","guadalupe","perpetual","carmel","immaculate","rosary","sorrows","medal","queen"};
        Color[] devTop = {new Color(0x2E3A1B),new Color(0x1B3A6B),new Color(0x6B3A1B),new Color(0x3A2E1B),new Color(0x2E1B3A),new Color(0x3A1B2E),new Color(0x1B4A4A),new Color(0x3A1B1B),new Color(0x1B2E4A),new Color(0x4A3A1B)};
        for (int i = 0; i < devs.length; i++) {
            save(sceneDevotion(devs[i], devTop[i]), dr + "/dev_" + devs[i] + ".jpg");
        }
        // kreupasanam, st_joseph
        save(sceneKreupasanam(), dr + "/kreupasanam.jpg");
        save(sceneJoseph(), dr + "/st_joseph.jpg");
        // launcher icons
        for (String size : new String[]{"mdpi","hdpi","xhdpi","xxhdpi","xxxhdpi"}) {}
        int[] sizes = {48,72,96,144,192};
        String[] dirs = {"mipmap-mdpi","mipmap-hdpi","mipmap-xhdpi","mipmap-xxhdpi","mipmap-xxxhdpi"};
        for (int i = 0; i < sizes.length; i++) {
            BufferedImage ic = icon(sizes[i]);
            savePng(ic, base + "/" + dirs[i] + "/ic_launcher.png");
            savePng(ic, base + "/" + dirs[i] + "/ic_launcher_round.png");
        }
        System.out.println("ALL DONE");
    }

    static void save(BufferedImage im, String path) throws Exception {
        ImageIO.write(im, "jpg", new File(path));
    }
    static void savePng(BufferedImage im, String path) throws Exception {
        ImageIO.write(im, "png", new File(path));
    }

    // ---------- backgrounds ----------
    static void bg(Graphics2D g, int w, int h, Color top, Color bottom, boolean stars) {
        g.setPaint(new GradientPaint(0,0,top,0,h,bottom));
        g.fillRect(0,0,w,h);
        if (stars) {
            g.setColor(new Color(255,255,240,180));
            for (int i=0;i<60;i++){
                int x=(i*97)%w; int y=(i*53)% (h/2);
                int r = (i%3)+1;
                g.fillOval(x,y,r,r);
            }
        }
        // clouds
        g.setColor(new Color(255,255,255,40));
        for (int i=0;i<4;i++){
            int cx = (i*230 + 80) % w;
            int cy = 60 + i*40;
            g.fillOval(cx, cy, 180, 46);
            g.fillOval(cx+60, cy-12, 140, 40);
            g.fillOval(cx+120, cy, 160, 44);
        }
    }

    static void halo(Graphics2D g, float cx, float cy, float r, Color c) {
        for (int i=6;i>=1;i--){
            float rr = r*i/6f;
            int a = (int)(90*(1f-i/7f));
            g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),a));
            g.fillOval((int)(cx-rr),(int)(cy-rr),(int)(rr*2),(int)(rr*2));
        }
    }

    static void mary(Graphics2D g, float cx, float cy, float scale, Color robe, Color veil, boolean halo) {
        if (halo) halo(g, cx, cy - 60*scale, 70*scale, new Color(0xE8C96A));
        // veil
        g.setColor(veil);
        Path2D v = new Path2D.Float();
        v.moveTo(cx-40*scale, cy-80*scale);
        v.curveTo(cx-70*scale, cy-40*scale, cx-60*scale, cy+20*scale, cx-30*scale, cy+40*scale);
        v.lineTo(cx+30*scale, cy+40*scale);
        v.curveTo(cx+60*scale, cy+20*scale, cx+70*scale, cy-40*scale, cx+40*scale, cy-80*scale);
        v.closePath();
        g.fill(v);
        // face
        g.setColor(new Color(0xF2D6B3));
        g.fillOval((int)(cx-22*scale),(int)(cy-95*scale),(int)(44*scale),(int)(48*scale));
        // robe
        g.setColor(robe);
        Path2D r = new Path2D.Float();
        r.moveTo(cx-35*scale, cy-40*scale);
        r.curveTo(cx-70*scale, cy+20*scale, cx-80*scale, cy+120*scale, cx-60*scale, cy+150*scale);
        r.lineTo(cx+60*scale, cy+150*scale);
        r.curveTo(cx+80*scale, cy+120*scale, cx+70*scale, cy+20*scale, cx+35*scale, cy-40*scale);
        r.closePath();
        g.fill(r);
        // hands
        g.setColor(new Color(0xF2D6B3));
        g.fillOval((int)(cx-75*scale),(int)(cy+10*scale),(int)(22*scale),(int)(18*scale));
        g.fillOval((int)(cx+53*scale),(int)(cy+10*scale),(int)(22*scale),(int)(18*scale));
    }

    static BufferedImage imgMaryMain() {
        BufferedImage im = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g,W,H,new Color(0x1B3A6B),new Color(0x0E2347),true);
        // radiant center
        halo(g,W/2f,H/2f-40,220,new Color(0xE8C96A));
        mary(g,W/2f,H/2f+40,1.6f,new Color(0x1B3A6B),new Color(0x2C5C9E),true);
        // gold title band
        g.setColor(new Color(0xC9A227));
        g.fillRect(0,H-70,W,70);
        g.setColor(new Color(0x1B3A6B));
        g.setFont(new Font("Serif",Font.BOLD,26));
        g.drawString("A Gift to Mama Mary", 40, H-30);
        g.dispose();
        return im;
    }

    static BufferedImage imgMarySplash() {
        int w=720,h=1280;
        BufferedImage im = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // transparent background
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0,0,w,h);
        g.setComposite(AlphaComposite.SrcOver);
        halo(g,w/2f,h*0.42f,260,new Color(0xE8C96A));
        mary(g,w/2f,h*0.5f,2.4f,new Color(0x1B3A6B),new Color(0x2C5C9E),true);
        g.dispose();
        return im;
    }

    static BufferedImage sceneMystery(String set, int n, Color[] pal) {
        BufferedImage im = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g,W,H,pal[0],pal[1], n%2==0);
        halo(g,W/2f,H/2f,180,pal[2]);
        // mary left
        mary(g,W*0.32f,H*0.62f,1.25f,pal[0],pal[1],true);
        // symbol right
        drawSymbol(g, set+"_"+n, W*0.72f, H*0.42f, 90);
        // bottom band
        g.setColor(new Color(0,0,0,90));
        g.fillRect(0,H-52,W,52);
        g.setColor(pal[2]);
        g.setFont(new Font("Serif",Font.BOLD,20));
        g.drawString(set.toUpperCase()+" "+n, 30, H-20);
        g.dispose();
        return im;
    }

    static void drawSymbol(Graphics2D g, String key, float cx, float cy, float s) {
        g.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // default gold
        g.setColor(new Color(0xE8C96A));
        if (key.startsWith("j")) {
            if (key.endsWith("1")) { // annunciation - lily + dove
                // dove
                g.setColor(Color.WHITE);
                Path2D d = new Path2D.Float();
                d.moveTo(cx,cy); d.curveTo(cx-30,cy-40,cx-80,cy-30,cx-90,cy); d.curveTo(cx-60,cy+10,cx-20,cy+10,cx,cy); d.closePath();
                g.fill(d);
                g.setColor(new Color(0xE8C96A));
                g.drawOval((int)(cx-6),(int)(cy-6),12,12);
            } else if (key.endsWith("2")) { // visitation - two figures
                g.setColor(new Color(0xF2D6B3)); g.fillOval((int)(cx-60),(int)(cy-20),40,40); g.fillOval((int)(cx+20),(int)(cy-20),40,40);
            } else if (key.endsWith("3")) { // nativity - star
                star(g,cx,cy,s,new Color(0xF3D98B));
            } else if (key.endsWith("4")) { // presentation - candle
                g.setColor(new Color(0xF3D98B)); g.fillRect((int)(cx-6),(int)(cy-40),12,80); g.fillOval((int)(cx-12),(int)(cy-60),24,24);
            } else { // finding - book
                g.setColor(new Color(0x8B5A2B)); g.fillRect((int)(cx-50),(int)(cy-30),100,60); g.setColor(new Color(0xF3D98B)); g.drawLine((int)cx,(int)(cy-30),(int)cx,(int)(cy+30));
            }
        } else if (key.startsWith("s")) {
            if (key.endsWith("1")) { // agony - moon
                g.setColor(new Color(0xFFF0C0)); g.fillOval((int)(cx-40),(int)(cy-40),80,80); g.setColor(Color.BLACK); g.fillOval((int)(cx-20),(int)(cy-40),80,80);
            } else if (key.endsWith("2")) { // scourging - pillar
                g.setColor(new Color(0x9E9E9E)); g.fillRect((int)(cx-15),(int)(cy-60),30,120);
            } else if (key.endsWith("3")) { // crowning - thorns
                g.setColor(new Color(0x8B5A2B)); 
                for (int i=0;i<8;i++){ double a=Math.PI/4*i; g.drawLine((int)cx,(int)cy,(int)(cx+Math.cos(a)*60),(int)(cy+Math.sin(a)*60)); }
            } else if (key.endsWith("4")) { // carrying cross
                g.setColor(new Color(0x8B5A2B)); g.fillRect((int)(cx-8),(int)(cy-70),16,140); g.fillRect((int)(cx-40),(int)(cy-30),80,16);
            } else { // crucifixion
                g.setColor(new Color(0x5C4033)); g.fillRect((int)(cx-6),(int)(cy-80),12,160); g.fillRect((int)(cx-40),(int)(cy-40),80,12);
            }
        } else if (key.startsWith("g")) {
            if (key.endsWith("1")) { star(g,cx,cy,s,new Color(0xF3D98B)); }
            else if (key.endsWith("2")) { // ascension rays
                g.setColor(new Color(0xF3D98B));
                for (int i=0;i<8;i++){ double a=Math.PI/4*i; g.drawLine((int)cx,(int)cy,(int)(cx+Math.cos(a)*70),(int)(cy+Math.sin(a)*70)); }
            } else if (key.endsWith("3")) { // pentecost flames
                g.setColor(new Color(0xFF6A00)); 
                for (int i=-2;i<=2;i++) flame(g,cx+i*28,cy,24);
            } else if (key.endsWith("4")) { // assumption clouds
                g.setColor(new Color(255,255,255,160)); 
                for (int i=0;i<3;i++) g.fillOval((int)(cx-60+i*20),(int)(cy-20+i*10),90,36);
            } else { // coronation crown
                crown(g,cx,cy,s);
            }
        } else { // luminous
            if (key.endsWith("1")) { // baptism water
                g.setColor(new Color(0x5C8FD6)); 
                for (int i=0;i<5;i++) g.drawArc((int)(cx-60),(int)(cy-20+i*8),120,20,0,180);
                // dove
                g.setColor(Color.WHITE); g.fillOval((int)(cx-12),(int)(cy-70),24,16);
            } else if (key.endsWith("2")) { // cana jars
                g.setColor(new Color(0x8B5A2B)); g.fillRect((int)(cx-40),(int)(cy-20),30,60); g.fillRect((int)(cx+10),(int)(cy-20),30,60);
                g.setColor(new Color(0x9E0000)); g.fillRect((int)(cx-38),(int)(cy+5),26,30); g.fillRect((int)(cx+12),(int)(cy+5),26,30);
            } else if (key.endsWith("3")) { // kingdom sun
                g.setColor(new Color(0xF3D98B)); g.fillOval((int)(cx-40),(int)(cy-40),80,80);
            } else if (key.endsWith("4")) { // transfiguration rays
                g.setColor(new Color(0xFFF0C0));
                for (int i=0;i<12;i++){ double a=Math.PI/6*i; g.drawLine((int)cx,(int)cy,(int)(cx+Math.cos(a)*80),(int)(cy+Math.sin(a)*80)); }
            } else { // eucharist chalice
                g.setColor(new Color(0xE8C96A)); 
                g.fillRect((int)(cx-25),(int)(cy+10),50,8); 
                g.drawArc((int)(cx-30),(int)(cy-30),60,60,0,-180);
                g.setColor(new Color(0xFFF0C0)); g.fillOval((int)(cx-12),(int)(cy-10),24,16);
            }
        }
    }

    static void star(Graphics2D g, float cx, float cy, float r, Color c) {
        g.setColor(c);
        int[] xs=new int[10], ys=new int[10];
        for (int i=0;i<10;i++){
            double a=Math.PI/5*i - Math.PI/2;
            float rr = (i%2==0)? r : r*0.4f;
            xs[i]=(int)(cx+Math.cos(a)*rr); ys[i]=(int)(cy+Math.sin(a)*rr);
        }
        g.fillPolygon(xs,ys,10);
    }
    static void flame(Graphics2D g, float cx, float cy, float s) {
        g.setColor(new Color(0xFF6A00));
        Path2D p=new Path2D.Float();
        p.moveTo(cx,cy+s); p.curveTo(cx-s,cy+s/2,cx-s*0.6f,cy-s,cx,cy-s*1.4f); p.curveTo(cx+s*0.6f,cy-s,cx+s,cy+s/2,cx,cy+s); p.closePath();
        g.fill(p);
    }
    static void crown(Graphics2D g, float cx, float cy, float s) {
        g.setColor(new Color(0xE8C96A));
        int[] xs={(int)(cx-s),(int)(cx-s*0.5f),(int)cx,(int)(cx+s*0.5f),(int)(cx+s)};
        int[] ys={(int)(cy+s*0.3f),(int)(cy-s*0.5f),(int)(cy+s*0.1f),(int)(cy-s*0.5f),(int)(cy+s*0.3f)};
        g.fillPolygon(xs,ys,5);
        g.fillRect((int)(cx-s),(int)(cy+s*0.3f),(int)(s*2),(int)(s*0.25f));
    }

    static BufferedImage sceneDevotion(String id, Color top) {
        BufferedImage im = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g,W,H,top,new Color(0x0E2347),true);
        halo(g,W/2f,H/2f,200,new Color(0xE8C96A));
        mary(g,W/2f,H*0.62f,1.35f,top,new Color(0x2C5C9E),true);
        // symbol
        if (id.equals("fatima")) { star(g,W*0.78f,H*0.28f,40,new Color(0xF3D98B)); }
        else if (id.equals("lourdes")) { g.setColor(new Color(0x5C8FD6)); g.fillOval((int)(W*0.72f-30),(int)(H*0.32f-30),60,60); }
        else if (id.equals("guadalupe")) { g.setColor(new Color(0xE8C96A)); g.fillOval((int)(W*0.72f-40),(int)(H*0.3f-50),80,100); }
        else if (id.equals("perpetual")) { g.setColor(new Color(0xC9A227)); g.drawOval((int)(W*0.7f-45),(int)(H*0.25f-60),90,120); }
        else if (id.equals("carmel")) { g.setColor(new Color(0x8B5A2B)); g.fillRect((int)(W*0.7f-10),(int)(H*0.25f-50),20,100); }
        else if (id.equals("immaculate")) { 
            g.setColor(new Color(0xFF6A6A)); 
            // heart
            Path2D h=new Path2D.Float(); float cx=W*0.72f,cy=H*0.32f;
            h.moveTo(cx,cy+40); h.curveTo(cx-60,cy-20,cx-60,cy-70,cx,cy-40); h.curveTo(cx+60,cy-70,cx+60,cy-20,cx,cy+40); g.fill(h);
        }
        else if (id.equals("rosary")) { 
            g.setColor(new Color(0xE8C96A)); g.drawOval((int)(W*0.68f-50),(int)(H*0.28f-50),100,100);
        }
        else if (id.equals("sorrows")) { 
            g.setColor(new Color(0x9E0000));
            for (int i=0;i<7;i++){ float a=(float)(Math.PI/7*i); g.drawLine((int)(W*0.72f),(int)(H*0.32f),(int)(W*0.72f+Math.cos(a)*50),(int)(H*0.32f+Math.sin(a)*50)); }
        }
        else if (id.equals("medal")) { g.setColor(new Color(0xC9A227)); g.drawOval((int)(W*0.7f-40),(int)(H*0.25f-55),80,110); }
        else if (id.equals("queen")) { crown(g,W*0.72f,H*0.3f,45); }
        g.setColor(new Color(0,0,0,90));
        g.fillRect(0,H-52,W,52);
        g.setColor(new Color(0xE8C96A));
        g.setFont(new Font("Serif",Font.BOLD,20));
        g.drawString(id, 30, H-20);
        g.dispose();
        return im;
    }

    static BufferedImage sceneKreupasanam() {
        BufferedImage im = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g,W,H,new Color(0x2E4A3A),new Color(0x142E22),true);
        halo(g,W/2f,H*0.35f,200,new Color(0xE8C96A));
        // shrine silhouette
        g.setColor(new Color(0x1B3A6B));
        g.fillRect((int)(W*0.35f),(int)(H*0.4f), (int)(W*0.3f), (int)(H*0.35f));
        g.setColor(new Color(0xC9A227));
        g.fillRect((int)(W*0.47f),(int)(H*0.25f), (int)(W*0.06f), (int)(H*0.15f));
        mary(g,W/2f,H*0.62f,1.2f,new Color(0x1B3A6B),new Color(0x2C5C9E),true);
        g.setColor(new Color(0,0,0,90));
        g.fillRect(0,H-52,W,52);
        g.setColor(new Color(0xE8C96A));
        g.setFont(new Font("Serif",Font.BOLD,20));
        g.drawString("Kreupasanam", 30, H-20);
        g.dispose();
        return im;
    }

    static BufferedImage sceneJoseph() {
        BufferedImage im = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bg(g,W,H,new Color(0x3A2E1B),new Color(0x1E1A0E),false);
        halo(g,W/2f,H*0.35f,180,new Color(0xE8C96A));
        // joseph figure (brown robe)
        mary(g,W/2f,H*0.62f,1.3f,new Color(0x5C4033),new Color(0x3A2E1B),false);
        // lily
        g.setColor(new Color(0xFFFFFF));
        g.fillOval((int)(W*0.68f-8),(int)(H*0.3f-30),16,60);
        g.fillOval((int)(W*0.68f-20),(int)(H*0.3f-20),16,16);
        g.fillOval((int)(W*0.68f+4),(int)(H*0.3f-20),16,16);
        // carpentry tool
        g.setColor(new Color(0x8B5A2B)); g.fillRect((int)(W*0.28f-40),(int)(H*0.55f),80,12);
        g.setColor(new Color(0x9E9E9E)); g.fillRect((int)(W*0.28f-8),(int)(H*0.53f),16,16);
        g.setColor(new Color(0,0,0,90));
        g.fillRect(0,H-52,W,52);
        g.setColor(new Color(0xE8C96A));
        g.setFont(new Font("Serif",Font.BOLD,20));
        g.drawString("St Joseph", 30, H-20);
        g.dispose();
        return im;
    }

    static BufferedImage icon(int size) {
        BufferedImage im = new BufferedImage(size,size,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // background gradient
        g.setPaint(new GradientPaint(0,0,new Color(0x1B3A6B),size,size,new Color(0x0E2347)));
        g.fillRect(0,0,size,size);
        // Mary portrait
        halo(g, size/2f, size*0.38f, size*0.28f, new Color(0xE8C96A));
        mary(g, size/2f, size*0.52f, size/180f, new Color(0x1B3A6B), new Color(0x2C5C9E), true);
        // gold ring
        g.setColor(new Color(0xC9A227));
        g.setStroke(new BasicStroke(size*0.05f));
        g.drawOval((int)(size*0.06f),(int)(size*0.06f),(int)(size*0.88f),(int)(size*0.88f));
        g.dispose();
        return im;
    }
}
