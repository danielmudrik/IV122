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
public class CV3 {
    public static void main(String args[]) {
        ImgV.folderName = "CV3_Images//";
        ImgB.folderName = "CV3_Images//";
        int len = 100;
      //A)
        //Test ngon
        Turtle.init("ngon");
        
            ngon(5, len);
            
        Turtle.save();
        
        //Test nStar
        Turtle.init("nstar");
        
            oddStar(5, len);
            
        Turtle.save();
      //B)
        
        //Relative penta
        Turtle.init("relativePenta");
        
            ngon(5,len);

            // cos veta
            double lengthTwo = Math.sqrt(len*len + len*len - 2*len*len*Math.cos(Math.PI - 2*Math.PI/5));

            //sin veta (b/sinB = c/sinC -> sinB = b*sinC/c)
            double angleTwo = Math.asin(len*Math.sin(Math.PI - 2*Math.PI/5)/lengthTwo)*180/Math.PI;
            Turtle.left(angleTwo);
            oddStar(5,lengthTwo);
            
        Turtle.save();
        
        //Absolute penta
        ImgV.init(1000, 1000, "absolutePenta");
        
            absoluteNgonStar(5,len);
        
        ImgV.save();
        
        //x split rotated square
        Turtle.init("splitRotate");
            
            xsplitRotatedSquare(10, 0.75, len);
        
        Turtle.save();
        //Circle grid
        ImgV.init(1000,1000,"circleGrid");
        
            circleGrid(16, 100);
        
        ImgV.save();
        //Inner triangles
        Turtle.init("innerTriangles");
        
            innerNgon(3, 7, 10, 1);
        
        Turtle.save();
        //Ngon circle
        Turtle.init("ngonCircle");
        
            ngonCircle(12, 50);
            
        Turtle.save();
        
      // C) 
        
        // N depth tree
        Turtle.init("ndepthTree");
        
            ndepthTree(9, 200);
        
        Turtle.save();
        // Koch snowflake
        
        Turtle.init("KochovaVlocka");
        kochSnowflake(4, 100);
        Turtle.save();
        
        // Sierpinsky triangle
        Turtle.init("Sierpinsky");
        double sLen = 500;
        Turtle.penUp();
        Turtle.forward(Math.sqrt(sLen*sLen-sLen*sLen/4)/2);
        Turtle.penDown();
        
        Turtle.left(30);
        for (int i = 0; i < 3; i++) {
            Turtle.left(120);
            Turtle.forward(sLen);
        }
        Turtle.right(30);
        
        Turtle.penUp();
        Turtle.back(Math.sqrt(sLen*sLen-sLen*sLen/4)/2);
        Turtle.penDown();
        
        sierpinsky(6, sLen);
        Turtle.save();
        
        // recursive pentagon snowflake
        
        Turtle.init("RecursivePentaFlake");
            Turtle.right(18);
            recursivePentaFlake(4, 200);
        
        Turtle.save();
        
      // D) Custom drawings
        Turtle.init("CustomCircles");
        
        Turtle.save();
        
    }
    
    public static void recursivePentaFlake(int depth, double radius) {
        if (depth < 1) {
            ngon(5, radius);
            return;
        }
        double goldenRatio = (1 + Math.sqrt(5)) / 2;
        double innerRadius = radius/(1 + goldenRatio);
        //draw 5 surrounding mini flakes
        for (int i = 0; i < 5; i++) {
            recursivePentaFlake(depth-1, innerRadius);
            Turtle.penUp();
            Turtle.forward(radius);
            Turtle.penDown();
            Turtle.left(360/5);
        }
        //walk inside and draw inner mini flake
        Turtle.penUp();
        Turtle.forward(innerRadius);
        Turtle.left(360/5);
        Turtle.forward(innerRadius);
        Turtle.right(360/10);
        Turtle.penDown();
        recursivePentaFlake(depth-1,innerRadius);
        Turtle.penUp();
        Turtle.left(360/10);
        Turtle.back(innerRadius);
        Turtle.right(360/5);
        Turtle.back(innerRadius);
        Turtle.penDown();
        
    }
    
    public static void innerNgon(int ngon, int depth, double step, double length) {
        if (depth == 0) {
            return;
        }
        double outerAngle = 360/(double)ngon;
        double innerAngle = 180 - outerAngle;
        Turtle.penUp();
        Turtle.forward(step);
        Turtle.penDown();
        Turtle.left(outerAngle + innerAngle/2);
        for (int i = 0; i < ngon; i++) {
            Turtle.forward(length);
            Turtle.left(outerAngle);
        }
        Turtle.right(outerAngle + innerAngle/2);
        innerNgon(ngon, depth-1, step, length+((1+Math.sqrt(5))*step/2));
    }
    
    public static void ngonCircle(int ngon, double length) {
        Turtle.right(90);
        for (int i = 0; i < ngon; i++) {
            ngon(ngon, length);
            Turtle.left(360/(double)ngon);
        }
    }
    
    public static void ndepthTree(int depth, double length) {
        if (depth == 0) {
            return;
        }
        Turtle.forward(length);
        Turtle.left(45);
        ndepthTree(depth-1, length*0.59);
        Turtle.right(45);
        Turtle.right(45);
        ndepthTree(depth-1, length*0.59);
        Turtle.left(45);
        Turtle.back(length);
    }
    
