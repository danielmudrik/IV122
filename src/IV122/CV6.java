package IV122;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV6 {
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV6_Images//";
        ImgB.folderName = "CV6_Images//";
        
        //A) Chaos Game
        
        ImgB.init(width, height, "ChaosGame");
            chaosGame(5, 100, 0.3333, 200);
        ImgB.save();
        
        //B) Feigenbaum
        
        ImgB.init(width, height, "Feigenbaum");
            feigenbaum(1,4,0.005);
        ImgB.save();
        
        //C) L-system
        
        Turtle.init("SierpinskyTriangleL");
            char[] leftSTL = {'A','B'};
            String[] rightSTL = {"B-A-B", "A+B+A"};
            lindenmayer(leftSTL, rightSTL, 9, 100, -60, true, 180);
        Turtle.save();
        
        Turtle.init("Hilbert");
            char[] leftHil = {'A','B'};
            String[] rightHil = {"-BF+AFA+FB-", "+AF-BFB-FA+"};
            lindenmayer(leftHil, rightHil, 5, 100, -90, false, 180);
        Turtle.save();
        
        Turtle.init("Tree");
            char[] leftTree = {'A','F'};
            String[] rightTree = {"F[+A]-A", "FF"};
            lindenmayer(leftTree,rightTree, 8, 50, 45, false, 90);
        Turtle.save();
        
        Turtle.init("Tree_2");
            char[] leftTree2 = {'A','F'};
            String[] rightTree2 = {"F-[[A]+A]+F[+FA]-A", "FF"};
            lindenmayer(leftTree2,rightTree2, 5, 30, 25, false, 90);
        Turtle.save();
        
        
        
    }
    
    public static void lindenmayer(char[] left, String[] right, int iters, double length, double angle, boolean def, double startAngle) {
        Turtle.angle = startAngle;
        Turtle.x = 800;
        Turtle.y = 200;
        length = length/Math.pow(2, iters-3);
        Stack<Double> turtleX = new Stack<>();
        Stack<Double> turtleY = new Stack<>();
        Stack<Double> angleT = new Stack<>();
        
        String finalString = String.valueOf(left[0]);
        for (int i = 0; i < iters; i++) {
            for (int j = 0; j < left.length; j++) {
                finalString = finalString.replaceAll(String.valueOf(left[j]), String.valueOf(j));
                //System.out.println(finalString);
            }
            for (int j = 0; j < left.length; j++) {
                finalString = finalString.replaceAll(String.valueOf(j), right[j]);
                //System.out.println(finalString);
            }
        }
        System.out.println(finalString);
        
        for (int i = 0; i < finalString.length(); i++) {
            switch (finalString.charAt(i)) {
                case '-': 
                    Turtle.right(angle);
                    break;
                case '+':
                    Turtle.left(angle);
                    break;
                case 'F':
                    Turtle.forward(length);
                    break;
                case '[':
                    turtleX.push(Turtle.x);
                    turtleY.push(Turtle.y);
                    angleT.push(Turtle.angle);
                    break;
                case ']':
                    Turtle.x = turtleX.pop();
                    Turtle.y = turtleY.pop();
                    Turtle.angle = angleT.pop();
                    break;
                default:
                    if (def) {
                        Turtle.forward(length);
                    }
                    break;
            }
        }
    }    
    public static void feigenbaum(double rMin, double rMax, double rStep) {
        ImgB.flipImage = true;
        int discard = 100;
        int max = 200;
        
        for (double r = rMin; r < rMax; r += rStep) {
            double[] points = new double[max];
            points[0] = 0.5;
            for (int i = 0; i < max-1; i++) {
                points[i+1] = r * points[i] * (1 - points[i]);
            }
            for (int i = discard; i < max; i++) {
                ImgB.putPixel((int)(r * width / rMax), (int)(points[i]*height), 0, 0, 0);
            }
        }
        ImgB.flipImage = false;
    }
    
    
    
    public static void chaosGame(int ngon, int iters, double ratio, double length) {
        ImgB.flipImage = true;
        double[] coordsX = new double[ngon];
        double[] coordsY = new double[ngon];
        double angle = 360/ngon;
        double currentAngle = 90;
        for (int i = 0; i < ngon; i++) {
            coordsX[i] = 500 + length*Math.cos(Math.PI * currentAngle / 180);
            coordsY[i] = 500 + length*Math.sin(Math.PI * currentAngle / 180);
            currentAngle += angle;
        }
        
        for (int i = 0; i < ngon; i++) {
            ImgB.drawLine((int)coordsX[i], (int)coordsY[i], (int)coordsX[(i+1) % ngon], (int)coordsY[(i+1) % ngon], 0, 0, 0);
        }
        
        double xX = 500+length/2 - Math.random()*length;
        double xY = 500+length/2 - Math.random()*length;
        Random rn = new Random();
        for (int i = 0; i < iters; i++) {
            int vertex = rn.nextInt(ngon);
            xX = (xX * ratio) + coordsX[vertex]*(1-ratio);
            xY = (xY * ratio) + coordsY[vertex]*(1-ratio);
            ImgB.putPixel((int)xX, (int)xY, 0, 0, 0);
        }
        
        ImgB.flipImage = false;
    }
}
