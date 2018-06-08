package IV122;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV10 {
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV10_Images//";
        ImgB.folderName = "CV10_Images//";
        
        //A) Linear regression - line
        double[][] pts = readDoublePair("dataAnalysis\\faithful.txt");
        double[][] syntheticPts = generatePointsLine(-3, 2, 0.2, width, 1000);
        ImgV.init(width, height, "LinRegFaithful");
            
            linReg(normalizePoints(pts, width), 1);
        ImgV.save();
        
        
        
        ImgV.init(width, height, "LinRegSimulated");
            linReg(syntheticPts, 1000);
        ImgV.save();
        
        // Gradient Descent
        
        ImgV.init(width, height, "GradientDescentFaithful");
            gradientDescent(normalizePoints(pts, 1), 1000, 1, 0, 0.00045);
        ImgV.save();
        
        ImgV.init(width, height, "GradientDescentSimulated");
            gradientDescent(syntheticPts, 1000, 1, 0, 0.0001);
        ImgV.save();
        
        
        //B) K-means clustering
        
        ImgV.init(width, height, "ClusteringK");
            clusteringK(normalizePoints(pts,1), 2, 10);
        ImgV.save();
        
        ImgV.init(width, height, "ClusteringKSimulated");
            clusteringK(generatePointsClusters(5, 150, 1000, 500), 5, 50);
        ImgV.save();
    }
    
    public static void clusteringK(double[][] pts, int k, int iters) {
        Random rand = new Random();
        double[][] clusterPts = new double[k][2];
        ArrayList<double[]>[] pointClusterMap = new ArrayList[k];
        //choose 'k' random points to be initial guesses for the cluster centers
        for (int i = 0; i < k; i++) {
            int randomIndex = rand.nextInt(pts.length);
            clusterPts[i][0] = pts[randomIndex][0];
            clusterPts[i][1] = pts[randomIndex][1];
            pointClusterMap[i] = new ArrayList<>();
        }
        int[] pointClusterMapping = new int[pts.length];
        for (int i = 0; i < pts.length; i++) {
            pointClusterMapping[i] = 0;
            pointClusterMap[0].add(pts[i]);
        }
        for (int i = 0; i < iters; i++) {
            int clusterChanges = 0;
            for (int j = 0; j < pts.length; j++) {
                double minDistance = d(pts[j][0], pts[j][1], clusterPts[0][0],clusterPts[0][1]);
                int clusterIndex = 0;
                for (int l = 1; l < k; l++) {
                    double distance = d(pts[j][0], pts[j][1], clusterPts[l][0],clusterPts[l][1]);
                    if (minDistance > distance) {
                        minDistance = distance;
                        clusterIndex = l;
                    }
                }
                if (pointClusterMapping[j] != clusterIndex) {
                    pointClusterMap[pointClusterMapping[j]].remove(pts[j]);
                    pointClusterMapping[j] = clusterIndex;
                    pointClusterMap[clusterIndex].add(pts[j]);
                    clusterChanges++;
                }
            }
            for (int j = 0; j < k; j++) {
                clusterPts[j] = pointCenter(pointClusterMap[j]);
                System.out.println(clusterPts[j][0] + " " + clusterPts[j][1]);
            }
        }
        
        for (int i = 0; i < pts.length; i++) {
            ImgV.circle(width*pts[i][0], width*pts[i][1], 1, 55, (255/k)*pointClusterMapping[i], 127, 3);
        }
        
        for (int i = 0; i < k; i++) {
            ImgV.circle(width*clusterPts[i][0], width*clusterPts[i][1], 1, 255, 0, 0, 10);
        }
    }
    
    public static double[] pointCenter(ArrayList<double[]> pts) {
        double sumX = 0, sumY = 0;
        for (int i = 0; i < pts.size(); i++) {
            sumX += pts.get(i)[0];
            sumY += pts.get(i)[1];
        }
        double[] center = new double[2];
        center[0] = sumX/(double)pts.size();
        center[1] = sumY/(double)pts.size();
        return center;
    }
    
    public static double d(double x, double y, double x2, double y2) {
        return Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
    }
    
    public static void gradientDescent(double[][] pts, int iters, double initA, double initB, double learningRate) {
        double a = initA;
        double b = initB;
        double error = 0.01;
        for (int i = 0; i < iters; i++) {
            double gradientA = 0;
            double gradientB = 0;
            
            
            for (int j = 0; j < pts.length; j++) {
            //                    x     *   (y       - (a * x         + b))
                gradientA += (pts[j][0] * (pts[j][1] - (a * pts[j][0] + b)));
            //                 (y       - (a * x         + b))
                gradientB += (pts[j][1] - (a * pts[j][0] + b));
            }
            a += (learningRate * gradientA);
            b += (learningRate * gradientB);
            if (Math.abs(gradientA) < error && Math.abs(gradientB) < error) {
                break;
            }
        }
        for (int i = 0; i < pts.length; i++) {
            ImgV.circle(width*pts[i][0], width*pts[i][1], 1, 255, 0, 0, 1);
        }
        b *= width;
        System.out.println("a: " + a + ", b: " + b + " | " + (a*width+b));
        ImgV.line(0, b, width, a*width+b, 0, 0, 0, 2);
    }
    
    public static void linReg(double[][] points, double scale) {
        // (n * sum(x*y) - sum(x)*sum(y)) / (n * sum(x*x) - sum^2(x)) = a
        // avg(y) - a*avg(x) = b -> sumY/n - a*sumX/n = (sumY - a*sumX)/n
        
        int n = points.length;
        double sumXY = 0;
        double sumX = 0;
        double sumY = 0;
        double sumXX = 0;
        for (int i = 0; i < n; i++) {
            sumXY += (points[i][0] * points[i][1]);
            sumX += points[i][0];
            sumY += points[i][1];
            sumXX += (points[i][0] * points[i][0]);
        }
        double a = (n * sumXY - sumX*sumY) / (n * sumXX - sumX*sumX);
        double b = (sumY - a*sumX)/(double)n;
        
        for (int i = 0; i < points.length; i++) {
            ImgV.circle(points[i][0]*scale, points[i][1]*scale, 1, 255, 0, 0, 1);
        }
        //y = b <- x1 = 0, y1 = b
        
        //y = a*1000 + b <- x1 = 1000, y1 = a*1000 + b
        System.out.println(scale*b);
        System.out.println((a*1000+scale*b));
        
        ImgV.line(0, b*scale, 1000, a*1000+b*scale, 0, 0, 0, 2);
    }
    
    
    
    public static double[][] readDoublePair(String fileName) {
        File f = new File(fileName);
        try {
            String numberString = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
            String[] doublePairs = numberString.split("\n");
            double[][] doubles = new double[doublePairs.length][2];
            for (int i = 0; i < doublePairs.length; i++) {
                String[] pair = doublePairs[i].split(" ");
                for (int j = 0; j < 2; j++) {
                    doubles[i][j] = Double.valueOf(pair[j]);
                }
            }
            return doubles;
        } catch (IOException ex) {
            Logger.getLogger(CV10.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static double[][] normalizePoints(double[][] pts, double scale) {
        double minX = pts[0][0], maxX = pts[0][0], minY = pts[0][1], maxY = pts[0][1];
        for (int i = 1; i < pts.length; i++) {
            minX = (minX > pts[i][0]) ? pts[i][0] : minX;
            minY = (minY > pts[i][1]) ? pts[i][1] : minY;
            maxX = (maxX < pts[i][0]) ? pts[i][0] : maxX;
            maxY = (maxY < pts[i][1]) ? pts[i][1] : maxY;
        }
        double rangeX = maxX - minX;
        double rangeY = maxY - minY;
        
        for (int i = 0; i < pts.length; i++) {
            pts[i][0] = scale * (pts[i][0]-minX)/rangeX;
            pts[i][1] = scale * (pts[i][1]-minY)/rangeY;
        }
        return pts;
    }
    
    public static double[][] generatePointsClusters(int k, double radius, double scale, int count) {
        Random rand = new Random();
        double[][] pts = new double[count][2];
        double[][] clusterPts = new double[k][2];
        for (int i = 0; i < k; i++) {
            clusterPts[i][0] = (i+0.5)*scale/(double)k;
            clusterPts[i][1] = scale*Math.random();
        }
        for (int i = 0; i < count; i++) {
            int cluster = rand.nextInt(k);
            double xDeviation = 2*Math.random()*radius*(Math.random()-0.5);
            pts[i][0] = clusterPts[cluster][0] + xDeviation;
            pts[i][1] = clusterPts[cluster][1] + 2*Math.sqrt(radius*radius - xDeviation*xDeviation)*(Math.random()-0.5);
        }
        return normalizePoints(pts,1);
    }
    
    public static double[][] generatePointsLine(double a, double b, double noise, double scale, int count) {
        double[][] pts = new double[count][2];
        for (int i = 0; i < count; i++) {
            pts[i][0] = Math.random() * scale;
            pts[i][1] = a*pts[i][0] + b + Math.random()*noise*scale;
        }
        return normalizePoints(pts,1);
    }
}
