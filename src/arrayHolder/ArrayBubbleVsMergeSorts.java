/**
 * @author Larry Shannon
 *  * CSC 201 section 010N
 * This program uses modular structure to compare two sort algorithms.
 * The global variables bCount and mCount track the number of comparisons
 * done in each of the sorts. bCount for the bubble sort, 
 * and mCount for the merge sort.
 * Note I have also included some commented out methods. 
 * The methods show how to sort parallel arrays using the bubble sort.
 * I use the bubble sort, even though it is not the most efficient sort.
 * This is because it is easy to keep track of multiple arrays in a bubble sort.
 * It becomes much more complicated when using the merge sort.
 * Also note that the parallel bubble sort moves the swapping processes 
 * out to modules that are array type specific. 
 * This helps reduce the logic complexity in the bubble sort module.
 * 
 * x = y^a
 * x = 
 * BEGIN myPower(REAL base, INTEGER power) RETURN result
 *   REAL result = 1;
 *   INTEGER powerCount = power
 *   WHILE powerCount > 0
 *     result = result * base
 *     powerCount = powerCount - 1
 *   ENDWHILE
 *   RETURN result
 * END
 * if y is an integer
 * x^y = x^(y/2) * x^(y/2) if y is even
 * x^y = x^(y/2) * x^(y/2) * x if y is odd
 *DESCRIPTION - This method finds the value of a number raised to an integer power by recursively multiplying the number. 
 *ALGORITM
 * BEGIN myRecPower(REAL base, INTEGER power) RETURN result
 *   REAL result = 1;
 *   IF power equals 0
 *     RETURN result
 *   ENDIF
 *   IF power equals 1
 *     RETURN base
 *   ENDIF
 *   result = myRecPower(base, power/2)
 *   IF power%2 = 0
 *     RETURN result * result
 *   ELSE
 *     RETURN result * result * base
 *   ENDIF
 * END
 * What do we need to do if power is negative?
 *   
 */
package arrayHolder;

import java.util.Scanner;


public class ArrayBubbleVsMergeSorts
{
	private static Scanner keyboard = new Scanner(System.in);
	private static int mCount = 0;
	private static int bCount = 0;

	/**
	 * @param args
	 * precondition
	 * postcondition
	 */
	public static void main(String[] args)
	{
		String yesNo = "";
		double[] myArrayI = null;
		double[] myArrayII = null;
		do
		{
			myArrayI = buildDoubleArray();
			myArrayII = myArrayI.clone();//duplicateDoubleArray(myArrayI);
			//			printArray(myArrayI);
			mCount = 0;
			mergeSortArray(myArrayII);
			System.out.printf("It took %d compares to sort with the merge sort.\n", mCount);
			//			printArray(myArrayII);
			bCount = 0;
			bubbleSortArray(myArrayI);
			System.out.printf("It took %d compares to sort with the bubble sort.\n", bCount);
			//			printArray(myArrayI);
			yesNo = repeatProcessQuery();
		} while (yesNo.equals("yes"));
	}

	/**
	 * 
	 * @param pMyArray
	 * Bubble sort module 
	 * @Arguments pMyArray, reference to array to sort
	 * Since the reference to the array is used, any changes to the array 
	 * are made in the original array.
	 * bCount is a global counter variable.
	 * bCount is incremented each time a comparison is made.
	 */
	private static void bubbleSortArray(double[] pMyArray)
	{
		int arraySize = pMyArray.length;
		double myTemp = 0;
		//bCount++;
		for (int pass = arraySize - 1; pass > 0; pass--, bCount++)
		{
			//bCount++;
			for (int index = 0, indexI = 1; index < pass; index++, indexI++, bCount++)
			{
				bCount++;
				if (pMyArray[index] > pMyArray[indexI])
				{
					// swapElements
					myTemp = pMyArray[index];
					pMyArray[index] = pMyArray[indexI];
					pMyArray[indexI] = myTemp;
				}
			}
		}

	}

