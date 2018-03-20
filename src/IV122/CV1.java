package IV122;

/**
 *
 * @author xmudrik1
 */
public class CV1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*int limit = 10000;
        
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
        */
        /*
        HashSet<Integer> uniqueNums = new HashSet<Integer>();
        int limit = 1000;
        
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
       */
        /*
        int limit = 10000;
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
        */
        /*
        int limit = 1000;
        int sum = 0;
        for (int i = 0; i < limit; i++) {
            if (isPrime(i) && notContainsThree(i)) {
                sum += i;
            }
        }
        System.out.println(sum);
        */
        System.out.println("BitMapTest");
        ImgB img = new ImgB(1000, 1000, "image");
        for (int i = 1; i < 1000; i++) {
            for (int j = 1; j < 1000; j++) {
                img.putPixel(i, j, i / 4, 0, j / 4);
            }
            
        }
        img.save();
        System.out.println("StarTest");
        lineStar(20,1000,"lineStarTest");
        
        System.out.println("Ulam Spiral");
        ulamSpiral(41,200,"ulam41");
        
        //ImgB.init(1000,1000,"testNSD");
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
