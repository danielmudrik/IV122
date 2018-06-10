package IV122;

import java.util.HashSet;

/**
 *
 * @author xmudrik1
 */
public class CV1 {

    
    private static int width = 1000;
    private static int height = 1000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        highestDivs(10000);
        uniqueNums(1000);
        steps(10000);
        primesThree(1000);
        sequenceNSD(1000000);
        
        ImgV.folderName = "CV1_Images//";
        ImgB.folderName = "CV1_Images//";
        System.out.println("BitMapTest");
        ImgB.init(width, height, "BitmapTest");
        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                ImgB.putPixel(i, j, i / 4, 0, j / 4);
            }
            
        }
        ImgB.save();
        System.out.println("StarTest");
        lineStar(20,1000,"lineStarTest");
        
        System.out.println("Ulam Spiral");
        ulamSpiral(41,200,"ulam41");
        ImgB.init(width,height,"VisualizeNSD");
            visualizeNSD();
        ImgB.save();
        ImgB.init(width,height,"VisualizeSpeedNSD");
            visualizeSpeedNSD();
        ImgB.save();
    }
    
    public static void visualizeNSD() {
        int highest = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int modulo = modNSD(i+1,j+1)[0];
                highest = (highest < modulo) ? modulo : highest;
            }
        }
        double colorBase = 255/(double)highest;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int modulo = modNSD(i+1,j+1)[0];
                ImgB.putPixel(i, j, 255-(int)(modulo*colorBase), 0, 0);
            }
        }
    }
    
    public static void visualizeSpeedNSD() {
        int slowestSlow = 0;
        int slowestMod = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int slow = slowNSD(i+1,j+1)[1];
                int modulo = modNSD(i+1,j+1)[1];
                slowestSlow = (slowestSlow < slow) ? slow : slowestSlow;
                slowestMod = (slowestMod < modulo) ? modulo : slowestMod;
            }
        }
        double slowColorBase = 255/(double)slowestSlow;
        double modColorBase = 255/(double)slowestMod;
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int slow = slowNSD(i+1,j+1)[1];
                int modulo = modNSD(i+1,j+1)[1];
                ImgB.putPixel(i, j, 255-(int)(slow*slowColorBase), 255-(int)(modulo*modColorBase), 0);
            }
        }
    }
    
    public static int[] slowNSD(int a, int b) {
        int steps = 0;
        int tempA = a;
        while (b > 0) {
            tempA = a;
            a = b;
            b = tempA - b;
            steps++;
        }
        return new int[]{a, steps};
    }
    
    public static int[] modNSD(int a, int b) {
        int steps = 0;
        int tempA = a;
        while (b > 0) {
            tempA = a;
            a = b;
            b = tempA % b;
            steps++;
        }
        return new int[]{a, steps};
    }
    
    public static void sequenceNSD(int lim) {
        int a = 1;
        int b = 1;
        int tempA = a;
        int index = 2;
        while (a < lim) {
            tempA = a;
            a = a + b + modNSD(a,b)[0];
            b = tempA;
            index++;
        }
        System.out.println(index + ". element is above " + lim);
    }
    
    public static void primesThree(int lim) {
        
        int limit = lim;
        int sum = 0;
        for (int i = 0; i < limit; i++) {
            if (isPrime(i) && notContainsThree(i)) {
                sum += i;
            }
        }
        System.out.println(sum);
        
    }
    
    public static void steps(int lim) {
        
        int limit = lim;
        int highestSteps = 0;
        int num = 1;
        for (int i = 1; i < limit; i++) {
            int currentSteps = collatzSteps(i);
            if (currentSteps > highestSteps) {
                highestSteps = currentSteps;
                num = i;
            }
        }
        
        System.out.println("Num: " + num);
        
    }
    
    public static void uniqueNums(int lim) {
        
        HashSet<Integer> uniqueNums = new HashSet<Integer>();
        int limit = lim;
        
        for (int i = 1; i < 31; i++) {
            for (int j = 1; j < 31; j++) {
                for (int k = 1; k < 31; k++) {
                    if (i*i + j*j + k*k < limit) {
                        uniqueNums.add(i*i + j*j + k*k);
                    }
                }
            }
        }
        
        System.out.println("Bad nums: " + (limit-uniqueNums.size()-1));
       
    }
    
    public static void highestDivs(int lim) {
        int limit = lim;
        
        int divNumber = 1;
        int secondDivNumber = 1;
        int currentDivs = 1;
        int oldHighestDivs = 1;
        int highestDivs = 1;
        for (int i = 2; i < limit; i++) {
            currentDivs = divs(i);
            if (currentDivs > highestDivs) {
                highestDivs = currentDivs;
                divNumber = i;
            } else if (currentDivs == highestDivs) {
                oldHighestDivs = currentDivs;
                secondDivNumber = i;
            }
        }
        
        System.out.println("Highest: " + highestDivs + " at " + divNumber + ", Second: " +  oldHighestDivs + " at " + secondDivNumber);
        
    }
    
    public static void lineStar(int divisions, int size, String fileName) {
        // WIP
        ImgV.init(size, size, fileName);
        int half = size/2;
        int off = half / divisions;
        ImgV.line(0, half, size, half, 0, 0, 0, 3);
        ImgV.line(half, 0, half, size, 0, 0, 0, 3);
        for (int i = 0; i < divisions; i++) {
            ImgV.line(0+i*off, half, half, half+i*off, 0, 0, 0, 2);
            ImgV.line(size-i*off, half, half, half+i*off, 0, 0, 0, 2);
            ImgV.line(0+i*off, half, half, half-i*off, 0, 0, 0, 2);
            ImgV.line(size-i*off, half, half, half-i*off, 0, 0, 0, 2);
        }
        ImgV.save();
    }
    
    public static void ulamSpiral(int start, int range, String fileName) {
        ImgB.init(range,range,fileName);
        int x = range/2;
        int y = range/2;
        int xOffset = 0;
        int yOffset = 0;
        int requiredOffsetsInDir = 1;
        int currentOffsets = 0;
        int direction = 1;
        for (int i = start; i < start+range*range; i++) {
            if (isPrime(i)) {
                ImgB.putPixel(x+xOffset-1, y-yOffset, 0, 0, 0);
            } else {
                ImgB.putPixel(x+xOffset-1, y-yOffset, 255, 255, 255);
            }
            if (currentOffsets < 2*requiredOffsetsInDir) {
                if (currentOffsets < requiredOffsetsInDir) {
                    xOffset += direction;
                    currentOffsets++;
                } else {
                    yOffset += direction;
                    currentOffsets++;
                    if (currentOffsets == 2*requiredOffsetsInDir) {
                        direction *= -1;
                        requiredOffsetsInDir += 1;
                        currentOffsets = 0;
                    }
                }
            }
            
        }
        ImgB.save();
    }
    
    public static boolean notContainsThree(int num) {
        if (num % 10 == 3) {
            return false;
        }
        num -= num % 10;
        num = num/10;
        if (num % 10 == 3) {
            return false;
        }
        num -= num % 10;
        num = num/10;
        if (num % 10 == 3) {
            return false;
        }
        return true;
    }
    
    public static boolean isPrime(int num) {
        if (num == 1) {
            return false;
        }
        for (int i = 2; i <= Math.floor(Math.sqrt(num)); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static int collatzSteps(int num) {
        int i = 0;
        while (num != 1) {
            num = (num % 2 == 0) ? num/2 : (num*3 + 1);
            i++;
        }
        return i;
    }
    
    public static int divs(int num) {
        int numOfDivs = 0;
        for(int i = 1; i < Math.round(Math.sqrt(num)); i++) {
            if (num % i == 0) {
                numOfDivs += 2;
            }
        }
        
        return numOfDivs;
    }
}
