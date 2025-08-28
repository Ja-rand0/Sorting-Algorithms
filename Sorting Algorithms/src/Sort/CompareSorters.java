package Sort;

/**
 *  
 * @author Christian Salazar
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {

        Scanner scnr = new Scanner(System.in);
        int trialNumber = 1;
        
        System.out.println("keys: 1 (random integers) | 2 (file input) 3 | (exit)");
        
        while (true) {
            System.out.print("\nTrial " + trialNumber + ": ");
            String input = scnr.next();
            
            if (input.equals("3")) {
                System.out.println("Exiting program.");
                break;
            }

            PointScanner[] scanners = new PointScanner[4];
            Algorithm[] algorithms = {Algorithm.SelectionSort, Algorithm.InsertionSort, Algorithm.MergeSort, Algorithm.QuickSort};
            boolean isFileInput = false;
            
            if (input.equals("1")) {
                System.out.println("Enter the number of random points:");
                int numPoints = scnr.nextInt();
                Random rand = new Random();
                Point[] points = generateRandomPoints(numPoints, rand);
                
                for (int i = 0; i < 4; i++) {
                    scanners[i] = new PointScanner(points, algorithms[i]);
                }
                
            } else if (input.equals("2")) {
                isFileInput = true;
                System.out.println("Points from a file\nEnter the file name:");
                String fileName = scnr.next();
                
                try {
                    for (int i = 0; i < 4; i++) {
                        scanners[i] = new PointScanner(fileName, algorithms[i]);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: File not found. Please try again.");
                    continue;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            } else {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                continue;
            }
            
            System.out.println("\nalgorithm       size    time (ns)");
            System.out.println("--------------------------------");
            
            for (PointScanner scanner : scanners) {
                scanner.scan();
                System.out.println(scanner.stats());
                if (isFileInput) {
                    scanner.writeMCPToFile();
                }
            }
            
            System.out.println("--------------------------------");
            trialNumber++;
        }
        
        scnr.close();
    }
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] ? [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1) {
			throw new IllegalArgumentException("ERROR: numPts cannot be 0.");
		}
		
		if (rand == null) {
	        throw new IllegalArgumentException("ERROR: rand cannot be null.");
	    }
		
		Point[] randPts = new Point[numPts];
		for (int i = 0; i < numPts; ++i) {
			randPts[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}
		
		return randPts; 
	}
	
}
