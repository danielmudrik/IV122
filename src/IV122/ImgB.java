package IV122;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class ImgB {

    public static boolean flipImage = false;
    public static String folderName = "";
    private static File imgFile;
    private static BufferedImage img;
    private static int width = 1000;
    private static int height = 1000;
    public ImgB(int width, int height, String fileName) {
        this.width = width;
        this.height = height; 
        init(width, height, fileName);
    }

    //init file of size and type
    public static BufferedImage init(int width, int height, String fileName) {
        imgFile = new File(folderName + fileName + ".png");
        imgFile.getParentFile().mkdirs();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return img;
    }
    //put pixel of color
    public static void putPixel(int x, int y, int red, int green, int blue) {
        if (flipImage) {
            img.setRGB(x, height-y-1, new Color(red, green, blue).getRGB());
        } else {
            img.setRGB(x, y, new Color(red, green, blue).getRGB());
        }
        
    }
    
    public static Color getPixel(int x, int y) {
        return new Color(img.getRGB(x, y));
    }
    
    public static void drawLine(int x1, int y1, int x2, int y2, int red, int green, int blue) {
        double distance = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        for (int i = 0; i < distance*2; i++) {
            putPixel((int)(x1+0.5*i*(x2-x1)/distance), (int)(y1+0.5*i*(y2-y1)/distance), red, green, blue);
        }
    }
    
    //save file
    public static void save() {
        try {
            ImageIO.write(img, "PNG", imgFile);      
        } catch (IOException ex) {
            Logger.getLogger(CV1.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
}
