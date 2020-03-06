// Jiajun Zhang


import java.io.IOException;
import java.io.File;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order.
 */
public class WordCount {

    private static void countWords(String dataStruct, String printOption, String file) {
        DataCounter<String> counter = null;
        if (dataStruct.equals("-b"))
        {
            counter = new BinarySearchTree<String>();
        } 
        else if (dataStruct.equals("-a")) {
        	   counter = new AVLTree<String>();
        }
        else if (dataStruct.equals("-h")) {
        	   counter = new HashTable();
        }

        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + e);
            System.exit(1);
        }

        DataCount<String>[] counts = counter.getCounts();
        heapSort(counts);
        
        if (printOption.equals("-frequency" )){
        	   for (DataCount<String> c : counts)
                System.out.println(c.count + " \t" + c.data);	// frequency count
        } 
        else if (printOption.equals("-num_unique")) {
        	   System.out.println("The number of unique words in the text is: " +counts.length);   // unique words count
        }    

        return;
    }

    
   // Heap sort
   private static <E extends Comparable<? super E>> void heapSort(DataCount<E>[] counts) {
        int n = counts.length; 
  
        // Build heap (rearrange array) 
        for (int i = n / 2 - 1; i >= 0; i--) 
            heapify(counts, n, i); 
  
        // One by one extract an element from heap 
        for (int i=n-1; i>=0; i--) 
        { 
            // Move current root to end 
            DataCount<E> temp = counts[0]; 
            counts[0] = counts[i]; 
            counts[i] = temp; 
  
            // call max heapify on the reduced heap 
            heapify(counts, i, 0); 
        } 
    } 
  
    // To heapify a subtree rooted with node i which is 
    // an index in arr[]. n is size of heap 
    static <E extends Comparable<? super E>> void heapify(DataCount<E>[] arr, int n, int i) 
    { 
        int largest = i; // Initialize largest as root 
        int l = 2*i + 1; // left = 2*i + 1 
        int r = 2*i + 2; // right = 2*i + 2 
  
        // If left child is larger than root 
        if (l < n && arr[l].count > arr[largest].count) 
            largest = l; 
  
        // If right child is larger than largest so far 
        if (r < n && arr[r].count > arr[largest].count) 
            largest = r; 
  
        // If largest is not root 
        if (largest != i) 
        { 
            DataCount<E> swap = arr[i]; 
            arr[i] = arr[largest]; 
            arr[largest] = swap; 
  
            // Recursively heapify the affected sub-tree 
            heapify(arr, n, largest); 
        } 
    }
   
   
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Incorrect arguement number. Usage: [ -b | -a | -h ] [ -frequency | -num_unique ] <filename>");
            System.exit(1);
        }
        
        if (!args[0].equals("-b") && !args[0].equals("-a") && !args[0].equals("-h")) {
            System.err.println("Error in first argument: " + args[0] + "   ------ Use [ -b | -a | -h ] ");
            System.exit(1);
        }
        
        if (!args[1].equals("-frequency") && !args[1].equals("-num_unique")) {
            System.err.println("Error in second argument: " + args[1] + "   ----- Use [ -frequency | -num_unique ]");
            System.exit(1);
        }
         
        File file = new File(args[2]);
        if (!file.exists())
            System.exit(1);
        
        countWords(args[0], args[1], args[2]);
    }
}
