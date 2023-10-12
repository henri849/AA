public class Matrix{
    private Complex[][] mat;
    public Matrix(int rows, int columns){
        mat = new Complex[rows][columns];
    }
    public void setValue(int row, int column, Complex value){
        mat[row][column] = value;
    }
    public Complex getValue(int row, int column){
        return mat[row][column];
    }
    public void setRow(Complex[] row, int r){
        for (int c = 0; c < this.numColumns(); c++){
            mat[r][c] = row[c];
        }
    }
    public Complex sumColumn(int column){
        Complex n = new Complex(0,0);
        for (int r = 0; r < this.numRows(); r++){
            n = n.add(this.getValue(r,column).norm());
        }
        return n;
    }
    public void normalizeColumn(int column){
        Complex sum = sumColumn(column);
        for (int r = 0; r < this.numRows(); r++){
            this.setValue(r,column,this.getValue(r,column).divide(sum));
        }
    }
    public void multiplicationTable(){
        for (int r = 0; r < this.numRows(); r++){
            for (int c = 0; c < this.numColumns(); c++){
                this.setValue(r,c,new Complex(r*c,0));
            }
        }
    }
    public void complexMultiplicationTable(){
        for (int r = 0; r < this.numRows(); r++){
            for (int c = 0; c < this.numColumns(); c++){
                this.setValue(r,c,Complex.rootOfUnity(this.numColumns(),r*c).divide(new Complex(this.numColumns(),0)));
            }
        }
    }

    public Matrix times(Matrix mat2){
        Matrix rtn = new Matrix(this.numRows(),mat2.numColumns());
        for (int r = 0; r < this.numRows(); r++){
            for (int c = 0; c < mat2.numColumns(); c++){
                Complex p = new Complex(0,0);
                for (int i = 0; i < this.numRows(); i++){
                    p = p.add(mat[r][i].times(mat2.getValue(i,c)));
                }
                rtn.setValue(r,c,p);
            }
        }
        return rtn;
    }
    public int numRows(){return this.mat.length;}
    public int numColumns(){return this.mat[0].length;}

    public String toString(){
        String rtn = "";
        for (int r = 0; r < this.numRows(); r++){
            rtn += "{";
            for (int c = 0; c < this.numColumns(); c++){
                rtn += this.getValue(r,c) +",";
            }
            rtn = rtn.substring(0,rtn.length()-1) + "}\n";
        }
        return rtn;
    }
}