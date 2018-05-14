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

    public ImgB(int width, int height, String fileName) {
        
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
            img.setRGB(x, 1000-y, new Color(red, green, blue).getRGB());
        } else {
            img.setRGB(x, y, new Color(red, green, blue).getRGB());
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
