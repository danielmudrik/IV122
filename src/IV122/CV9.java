package IV122;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Number;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV9 {
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV9_Images//";
        ImgB.folderName = "CV9_Images//";
        
        //A) Monty Hall
        
        //montyHall(10000);
        System.out.println("");
        
        //B) Non-random sequences
        //checking for how often each number appears (single frequency test) 
        //and patterns of 2 (optionally more) numbers in a row (sequence frequency test, "serial test")
        //
        //nonRandomSeq();
        
        //C) CLT
        
        //CLT(1000,10);
        
        //D) Bayes
        
        bayes(10, 5);
        bayes(100,3);
        bayes(1000,5);
        
    }
    
    public static void bayes(int dice, int throwN) {
        Random rand = new Random();
        double sixth = 1/(double)6;
        double[] normalDie = {sixth,sixth,sixth,sixth,sixth,sixth};
        double[] weightedDie = {0,0,0,0,0,1};
        int allSixes = 0;
        int normalSixes = 0;
        while (normalSixes < 300) {
            int sum = 0;
            double[] pickedDie = (rand.nextInt(dice) == 0) ? weightedDie : normalDie;
            for (int j = 0; j < throwN; j++) {
                sum += randomWeighted(pickedDie);
            }
            if (sum == throwN*6) {
                allSixes++;
                if (pickedDie[0] > 0) {
                    normalSixes++;
                }
            }
        }
        System.out.println("--" + dice + " dice, " + throwN + " sixes in a row--");
        System.out.println("Real die: ");
        System.out.println("Simulated chance: " + 100*normalSixes/(double)allSixes + " %");
        int fakeDie = 1;
        int realDie = dice-1;
        double fakeDieSuccess = fakeDie*1;
        double realDieSuccess = realDie*Math.pow(sixth, throwN);
        //P(realDie | success) = realDieSuccess / (realDieSuccess + fakeDieSuccess)
        double realDieChance = realDieSuccess / (realDieSuccess + fakeDieSuccess);
        System.out.println("Calculated chance: " + 100*realDieChance + " %");
        System.out.println("");
    }
    
    public static void CLT(int throwN, int k) {
        Random rand = new Random();
        double w = 1+2+3+4+5+6;
        double[] dieWeights1 = {1/w,2/w,3/w,4/w,5/w,6/w};
        double[] dieWeights2 = {6/w,5/w,4/w,3/w,2/w,1/w};
        
        double[] avgDW1 = new double[k];
        double[] avgRandomEach = new double[k];
        double[] avgRandomAll = new double[k];
        
        for (int i = 0; i < k; i++) {
            int[] throwNums = new int[throwN];
            for (int j = 0; j < throwN; j++) {
                throwNums[j] = randomWeighted(dieWeights1);
            }
            avgDW1[i] = avg(IntStream.of(throwNums).sum(),throwN);
        }
        
        for (int i = 0; i < k; i++) {
            int[] throwNums = new int[throwN];
            for (int j = 0; j < throwN; j++) {
                throwNums[j] = (rand.nextInt() % 2 == 0) ? randomWeighted(dieWeights1) : randomWeighted(dieWeights2);
            }
            avgRandomEach[i] = avg(IntStream.of(throwNums).sum(),throwN);
        }
        
        for (int i = 0; i < k; i++) {
            double[] trialDie = (rand.nextInt() % 2 == 0) ? dieWeights1 : dieWeights2;
            int[] throwNums = new int[throwN];
            for (int j = 0; j < throwN; j++) {
                throwNums[j] = randomWeighted(trialDie);
            }
            avgRandomAll[i] = avg(IntStream.of(throwNums).sum(),throwN);
        }
        System.out.println("-- " + k + " trials --");
        System.out.println("Weighted die #1: " + Arrays.toString(avgDW1) + ", avg: " + 
                avg(DoubleStream.of(avgDW1).sum(),k));
        System.out.println("Random die per throw: " + Arrays.toString(avgRandomEach) + ", avg: " + 
                avg(DoubleStream.of(avgRandomEach).sum(),k));
        System.out.println("Random die per trial: " + Arrays.toString(avgRandomAll) + ", avg: " + 
                avg(DoubleStream.of(avgRandomAll).sum(),k));
        System.out.println("");
        
        
    }
    
    public static double avg(double x, double y) {
        return x/y;
    }
    
    // smaller weight means Math.random() is less likely to land in the weight's "interval"
    public static int randomWeighted(double[] weights) {
        int randomIndex = weights.length-1;
        double random = Math.random();
        for (int i = 0; i < weights.length; ++i)
        {
            random -= weights[i];
            if (random <= 0.0d)
            {
                randomIndex = i;
                break;
            }
        }
        return randomIndex+1;
    }
    
    public static void nonRandomSeq() {
        int randomFiles = 7;
        //dice rolls
        int maxNum = 6;
        for (int i = 1; i <= randomFiles; i++) {
            File numberFile = new File("random\\random" + i + ".txt");
            try {
                String numberString = new String(Files.readAllBytes(Paths.get(numberFile.getAbsolutePath())));
                numberString = numberString.replaceAll("\\s+","");
                int[] numbers = new int[numberString.length()];
                for (int j = 0; j < numbers.length; j++) {
                    numbers[j] = Character.getNumericValue(numberString.charAt(j));
                }
                
                int[] numFrequency = new int[maxNum];
                int[][] numSequence = new int[maxNum][maxNum];
                
                for (int j = 0; j < numbers.length-1; j++) {
                    numFrequency[numbers[j]-1]++;
                    numSequence[numbers[j]-1][numbers[j+1]-1]++;
                }
                System.out.println(numberFile.getName());
                for (int j = 0; j < maxNum; j++) {
                    System.out.println((j+1) + ": " + numFrequency[j] + ", " + Arrays.toString(numSequence[j]));
                }
                System.out.println("");
                
            } catch (IOException ex) {
                Logger.getLogger(CV9.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void montyHall(int iters) {
        Random rand = new Random();
        
        int stayWinCount = 0;
        int switchWinCount = 0;
        int randomWinCount = 0;
        
        for (int i = 0; i < iters; i++) {
            ArrayList<Integer> doors = new ArrayList<>(Arrays.asList(new Integer[]{0,1,2}));
            Integer car = doors.get(rand.nextInt(3));
            int guess = doors.remove(rand.nextInt(3));
            //goatDoor is neither the car nor the guess
            ArrayList<Integer> goatDoor = new ArrayList<>(doors);
            goatDoor.remove(car);
            Integer goat = goatDoor.get(0);
            doors.remove(goat);
            int switchOption = doors.get(0);
            int[] randomOptions = {guess, switchOption};
            if (i % 1000 == 0) {
                System.out.println("Car: " + car + ", Guess: " + guess + ", Switch: " + switchOption);
            }
            stayWinCount = (car == guess) ? stayWinCount+1 : stayWinCount;
            switchWinCount = (car == switchOption) ? switchWinCount+1 : switchWinCount;
            randomWinCount = (car == randomOptions[rand.nextInt(2)]) ? randomWinCount+1 : randomWinCount;

        }
        
        System.out.println("Wins by staying: " + stayWinCount + ", " + 100*stayWinCount/(double)iters + "%");
        System.out.println("Wins by switching: " + switchWinCount + ", " + 100*switchWinCount/(double)iters + "%");
        System.out.println("Wins by random choice: " + randomWinCount + ", " + 100*randomWinCount/(double)iters + "%");
    }
}
