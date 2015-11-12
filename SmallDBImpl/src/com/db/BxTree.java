package com.db;

import java.util.ArrayList;


public class BxTree<Key extends Comparable<? super Key>, Value>
{
    /** Pointer to the root node. It may be a leaf or an inner node, but it is never null. */
    private Node root;
    /** the maximum number of keys in the leaf node, M must be > 0 */
    private final int M;
    /** the maximum number of keys in inner node, the number of pointer is N+1, N must be > 2 */
    private final int N;

    /** Create a new empty tree. */
    public BxTree(int n) {
	this(n, n);
    }

    public BxTree(int m, int n) {
        M = m;
        N = n;
        root = new LNode();
    }

    public void insert(Key key, Value value) {
	//System.out.println("insert key=" + key);
	Split result = root.insert(key, value);
        if (result != null) {
	    // The old root was split into two parts.
	    // We have to create a new root pointing to them
            INode _root = new INode();
            _root.num = 1;
            _root.keys[0] = result.key;
            _root.children[0] = result.left;
            _root.children[1] = result.right;
            root = _root;
        }
    }

    /** 
     * Looks for the given key. If it is not found, it returns null.
     * If it is found, it returns the associated value.
     * @param key 
     * @return 
     */
    public Value find(Key key) {
        Node node = root;
        while (node instanceof BxTree.INode) { // need to traverse down to the leaf
	    INode inner = (INode) node;
            int idx = inner.getLoc(key);
            node = inner.children[idx];
        }

        //We are @ leaf after while loop
        LNode leaf = (LNode) node;
        int idx = leaf.getLoc(key);
        if (idx < leaf.num && leaf.keys[idx].equals(key)) {
	    return leaf.values[idx];
        } else {
	    return null;
        }
    }

    /**
     * Finds the nodes less than particular node
     * @param key
     * @return
     */
    public ArrayList<Value> findLess(Key key){
    	 INode node = (INode) root;
    	 int idx = 0;
    	 INode inner = null;
    	 int parentIdx = 0;
    	 ArrayList<Value> resultantNodes = new ArrayList<Value>();
    	 INode parent = null;
    	 idx = node.getLoc(key);
    	 getNodes(node,idx+1,resultantNodes,key,"LessThan");
    	 
 	 	return resultantNodes;
    }
    
    /**
     *  Finds the nodes less than Equal to a particular node
     * @param key
     * @return
     */
    public ArrayList<Value> findLessthanEqual(Key key){
   	 INode node = (INode)root;
   	 int idx = 0;
   	 INode inner = null;
   	 int parentIdx = 0;
   	 ArrayList<Value> resultantNodes = new ArrayList<Value>();
   	 INode parent = null;
   	 idx = node.getLoc(key);
   	 getNodes(node,idx+1,resultantNodes,key,"LessThanAndEqual");
   	 
	 	return resultantNodes;
   }
    
    public ArrayList<Value> findNotEqual(Key key){
    	return null;
    }
    
    /**
     * Find nodes greater than 
     * @param key
     * @return
     */
    public ArrayList<Value> findGreater(Key key){
      	 INode node = (INode) root;
      	 int idx = 0;
      	 INode inner = null;
      	 int parentIdx = 0;
      	 ArrayList<Value> resultantNodes = new ArrayList<Value>();
      	 INode parent = null;
      	 idx = node.getLoc(key);
      	getGreaterNodes(node,idx,resultantNodes,key,"GreaterThan");
      	 
   	 	return resultantNodes;
      }
    
    /**
     * Find nodes greater than equal to 
     * @param key
     * @return
     */
    public ArrayList<Value> findGreaterthanEqual(Key key){
     	 INode node = (INode) root;
     	 int idx = 0;
     	 INode inner = null;
     	 int parentIdx = 0;
     	 ArrayList<Value> resultantNodes = new ArrayList<Value>();
     	 INode parent = null;
     	 idx = node.getLoc(key);
     	getGreaterNodes(node,idx,resultantNodes,key,"GreaterThanAndEqual");
     	 
  	 	return resultantNodes;
     }
    
   
    /**
     * Get all the nodes
     * @return
     */
    public ArrayList<Value> getAllNodes(){
    	ArrayList<Value> allNodes = new ArrayList<Value>();
    	auxGetAllNodes(root,allNodes);
    	return allNodes;
    }
    
