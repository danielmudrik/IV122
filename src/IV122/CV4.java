/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IV122;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV4 {
    public static void main(String args[]) {
        ImgV.folderName = "CV4_Images//";
        ImgB.folderName = "CV4_Images//";
        //A)
        //Solid circle
        ImgB.init(200, 200, "SolidCircle");
            solidCircle(200, 100, true);
        ImgB.save();
        //Circle
        ImgB.init(200, 200, "EmptyCircle");
            solidCircle(200,100, true);
            solidCircle(200,97, false);
        ImgB.save();
        
        
        //Color spiral - TODO: steps
        ImgB.init(1000,1000,"ColorSpiral");
            colorSpiral(100, 5, 500, 500);
            colorSpiral(100, 5, 501, 500);
            colorSpiral(100, 5, 500, 501);
            colorSpiral(100, 5, 499, 500);
            colorSpiral(100, 5, 500, 499);
        ImgB.save();
        
        //Color triangle
        
        //Faded ellipse
        
        //B)
        // Ngon floodfill
        
        //C)
        
        //Reverse color
        
        //Separate parameters
        
        //Modulo color
    }
    
    public static void colorSpiral(int radius, int steps, double x, double y) {
        double cX = x;
        double cY = y;
        int r=0,g=0,b=0;
        double angle = 0;
        double distance = 0;
        double step = radius/(double)steps;
        for (int i = 0; i < 360 * 10; i++) {
            r = (int) Math.abs(Math.round(255 * Math.sin(Math.PI * angle / 180)));
            for (int j = 0; j < steps; j++) {
                g = (int) Math.max(Math.round((0.5-(steps-j)*0.1) * 255 * Math.sin(Math.PI * (angle-90) / 180)),0);
                b = Math.max(100 - g,0);
                ImgB.putPixel((int)(cX + (j*step+distance)*Math.cos(Math.PI * angle / 180)), 
                    (int)(cY + (j*step+distance)*Math.sin(Math.PI * angle / 180)),
                    r, g, b);
            }
            //System.out.println("Angle: " + angle + " Colors: " + r + ", " + g + ", " + b);
            distance += step / (360 * 10);
            angle += 0.1;
            
        }
        
        
    }
    
    public static void solidCircle(int size, double radius, boolean black) {
        double center = size/2;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((center-i)*(center-i) + (center-j)*(center-j) < radius*radius) {
                    if (black) {
                        ImgB.putPixel(i, j, 0, 0, 0);
                    } else {
                        ImgB.putPixel(i, j, 255, 255, 255);
                    }
                }
            }
        }
    }
}
