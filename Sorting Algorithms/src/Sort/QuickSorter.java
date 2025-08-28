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
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		algorithm = "Quicksort";
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override
	public void sort() {
		
		if (points.length > 1) {
			quickSortRec(0, points.length - 1);
		}
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last) {
		if (first < last) {
            int pivotIndex = partition(first, last);  //Define index for partition
            quickSortRec(first, pivotIndex - 1);  //Left sort
            quickSortRec(pivotIndex + 1, last);   //Right sort
        }
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last) {
		int mid = first + (last - first) / 2;
		
		//I'll do the median-of-three approach for pivot (plus more uses of swap)
		if (points[mid].compareTo(points[first]) < 0) {
			swap(first, mid);
		}
	    if (points[last].compareTo(points[first]) < 0) {
	    	swap(first, last);
	    }
	    if (points[last].compareTo(points[mid]) < 0) {
	    	swap(mid, last);
	    }
	    
	    swap(mid, last);
	    Point pivot = points[last]; // Pivot is now at last index
	    
	    int i = first - 1; // Index for smaller elements
	    
	    for (int j = first; j < last; ++j) {
	    	if (points[j].compareTo(pivot) < 0) {
	    		i++;
	    		swap(i, j);
	    	}
	    }
	    
	    swap(i + 1, last);
	    return i + 1;
	}	
	// Other private methods if needed ...
}
