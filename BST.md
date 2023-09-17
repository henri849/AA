### Introduction:  
> This is an implementation of a simple Binary Search Tree for my Advanced Algorithms class.

### Specification:  
- Must be able to:
  * Insert
  * Remove
  * Print
  * Search
- Object Oriented
- Proper debugging:
  * Every method should be tested under: Normal Conditions, Limit Conditions, and Worst Conditions.
  * The testing must be organized comparably to the structure of what's being tested.  
### Overview of code structure:
* BST Class:  
```java
private Node root; //root node of the Binary Search tree
```   
```java
public BST() //default constructor
public BST(Node root) //constructor that takes in a root node
```
```java
public Node getRoot() //returns the root
public void insert(int key) //inserts a new node into the Binary Search Tree with the provided key
public void remove(int key) //removed a the node with the provided key from the Binary Search Tree
public void scrubParent(Node n1) //removes the provided node and branch it holds from the tree
public int depth() //returns the number of elements in the tree, including the root
public String printwalk() //prints out a representation of the Binary Search tree as a String of successive keys
public boolean isEmpty() //returns whether the tree is empty
```
* Node Class:
```java
private int key //key variable for the node, at the moment is an int
Node[2] child // array of children for the node, at the moment is binary
Node parent //parent node pointer
```
```java
public Node() //default constructor for node
public Node(int key) //constructor that takes in a key
public Node(int key, Node parent) //constructor that takes in a key and an parent node
```
```java
public int depth() //returns the number of elements underneath a node (including that node)
public Node successor() //returns the next largest element in the tree
public Node smallest() //returns the smallest element underneath this node
public String toString() //prints out the key
```
### Acknowledgements:  
* Mr. Kuszmaul's [Binary Search Tree videos](https://www.youtube.com/playlist?list=PLxcs8YW55pqo808FdMdSOTi5-JGJrr2iS)
