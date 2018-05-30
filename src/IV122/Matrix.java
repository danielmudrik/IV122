package IV122;

/**
 *
 * @author Daniel Mudrik (433655)
 */
public class Matrix {
    private double[][] matrix;
    
    public double[][] getMatrix() {
        return matrix;
    }
    
    public void setMatrix(double[][] mat) {
        matrix = mat;
    }
    
    public Matrix(double[][] mat) {
        matrix = mat;
    }
    
    public Matrix() {
        matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    public Matrix multiply(Matrix other) {
        Matrix result = new Matrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                double sum = 0;
                for (int k = 0; k < 3; k++) {
                    sum += (matrix[i][k]*other.getMatrix()[k][j]);
                }
                result.getMatrix()[i][j] = sum;
            }
        }
        return result;
    }
    
    public void rotate(double angle) {
        Matrix rot = new Matrix();
        double[][] rotation = rot.getMatrix();
        rotation[0][0] = Math.cos(angle*Math.PI/180);
        rotation[1][1] = Math.cos(angle*Math.PI/180);
        rotation[0][1] = -Math.sin(angle*Math.PI/180);
        rotation[1][0] = Math.sin(angle*Math.PI/180);
        
        matrix = multiply(rot).getMatrix();
    }
    
    public String toString() {
        StringBuilder res = new StringBuilder();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res.append(matrix[i][j]).append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }
}
