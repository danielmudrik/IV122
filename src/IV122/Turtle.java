package IV122;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class Turtle {
    
    public static double x = 0;
    public static double y = 0;
    public static double angle = 90;
    public static boolean drawing = true;
    
    public Turtle() {
    }
    
    public static void init(String fileName) {
        ImgV.init(1000, 1000, fileName);
        x = 500;
        y = 500;
        angle = 90;
    }
    
    public static void set(double newX, double newY) {
        x = newX;
        y = newY;
    }
    
    public static void forward(double step) {
        if (drawing) {
            ImgV.lineInverted(x, y, x + step*Math.cos(Math.PI * angle / 180), y + step*Math.sin(Math.PI * angle / 180), 0, 0, 0, 1);
        }

        x = x + step*Math.cos(Math.PI * angle / 180);
        y = y + step*Math.sin(Math.PI * angle / 180);
    }
    
    public static void back(double step) {
        forward(-step);
    }
    
    public static void right(double angR) {
        angle += angR;
    }
    
    public static void left(double angL) {
        angle -= angL;
    }
    
    public static void penUp() {
        drawing = false;
    }
    
    public static void penDown() {
        drawing = true;
    }
    
    public static void save() {
        ImgV.save();
    }
}
