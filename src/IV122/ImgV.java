package IV122;

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
    
private static PrintWriter svgFile;
    
public ImgV(int width, int height, String fileName) {
    init(width, height, fileName);
}

//init svg file
public static void init(int width, int height, String fileName) {
    try {
        FileWriter fw = new FileWriter(fileName + ".svg");
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
public static void line(int x1, int y1, int x2, int y2, int red, int green, int blue, int swidth) {
    svgFile.println("<line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2 + 
            "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
            ");stroke-width:" + swidth + "\" />");
}
//rect of color
public static void rect(int width, int height, int red, int green, int blue) {
    svgFile.println("<rect width=\"" + width + "\" height=\"" + height + 
            "\" style=\"stroke:rgb(" + red + "," + green + "," + blue + 
            ");stroke-width:3;stroke:rgb(0,0,0)\" />");
}    
//circle of color
public static void circle(int cx, int cy, int r, int red, int green, int blue, int width) {
    svgFile.println("<circle cx=\"" + cx + "\" cy=\"" + cy + "\" r=\"" + r + 
            "\" stroke=\"black\" stroke-width=\"3\" fill=\"red\" />");
}    
//ellipse of color
public static void ellipse(int cx, int cy, int rx, int ry, int red, int green, int blue, int width) {
    svgFile.println("<ellipse cx=\"" + cx + "\" cy=\"" + cy + "\" rx=\"" + rx + "\" ry=\"" + ry +
            "\" stroke=\"black\" stroke-width=\"2\" fill=\"red\" />");
}      
//other vector primitives
    
    /*polylines and polygons
Bézier curves and bezigons*/
    

}
