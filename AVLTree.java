// Jiajun Zhang

import java.lang.*;

public class AVLTree<E extends Comparable<? super E>> implements DataCounter<E> {

    /**
     * The root of the AVL tree. root is null if and only if the tree
     * is empty.
     */
    protected AVLNode overallRoot;

    /**
     * Number of nodes in the AVL tree.
     */
    protected int size;

    /**
     * Inner (non-static) class to represent a node in the tree. Each node
     * includes a String and an integer count. The class is protected so that it
     * may be accessed by subclasses of BSTCounter.
     */
    protected class AVLNode {
        /**
         * The left child of this node.
         */
        public AVLNode left;

        /**
         * The right child of this node.
         */
        public AVLNode right;

        /**
         * The data element stored at this node.
         */
        public E data;

        /**
         * The count for this data element.
         */
        public int count;
        
        public int height;
        /**
         * Create a new data node. Also takes care of incrementing the tree
         * size.
         *
         * @param data data element to be stored at this node.
         */
        public AVLNode(E data) {
            this.data = data;
            count = 1;
            height = 1;
            left = right = null;
            size++;
        }
    }

    /**
     * Create an empty AVL tree.
     */
    public AVLTree() {
        overallRoot = null;
        size = 0;
    }

    // Get the height of the node
    private int height(AVLNode n){
      return n == null ? 0 : n.height;    
    }
        
    public void incCount(E data) {
    	overallRoot = insert(data, overallRoot);
    }
    
    AVLNode insert(E data, AVLNode currNode) {
      if (currNode == null) {
          currNode = new AVLNode(data);
          return currNode;
      }
      
      int cmp =  data.compareTo(currNode.data);
      if (cmp == 0) {
            // current node is a match
         currNode.count++;
         
         return currNode;
      }

      else if (cmp < 0) 
         currNode.left = insert(data, currNode.left);
         
      else    // cmp > 0
         currNode.right = insert(data, currNode.right);
         
      currNode.height = Math.max(height(currNode.left), height(currNode.right)) + 1;
         
      if(height(currNode.left) - height(currNode.right) > 1) {
         if(data.compareTo(currNode.left.data) < 0) {
            currNode = rightRotation(currNode);
         }
         else {
            currNode = LR_Rotation(currNode);
         }
      }

      else if(height(currNode.right) - height(currNode.left) > 1) {
         if(data.compareTo(currNode.right.data) > 0) {
            currNode = leftRotation(currNode);
         }
         else {
            currNode = RL_Rotation(currNode);
         }
      }
      else
         ;  // Duplicate; do nothing
         
      return currNode;
    }
         
    /* Single right rotation */
    private AVLNode rightRotation(AVLNode k2) {	   	 
        AVLNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /* single left rotation */
    private AVLNode leftRotation(AVLNode k1)	{	
        AVLNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }
    
    /* double left right rotation */      
    private AVLNode LR_Rotation(AVLNode k3) {	
        k3.left = leftRotation( k3.left );
        return rightRotation( k3 );
    }
    
    /* double right left rotation */
    private AVLNode RL_Rotation(AVLNode k1) {	
        k1.right = rightRotation( k1.right );
        return leftRotation( k1 );
    }

    public int getSize() {
        return size;
    }

    /** {@inheritDoc} */
    public DataCount<E>[] getCounts() {
    	@SuppressWarnings("unchecked")
        DataCount<E>[] counts = new DataCount[size];
        if (overallRoot != null)
            traverse(overallRoot, counts, 0);
        return counts;
    }

    /**
     * Do an inorder traversal of the tree, filling in an array of DataCount
     * objects with the count of each element. Doing an inorder traversal
     * guarantees that the result will be sorted by element. We fill in some
     * contiguous block of array elements, starting at index, and return the
     * next available index in the array.
     *
     * @param counts The array to populate.
     */
    protected int traverse(AVLNode root, DataCount<E>[] counts, int idx) {
        if(root != null) {
            idx = traverse(root.left, counts, idx);
            counts[idx] = new DataCount<E>(root.data, root.count);
            idx = traverse(root.right, counts, idx + 1);
        }
        return idx;
    }

    
/*------------------------------test-------------------------------------*/
 
    /*    use preorder to test   */   
/*     void preOrder(AVLNode node) { 
        if (node != null) { 
            System.out.print(node.data + " "); 
            preOrder(node.left); 
            preOrder(node.right); 
        } 
    }


    public static void main(String[] args) { 
        AVLTree tree = new AVLTree(); 
  

        tree.incCount("10");
        tree.incCount("20");
        tree.incCount("30");
        tree.incCount("40");
        tree.incCount("50");
        tree.incCount("25");
        
        System.out.println("Preorder traversal of constructed tree is : "); 
        tree.preOrder(tree.overallRoot); 
    } 
*/
}