/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IV122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.util.Pair;

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
        
        
        //Color spiral
        ImgB.init(1000,1000,"ColorSpiral");
            colorSpiral(100, 5, 500, 500);
            colorSpiral(100, 5, 501, 500);
            colorSpiral(100, 5, 500, 501);
            colorSpiral(100, 5, 499, 500);
            colorSpiral(100, 5, 500, 499);
        ImgB.save();
        
        //Color triangle
        
        ImgB.init(1000,1000,"ColorTriangle");
            colorTriangle(400);
        ImgB.save();
        
        
        //Faded ellipse
        
        ImgB.init(1000,1000,"FadedEllipse");
            fadedEllipse(200, 100, -45);
        ImgB.save();
        
        //B)
        // Ngon floodfill
        
        ImgB.init(200, 200, "NgonScanFill");
        int[] arrayX = {10,180,160,100,20};
        int[] arrayY = {10,20,150,50,180};
            ngonScanFill(arrayX, arrayY);
        ImgB.save();
        
        //C)
        
        //Checkered circles
        ImgB.init(1000, 1000, "CheckeredCircles");
            checkered(50,480,500,500,255, true);
            checkered(50,360,500,500,0, true);
            checkered(50,240,500,500,255, true);
            checkered(50,120,500,500,0, true);
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    if (i < 225 || i > 775 || j < 225 || j > 775) {
                        ImgB.putPixel(i, j, 255, 255, 255);
                    }
                }
            }
        ImgB.save();
        
        
        //Pulsing circles
        
        ImgB.init(1000, 1000, "PulsingCircles");
            pulsing(20,300,500,500,false,false);
            pulsing(20,200,500,500,true,false);
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    if (i < 225 || i > 775 || j < 225 || j > 775) {
                        ImgB.putPixel(i, j, 255, 255, 255);
                    }
                }
            }
        ImgB.save();
        
        //Modulo color lines
        ImgB.init(1000, 1000, "ColorLines");
            colorLines(8, 200);
        ImgB.save();
        
    }
    
    public static void colorLines(int count, int thickness) {
        int r = 0, g = 0, b = 0;
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                r = (x % thickness*1.5 < thickness) ? (int)(255*Math.sin(Math.PI*(x % thickness*1.5)/thickness)) : 0;
                b = (y % thickness*1.5 < thickness) ? (int)(255*Math.sin(Math.PI*(y % thickness*1.5)/thickness)) : 0;
                g = ((x+y) % thickness*1.5 < thickness) ? (int)(255*Math.sin(Math.PI*((x+y) % thickness*1.5)/thickness)) : 0;
                
                ImgB.putPixel(x, y, r, g, b);
            }
        }
    }
    
    public static void pulsing(int step, int radius, int cX, int cY, boolean reverse, boolean circle) {
        for (int x = cX-radius; x < cX+radius; x++) {
            for (int y = cY-radius; y < cY+radius; y++) {
                double distance = Math.sqrt((x-cX)*(x-cX)+(y-cY)*(y-cY));
                int shade = 0;
                //sin/cos to generate pulsing, divide by step to widen pulses
                //abs to stay in positive integers
                //-51 * 5/4 to make black lines thicker while keeping max value 255
                if (reverse) {
                    shade = (int)(Math.max((255*Math.abs(Math.sin(distance/step)))-51,0)*5/4);
                } else {
                    shade = (int)(Math.max((255*Math.abs(Math.cos(distance/step)))-51,0)*5/4);
                }
                if ((circle && distance < radius) || !circle) {
                    ImgB.putPixel(x, y, shade, shade, shade);
                }
            }
            
        }
    }
    
    public static void checkered(int side, int radius, int cX, int cY, int reverse, boolean circle) {
        for (int x = cX-radius; x < cX+radius; x++) {
            for (int y = cY-radius; y < cY+radius; y++) {
                int shade = 0;
                if (x % (side*2) < side ^ y % (side*2) < side) {
                    shade = 255-reverse;
                } else {
                    shade = 0+reverse;
                }
                
                if ((circle && (x-cX)*(x-cX)+(y-cY)*(y-cY) < radius*radius) || !circle) {
                    ImgB.putPixel(x, y, shade, shade, shade);
                }
            }
            
        }
    }
    
    public static void ngonScanFill(int[] aX, int[] aY) {
        //ImgB.flipImage = true;
        int yMin = 200;
        int yMax = 0;
        
        //initial white fill
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                ImgB.putPixel(i, j, 255, 255, 255);
            }
        }
        //draw ngon outline
        for (int i = 0; i < 5; i++) {
            ImgB.drawLine(aX[i % 5], 199-aY[i % 5], aX[(i+1) % 5], 199-aY[(i+1) % 5], 0, 0, 0);
            yMin = (aY[i] < yMin) ? aY[i] : yMin;
            yMax = (aY[i] > yMax) ? aY[i] : yMax;
        }
        
        //find white space inside ngon
        int yMid = (yMin+yMax)/2;
        int whiteX = 0;
        boolean blackStart = false;
        boolean whiteMid = false;
        for (int j = 0; j < 200; j++) {
            int black = ImgB.getPixel(j,yMid).getBlue();
            if (!blackStart) {
                if (black == 0) {
                    blackStart = true;
                }
            } else {
                if (black == 0 && whiteMid) {
                    whiteX = j-1;
                    break;
                } else if (black != 0) {
                    whiteMid = true;
                }
            }
        }
        blackFill(whiteX, yMid);
        //ImgB.flipImage = false;
    }
    
    public static void blackFill(int cX, int cY) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        ImgB.putPixel(cX, cY, 0, 0, 0);
        queue.add(new Pair(cX, cY));
        
        while (!queue.isEmpty()) {
            Pair pixel = queue.remove();
            int x = (Integer) pixel.getKey();
            int y = (Integer) pixel.getValue();
            for (int i = 1; i <= 4; i++) {
                if (ImgB.getPixel(x+(i % 2)*Integer.signum(i-2),y+((i-1) % 2)*Integer.signum(i-3)).getBlue() == 255) {
                    ImgB.putPixel(x+(i % 2)*Integer.signum(i-2), y+((i-1) % 2)*Integer.signum(i-3), 0, 0, 0);
                    queue.add(new Pair(x+(i % 2)*Integer.signum(i-2), y+((i-1) % 2)*Integer.signum(i-3)));
                }
            }
        }
        
        /*
        if (ImgB.getPixel(x,y).getBlue() == 255) {
            ImgB.putPixel(x, y, 0, 0, 0);
            blackFill(x-1,y);
            blackFill(x,y-1);
            blackFill(x+1,y);
            blackFill(x,y+1);
        }*/
    }
    
    public static void fadedEllipse(int length1, int length2, double angleDegrees) {
        int width = 1000, height = 1000, cX = 500, cY = 500;
        double angle = Math.PI * angleDegrees / 180;
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double a2 = length1*length1;
        double b2 = length2*length2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double equation = Math.pow((i-cX)*cosA+(j-cY)*sinA,2)/a2 + Math.pow((i-cX)*sinA-(j-cY)*cosA,2)/b2;
                if (equation < 1) {
                    ImgB.putPixel(i, j, 0, 0, 0);
                } else if (equation < 6) {
                    ImgB.putPixel(i, j, (int)(255*(equation-1)/5), (int)(255*(equation-1)/5), (int)(255*(equation-1)/5));
                }
            }
        }
        //(x - cX)*Math.cos(angle) + (y-cY)*Math.sin(angle)
    }
    
    public static void colorTriangle(int length) {
        //ImgB.flipImage = true;
        double heightFactor = Math.sqrt(3)/2;
        int height = (int)Math.round(length*heightFactor);
        int[] triangleX = {500-length/2, 500+length/2, 500};
        int[] triangleY = {500-height/2, 500-height/2, 500+height/2};
        
        int r=0, g=0, b=0;
        
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < (int) Math.floor(height - Math.abs(2 * heightFactor * (length/2 - i))); j++) {
                r = (int) Math.round((255 * i) / length + (255 * j / 2) / height);
                g = (int) Math.round((255 * (length-i)) / length + (255 * j / 2) / height);
                b = (int) Math.round((255 * (height-j)) / height);
                
                ImgB.putPixel(triangleX[0]+i, 999-(triangleY[0]+j), r, g, b);
            }
        }
        //ImgB.flipImage = false;
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
    
    public static void bad() {
        /*//complicated ugly polygon scan-line fill algorithm, 
        //marking start and end points to fill with lines
        
        List<Integer> start = new ArrayList<>();
        List<Integer> end = new ArrayList<>();
        for (int x = yMin; x < yMax; x++) {
            boolean shouldFill = false;
            boolean filled = false;
            for (int y = 0; y < 200; y++) {
                int b = ImgB.getPixel(y,x).getBlue();
                if (!shouldFill) {
                    if (b == 0 && !filled) {
                        shouldFill = true;
                        start.add(y);
                    }
                    if (b != 0 && filled) {
                        filled = false;
                    }
                } else {
                    if (b == 0 && filled) {
                        end.add(y);
                        shouldFill = false;
                    } else if (b != 0) {
                        filled = true;
                    }
                }
                
            }
            for (int k = 0; k < end.size(); k++) {
                ImgB.drawLine(start.get(k), x, end.get(k), x, 0, 0, 0);
            }
            start.clear();
            end.clear(); 
       }*/
    }
}
