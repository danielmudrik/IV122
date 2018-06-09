package IV122;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV2 {
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV2_Images//";
        ImgB.folderName = "CV2_Images//";
        
        //A) Permutations, combinations, variations
            Integer[] numnums = {1,3,5,7,9};
            List<Integer> nums = new ArrayList<>();
            nums.addAll(Arrays.asList(numnums));
            //variations with repeats
                System.out.println("Variations with repeats");
                printPCV(nums, "", 3, true, true);

            //variations without repeats
                System.out.println("Variations without repeats");
                printPCV(nums, "", 3, false, true);

            //combinations with repeats
                System.out.println("Combinations with repeats");
                printPCV(nums, "", 3, true, false);

            //combinations without repeats
                System.out.println("Combinations without repeats");
                printPCV(nums, "", 3, false, false);

            //permutations (= variations of max K, without repeats)
                System.out.println("Permutations without repeats");
                printPCV(nums, "", nums.size(), false, true);
                
        //B) Pascal's triangle
            int[][] triangle = pascalTriangle(256);
            //powers of 2 create dividing triangles in the resulting image
            for (int i = 1; i <= 4; i++) {
                int pow = Math.round((float)Math.pow(2, i));
                ImgB.init(width, height, "Pascal_" + pow);
                    printPascalModulo(triangle, pow);
                ImgB.save();
            }
            
         //C) Pi approximation
            //monte carlo
            System.out.println("1000 iterations of monte carlo: " + monteCarlo(1000));
            System.out.println("PI - monte carlo 1 second approximation: " + (Math.PI - monteCarlo(-1)));
         
            //gregory leibnitz
            System.out.println("1000 iterations of GL: " + gregoryLeibnitz(1000));
            System.out.println("PI - GL 1 second approximation: " + (Math.PI - gregoryLeibnitz(-1)));
         
            //archimedes
            System.out.println("1000 iterations of archimedes: " + archimedes(1000));
            System.out.println("PI - archimedes 1 second approximation: " + (Math.PI - archimedes(-1)));
         
         //D) Powers
             //Logarithmic time modulo exponentiation
             System.out.println("--Time in nanoseconds--");
             System.out.println("Fast result = " + fastModuloExp(2,253,17));
             System.out.println("Slow result = " + dumbModuloExp(2,253,17));
         
    }
    
    public static BigInteger dumbModuloExp(int a, int n, int k) {
        BigInteger res = BigInteger.valueOf(1);
        BigInteger base = BigInteger.valueOf(a);
        double startingTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            res = res.multiply(base);
        }
        res = res.mod(BigInteger.valueOf(k));
        System.out.println("Time to exp: " + (System.nanoTime() - startingTime));
        return res;
    }
    
    public static long fastModuloExp(int a, int n, int k) {
        long res = 1;
        int base = a;
        int remaining = n;
        double startingTime = System.nanoTime();
        while (remaining > 0) {
            if (remaining % 2 == 1) {
                res = (res*base) % k;
            }
            base = (base*base) % k;
            remaining /= 2;
        }
        System.out.println("Time to exp: " + (System.nanoTime() - startingTime));
        return res;
    }
    
    
    public static double archimedes(int iters) {
        double a = 2*Math.sqrt(3);
        double b = 3;
        if (iters < 0) {
            iters = 0;
            double startingTime = System.nanoTime();
            double currentTime = startingTime;
            //1 second = 1000000000 nanoseconds
            
            while (currentTime - startingTime < 1000000000) {
                a = (2*a*b)/(a+b);
                b = Math.sqrt(a*b);
                
                iters++;
                currentTime = System.nanoTime();
            }
        } else {
            //iteration mode
            for (int i = 0; i < iters; i++) {
                a = (2*a*b)/(a+b);
                b = Math.sqrt(a*b);
            }
        }
        
        return a;
    }
    
    public static double gregoryLeibnitz(int iters) {
        double sum = 0;
        //timed mode
        if (iters < 0) {
            iters = 0;
            double startingTime = System.nanoTime();
            double currentTime = startingTime;
            //1 second = 1000000000 nanoseconds
            
            while (currentTime - startingTime < 1000000000) {
                sum += Math.pow(-1, iters)/(double)(2*iters+1);
                
                iters++;
                currentTime = System.nanoTime();
            }
        } else {
            //iteration mode
            for (int i = 0; i < iters; i++) {
                sum += Math.pow(-1, i)/(double)(2*i+1);
            }
        }
        return 4 * sum;
    }
    
    public static double monteCarlo(int iters) {
        double monteCarlo = 0;
        int inside = 0;
        //timed mode
        if (iters < 0) {
            iters = 0;
            double startingTime = System.nanoTime();
            double currentTime = startingTime;
            //1 second = 1000000000 nanoseconds
            while (currentTime - startingTime < 1000000000) {
                double x = Math.random();
                double y = Math.random();
                if (x*x+y*y < 1) {
                    inside++;
                }
                iters++;
                currentTime = System.nanoTime();
            }
        } else {
            //iteration mode
            for (int i = 0; i < iters; i++) {
                double x = Math.random();
                double y = Math.random();
                if (x*x+y*y < 1) {
                    inside++;
                }
            }
        }
        monteCarlo =  4 * inside / (double) iters;
        return monteCarlo;
    } 
    
    public static void printPascalModulo(int[][] triangle, int modulo) {
        Random rand = new Random();
        int[][] rgb = new int[modulo][3];
        for (int i = 0; i < modulo; i++) {
            for (int j = 0; j < 3; j++) {
                rgb[i][j] = rand.nextInt(255);
            }
        }
        for (int i = 0; i < triangle.length; i++) {
            for (int j = 0; j <= i; j++) {
                int color = Math.floorMod(triangle[i][j], modulo);
                ImgB.putPixel(500-i/2+j, 500+i, rgb[color][0], rgb[color][1], rgb[color][2]);
                //System.out.println(triangle[i][j] % modulo);
            }
        }
    }
    
    public static int[][] pascalTriangle(int n) {
        int[][] triangle = new int[n][n];
        triangle[0][0] = 1;
        for (int i = 1; i < n; i++) {
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i-1][j-1]+triangle[i-1][j];
            }
        }
        return triangle;
    }
    
    public static void printPCV(List<Integer> nums, String base, int k, boolean repeats, boolean ordered) {
        if (k == 0) {
            System.out.println(base);
            return;
        }
        int n = nums.size();
        String newBase;
        for (int i = 0; i < n; i++) {
            newBase = base + nums.get(i);
            List<Integer> newNums = new ArrayList<>(nums);
            if (repeats) {
                //variations with repeats, use same numbers
                if (ordered) {
                    printPCV(newNums, newBase, k-1, true, true);
                } else {
                    //combinations with repeats, previous numbers already accounted for
                    newNums = newNums.subList(i, n-1);
                    printPCV(newNums, newBase, k-1, true, false);
                }
            } else {
                //variations without repeats, all except current number
                if (ordered) {
                    newNums.remove(i);
                    printPCV(newNums, newBase, k-1, false, true);
                } else {
                    //combinations without repeats, previous numbers and current number accounted for
                    newNums = newNums.subList(i+1, n);
                    printPCV(newNums, newBase, k-1, false, false);
                }
            }
        }
    }
}
