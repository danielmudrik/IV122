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
        
        Matrix test = new Matrix();
        
        test.rotate(45);
        test.rotate(-45);
        
        System.out.println(test.toString());
    }
}
