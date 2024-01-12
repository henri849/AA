import java.util.Arrays; 
class Graph{
    Edge[] egs;
    int ec = 0;
    Vertex[] ver;
    int vc = 0;
    public static void main(String[] args){
        
    }

    public static Boolean kInd(Graph G, int k){
        int numV = G.numVert();
        for (int i =1; i <= nCr(numV,k); i++){
            Vertex[] perm = nextP(G.ver,i,k,numV);
            //here we check if there are any edges that connect the nodes of our subset...
        }
    }
    public Vertex[] nextP(Vertex V, int k, int n){
        Vertex[] perm = new Vertex[k];
        for (int j = 0; j< n-k; j++){
            if (k <= nCr(n-1,k-1)*(j+1)){
                perm[j] = V[j]
                Vertex[] perm2 = nextP(Arrays.copyOfRange(V, j, oldArr.length), int k-j, int n-j);
                for (int k = j; k < n; k++){
                    perm[k] = perm2[k-j];
                }
                break;
            }
        }
        return perm;
    }
    static int nCr(int n, int r){
        return factorial(n)/(factorial(r) *factorial(n - r));
    }
    static int factorial(int n){
        if(n==0) return 1;
        int res = 1;
        for (int i = 2; i <= n; i++)
            res = res * i;
        return res;
    }

    public int numVert(){
        return ver.length();
    }
    public Vertex getVert(int k){return this.ver[k];}

    public void addEdge(Vertex s, Vertex e, int w) throws RuntimeException{
        try{
            egs[ec++] = new Edge(s,e,w);
        }catch(ArrayIndexOutOfBoundsException a){
            throw(new RuntimeException("EdgeArrayFilled"));
        }
    }

    public void addVertex(int id) throws RuntimeException{
        try{
            ver[vc] = new Vertex(id);
            vc++;
        }catch(ArrayIndexOutOfBoundsException a){
            throw(new RuntimeException("VertexArrayFilled"));
        }
    }
    public Vertex getVertex(int id){
        for (int i = 0; i < vc ; i++){
            if (ver[i].id == id) return ver[i];
        }
        
        return null;
    }
    public Graph(){
        egs = new Edge[5];
        ver = new Vertex[5];
    }
}

class Vertex{
	int id;
	public Vertex(int _id){id=_id;}
}
class Edge{
	Vertex start;
	Vertex end; //For directed graph
    int weight;
	public Edge(Vertex s, Vertex e, int w){start = s; end = e; weight = w;}
}
