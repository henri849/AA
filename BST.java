import java.util.concurrent.ThreadLocalRandom;

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

    public void scrubParent(Node n1){ //sets the parent's link to n1 to null thereby scrubing the branch n1 forks to, does nothing when n1 is null and scrub's itself when n1 is root;
        if (n1 == null) return;
        if (n1 == this.getRoot()){this.root = null;return;};
        if (n1.parent == null){return;};//handles case where n1 is incorrectly initalized and thus has no parent but isn't root, this could prevent total deletion of tree
        if (n1 == n1.parent.child[1]) n1.parent.child[1] = null; else n1.parent.child[0] = null;//sets the link between n1 and it's parent to null
    }

    public int depth(){
        return root.depth();
    }

    public String printwalk(){
        //the idea here is to print the nodes in incremental order

        //next I consider using an assert but given that printwalking an empty tree is a valid use case I switched it out for an if
        if (this.getRoot() == null) return "";//assert current != null : "Cannot printwalk empty tree";

        Node current = this.getRoot().smallest();
        Node next = current.successor();
        String rtn = current.toString();
        while (next != null){//we start with the smallest node and then print each successive successor.
            if (next != null) rtn += " ";//adding spaces if there's a next element
            rtn += next.toString();
            current = next;
            next = next.successor();
        }
        return rtn;
    }

    public boolean isEmpty(){return root == null;};
}

class Tester{
    public int pass = 0, fail =0;
    public Tester(){};//default constructor
    public void testNode(){
        NodeCheckInitialization(); 
        NodeCheckSuccessor();
    }
    public void testBST(){
        BSTCheckInitialization(); //tests depth, isEmpty,getRoot and constructors
        BSTCheckInsert();
        BSTCheckRemove(); 
        BSTCheckPrintWalk(); 
    }
    private void NodeCheckSuccessor(){
        BST tree = new BST();
        //Standard uses for insert function:
        tree.insert(1);
        tree.insert(-1);
        tree.insert(2);
        tree.insert(1);
        tree.insert(1);
        tree.insert(2);
        /*
            1
         -1   2
           1 2
          1
        */
       //testing smallest:
       //right side of tree
       if (tree.getRoot().child[1].smallest() == tree.getRoot().child[1].child[0])pass++;else fail++;
       if (tree.getRoot().child[1].child[0].smallest() == tree.getRoot().child[1].child[0])pass++;else fail++;
       //left side of tree
       if (tree.getRoot().smallest() == tree.getRoot().child[0])pass++;else fail++;
       if (tree.getRoot().child[0].smallest() == tree.getRoot().child[0])pass++;else fail++;
       if (tree.getRoot().child[0].child[1].smallest() == tree.getRoot().child[0].child[1].child[0])pass++;else fail++;
       if (tree.getRoot().child[0].child[1].child[0].smallest() == tree.getRoot().child[0].child[1].child[0])pass++;else fail++;
       //testing successor:
       //right side of tree
       if (tree.getRoot().successor() == tree.getRoot().child[1].child[0])pass++;else fail++;
       if (tree.getRoot().child[1].successor() == null)pass++;else fail++;
       if (tree.getRoot().child[1].child[0].successor() == tree.getRoot().child[1])pass++;else fail++;
       //left side of tree
       if (tree.getRoot().child[0].successor() == tree.getRoot().child[0].child[1].child[0])pass++;else fail++;
       if (tree.getRoot().child[0].child[1].successor() == tree.getRoot())pass++;else fail++;
       if (tree.getRoot().child[0].child[1].child[0].successor() == tree.getRoot().child[0].child[1])pass++;else fail++;
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

        //depth test
        if (tree.depth() == 1)pass++; else fail++; 
        for (int i =0; i < 100; i++){
            tree.insert(ThreadLocalRandom.current().nextInt(-100,100));
            if (tree.depth() == 2+i)pass++; else fail++; 
        }
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
        /*
            1
         -1   2
           1 2
          1
        */
    }

