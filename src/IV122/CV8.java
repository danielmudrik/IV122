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
        
        double[][] xy1s = new double[x.length][3];
        for (int i = 0; i < x.length; i++) {
            xy1s[i][0] = x[i];
            xy1s[i][1] = y[i];
            xy1s[i][2] = 1;
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
        
        for (int i = 0; i < 10; i++) {
            linesFromPointArray(xy1s, x.length);
            for (int j = 0; j < x.length; j++) {
                xy1s[j] = result.applyToPoint(xy1s[j][0], xy1s[j][1]);
            }
            
        }
    }
    
    public static void linesFromPointArray(double[][] pts, int count) {
        for (int i = 0; i < count; i++) {
            ImgV.line(pts[i][0], pts[i][1], pts[(i+1) % count][0], pts[(i+1) % count][1], 0, 0, 0, 2);
        }
    }
}