    private void auxGetAllNodes(Node root, ArrayList<Value> nodesList){
    	if(root instanceof BxTree.LNode){
    		LNode leafNode = (LNode)root;
    		for(int j= 0;j<leafNode.num;j++)
    			nodesList.add(leafNode.values[j]);
    	}
    	else{
	    	for(int i = 0;i<=root.num;i++)
	    		auxGetAllNodes(((INode)root).children[i],nodesList);
    	}
    }
    
    
    public ArrayList<Value> findAllNotEqual(Key notEqualKey){
    	ArrayList<Value> allNodes = new ArrayList<Value>();
    	auxfindAllNotEqual(root,allNodes,notEqualKey);
    	return allNodes;
    }
    
    private void auxfindAllNotEqual(Node root, ArrayList<Value> nodesList, Key notEqualKey){
    	if(root instanceof BxTree.LNode){
    		LNode leafNode = (LNode)root;
    		for(int j= 0;j<leafNode.num;j++){
    			if(!(leafNode.keys[j].equals(notEqualKey)))
    				nodesList.add(leafNode.values[j]);
    		}
    	}
    	else{
	    	for(int i = 0;i<=root.num;i++)
	    		auxfindAllNotEqual(((INode)root).children[i],nodesList, notEqualKey);
    	}
    }
    
    
    
    
   private void getNodes(Node node,int rootIndex,ArrayList resultantNodes,Key key,String compareString)
   {
	   if(node instanceof BxTree.LNode){
		   for(int j=0;j<((LNode)node).num;j++){
			   if(compareString.equals("LessThan")){
					   if(((LNode)node).keys[j].compareTo(key)>=0)
						   break;
			   }
			   else if(compareString.equals("LessThanAndEqual")){
				   if(((LNode)node).keys[j].compareTo(key)>0)
					    break;
			   }
			   else if(compareString.equals("GreaterThan")){
				   if(((LNode)node).keys[j].compareTo(key)<=0)
					   continue;
			   }
			   else if(compareString.equals("GreaterThanAndEqual")){
				   if(((LNode)node).keys[j].compareTo(key)<0)
					   continue;
			   }
			   resultantNodes.add(((LNode)node).values[j]);
		   }
		   
	   }
	   else{
		   rootIndex = rootIndex == 0?((BxTree.INode)node).num+1:rootIndex;
		   
		   for(int i = 0;i<rootIndex;i++){
			   
			   getNodes(((BxTree.INode)node).children[i],0,resultantNodes,key,compareString); 
		   }
	   } 
   }
   
   
   private void getGreaterNodes(Node node,int rootIndex,ArrayList resultantNodes,Key key,String compareString)
   {
	   if(node instanceof BxTree.LNode){
		   for(int j=0;j<((LNode)node).num;j++){
			   if(compareString.equals("GreaterThan")){
				   if(((LNode)node).keys[j].compareTo(key)<=0)
					   continue;
			   }
			   else if(compareString.equals("GreaterThanAndEqual")){
				   if(((LNode)node).keys[j].compareTo(key)<0)
					   continue;
			   }
			   resultantNodes.add(((LNode)node).values[j]);
		   }
		   
	   }
	   else{
		  
		   
		   for(int i = rootIndex;(((BxTree.INode)node).children[i]!=null);i++){
			   
			   getGreaterNodes(((BxTree.INode)node).children[i],0,resultantNodes,key,compareString); 
		   }
	   } 
   }
   
   
    
    public void dump() {
	root.dump();
    }

    abstract class Node {
	protected int num; //number of keys
	protected Key[] keys;

	abstract public int getLoc(Key key);
	// returns null if no split, otherwise returns split info
	abstract public Split insert(Key key, Value value);
	abstract public void dump();
    }

    class LNode extends Node {
	// In some sense, the following casts are almost always illegal
	// (if Value was replaced with a real type other than Object,
	// the cast would fail); but they make our code simpler
	// by allowing us to pretend we have arrays of certain types.
	// They work because type erasure will erase the type variables.
	// It will break if we return it and other people try to use it.
	final Value[] values = (Value[]) new Object[M];
	{ keys = (Key[]) new Comparable[M]; }

	/**
	 * Returns the position where 'key' should be inserted in a leaf node
	 * that has the given keys.
	 */
	public int getLoc(Key key) {
	    // Simple linear search. Faster for small values of N or M, binary search would be faster for larger M / N
	    for (int i = 0; i < num; i++) {
		if (keys[i].compareTo(key) >= 0) {
		    return i;
		}
	    }
	    return num;
	}

