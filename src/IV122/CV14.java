package IV122;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV14 {
    //wall locations
    private static int n = 20;
    private static boolean[][] north;     
    private static boolean[][] east;
    private static boolean[][] south;
    private static boolean[][] west;
    private static boolean[][] visited;
    private static boolean done = false;
    
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV14_Images//";
        ImgB.folderName = "CV14_Images//";
        ImgV.init(width, height, "BraidMazeGen");
                double center = 0.5*width/(double)n;
                init();
                generate(1,1);
                removeBlinds();
                mazeLines();
                
                ImgV.line(0, 0, width, 0, 0, 0, 0, 2);
                ImgV.line(0, 0, 0, height, 0, 0, 0, 2);
                ImgV.line(0, height, width, height, 0, 0, 0, 2);
                ImgV.line(width, 0, width, height, 0, 0, 0, 2);
                ImgV.circle(center, center, center*0.8, 255, 0, 0, 3);
                ImgV.circle(width-center, height-center, center*0.8, 255, 0, 0, 3);
                
        ImgV.save();
        
    }
    
    public static void removeBlinds() {
        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < n+1; j++) {
                int counter = 0;
                counter = (south[i][j]) ? counter+1 : counter;
                counter = (east[i][j]) ? counter+1 : counter;
                counter = (west[i][j]) ? counter+1 : counter;
                counter = (north[i][j]) ? counter+1 : counter;
                if (counter >= 3) {
                    if (south[i][j] && !north[i][j]) {
                        south[i][j] = false;
                        north[i][j+1] = false;
                    }
                    if (west[i][j] && !east[i][j]) {
                        west[i][j] = false;
                        east[i-1][j] = false;
                    }
                    if (!west[i][j] && east[i][j]) {
                        east[i][j] = false;
                        west[i+1][j] = false;
                    }
                    if (!south[i][j] && north[i][j]) {
                        north[i][j] = false;
                        south[i][j-1] = false;
                    }
                }
            }
        }
        
    }
    
    public static void mazeLines() {
        double l = width/(double)n;
        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < n+1; j++) {
                double x = (i-1)*l;
                double y = (j-1)*l;
                if (south[i][j]) {
                    ImgV.line(x, y+l, x+l, y+l, 0, 0, 0, 2);
                }
                if (east[i][j]) {
                    ImgV.line(x+l, y, x+l, y+l, 0, 0, 0, 2);
                }
            }
        }
    }
    
    public static void init() {
        visited = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            visited[x][0] = true;
            visited[x][n+1] = true;
        }
        for (int y = 0; y < n+2; y++) {
            visited[0][y] = true;
            visited[n+1][y] = true;
        }
        
        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            for (int y = 0; y < n+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }
    
    public static void generate(int x, int y) {
        visited[x][y] = true;
        List<int[]> neighbors = new ArrayList<>();
        for (int i = -1; i < 3; i++) {
             neighbors.add(new int[]{i % 2,(i-1)%2});
        }
        Collections.shuffle(neighbors);
        for (int i = 0; i < 4; i++) {
            int nX = neighbors.get(i)[0];
            int nY = neighbors.get(i)[1];
            if (!visited[x+nX][y+nY]) {
                visited[x+nX][y+nY] = true;
                if (nY == 1) {
                    north[x][y+1] = false;
                    south[x][y] = false;
                }
                else if (nX == 1) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                }
                else if (nY == -1) {
                    south[x][y-1] = false;
                    north[x][y] = false;
                }
                else if (nX == -1) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                }
                generate(x+nX, y+nY);
            }
        }
    }
}
