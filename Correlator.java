// Jiajun Zhang

public class Correlator{
   static WordCount test = new WordCount();
   static long BSTTime1,BSTTime2,AVLTime1,AVLTime2,HashTime1,HashTime2;
	static String dataStruct, file1, file2;
   
   public static void main(String[] args) {
      if (args.length != 3) {
         System.err.println("Incorrect number of arguments");
         System.err.println("Usage:	java	Correlator [ -b | -a | -h ] <filename1> <filename2>");
         System.exit(1);
      }
      
      dataStruct = args[0].toString();
    	file1 = args[1].toString();
    	file2 = args[2].toString();
      
      if (dataStruct.equals("-b")){
			getTime(args);
			System.out.println(file1 + " took " + BSTTime1 + " milliseconds to run with BST");
			System.out.println(file2 + " took " + BSTTime2 + " milliseconds to run with BST");
		}
      
		else if(dataStruct.equals("-a")){
			getTime(args);
			System.out.println(file1 + " took " + AVLTime1 + " milliseconds to run with AVL");
			System.out.println(file2 + " took " + AVLTime2 + " milliseconds to run with AVL");
		}
      
		else if(dataStruct.equals("-h")){
			getTime(args);
			System.out.println(file1 + " took " + HashTime1 + " milliseconds to run with Hash Table");
			System.out.println(file2 + " took " + HashTime2 + " milliseconds to run with Hash Table");			
		} 
      
      else {
			System.err.println("Format Error. Usage: java Correlator [ -b | -a | -h ] <filename1> <filename2>");
         System.exit(1);
		}
	}
   
   @SuppressWarnings("static-access")
	public static void getTime(String[] args){
		long startTime, endTime, runTime;
      
      args[0] = dataStruct;
		args[1] = "-frequency";
		args[2] = file1;	
      
		startTime = System.currentTimeMillis();
		test.main(args);
		endTime = System.currentTimeMillis();
		runTime = (endTime - startTime);	
      
		if (dataStruct.equals("-b")){
			BSTTime1 = runTime;
		} else if(dataStruct.equals("-a")){
			AVLTime1 = runTime;
		} else {
			HashTime1 = runTime;
		}
      				
		args[2] = file2;   // get the file2's runtime.
      
		startTime = System.currentTimeMillis();
		test.main(args);
		endTime = System.currentTimeMillis();
		runTime = (endTime - startTime);
      
		if (dataStruct.equals("-b")){
			BSTTime2 = runTime;
		} else if(dataStruct.equals("-a")){
			AVLTime2 = runTime;
		} else {
			HashTime2 = runTime;
		}
	}
}