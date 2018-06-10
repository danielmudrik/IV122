package IV122;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class CV13 {
    private static int width = 1000;
    private static int height = 1000;
    
    public static void main(String args[]) {
        ImgV.folderName = "CV13_Images//";
        ImgB.folderName = "CV13_Images//";
        
        //A) Number-step maze
        int[][] maze1 = {{2,4,4,3,3},{2,3,3,2,3},{3,2,3,1,3},{2,2,3,2,1},{1,4,4,4,0}};
        int[][] maze2 = {{3,4,3,2,2},{4,4,2,2,4},{4,3,1,4,2},{2,1,1,3,3},{1,4,2,3,0}};
        int[][] maze3 = {{1,4,2,1,1},{2,4,2,3,2},{2,3,4,2,4},{3,3,3,3,2},{2,4,2,2,0}};
        int[][] maze4 = {{4,0,0,0,4},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{4,0,0,0,0}};
        int[] start = {0,0};
        int[] end = {4,4};
        //numberStepMaze(maze1,start,end);
        //numberStepMaze(maze2,start,end);
        //numberStepMaze(maze3,start,end);
        //numberStepMaze(maze4,start,end);
        
        //B) Maze solution
        //three lamps - BFS each empty space, minimum
        String[] lampMaze = 
        {   "A..#.##",
            ".#.#...",
            ".....#B",
            "#.##.#.",
            ".....#.",
            ".#C.##."  };
        threeLamps(lampMaze);
    }
    
    public static void threeLamps(String[] maze) {
        
        int n = maze.length;
        for (int i = 0; i < n; i++) {
            System.out.println(maze[i]);
        }
        int m = maze[0].length();
        //convert String maze into graph with nodes and neighbors
        int[][] lamps = new int[3][2];
        boolean[][] nodes = new boolean[n][m];
        List<int[]>[][] neighbors = new ArrayList[n][m];
        List<int[]>[][][] pathToABC = new ArrayList[n][m][3];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                neighbors[i][j] = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    pathToABC[i][j][k] = new ArrayList<>();
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int[] pos = {i,j};
                nodes[i][j] = true;
                switch (maze[i].charAt(j)) {
                    case 'A':
                        lamps[0] = pos;
                        break;
                    case 'B':
                        lamps[1] = pos;
                        break;
                    case 'C':
                        lamps[2] = pos;
                        break;
                    case '.':
                        break;
                    default:
                        nodes[i][j] = false;
                        break;
                }
            }
        }
        //add neighboring white space to white space nodes
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (nodes[i][j]) {
                    for (int k = -1; k <= 1; k+=2) {
                        if (valid(n, i+k) && valid(m, j)) {
                            if (nodes[i+k][j]) {
                                neighbors[i][j].add(new int[]{i+k, j});
                            }
                            
                        }
                        if (valid(n, i) && valid(m, j+k)) {
                            if (nodes[i][j+k]) {
                                neighbors[i][j].add(new int[]{i, j+k});
                            }
                        }
                    }
                }
            }
        }
        //for each non-# space, calculate BFS distance to A,B,C
        // dist(A,A) = 0
        int[][][] distanceABC = new int[n][m][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!nodes[i][j]) {
                    continue;
                }
                for (int k = 0; k < 3; k++) {
                    //BFS
                    
                    int[] start = {i,j};
                    int[] end = lamps[k];
                    if (Arrays.equals(end,start)) {
                        distanceABC[i][j][k] = 0;
                        continue;
                    }
                    boolean[][] visited = new boolean[n][m];

                    // Create a queue for BFS
                    LinkedList<int[]> queue = new LinkedList<>();

                    // Mark the current node as visited and enqueue it
                    visited[i][j] = true;
                    queue.add(start);

                    //remember previous vertex, for reconstructing path
                    int[][][] previousXY = new int[n][m][2];
                    for (int x = 0; x < n; x++) {
                        for (int y = 0; y < m; y++) {
                            for (int z = 0; z < 2; z++) {
                                previousXY[x][y][z] = -1;
                            }
                        }
                    }
                    while (queue.size() != 0)
                    {
                        // Dequeue a vertex from queue and print it
                        int[] vertex = queue.poll();
                        
                        if (Arrays.equals(end,vertex)) {
                            break;
                        }
                        // Get all adjacent vertices of the dequeued vertex s
                        // If a adjacent has not been visited, then mark it
                        // visited and enqueue it
                        List<int[]> adj = neighbors[vertex[0]][vertex[1]];
                        for (int l = 0; l < adj.size(); l++) {
                            int[] neighbor = adj.get(l);


                            if (!visited[neighbor[0]][neighbor[1]]) {
                                previousXY[neighbor[0]][neighbor[1]] = vertex;
                                visited[neighbor[0]][neighbor[1]] = true;
                                queue.add(neighbor);
                            }
                            if (Arrays.equals(end,neighbor)) {
                                break;
                            }
                        }
                    }
                    
                    int length = 1;
                    pathToABC[i][j][k].add(end);
                    int[] previousNode = previousXY[end[0]][end[1]];
                    //backtrack previousNode until end is reached
                    while (previousNode[0] != -1) {
                         
                        pathToABC[i][j][k].add(previousNode);
                        if (previousXY[previousNode[0]][previousNode[1]][0] != -1) {
                           
                            previousNode = previousXY[previousNode[0]][previousNode[1]];
                            length++;
                            
                        } else {
                            break;
                        }
            
                    }
                    distanceABC[i][j][k] = length;
                    //System.out.println("[" + start[0] + "," + start[1] + "] ");
                }
            }
            
        }
        //find position of tile which has the shortest distance to all 3 lamps
        int[] crossPos = lamps[0];
        int minSumPaths = distanceABC[lamps[0][0]][lamps[0][1]][0]+distanceABC[lamps[0][0]][lamps[0][1]][1]+distanceABC[lamps[0][0]][lamps[0][1]][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!nodes[i][j]) {
                    continue;
                } 
                int sumPaths = distanceABC[i][j][0] + distanceABC[i][j][1] + distanceABC[i][j][2];
                if (minSumPaths > sumPaths) {
                    crossPos = new int[]{i,j};
                    minSumPaths = sumPaths;
                }
            }
        }
        //tiles which must be converted from . to o
        List<int[]> pathTiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pathTiles.addAll(pathToABC[crossPos[0]][crossPos[1]][i]);
        }
        String[] newMaze = new String[n];
        System.out.println("Solved maze: ");
        //Create solved maze map
        for (int i = 0; i < n; i++) {
            StringBuilder mazeLine = new StringBuilder("");
            //for continuing through 2 nested loops
            outerloop:
            for (int j = 0; j < m; j++) {
                int[] pos = {i,j};
                //if pos is one of the path tiles that need to be marked, mark it
                if (pathTiles.stream().anyMatch(a -> Arrays.equals(a, pos))) {
                    for (int k = 0; k < 3; k++) {
                        //unless it's one of the Letters (A,B,C), in which case don't mark it
                        if (Arrays.equals(lamps[k], pos)) {
                            mazeLine.append(maze[i].charAt(j));
                            continue outerloop;
                        }
                    }
                    mazeLine.append("o");
                } else {
                    mazeLine.append(maze[i].charAt(j));
                }
            }
            newMaze[i] = mazeLine.toString();
        }
        for (int i = 0; i < n; i++) {
            System.out.println(newMaze[i]);
        }
        
    }
    
    
    
    public static void numberStepMaze(int[][] maze, int[] start, int[] end) {
        int n = maze.length;
        System.out.println("--Maze--");
        List<int[]>[][] neighbors = new ArrayList[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                neighbors[i][j] = new ArrayList<>();
            }
        }
        //for every node
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //for every neighbor maze[i][j] distance away
                int node = maze[i][j];
                for (int k = -1; k <= 1 ; k+=2) {
                    
                    //check if potential neighbor is within maze
                    //System.out.print(i+node*k + " "); System.out.println(j+node*l);
                    if (valid(n, i+node*k) && valid(n, j)) {
                        //add to neighbor list
                        neighbors[i][j].add(new int[]{i+node*k, j});
                    }
                    if (valid(n, i) && valid(n, j+node*k)) {
                        //add to neighbor list

                        neighbors[i][j].add(new int[]{i, j+node*k});
                    }
                    
                    
                }
                //System.out.println(i + "," + j + ": " + neighbors[i][j].size());
            }
        }
        
        //BFS
        
        boolean[][] visited = new boolean[n][n];
 
        // Create a queue for BFS
        LinkedList<int[]> queue = new LinkedList<>();
 
        // Mark the current node as visited and enqueue it
        visited[start[0]][start[1]] = true;
        queue.add(start);
        
        //remember previous vertex, for reconstructing path
        int[][][] previousXY = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 2; k++) {
                    previousXY[i][j][k] = -1;
                }
            }
        }
        int shortestPathCounter = 0;
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            int[] vertex = queue.poll();
            System.out.print("[" + vertex[0]+"," + vertex[1] + "] ");
            if (Arrays.equals(end,vertex)) {
                break;
            }
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            List<int[]> adj = neighbors[vertex[0]][vertex[1]];
            for (int i = 0; i < adj.size(); i++) {
                int[] neighbor = adj.get(i);
                
                
                if (!visited[neighbor[0]][neighbor[1]]) {
                    previousXY[neighbor[0]][neighbor[1]] = vertex;
                    visited[neighbor[0]][neighbor[1]] = true;
                    queue.add(neighbor);
                }
                if (Arrays.equals(end,neighbor)) {
                    shortestPathCounter++;
                }
            }
        }
        int length = 1;
        System.out.println("");
        System.out.println("Path: ");
        System.out.println("[" + end[0] + "," + end[1] + "] ");
        int[] previousNode = previousXY[end[0]][end[1]];
        while (length < 10) {
            
            if (previousXY[previousNode[0]][previousNode[1]][0] != -1) {
                System.out.println("[" + previousNode[0] + "," + previousNode[1] + "] ");
                previousNode = previousXY[previousNode[0]][previousNode[1]];
                length++;
            } else {
                break;
            }
            
        }
        System.out.println("[" + start[0] + "," + start[1] + "] ");
        System.out.println("Different shortest paths: " + shortestPathCounter);
        
    }
    
    public static boolean valid(int n, int num) {
        return (num < n && num >= 0);
    }
}
