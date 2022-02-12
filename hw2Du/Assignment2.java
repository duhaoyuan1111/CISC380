/**
 * CISC 380
 *
 * Starter code for Assignment 2
 *
 * @author Keghan Halloran, Haoyuan Du Due Date: 10/11/20
 */

public class Assignment2 {

    /**
     * STARTER CODE FOR PROBLEM 2: Maximum Subarray
     *
     * @param array the array where the maximum subarray will be found
     * @return the sum of the subarray with the maximum sum
     */
    public static int maxSubArray(int[] array) {
        if (array.length == 0) {
            return 0;
        } else if (array == null) {
            return 0;
        } else {
            return maxSubArrayRecursive(array, 0, array.length - 1);
        }
    }

    /**
     * STARTER CODE FOR PROBLEM 2: Maximum Subarray
     *
     * @param array the array where the maximum subarray will be found
     * @param start the starting index where the recursive method will check
     * @param end   the ending index where the recursive method will check
     * @return the sum of the subarray with the maximum sum. Note: this method will
     *         return 0 if the length of the array is 0 and will be negative if the
     *         array contains only negative numbers
     */
    private static int maxSubArrayRecursive(int[] array, int start, int end) {
        // HINT: you will need to add a base case and change the return value
        // HINT: you will need to use maxLeft and maxRight in your solution
        if (end - start == 0) {
            return array[start]; // base case: only have one element
        } else {
            int mid;
            int maxLeft;
            int maxRight;
            int temp = 0;
            int countLeftSoFar = 0;
            int countRightSoFar = 0;
            int countCross;
            int ans;
            mid = (start + end) / 2;

            maxLeft = maxSubArrayRecursive(array, start, mid); // recursive calls on left side
            maxRight = maxSubArrayRecursive(array, mid + 1, end); // recursive calls on right side
            for (int i = mid; i >= start; i--) { // maximum cross
                if (i == mid) { // execute only once
                    countLeftSoFar = array[i];
                    temp = array[i];
                } else { // keep tracking the left side from middle
                    temp += array[i];
                    if (temp > countLeftSoFar) {
                        countLeftSoFar = temp;
                    }
                }
            }
            for (int i = mid + 1; i <= end; i++) {
                if (i == mid + 1) {
                    countRightSoFar = array[i];
                    temp = array[i];
                } else { // keep tracking the right side from middle
                    temp += array[i];
                    if (temp > countRightSoFar) {
                        countRightSoFar = temp;
                    }
                }
            }
            countCross = countLeftSoFar + countRightSoFar; // maximum cross value
            if (maxLeft > maxRight) { // compare left, right and cross
                ans = maxLeft;
            } else {
                ans = maxRight;
            }
            if (ans < countCross) {
                ans = countCross;
            }
            return ans; // return the biggest one

        }

    }

    /**
     * STARTER CODE FOR PROBLEM 3: Dominating Entry
     *
     * @param array the array which will be evaluated for containing a dominant
     *              entry
     * @return the dominating entry, will return null if there is no dominating
     *         entry (return type Integer)
     */
    public static Integer dominant(int[] array) {
        // HINT: you will need to create a private recursive method (similar to
        // maxSubArray())
        Integer ans;
        Integer low;
        Integer high;
        if (array.length == 0) {
            return null;
        } else {
            low = 0;
            high = array.length - 1;
            ans = DominantEntry(array, low, high);
            return ans;
        }
    }

    /**
     * private function for dominant
     *
     * @param array the array which will be evaluated for containing a dominant
     *              entry
     * @param low   the start index
     * @param high  the end index
     * @return the dominating entry, will return null if there is no dominating
     *         entry (return type Integer)
     */
    private static Integer DominantEntry(int[] array, int low, int high) {
        if (high - low == 0) {// if only have one element, return this element
            return array[low];
        } else if (high - low == 1) {// if have two, return left one
            return array[low];
        } else {
            Integer countLeft = 0;
            Integer countRight = 0;
            Integer left;
            Integer right;
            left = DominantEntry(array, low, (low + high) / 2);// recursive calls
            right = DominantEntry(array, (low + high) / 2 + 1, high);
            for (int i = low; i <= high; i++) {// loop in current array
                if (left != null) {
                    if (left == array[i]) {
                        countLeft++;// count left occurrences
                    }
                }
                if (right != null) {
                    if (right == array[i]) {
                        countRight++;// count right occurrences
                    }
                }
            }
            if (countLeft > (high - low + 1) / 2) {// return the dominant if occurrences > half array
                return left;
            } else if (countRight > (high - low + 1) / 2) {
                return right;
            } else {
                return null;// if no dominant, return null
            }
        }
    }

