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
        //A)
        ImgV.init(width, height, "Ukazka1");
            ukazka1();
        ImgV.save();
        
        //B) 
        
        //Snowflake
        //to do: improve
        double[][] pointsNormal = {{0,0,1},
                                {1,0,1},
                                {1,1,1},
                                {0,1,1}};
        
        
        ImgV.init(width, height, "SnowflakeMRCM");
            snowflakeMRCM(pointsNormal, 6);
        ImgV.save();
        
        //Barnsley Fern
        //to do: improve
        ImgV.init(width, height, "BarnsleyMRCM");
            barnsleyMRCM(pointsNormal, 6);
        ImgV.save();
        
        
        //Sierpinsky triangle
        double[][] pointsHalfWidth = {{0,0,1},
                                {width/2,0,1},
                                {width/2,width/2,1},
                                {0,width/2,1}};
        
        ImgV.init(width, height, "SierpinskyMRCM");
            
            sierpinskyMRCM(pointsHalfWidth, 5);
        ImgV.save();
        
        
        double[][] pointsHalfWidthCentered = {{-width/4,-width/4,1},
                                {width/4,-width/4,1},
                                {width/4,width/4,1},
                                {-width/4,width/4,1}};
        //Sierpinsky similar-typed
        
        //different combinations of rotating and translating have wildly different results
        ImgV.init(width, height, "SierpnskyGroupMRCM_4");
            sierpinskySimMRCM(pointsHalfWidth, 8, width/4, 1, 270);
        ImgV.save();
        
        ImgV.init(width, height, "SierpnskyGroupMRCM_5");
            sierpinskySimMRCM(pointsHalfWidth, 8, width/4, 2, 0);
        ImgV.save();
        
        ImgV.init(width, height, "SierpnskyGroupMRCM_3");
            sierpinskySimMRCM(pointsHalfWidth, 8, width/4, 2, 180);
        ImgV.save();
        
        ImgV.init(width, height, "SierpnskyGroupMRCM_7");
            sierpinskySimMRCM(pointsHalfWidth, 8, width/4, 0, 180);
        ImgV.save();
        
        ImgV.init(width, height, "SierpnskyGroupMRCM_6");
            sierpinskySimMRCM(pointsHalfWidth, 8, width/4, 1, 90);
        ImgV.save();
        
        ImgV.init(width, height, "SierpnskyGroupMRCM_8_fail");
            sierpinskySimMRCM(pointsHalfWidth, 7, width/4, -1, 90);
        ImgV.save();
    }
    
    public static void sierpinskySimMRCM(double[][] pts, int iters, double len, double angleMultiplier, double angleStart) {
        Matrix[] transforms = new Matrix[3];
        for (int i = 0; i < 3; i++) {
            transforms[i] = new Matrix();
        }
        for (int i = 0; i < 3; i++) {
            transforms[i].scale(0.5,0.5);
            transforms[i].rotate(i*90*angleMultiplier+angleStart);
        }
        transforms[0].translate(-len, len);
        transforms[1].translate(-len, -len);
        transforms[2].translate(len, -len);
        
        multipleReduceCopy(pts, iters, transforms, 1);
    }
    
    public static void barnsleyMRCM(double[][] pts, int iters) {
        Matrix[] transforms = new Matrix[4];
        transforms[0] = new Matrix(new double[]{
            0.849,0.037,0.075,
            -0.037,0.849,0.183,
            0,0,1});
        transforms[1] = new Matrix(new double[]{
            0.197,-0.226,0.4,
            0.226,0.197,0.049,
            0,0,1});
        transforms[2] = new Matrix(new double[]{
            -0.15,0.283,0.575,
            0.26,0.237,0.084,
            0,0,1});
        transforms[3] = new Matrix(new double[]{
            0,0,0.5,
            0,0.16,0,
            0,0,1});
        
        multipleReduceCopy(pts, iters, transforms, width/2);
    }
    
    public static void snowflakeMRCM(double[][] pts, int iters) {
        Matrix[] transforms = new Matrix[4];
        transforms[0] = new Matrix(new double[]{
            0.255,0,0.3726,
            0,0.255,0.6714,
            0,0,1});
        transforms[1] = new Matrix(new double[]{
            0.255,0,0.1146,
            0,0.255,0.2232,
            0,0,1});
        transforms[2] = new Matrix(new double[]{
            0.255,0,0.6306,
            0,0.255,0.2232,
            0,0,1});
        transforms[3] = new Matrix(new double[]{
            0.370,-0.642,0.6356,
            0.642,0.370,-0.0061,
            0,0,1});
        
        multipleReduceCopy(pts, iters, transforms, width/2);
    }
    
    public static void sierpinskyMRCM(double[][] pts, int iters) {
        Matrix[] transforms = new Matrix[3];
        for (int i = 0; i < 3; i++) {
            transforms[i] = new Matrix();
        }
        for (int i = 0; i < 3; i++) {
            transforms[i].scale(0.5,0.5);
            transforms[i].rotate(i*120);
            transforms[i].translate(0, width/4);
        }
        multipleReduceCopy(pts, iters, transforms, 1);
    }
    
    public static void ukazka1() {
        double[][] pts = {{0,0,1},
                                {100,0,1},
                                {100,100,1},
                                {0,100,1}};
        Matrix result = new Matrix();
        
        //operations in order
        result.rotate(20);
        result.scale(1.1,1.1);
        result.translate(5,10);
        
        for (int i = 0; i < 11; i++) {
            linesFromPointArray(pts, pts.length);
            for (int j = 0; j < pts.length; j++) {
                pts[j] = result.applyToPoint(pts[j][0], pts[j][1]);
            }
            
        }
    }
    
    public static void multipleReduceCopy(double[][] pts, int copies, Matrix[] transforms, double scale) {
        double[][] newPts = pts;
        for (int i = 0; i < copies; i++) {
            newPts = reduceCopy(newPts, transforms);
        }
        ngonLinesFromPointArray(newPts, 4, scale);
    }
    
    public static double[][] reduceCopy(double[][] pts, Matrix[] transforms) {
        double[][] newPts = new double[pts.length*transforms.length][3];
            for (int i = 0; i < transforms.length; i++) {
                
                for (int j = 0; j < pts.length; j++) {
                    newPts[i*pts.length + j] = transforms[i].applyToPoint(pts[j][0], pts[j][1]);
                }
            }
        return newPts;
    }
    
    public static void ngonLinesFromPointArray(double[][] pts, int ngon, double scale) {
        for (int i = 0; i < pts.length/ngon; i++) {
            for (int j = 0; j < ngon; j++) {
                ImgV.line(width/2 + pts[i*ngon + j][0]*scale, height/2 - pts[i*ngon+j][1]*scale, width/2 + pts[(i*ngon)+((j+1) % ngon)][0]*scale, height/2 - pts[(i*ngon)+((i*ngon+j+1) % ngon)][1]*scale, 0, 0, 0, 1);
            }
        }
        
    }
    
    public static void linesFromPointArray(double[][] pts, int count) {
        for (int i = 0; i < count; i++) {
            ImgV.line(width/2 + pts[i][0], height/2 - pts[i][1], width/2 + pts[(i+1) % count][0], height/2 - pts[(i+1) % count][1], 0, 0, 0, 1);
        }
    }
}