	public Split insert(Key key, Value value) {
	    // Simple linear search
	    int i = getLoc(key);
	    if (this.num == M) { // The node was full. We must split it
		int mid = (M+1)/2;
		int sNum = this.num - mid;
		LNode sibling = new LNode();
		sibling.num = sNum;
		System.arraycopy(this.keys, mid, sibling.keys, 0, sNum);
		System.arraycopy(this.values, mid, sibling.values, 0, sNum);
		this.num = mid;
		if (i < mid) {
		    // Inserted element goes to left sibling
		    this.insertNonfull(key, value, i);
		} else {
		    // Inserted element goes to right sibling
		    sibling.insertNonfull(key, value, i-mid);
		}
		// Notify the parent about the split
		Split result = new Split(sibling.keys[0], //make the right's key >= result.key
					 this,
					 sibling);
		return result;
	    } else {
		// The node was not full
		this.insertNonfull(key, value, i);
		return null;
	    }
	}

	private void insertNonfull(Key key, Value value, int idx) {
	    //if (idx < M && keys[idx].equals(key)) {
	    if (idx < num && keys[idx].equals(key)) {
		// We are inserting a duplicate value, simply overwrite the old one
		values[idx] = value;
	    } else {
		// The key we are inserting is unique
		System.arraycopy(keys, idx, keys, idx+1, num-idx);
		System.arraycopy(values, idx, values, idx+1, num-idx);

		keys[idx] = key;
		values[idx] = value;
		num++;
	    }
	}

	public void dump() {
	    System.out.println("lNode h==0");
	    for (int i = 0; i < num; i++){
		System.out.println(keys[i]);
	    }
	}
    }

    class INode extends Node {
	final Node[] children = new BxTree.Node[N+1];
	{ keys = (Key[]) new Comparable[N]; }

	/**
	 * Returns the position where 'key' should be inserted in an inner node
	 * that has the given keys.
	 */
	public int getLoc(Key key) {
	    // Simple linear search. Faster for small values of N or M
	    for (int i = 0; i < num; i++) {
		if (keys[i].compareTo(key) > 0) {
		    return i;
		}
	    }
	    return num;
	    // Binary search is faster when N or M is big,
	}

	public Split insert(Key key, Value value) {
	    /* Early split if node is full.
	     * This is not the canonical algorithm for B+ trees,
	     * but it is simpler and it does break the definition
	     * which might result in immature split, which might not be desired in database
	     * because additional split lead to tree's height increase by 1, thus the number of disk read
	     * so first search to the leaf, and split from bottom up is the correct approach.
	     */

	    if (this.num == N) { // Split
	    	//System.out.println("Inode split");
		int mid = (N+1)/2;
		int sNum = this.num - mid;
		INode sibling = new INode();
		sibling.num = sNum;
		System.arraycopy(this.keys, mid, sibling.keys, 0, sNum);
		System.arraycopy(this.children, mid, sibling.children, 0, sNum+1);

		this.num = mid-1;//this is important, so the middle one elevate to next depth(height), inner node's key don't repeat itself

		// Set up the return variable
		Split result = new Split(this.keys[mid-1],
					 this,
					 sibling);

		// Now insert in the appropriate sibling
		if (key.compareTo(result.key) < 0) {
		    this.insertNonfull(key, value);
		} else {
		    sibling.insertNonfull(key, value);
		}
		return result;

	    } else {// No split
		this.insertNonfull(key, value);
		return null;
	    }
	}

	private void insertNonfull(Key key, Value value) {
	    // Simple linear search
	    int idx = getLoc(key);
	    Split result = children[idx].insert(key, value);

	    if (result != null) {
		if (idx == num) {
		    // Insertion at the rightmost key
		    keys[idx] = result.key;
		    children[idx] = result.left;
		    children[idx+1] = result.right;
		    num++;
		} else {
		    // Insertion not at the rightmost key
		    //shift i>idx to the right
		    System.arraycopy(keys, idx, keys, idx+1, num-idx);
		    System.arraycopy(children, idx, children, idx+1, num-idx+1);

		    children[idx] = result.left;
		    children[idx+1] = result.right;
		    keys[idx] = result.key;
		    num++;
		}
	    } // else the current node is not affected
	}

	/**
	 * This one only dump integer key
	 */
	public void dump() {
	    System.out.println("iNode h==?");
	    for (int i = 0; i < num; i++){
		children[i].dump();
		System.out.print('>');
		System.out.println(keys[i]);
	    }
	    children[num].dump();
	}
    }

    class Split {
	public final Key key;
	public final Node left;
	public final Node right;

	public Split(Key k, Node l, Node r) {
	    key = k; left = l; right = r;
	}
    }
}