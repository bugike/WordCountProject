// Jiajun Zhang

public class HashTable implements DataCounter<String> {
       
   int wordCount;
   int uniqueWordNum = 0;
   int tableSize = 101;
   Node[] nodes = null;
    
   public class Node { 
   
      public String data;
		public int count;
		
		public Node() {}
      
		public Node(String data) {
	        this.data = data;
	        this.count =  1;
	   }
       
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}		
	}
   
	public HashTable() {
		nodes = buildTable(tableSize);
	}
    
    /** {@inheritDoc} */
  
   public DataCount<String>[] getCounts() {
    @SuppressWarnings("unchecked")
      DataCount<String>[] counts = new DataCount[uniqueWordNum];
      
    	int j = 0;
    	for (int i = 0; i < nodes.length; i++){
    		if(nodes[i].data.equals("")){
    			;
    		} 
         
         else {
    			counts[j] = new DataCount<String>(nodes[i].data, nodes[i].count);
    			j++;
    		}
    	}
        return counts;
    }

   

    /** {@inheritDoc} */
    public int getSize() {
        // TODO Auto-generated method stub
        return uniqueWordNum;
    }

    /** {@inheritDoc} */
   public void incCount(String data) {
    	wordCount++;
    	insertHash(data);
    	if (uniqueWordNum >= ((nodes.length)*0.5)) { 
    		nodes = reHash(); // reHash if the table is half filled
    	}
      return;
    }
   
   // convert string to value
   public int stringToValue(String s){
      int hashVal = 0;
      for (int i = 0; i < s.length(); i++)
         hashVal = (37 * hashVal + s.charAt(i)) % tableSize;
      
      if (hashVal < 0)
         hashVal += tableSize;
      
      return hashVal;
   }
   
   // insert data into hashtable
   public void insertHash(String key){
      int hashVal = stringToValue(key);
      
      // check if the spot is empty, ture-->setData, false-->compare      
      while (!nodes[hashVal].data.equals("")){
         if (nodes[hashVal].data.equals(key)) {
            nodes[hashVal].count++;
            return;
         }
         hashVal = (hashVal + 1) % tableSize;  // move to next spot
      }
      
      nodes[hashVal].setData(key);
      uniqueWordNum++;
      return;
   }
   
   // reHash
   public Node[] reHash(){
      tableSize = nextPrime(nodes.length * 2);  // double the size of table
      Node[] temp = buildTable(tableSize);
      uniqueWordNum = 0;
      
      for (int i = 0; i < nodes.length; i++){
         if (!nodes[i].data.equals("")){
            int hashVal = stringToValue(nodes[i].data);
            
            while (!temp[hashVal].data.equals("")) {
    				hashVal = (hashVal + 1) % tableSize;
    			}
            
    			temp[hashVal].setData(nodes[i].data);
    			temp[hashVal].setCount(nodes[i].count);
    			uniqueWordNum++;
         }
      }
      
      return temp;
   }            
    
   // build an empty table    
   public Node[] buildTable(int n) {   
    	Node[] nodes = new Node[n];
    	for (int i=0; i < nodes.length; i++) {
    		Node node = new Node("");
    		nodes[i] = node;
		}
    	return nodes;
   }
    
    // next prime number
   public int nextPrime(int n){
      if(n % 2 == 0)
         n++;

      for( ; !isPrime(n); n += 2)
         ;
      
      return n;
   }
    
    // check prime number
   public boolean isPrime(int n) {
      for(int i=2;i<n;i++) {
         if(n%i==0)
            return false;
      }
      return true;
   }

/*----------------------------------------------------------------------*/
/*   
   public void printHash(Node[] nodes){
      for (int i = 0; i < nodes.length; i++)
         System.out.print(nodes[i].data + ", ");

      System.out.println();
   }
      
   public static void main(String[] args) {
      HashTable test = new HashTable();
      test.incCount("You");
      test.incCount("are"); 
      test.incCount("fine"); 
      test.incCount("I"); 
      test.incCount("love"); 
      test.incCount("You"); 
      test.incCount("We"); 
      test.incCount("are"); 
      test.incCount("family");
      
      test.printHash(test.nodes);
   }      
*/
}