    private void BSTCheckRemove(){
        BST tree = new BST();
        tree.insert(1);
        tree.insert(-1);
        tree.insert(2);
        tree.insert(1);
        tree.insert(1);
        tree.insert(2);
        /*
            1
         -1   2
           1 2
          1
        */
        //Standard uses for scrub function:
        tree.scrubParent(null);
        if (tree.printwalk().equals("-1 1 1 1 2 2"))pass++; else fail++;
        tree.scrubParent(tree.getRoot().child[1]);
        if (tree.printwalk().equals("-1 1 1 1"))pass++; else fail++;
        tree.insert(2);
        tree.insert(2);
        tree.scrubParent(tree.getRoot().child[1].child[0]);
        if (tree.printwalk().equals("-1 1 1 1 2"))pass++; else fail++;
        tree.insert(2);
        tree.scrubParent(tree.getRoot().child[0].child[1].child[0]);
        if (tree.printwalk().equals("-1 1 1 2 2"))pass++; else fail++;
        tree.insert(1);
        tree.scrubParent(tree.getRoot().child[0].child[1]);
        if (tree.printwalk().equals("-1 1 2 2"))pass++; else fail++;
        tree.insert(1);
        tree.insert(1);
        tree.scrubParent(tree.getRoot().child[0]);
        if (tree.printwalk().equals("1 2 2"))pass++; else fail++;
        tree.insert(1);
        tree.insert(1);
        tree.insert(-1);
        tree.scrubParent(tree.getRoot());
        if (tree.printwalk().equals(""))pass++; else fail++;

        //testing remove function
        tree = new BST(new Node(1));
        tree.remove(1);
        if (tree.isEmpty())pass++; else fail++;
    }; 
    private void BSTCheckPrintWalk(){
        BST tree = new BST();
        if (tree.printwalk().equals(""))pass++; else fail++;//test empty tree (constructor 1)

        tree = new BST(new Node(1));
        if (tree.printwalk().equals("1"))pass++; else fail++;// test constructor 2's tree

        String ans = "1";//test decent length but standard input w/ spacing
        for (int i = 1; i < 100; i++){
            tree.insert(i);
            ans += " " + String.valueOf(i);
            if (tree.printwalk().equals(ans))pass++; else fail++;
        }

        tree = new BST(new Node(1));//test standard use case with negatives and dupliactes
        tree.insert(-1);
        if (tree.printwalk().equals("-1 1"))pass++; else fail++;
        tree.insert(2);
        if (tree.printwalk().equals("-1 1 2"))pass++; else fail++;
        tree.insert(1);
        if (tree.printwalk().equals("-1 1 1 2"))pass++; else fail++;
        tree.insert(1);
        if (tree.printwalk().equals("-1 1 1 1 2"))pass++; else fail++;
        tree.insert(2);
        if (tree.printwalk().equals("-1 1 1 1 2 2"))pass++; else fail++;
        tree.insert(-400);
        if (tree.printwalk().equals("-400 -1 1 1 1 2 2"))pass++; else fail++;
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
    public int depth(){ //inclues the node it's called on
        int total = 1;//recursion base case
        if (this.child[0] != null){total+=this.child[0].depth();}// recursivly adds all leafs on the left branch
        if (this.child[1] != null){total+=this.child[1].depth();}// recursivly adds all leafs on the right branch
        return total;
    }
    public Node(int _key, Node _parent){
        key = _key;
        parent = _parent;
    }
    public Node successor(){
        if (this.child[1] != null)return this.child[1].smallest(); // if there's a right child take the smallest of that branch
        Node next = this.parent;
        if (next == null) return null;//this is for the case where the only node is the root
        Node current = this;
        while (next.child[1] == current){// go up the tree from the starting node until the branch becomes the left child of some other branch, that parent must be the successor assuming the tree is valid
            current = next;
            next = current.parent;
            if (next == null) return null;
        }
        return next;
    }
    public Node smallest(){
        if (this.child[0] == null) return this;//recusive minimum function, always looks for smallest node underneath by following left child
        return this.child[0].smallest();
    }
    public String toString(){
        return String.valueOf(key);//returns string version on int, should be changed to accomodate generics
    }
}
