package IV122;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.util.Pair;


/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV5 {
    
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV5_Images//";
        ImgB.folderName = "CV5_Images//";
        
        //generate, data structure
        
        //A)
        ImgV.init(width, height, "LineIntersects");
            lineIntersect(5, 200);
        ImgV.save();
        //B)
        ImgV.init(width, height, "Triangulation");
            triangulate(50, 5);
        ImgV.save();
        //C)
    }
    
    public static void triangulate(int count, int steps) {
        double step = width / steps;
        double[] x = new double[count];
        double[] y = new double[count];
        List<Pair<Integer,Integer>> lines = new ArrayList<>();
        lines.add(new Pair(0,1));
        List<Pair<Integer,Integer>> indices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            x[i] = Math.random()*width;
            y[i] = Math.random()*height;
            ImgV.circle(x[i], y[i], 4, 255, 0, 0, 1);
            for (int j = 0; j < count; j++) {
                indices.add(new Pair(i,j));
            }
            
        }
        Collections.shuffle(indices);
        //lines from xi,yi to xj,yj
        for (int s = 1; s <= steps; s++) {
            for (Pair<Integer,Integer> p: indices) {
                int i = p.getKey();
                int j = p.getValue();
                if (j==i) {
                    continue;
                }
                
                if (dist(x[i],y[i],x[j],y[j]) > s*step) {
                    continue;
                }
                
                boolean intersected = false;
                for(Pair<Integer,Integer> line: lines) {
                    Pair<Boolean, Pair<Double,Double>> inter = intersects(x[i],y[i],x[j],y[j],x[line.getKey()],y[line.getKey()],x[line.getValue()], y[line.getValue()]);
                    if (inter.getKey()) {
                        intersected = true;
                        break;
                    }
                }
                if (!intersected) {
                    lines.add(new Pair(i,j));
                }
            
            }
        }
        
        for(Pair<Integer,Integer> line: lines) {
            ImgV.line(x[line.getKey()], y[line.getKey()], x[line.getValue()], y[line.getValue()], 0, 0, 0, 1);
            System.out.println(x[line.getKey()] + " " + y[line.getKey()]);
        }
    }
    
    public static void lineIntersect(int count, double length) {
        double range = width - 2*length;
        double[] sX = new double[count];
        double[] sY = new double[count];
        double[] eX = new double[count];
        double[] eY = new double[count];
        
        
        for (int i = 0; i < count; i++) {
            sX[i] = length + range*Math.random();
            sY[i] = length + range*Math.random();
            eX[i] = sX[i] + 2*length*Math.random() - length;
            double sign = Math.signum(0.5 - Math.random());
            eY[i] = sY[i] + sign * Math.sqrt(length*length - (eX[i]-sX[i])*(eX[i]-sX[i]));
            ImgV.line(sX[i], sY[i], eX[i], eY[i], 0, 0, 0, 2);
        }
        
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (j == i) {
                    continue;
                }
                //line intersect
                Pair<Boolean, Pair<Double,Double>> inter = intersects(sX[i],sY[i],eX[i],eY[i],sX[j],sY[j],eX[j],eY[j]);
                if (inter.getKey()) {
                    ImgV.circle(inter.getValue().getKey(), inter.getValue().getValue(), 4, 255, 0, 0, 1);
                }
            }
        }
    }
    
    public static Pair<Boolean, Pair<Double,Double>> intersects(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
                if ((x1 == x3 && y1 == y3) || (x1 == x4 && y1 == y4) || (x2 == x3 && y2 == y3) || (x2 == x4 && y2 == y4)) {
                    return new Pair(false, new Pair(0,0));
                }
                double interX = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4)) /
                        ((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
                double interY = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4)) /
                        ((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
                //start -> inter + inter -> end - start->end should be close to zero if intersect point is on the line segment
                double inBetween1 = dist(x1,y1,interX,interY)+dist(interX,interY,x2,y2)-dist(x1,y1,x2,y2);
                double inBetween2 = dist(x3,y3,interX,interY)+dist(interX,interY,x4,y4)-dist(x3,y3,x4,y4);
                if (-0.01 < inBetween1 && inBetween1 < 0.01 && -0.01 < inBetween2 && inBetween2 < 0.01) {
                    return new Pair(true, new Pair(interX,interY));
                } else {
                    return new Pair(false, new Pair(interX,interY));
                }
    }
    
    public static double dist(double x, double y, double x2, double y2) {
        return Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
    }
    
    
    
}
