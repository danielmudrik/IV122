/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IV122;

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
         
         //D) Powers
         
         
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