    /**
     * STARTER CODE FOR PROBLEM 5a: Brute Force Hill
     *
     * Implements a brute force approach to finding a hill in an array
     *
     * @param arr - an array of integers
     * @return - the index of a hill within the array
     */
    public static int bruteHill(int[] arr) {
        /*
         * Firstly, we check two cases which are
         * "if the first element in the array is greater than or equal to the second element" and
         * "if the last element is greater than or equal to the second to last element".
         * If either case is true, then we can return either the first element's index
         * or the last element's index as a hill. If the array only has one
         * element, then we return the index of that element.
         *
         * Secondly, loop through the array from the second element to the second to last
         * element. For each element, compare it with the element before it and the
         * element after it. If some element is greater than or equal to its
         * neighbours, return that element's index.
         * ------------------------------------------------------------------------
         * Big-O analysis: Firstly, we have two IF statements which are constants.
         * Secondly, we have a for loop which is almost the length of the array. Assume
         * the array has a length of N. Each loop will execute an IF statement which
         * takes constant time. Thus, the for loop takes n time. Therefore, the total
         * time T(n) = c+n. That's a theta(n).
         *
         * The result in HillDriver also proved this. In OneHill examples, size from
         * 1024 to 2048, the BruteHill used from 426.1ns to 1142.9ns. But
         * DividedAndConquerHill used from 107ns to 168ns.
         */
        if (arr.length == 1) {
            return 0;
        }
        // first or final
        if (arr[0] >= arr[1]) {
            return 0;
        } else if (arr[arr.length - 1] >= arr[arr.length - 2]) {
            return arr.length - 1;
        }
        // middle
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] >= arr[i - 1] && arr[i] >= arr[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * STARTER CODE FOR PROBLEM 5b: Divide and Conquer Hill
     *
     * Implements a divide and conquer approach to finding a hill in an array
     *
     * @param arr - an array of integers
     * @return - the index of a hill within the array
     */
    public static int divideAndConquerHill(int[] arr) {
        /* Please see the algorithm below. This is a wrapper function. */
        int ans = -1;
        if (arr.length == 0) {
            return -1;
        } else {
            ans = DAndCHill(arr, 0, arr.length - 1);
        }
        return ans;
    }

    /**
     * Private method of divideAndConquerHill. Implements a divide and conquer
     * approach to finding a hill in an array
     *
     * @param arr  - an array of integers
     * @param low  - start index
     * @param high - end index
     * @return - the index of a hill within the array
     */
    private static int DAndCHill(int[] arr, int low, int high) {
        /*
         * Firstly, we have our base case which takes constant time (comparison and IF
         * statement).
         *
         * Secondly, in the recursion part, each time the recursive call will take half
         * of the current array. Also, each level we only trigger one of the recursive
         * calls because they're in IF and ELSE. So the recurrence relation will be
         * T(n)=T(n/2)+c. T(n/2) means the half of the current array in each level.
         * ---------------------------------------------------------------------------
         * Solve the recurrence: T(n)=T(n/2)+c T(n/2)=T(n/4)+2c ... T(1)=T(2)+logn*c
         *
         * Thus, the running time is theta(logn).
         *
         * The result in HillDriver also proved this. In OneHill examples, size from
         * 1024 to 2048, the BruteHill used from 426.1ns to 1142.9ns. But
         * DividedAndConquerHill used from 107ns to 168ns.
         */
        int left;
        int right;
        if (high - low == 0) { // base case
            return low;
        } else if (high - low == 1) {
            if (arr[low] >= arr[high]) {
                return low;
            } else {
                return high;
            }
        } else { // recursion calls
            if (arr[(high + low) / 2] >= arr[(high + low) / 2 + 1]) {
                left = DAndCHill(arr, low, (low + high) / 2);
                return left;
            } else {
                right = DAndCHill(arr, (low + high) / 2 + 1, high);
                return right;
            }
        }
    }

}