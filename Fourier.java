public class Fourier{
    public static void main(String[] args){
        // int n = 10;
        // Matrix data = new Matrix(n,1);
        // for (int i = 0; i < n; i++){
        //     data.setValue(i,0,new Complex(Math.cos(6*Math.PI/n*((double)i))+Math.cos(2*Math.PI/n*((double)i)),0));
        // }
        // data.normalizeColumn(0);
        // Matrix table = new Matrix(n,n);
        // table.complexMultiplicationTable();
        // System.out.println(table);
        // System.out.println(table.times(data));
        testComplex();
        testMatrix();
    }
    private static void testComplex(){
        int pass = 0;
        int fail = 0;        

        //Complex class testing
        Complex a = new Complex(5.0, 6.0);
        Complex b = new Complex(-3.0, 4.0);
        if (a.getReal() == 5 && a.getImaginary() == 6) pass ++;else fail++;
        if (b.getReal() == -3 && b.getImaginary() == 4) pass ++;else fail++;
        Complex add = b.add(a);
        if (add.getReal() == 2 && add.getImaginary() == 10) pass ++;else fail++;
        Complex sub = a.minus(b);
        if (sub.getReal() == 8 && sub.getImaginary() == 2) pass ++;else fail++;
        Complex mul = a.times(b);
        if (mul.getReal() == -39 && mul.getImaginary() == 2) pass ++;else fail++;
        Complex div = a.divide(b);
        if (div.getReal() == 0.36 && div.getImaginary() == -1.52) pass ++;else fail++;
        System.out.println("Complex class, passed : "+pass + ", failed:" + fail + ".");
    }
    private static void testMatrix(){
        int pass = 0;
        int fail = 0; 

        //Matrix class testing
        Matrix a =  new Matrix(2,3);
        if (a.numRows() == 2) pass ++; else fail++;
        if (a.numColumns() == 3) pass ++; else fail++;
        a.setValue(0,0,new Complex(1,0));
        if (a.getValue(0,0).getReal() == 1 && a.getValue(0,0).getImaginary() == 0)pass++; else fail++;
        a.setValue(0,1,new Complex(0,1));
        if (a.getValue(0,1).getReal() == 0 && a.getValue(0,1).getImaginary() == 1)pass++; else fail++;
        a.setValue(0,2,new Complex(1,1));
        if (a.getValue(0,2).getReal() == 1 && a.getValue(0,2).getImaginary() == 1)pass++; else fail++;
        a.setValue(1,0,new Complex(-1,1));
        if (a.getValue(1,0).getReal() == -1 && a.getValue(1,0).getImaginary() == 1)pass++; else fail++;

        //Matrix multiplication
        Matrix b = new Matrix(2,2);
        b.setValue(0,0,new Complex(1,0));b.setValue(0,1,new Complex(0,0));
        b.setValue(1,0,new Complex(0,0));b.setValue(1,1,new Complex(1,0));
        Matrix c = new Matrix(2,1);
        c.setValue(0,0,new Complex(1,1));c.setValue(1,0,new Complex(-3,2));
        Matrix bc = b.times(c);
        if (c.toString().equals(bc.toString()))pass++; else fail++;

        System.out.println("Matrix class, passed : "+pass + ", failed:" + fail + ".");
    }
}