	/**
	 * 
	 * @param pMyArray
	 * Recursive merge sort module 
	 * @Arguments pMyArray, reference to array to sort
	 * Since the reference to the array is used, any changes to the array 
	 * are made in the original array.
	 * mCount is a global counter variable.
	 * mCount is incremented each time a comparison is made.
	 */
	private static void mergeSortArray(double[] pMyArray)
	{
		int arraySize = pMyArray.length;
		double[] myTempArrayI = null;
		double[] myTempArrayII = null;
		int index = 0;
		int indexI = 0;
		int indexII = 0;
		int splitSizeI = arraySize / 2;
		int splitSizeII = arraySize - splitSizeI;

		// If the array has four or more elements,
		// we split the array into two smaller arrays of at least 2 elements each.
		// Otherwise we use the bubble sort,
		// which uses, at most, 11 comparisons to sort 3 numbers.
		// Note the comparison cost includes the overhead cost of traversing the array.
		//		mCount++;
		if (splitSizeI > 1)
		{
			myTempArrayI = new double[splitSizeI];
			myTempArrayII = new double[splitSizeII];
			//			mCount++;
			for (indexI = 0; indexI < splitSizeI; indexI++, index++, mCount++)
				myTempArrayI[indexI] = pMyArray[index];
			//			mCount++;
			for (indexII = 0; indexII < splitSizeII; indexII++, index++, mCount++)
				myTempArrayII[indexII] = pMyArray[index];
			mergeSortArray(myTempArrayI);
			mergeSortArray(myTempArrayII);
			//			mCount++;
			for (index = 0, indexI = 0, indexII = 0; index < arraySize; index++, mCount++)
			{
				//				mCount++;
				if (indexI < splitSizeI)
				{
					//					mCount++;
					if (indexII < splitSizeII)
					{
						mCount++;
						if (myTempArrayI[indexI] < myTempArrayII[indexII])
						{
							pMyArray[index] = myTempArrayI[indexI];
							indexI++;
						}
						else
						{
							pMyArray[index] = myTempArrayII[indexII];
							indexII++;
						}
					}
					else
					{
						pMyArray[index] = myTempArrayI[indexI];
						indexI++;
					}
				}
				else
				{
					//					mCount++;
					if (indexII < splitSizeII)
					{
						pMyArray[index] = myTempArrayII[indexII];
						indexII++;
					}
				}
			}
		}
		else //executed if the array is 3 or smaller
		{
			bCount = 0;
			bubbleSortArray(pMyArray);
			mCount += bCount;
		}

	}

	private static double[] buildDoubleArray()
	{
		int arraySize;
		double[] newArray;
		System.out.println("Please enter the size of the array you want.");
		do
		{
			System.out.println("The size must be greater than 0.");
			arraySize = keyboard.nextInt();
		} while (arraySize < 1);
		newArray = new double[arraySize];
		fillArray(newArray);

		return newArray;
	}

	private static void fillArray(double[] paraArray)
	{
		int incomingSize = paraArray.length;
		for (int index = 0; index < incomingSize; index++)
		{
			paraArray[index] = Math.random() * 101;
			if (paraArray[index] > 100.0) paraArray[index] = 100.0;
		}

	}

//	private static void printArray(double[] paraArray)
//	{
//		int incomingSize = paraArray.length;
//
//		System.out.println("Your array contains the following numbers.");
//		for (int index = 0; index < incomingSize - 1; index++)
//		{
//			System.out.printf("%10.2f, ", paraArray[index]);
//		}
//		System.out.printf("%10.2f\n", paraArray[incomingSize - 1]);
//	}

	private static String repeatProcessQuery()
	{
		String userResponse = "";
		System.out.println("Would you like to do this again?");
		do
		{
			System.out.println("Please enter \"yes\" or \"no\"");
			userResponse = keyboard.next();
		} while (!userResponse.equals("yes") && !userResponse.equals("no"));

		return userResponse;
	}

}
