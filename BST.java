public class BST{
    private Node root;
    public BST(){};//default constructor could cause issues where functions don't anticipate the root being null
    public BST(Node _root){root = _root;};

    public static void main(String[] args){
        Tester t = new Tester();
        t.testNode();
        t.testBST();
        System.out.println("pass:"+t.pass + ", fail:" + t.fail);
    };

    public Node getRoot(){return this.root;};

    public void insert(int key){
        if (this.isEmpty()){this.root = new Node(key);}else{ // if the tree is empty set the root
            Node next = this.root;
            int idx = (key > next.key)? 1 : 0; // if the object we're trying to place is smaller or equal to the next varible node this corresponds to left else right
            while (next.child[idx] != null){ next = next.child[idx];idx = (key > next.key)? 1 : 0;} // while the next node down the line where the keys should go is defined we go a layer deeper
            next.child[idx] = new Node(key,next); //once we found an empty spot we put the new value
        }
    }

    public void remove(int key){
        this.root = null;//nub
    }

    public int depth(){
        return 1;//nub
    }

    public String printwalk(){
        return this.root.toString();//nub
    }

    public boolean isEmpty(){return root == null;};
}

class Tester{
    public int pass = 0, fail =0;
    public Tester(){};//default constructor
    public void testNode(){
        NodeCheckInitialization(); 
    }
    public void testBST(){
        BSTCheckInitialization(); //tests depth, isEmpty,getRoot and constructors
        BSTCheckInsert();
        BSTCheckRemove(); 
        BSTCheckPrintWalk(); 
    }
    private void NodeCheckInitialization(){
        Node n1 = new Node();//testing 1st constructor
        if (n1.key == 0)pass++; else fail++; // again java convention, ints can't be null so the default value is 0
        if (n1.child[0] == null && n1.child[1] == null && n1.parent == null)pass++; else fail++;//checking children intialization
        n1.key = -1; //setting a key
        if (n1.key == -1)pass++; else fail++;//checking it set properly

        n1.child[0] = new Node(1);//testing 2nd constructor
        if (n1.child[0].key == 1)pass++; else fail++;
        if (n1.child[0].child[0] == null && n1.child[0].child[1] == null && n1.child[0].parent == null)pass++; else fail++;

        n1.child[0].child[1] = new Node(4,n1.child[0]);//testing 3rd constructor
        if (n1.child[0].child[1].key == 4)pass++; else fail++;
        if (n1.child[0].child[1].child[0] == null && n1.child[0].child[1].child[1] == null && n1.child[0].child[1].parent == n1.child[0])pass++; else fail++;

        //testing toString
        if (n1.toString().equals("-1") && n1.child[0].toString().equals("1") && n1.child[0].child[1].toString().equals("4"))pass++; else fail++;
    }
    private void BSTCheckInitialization(){
        BST tree = new BST();
        if (tree.getRoot() == null)pass++; else fail++; //checks if root is initalized properly
        if (tree.isEmpty())pass++; else fail++; // given that root is properly initalized test whether isEmpty gives a correct default value
        tree = new BST(new Node(1));//Same checks on alternate constructor
        if (tree.getRoot() != null)pass++; else fail++; 
        if (!tree.isEmpty())pass++; else fail++; 
    }
    private void BSTCheckInsert(){
        BST tree = new BST();

        //Standard uses for insert function:
        tree.insert(1);
        if (tree.getRoot() != null)pass++; else fail++; //checks if a value of any type was assigned to the root
        if (tree.getRoot() instanceof Node)pass++; else fail++; //checks if new root is of expected type
        if (tree.getRoot().key == 1 && tree.getRoot().child[0] == null && tree.getRoot().child[1] == null && tree.getRoot().parent == null)pass++; else fail++; //checks if new root has correct attributes and thus is indeed the requested new Node
        if (!tree.isEmpty())pass++; else fail++; //checks whether isEmpty correctly treats a very standard situation correctly given that the root was set to a value
        tree.insert(-1);
        //checks that root is only affected as expected
        if (tree.getRoot().key == 1 && tree.getRoot().child[0] != null && tree.getRoot().child[1] == null && tree.getRoot().parent == null)pass++; else fail++; 
        //checks that element is properly placed and initialized, the reason I don't use a printwalk here is that I'd have to depend on a print walk, I'll do similar testing to make sure it works afterwards
        if (tree.getRoot().child[0].key == -1 && tree.getRoot().child[0].child[0] == null && tree.getRoot().child[0].child[1] == null && tree.getRoot().child[0].parent == tree.getRoot())pass++; else fail++;
        tree.insert(2);
        if (tree.getRoot().child[1].key == 2 && tree.getRoot().child[1].child[0] == null && tree.getRoot().child[1].child[1] == null && tree.getRoot().child[1].parent == tree.getRoot())pass++; else fail++;
        tree.insert(1);
        if (tree.getRoot().child[0].child[1].key == 1 && tree.getRoot().child[0].child[1].child[0] == null && tree.getRoot().child[0].child[1].child[1] == null && tree.getRoot().child[0].child[1].parent == tree.getRoot().child[0])pass++; else fail++;
        tree.insert(1);
        if (tree.getRoot().child[0].child[1].child[0].key == 1 && tree.getRoot().child[0].child[1].child[0].child[0] == null && tree.getRoot().child[0].child[1].child[0].child[1] == null && tree.getRoot().child[0].child[1].child[0].parent == tree.getRoot().child[0].child[1])pass++; else fail++;
        tree.insert(2);
        if (tree.getRoot().child[1].child[0].key == 2 && tree.getRoot().child[1].child[0].child[0] == null && tree.getRoot().child[1].child[0].child[1] == null && tree.getRoot().child[1].child[0].parent == tree.getRoot().child[1])pass++; else fail++;
    }

    private void BSTCheckRemove(){
        BST tree = new BST(new Node(1));
        tree.remove(1);
        if (tree.isEmpty())pass++; else fail++;
    }; 
    private void BSTCheckPrintWalk(){
        BST tree = new BST(new Node(1));
        if (tree.printwalk().equals("1"))pass++; else fail++;
    }; 
}


class Node{
    int key;
    Node[] child = new Node[2];
    Node parent;
    public Node(){}
    public Node(int _key){
        key = _key;
    }
    public Node(int _key, Node _parent){
        key = _key;
        parent = _parent;
    }
    public String toString(){
        return String.valueOf(key);
    }
}