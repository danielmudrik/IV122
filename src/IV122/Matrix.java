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
    
    public Matrix(double[] nums) {
        matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = nums[3*i + j];
            }
        }
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
    
    public double[] applyToPoint(double x, double y) {
        double[] point = {x,y,1};
        double[] xy1 = new double[3];
        for (int i = 0; i < 3; i++) {
            double sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += (matrix[i][j]*point[j]);
            }
            xy1[i] = sum;
        }
        return xy1;
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
        angle = angle*-1;
        Matrix rot = new Matrix();
        double[][] rotation = rot.getMatrix();
        rotation[0][0] = Math.cos(angle*Math.PI/180);
        rotation[1][1] = Math.cos(angle*Math.PI/180);
        rotation[0][1] = -Math.sin(angle*Math.PI/180);
        rotation[1][0] = Math.sin(angle*Math.PI/180);
        
        matrix = multiply(rot).getMatrix();
    }
    
    public void scale(double sx, double sy) {
        Matrix scale = new Matrix();
        double[][] sc = scale.getMatrix();
        sc[0][0] = sx;
        sc[1][1] = sy;
        
        matrix = multiply(scale).getMatrix();
    }
    
    public void shear(double k) {
        Matrix shear = new Matrix();
        double[][] sh = shear.getMatrix();
        sh[0][1] = k;
        
        matrix = multiply(shear).getMatrix();
    }
    
    public void translate(double tx, double ty) {
        Matrix trans = new Matrix();
        double[][] tr = trans.getMatrix();
        tr[0][2] = tx;
        tr[1][2] = ty;
        
        matrix = multiply(trans).getMatrix();
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
    
    public Matrix multiplyBackwards(Matrix[] mts) {
        Matrix res = new Matrix();
        res.matrix = matrix;
        for (int i = mts.length-1; i >= 0; i--) {
            res = mts[i].multiply(res);
        }
        return res;
    }
}
