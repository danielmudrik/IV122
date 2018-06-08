package IV122;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class ImgV {
    
    public static String folderName = "";
    private static PrintWriter svgFile;
    private static double w = 1000;
    private static double h = 1000;

    public ImgV(int width, int height, String fileName) {
        init(width, height, fileName);
    }

    //init svg file
    public static void init(int width, int height, String fileName) {
        w = width;
        h = height;
        try {
            File fvFile = new File(folderName);
            fvFile.mkdirs();
            FileWriter fw = new FileWriter(folderName + fileName + ".svg");
            svgFile = new PrintWriter(fw);
            svgFile.println("<?xml version=\"1.0\" standalone=\"no\"?>\n" +
            "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n" +
            "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
            svgFile.println("<svg xmlns=\"http://www.w3.org/2000/svg\" height=\"" + height + "\" width=\"" + width + "\">");

        } catch (IOException ex) {
            Logger.getLogger(ImgV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //save svg file
    public static void save() {
        svgFile.println("</svg>");
        svgFile.close();
    }
    //line of color
    public static void line(double x1, double y1, double x2, double y2, int red, int green, int blue, double swidth) {
        svgFile.println("<line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2 + 
                "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
                ");stroke-width:" + swidth + "\" />");
    }

    public static void lineInverted(double x1, double y1, double x2, double y2, int red, int green, int blue, double swidth) {
        svgFile.println("<line x1=\"" + (w-x1) + "\" y1=\"" + (h-y1) + "\" x2=\"" + (w-x2) + "\" y2=\"" + (h-y2) + 
                "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
                ");stroke-width:" + swidth + "\" />");
    }
    //rect of color
    public static void rect(double width, double height, int red, int green, int blue) {
        svgFile.println("<rect width=\"" + width + "\" height=\"" + height + 
                "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
                ");stroke-width:" + width + "\" />");
    }    
    //circle of color
    public static void circle(double cx, double cy, double r, int red, int green, int blue, double width) {
        svgFile.println("<circle cx=\"" + cx + "\" cy=\"" + cy + "\" r=\"" + r + 
                "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
                ");stroke-width:" + width + "\" />");
    }    
    //ellipse of color
    public static void ellipse(double cx, double cy, double rx, double ry, int red, int green, int blue, double width) {
        svgFile.println("<ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry +
                "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
                ");stroke-width:" + width + "\" />");
    }      
    //other vector primitives

        /*polylines and polygons
    Bézier curves and bezigons*/
    

}
