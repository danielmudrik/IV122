package IV122;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV8 {
    private static int width = 1000;
    private static int height = 1000;
    private int test = 5;
    public static void main(String args[]) {
        ImgV.folderName = "CV8_Images//";
        ImgB.folderName = "CV8_Images//";
        
        ImgV.init(width, height, "Ukazka1");
            ukazka1();
        ImgV.save();
    }
    
    public static void ukazka1() {
        double[] x = {0,10,10,0};
        double[] y = {0,0,10,10};
        
        double[][] xy1s = new double[x.length][x.length];
        for (int i = 0; i < x.length; i++) {
            
        }
        
        Matrix result = new Matrix();
        int seqLength = 3;
        Matrix[] mts = new Matrix[seqLength];
        for (int i = 0; i < seqLength; i++) {
            mts[i] = new Matrix();
        }
        //operations in order
        mts[0].rotate(20);
        mts[1].scale(1.1,1.1);
        mts[2].translate(5,10);
        
        result = result.multiplyBackwards(mts);
        System.out.println(result);
    }
}
