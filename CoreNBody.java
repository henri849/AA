public class CoreNBody{
    public static void main(String[] args){
        int k = 4;
        Matrix mat = new Matrix(k,k);
        for (int i = 0; i < k*k; i++){mat.setValue(i/k,i%k,new Complex(i%k+1,0));}
        // naiveAlgo(mat,k);
        System.out.println("Better Algorithm");
        betterAlgo(mat,k);
    }

    public static void naiveAlgo(Matrix mat, int k){
        Complex[] elem = new Complex[k*k];//flatten
        for (int i = 0; i < k*k; i++){elem[i] = mat.getValue(i/k,(i%k));}//mat.getValue(i/k,(i%k))
        //now we do all the permutations, I found this facinating mask permutation algorithm online and decided to implement it here rather than my messy old naive algorithm
        for (int mask = 0; mask < 1<< k*k; mask++) {
            if (Integer.bitCount(mask) == k) {
                Complex[] combination = new Complex[k];
                int idx = 0;
                for (int i = 0; i < k*k; i++) {
                    if ((mask & (1 << i)) != 0) {
                        combination[idx++] = elem[i];
                    }
                }
                String po = "";
                for (int i =0; i < k; i++){
                    po += combination[i] + ", ";
                }
                System.out.println(po.substring(0,po.length()-1));//here the forced "interact"
            }
        }
    }

    public static void betterAlgo(Matrix mat, int k){
        //here we do the butterfly swaps
        System.out.println(mat);
        for (int i =1; i < k; i++){//succesive butterfly swaps
            int idx = 0;
            for (int j = 0; j < k/2; j++){
                Complex num = mat.getValue(i,(idx+i)%k);
                mat.setValue(i,(idx+i)%k,mat.getValue(i,idx));
                mat.setValue(i,idx,num);
                if ((idx+1)%i == 0){idx+=i;}
                idx++;
            }
        }
        System.out.println(mat);
    }
}