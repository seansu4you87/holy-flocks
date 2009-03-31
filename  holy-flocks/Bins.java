import java.util.*;
import java.io.*;


public class Bins
{
    private static PriorityQueue<Disk> myPQ;
    private static List<Integer> myData;
    
    private static final String[] METHOD_DESCRIPTIONS = {"worst-fit method", "worst-fit decreasing method", 
    	"worst-fit increasing method", "worst-fit random shuffle method", "worst-fit Weiping method"};
    private static final int INF = Integer.MAX_VALUE;
    
    public Bins(String args){
    	myPQ = new PriorityQueue<Disk>();
    	
    	try{
	        Scanner input = new Scanner(new File(args));
	        myData = readData(input);
	    }
	    catch (FileNotFoundException e){
	        System.err.println("Could not open file: " + args);
	    }
    }

/**
	 * Reads list of integer data from the given input.
	 * 
	 * @param input tied to an input source that contains space separated numbers
	 * @return list of the numbers in the order they were read
	 */
	public List<Integer> readData (Scanner input)
	{
	    List<Integer> results = new ArrayList<Integer>();
	
	    while (input.hasNext())
	    {
	        results.add(input.nextInt());
	    }
	
	    return results;
	}


	/**
 * The main program.
 */
public static void main (String args[])
{
    Bins b = new Bins(args[0]);
    
    int total = getDataSize();
    System.out.println("total size = " + total / 1000000.0 + "GB");
    
    runMethods();
}

	public static int getDataSize(){
		int total = 0;
		for(Integer size : myData){
			total += size;
		}
		return total;
	}

    
    public static void runMethods() {
		int[] numberDisks = new int[METHOD_DESCRIPTIONS.length];
		
		numberDisks[0] = worstFitMethod(METHOD_DESCRIPTIONS[0]);
		clearQueue();
		
		numberDisks[1] = worstFitDecreasingMethod();
		clearQueue();
		
		numberDisks[2] = worstFitIncreasingMethod();
		clearQueue();
		
		numberDisks[3] = worstFitRandomMethod();	
		clearQueue();
		
		numberDisks[4] = weipingMethod();
		clearQueue();
		
		determineOptimalMethod(numberDisks);
	}
	
	public static int worstFitMethod(String description) {	
		fillQueue();	
		printResults(new PriorityQueue<Disk>(myPQ), description);
		return myPQ.size();
	}


	public static int weipingMethod(){
		fillQueueWeiping();
		printResults(new PriorityQueue<Disk>(myPQ), METHOD_DESCRIPTIONS[4]);
		return myPQ.size();
	}


	public static int worstFitDecreasingMethod() {
		Collections.sort(myData, Collections.reverseOrder());
		return worstFitMethod(METHOD_DESCRIPTIONS[1]);
	}


	public static int worstFitIncreasingMethod() {
		Collections.sort(myData);
		return worstFitMethod(METHOD_DESCRIPTIONS[2]);
	}


	public static int worstFitRandomMethod() {
		Collections.shuffle(myData, new Random(0));
		return worstFitMethod(METHOD_DESCRIPTIONS[3]);
	}


	public static void determineOptimalMethod(int[] numberDisks){
		int methodNumber = 0;
		int disks = INF;
		for(int k=0; k<numberDisks.length; k++){
			if(numberDisks[k]<disks){
				disks = numberDisks[k];
				methodNumber = k;
			}
		}
		System.out.println("The most efficient algorithm is the " + METHOD_DESCRIPTIONS[methodNumber]);
		System.out.println("This method used " + numberDisks[methodNumber] + " disks to store all data");
	}


	public static void printResults(PriorityQueue<Disk> pq, String description) {
		System.out.println();
		System.out.println(description);
		System.out.println("number of disks used: " + pq.size());
		while (!pq.isEmpty())
		{
		    System.out.println(pq.poll());
		}
		System.out.println();
	}
	
	public static void fillQueueWeiping() {
		List<Disk> diskArray = new ArrayList<Disk>();
		diskArray.add(new Disk(0));
				
		int diskId = 1;
		boolean packed=false;
		for (Integer size : myData)
		{
		    for(Disk d : diskArray){
			    if (d.freeSpace() >= size)
			    {
			        d.add(size);
			        packed = true;
			        break;
			    }
			    packed = false;
		    }
		    if(packed==false){
		    	Disk d2 = new Disk(diskId);
		    	diskId++;
		    	d2.add(size);
		    	diskArray.add(d2);
		    }
		}
		
		for(Disk d : diskArray){
			myPQ.add(d);
		}
	}
	
	public static void fillQueue() {
		myPQ.add(new Disk(0));
	
		int diskId = 1;
		for (Integer size : myData)
		{
		    Disk d = myPQ.peek();
		    if (d.freeSpace() >= size)
		    {
		        myPQ.poll();
		        d.add(size);
		        myPQ.add(d);
		    }
		    else
		    {
		        Disk d2 = new Disk(diskId);
		        diskId++;
		        d2.add(size);
		        myPQ.add(d2);
		    }
		}
	}
	
	public static void clearQueue(){
		myPQ.clear();
	}
}
