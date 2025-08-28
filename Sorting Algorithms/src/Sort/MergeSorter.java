package Sort;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Christian Salazar
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "Merge Sort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		if (pts.length <= 1) {		//base case means array is already sorted
			return;
		}
		
		int mid = pts.length / 2;		//mid array index
		
		Point[] leftArr = new Point[mid]; 					//Create temp Left array
		Point[] rightArr = new Point[pts.length - mid];	//Create temp Right array

		System.arraycopy(pts, 0, leftArr, 0, mid);               	  //Left Partition
		System.arraycopy(pts, mid, rightArr, 0, pts.length - mid);  //Right partition
		
		mergeSortRec(leftArr);
		mergeSortRec(rightArr);
		
		merge(pts, leftArr, rightArr);

	}

	
	// Other private methods if needed ...
	private void merge(Point[] pts, Point[] leftArr, Point[] rightArr) {
		
		int i = 0, j = 0, k = 0; 	//Define iterators
		
		while (i < leftArr.length && j < rightArr.length) { //Only go up to arr limit of each.
			
			if (leftArr[i].compareTo(rightArr[j]) < 0) { 	//if leftArr[i] is less than rightArr[j]
				pts[k] = leftArr[i];
				i++;
				
			} else { //if rightArr is less than leftArr
				pts[k] = rightArr[j];
				j++;
			}
			
			k++;
		}
		
		//Grab remaining elements from left half
		while (i < leftArr.length) {
			pts[k] = leftArr[i];
			i++;
			k++;
		}
		
		//Grab remaining elements from right half
		while (j < rightArr.length) {
			pts[k] = rightArr[j];
			j++;
			k++;
		}
	}
}
