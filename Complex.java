public class Complex{
    private double real;
	private double imaginary;
    public Complex(double re, double im){real = re;imaginary = im;}
    public double getReal(){return real;}
    public double getImaginary(){return imaginary;}

    public Complex times(Complex c2){
        return new Complex(this.getReal()*c2.getReal()-this.getImaginary()*c2.getImaginary(),this.getReal()*c2.getImaginary()+c2.getReal()*this.getImaginary());
    }
    public Complex reciprocal() {
        //1/a+bi *= a-bi/a-bi
        //a-bi/a^2+b^2
        //a = a/a^2+b^2
        //b = -b/a^2+b^2
        double div = real*real + imaginary*imaginary;
        return new Complex(real / div, -imaginary / div);
    }

    public Complex divide(Complex other) {
        return this.times(other.reciprocal());
    }
    public Complex norm(){
        return new Complex(this.real*this.real+this.imaginary*this.imaginary,0);
    }

    public Complex add(Complex c2){
        return new Complex(this.getReal() + c2.getReal(),this.getImaginary()+c2.getImaginary());
    }
    public Complex minus(Complex c2){
        return new Complex(this.getReal() - c2.getReal(),this.getImaginary()-c2.getImaginary());
    }

    public static Complex[] rootsOfUnity(int n){
        Complex[] retval = new Complex[n];
        for (int k = 0; k < n; k++){
            retval[k] = rootOfUnity(n,k);
        }
        return retval;
    }
    public static Complex rootOfUnity(int n, int k){
        double real = Math.cos(2 *Math.PI *k/n);
        double imaginary = Math.sin(2 *Math.PI *k/n);
        Complex rtn = new Complex(real,imaginary);
        return rtn;
    }
    // public String toString() {
    //     if (imaginary == 0) return real + "";
    //     if (real == 0) return imaginary + "i";
    //     if (imaginary <  0) return "("+real + " - " + (-imaginary) + "i)";
    //     return "("+real + " + " + imaginary + "i)";
    // }
    public String toString() {
        double dig = 1000.0;
        if (Math.round(imaginary*dig)/dig == 0) return Math.round(real*dig)/dig + "";
        if (Math.round(real*dig)/dig == 0) return Math.round(imaginary*dig)/dig  + "i";
        if (Math.round(imaginary*dig)/dig <  0) return "("+Math.round(real*dig)/dig  + " - " + (-Math.round(imaginary*dig)/dig ) + "i)";
        return "("+Math.round(real*dig)/dig  + " + " + Math.round(imaginary*dig)/dig  + "i)";
    }
}