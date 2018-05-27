package IV122;

/**
 *
 * library from https://codereview.stackexchange.com/questions/140254/creating-a-newton-fractal-based-on-a-polynomial
 */
public class ComplexNum {
    private double real;
    private double imaginary;

    public ComplexNum(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double distance(ComplexNum c) {
        return Math.sqrt((this.real-c.real)*(this.real-c.real)+(this.imaginary-c.imaginary)*(this.imaginary-c.imaginary));
    }
    
    public ComplexNum add(double real, double imaginary) {
        return new ComplexNum(this.real + real, this.imaginary + imaginary);
    }

    public ComplexNum add(ComplexNum c) {
        return new ComplexNum(this.real + c.real, this.imaginary + c.imaginary);
    }

    public ComplexNum subtract(double real, double imaginary) {
        return new ComplexNum(this.real - real, this.imaginary - imaginary);
    }

    public ComplexNum subtract(ComplexNum c) {
        return new ComplexNum(this.real - c.real, this.imaginary - c.imaginary);
    }

    public ComplexNum multiply(double scalar) {
        return new ComplexNum(real * scalar, imaginary * scalar);
    }

    public ComplexNum multiply(ComplexNum c) {
        return new ComplexNum(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
    }

    public ComplexNum divide(double scalar) {
        return multiply(1.0 / scalar);
    }

    public ComplexNum divide(ComplexNum c) {
        return multiply(c.getConjugate()).multiply(1.0 / (c.real * c.real + c.imaginary * c.imaginary));
    }

    public ComplexNum getConjugate() {
        return new ComplexNum(real, imaginary * -1);
    }

    public ComplexNum pow(int exp) {
        ComplexNum c = new ComplexNum(real, imaginary);
        for (int k = 1; k < exp; k++) {
            c = multiply(c);
        }
        return c;
    }

    public ComplexNum exp() {
        return new ComplexNum(Math.exp(real) * Math.cos(imaginary), Math.exp(real) * Math.sin(imaginary));
    }

    public static ComplexNum exp(ComplexNum c) {
        return c.exp();
    }

    public ComplexNum cos() {
        return exp(multiply(new ComplexNum(0, 1))).add(exp(multiply(new ComplexNum(0, -1)))).divide(2);
    }

    public static ComplexNum cos(ComplexNum c) {
        return c.cos();
    }

    public ComplexNum sin() {
        return exp(multiply(new ComplexNum(0, 1))).subtract(exp(multiply(new ComplexNum(0, -1)))).divide(new ComplexNum(0, 2));
    }

    public static ComplexNum sin(ComplexNum c) {
        return c.sin();
    }

    public ComplexNum tan() {
        return sin().divide(cos());
    }

    public static ComplexNum tan(ComplexNum c) {
        return c.sin().divide(c.cos());
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        return "" + real + (imaginary >= 0 ? "+" : "") + imaginary + "i";
    }
}
