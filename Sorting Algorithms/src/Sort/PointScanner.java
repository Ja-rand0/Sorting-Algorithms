package Sort;

import java.io.File;

/**
 * 
 * @author Christian Salazar
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	protected long scanTime; 	       	  // execution time in nanoseconds. 
	
	private String outputFileName = "holymoly.txt";
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("ERROR: pts cannot be null or be empty.");
		}
		
		this.sortingAlgorithm = algo;
		this.points = new Point[pts.length];
		
		for (int i = 0; i < pts.length; ++i) { 	//using a for-loop instead of System.arrayCopy() this time for a shallow copy.
			this.points[i] = pts[i];
		}
	}
	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File file = new File(inputFileName);					//Let's make a new file
		this.sortingAlgorithm = algo;							//grab our algorithm type
		
		//Check for a FileNotFoundException
		if (!file.exists() || !file.isFile()) 
		{ 						
			throw new FileNotFoundException("ERROR: No File Found.");
		}//Done 
		
		Scanner scnr = new Scanner(file);		//Since "file" is valid, we'll create a scanner
		scnr.useDelimiter("[^\\-\\d]+");		//This fixes my issue of not being able to parse a file correctly.
		
		//Check for an InputMismatchException
		//Step 1
		int numInts = 0;												
		while (scnr.hasNextInt()) {												
			scnr.nextInt();						//reading through the list
			numInts++;							//counting current element.
		}
		
		scnr.close();							//Finished reading file.
		
		//Step 2
		if (numInts % 2 == 1) 					//Odd num check
		{
			throw new InputMismatchException("ERROR: Odd value of integers in file.");
		}
		//Done.
		else 								//Even num check (numInts % 2 == 0)
		{
		//Now we can really start	
		Scanner grab = new Scanner(file);	//Bring Scanner out of retirement
		grab.useDelimiter("[^\\-\\d]+");
		points = new Point[numInts / 2];	//2 integers make 1 point.
		
		//Populate our array.
			for (int i = 0; i < this.points.length; ++i) {
				points[i] = new Point(grab.nextInt(), grab.nextInt());
			}
			grab.close();
		}
	}
			
		

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		// TODO  
		AbstractSorter aSorter; 
	
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		switch(sortingAlgorithm) {
		case SelectionSort:
			aSorter = new SelectionSorter(points);
			break;
		case InsertionSort:
			aSorter = new InsertionSorter(points);
			break;
		case MergeSort:
			aSorter = new MergeSorter(points);
			break;
		case QuickSort:
			aSorter = new QuickSorter(points);
			break;
		default: 
			throw new IllegalArgumentException("Invalid sortingAlgorithm type.");
		}
		//     a) call setComparator() with an argument 0 or 1. 
//		aSorter.setComparator(0);	//compare by x-coordinate
		//     b) call sort(). 		
//		aSorter.sort();
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
//		Point median = medianCoordinatePoint;
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
				
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		
		long startTime = System.nanoTime();
		
		aSorter.setComparator(0);					//Sort X-coordinate and time round 1 of sort
		aSorter.sort();			
		int medianX = aSorter.getMedian().getX();
		
		long roundOneTime = System.nanoTime() - startTime;					//Isolate Round 1 Time
		
		aSorter.setComparator(1);											//Sort Y-coordinate and time round 2 of sort
		aSorter.sort();
		int medianY = aSorter.getMedian().getY();
		
		long roundTwoTime = System.nanoTime() - startTime - roundOneTime;	//Isolate Round 2 Time
		
		this.scanTime = roundOneTime + roundTwoTime;						//Sum of total time
		
		this.medianCoordinatePoint = new Point(medianX, medianY);			//Capture the median after sorting both rounds.		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		return String.format("%-15s %5d %10d", sortingAlgorithm, points.length, scanTime);
		
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + this.medianCoordinatePoint.getX() + ", " + this.medianCoordinatePoint.getY() + ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File file = new File(outputFileName);
		
		if (file.exists() && !file.canWrite()) {
			throw new FileNotFoundException("ERROR: Either File exists already or file can not be written.");
		} else {
			PrintWriter print = new PrintWriter(outputFileName);		
			print.write(toString());
			print.close();
		}
	}	
}