    public static void circleGrid(int lines, double radius) {
        double center = 1000/2;
        double step = 2*radius/(lines-1);
        double limit = center + radius;
        //pytag veta
        for (int i = 0; i < lines; i++) {
            double xLen = Math.sqrt(radius*radius - (radius-i*step)*(radius-i*step));
            ImgV.line(center-xLen, limit-i*step, center+xLen, limit-i*step, 0,0,0,2);
        }
        
        for (int i = 0; i < lines; i++) {
            double xLen = Math.sqrt(radius*radius - (radius-i*step)*(radius-i*step));
            ImgV.line(limit-i*step, center-xLen, limit-i*step, center+xLen, 0,0,0,2);
        }
    }
    
    public static void xsplitRotatedSquare(int depth, double x, double length) {
        
        // pytag veta
        double lengthTwo = length*Math.sqrt(x*x + (1-x)*(1-x));

        //sin veta (b/sinB = c/1 -> sinB = b*1/c)
        double angleTwo = Math.asin(length*x*1/lengthTwo)*180/Math.PI;
        
        for (int i = 0; i < 4; i++) {
            Turtle.forward(length);
            Turtle.left(90);
        }
        if (depth == 0) {
            return;
        }
        Turtle.penUp();
        Turtle.forward(length*x);
        Turtle.penDown();
        Turtle.left(angleTwo);
        xsplitRotatedSquare(depth-1, x, lengthTwo);
    }
    
    
    public static void absoluteNgonStar(int ngon, double length) {
        double[] coordsX = new double[ngon];
        double[] coordsY = new double[ngon];
        double angle = 360/5;
        double currentAngle = 90;
        for (int i = 0; i < ngon; i++) {
            coordsX[i] = 500 + length*Math.cos(Math.PI * currentAngle / 180);
            coordsY[i] = 500 + length*Math.sin(Math.PI * currentAngle / 180);
            currentAngle += angle;
        }
        int starStep = (ngon)/2;
        for (int i = 0; i < ngon; i++) {
            ImgV.line(coordsX[i], coordsY[i], coordsX[(i+1) % ngon], coordsY[(i+1) % ngon], 0, 0, 0, 2);
        }
        for (int i = 0; i < ngon; i++) {
            ImgV.line(coordsX[i], coordsY[i], coordsX[(i+starStep) % ngon], coordsY[(i+starStep) % ngon], 0, 0, 0, 2);
        }
    }
    
    public static void centeredNgon(int ngon, double radius) {
        Turtle.penUp();
        Turtle.forward(radius);
        Turtle.penDown();
        double outerAngle = 360/(double)ngon;
        double innerAngle = 180 - outerAngle;
        Turtle.left(outerAngle + innerAngle/2);
        ngon(ngon, radius);
        Turtle.right(outerAngle + innerAngle/2);
        Turtle.penUp();
        Turtle.back(radius);
        Turtle.penDown();
    }
    
    public static void ngon(int ngon, double length) {
        for (int i = 0; i < ngon; i++) {
            Turtle.forward(length);
            Turtle.left(360 / (double)ngon);
        }
    }
    
    public static void oddStar(int nstar, double length) {
        for (int i = 0; i < nstar; i++) {
            Turtle.forward(length);
            Turtle.left(180 - 180 / (double)nstar);
        }
    }
    
    public static void sierpinsky(int depth, double length) {
        if (depth == 0) {
            return;
        }        
        double halfInnerLength = length/4;
        double smallerTriangleHeight = Math.sqrt(halfInnerLength*halfInnerLength
                -(halfInnerLength/2)*(halfInnerLength/2));
        for (int i = 0; i < 3; i++) {
            Turtle.penUp();
            Turtle.forward(smallerTriangleHeight);
            Turtle.penDown();
            sierpinsky(depth-1, halfInnerLength*2);
            Turtle.penUp();
            Turtle.back(smallerTriangleHeight);
            Turtle.penDown();
            Turtle.left(90);
            Turtle.forward(halfInnerLength);
            Turtle.left(120);
            Turtle.forward(halfInnerLength);
            Turtle.right(90);
        }
    }
    
    public static void kochSnowflakeLine(int depth, double length) {
        if (depth == 0) {
            Turtle.forward(length);
            Turtle.left(60);
            Turtle.forward(length);
            Turtle.right(120);
            Turtle.forward(length);
            Turtle.left(60);
            Turtle.forward(length);
        } else {
            kochSnowflakeLine(depth-1, length/3);
            Turtle.left(60);
            kochSnowflakeLine(depth-1, length/3);
            Turtle.right(120);
            kochSnowflakeLine(depth-1, length/3);
            Turtle.left(60);
            kochSnowflakeLine(depth-1, length/3);
        }
    }
    
    public static void kochSnowflake(int depth, double length) {
        Turtle.left(30);
        kochSnowflakeLine(depth, length);
        Turtle.right(120);
        kochSnowflakeLine(depth, length);
        Turtle.right(120);
        kochSnowflakeLine(depth, length);
    }
}
