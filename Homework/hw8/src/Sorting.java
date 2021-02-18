import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Hye Lim Kim
 * @version 1.0
 * @userid hkim946
 * @GTID 903197983
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null so the array cannot be sorted.");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            T temp = arr[i];

            while ((j > 0) && comparator.compare(arr[j - 1], temp) > 0) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array can't be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Comparator can't be null.");
        }
        for (int i = 0; i < arr.length; i++) {
            int numOfSwaps = 0;
            for (int j = 1; j < (arr.length - i); j++) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                    swap(arr, j, j - 1);
                    numOfSwaps++;
                }
            }
            if (numOfSwaps == 0) {
                break;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null so the array cannot be sorted.");
        }
        mergeSort(arr, 0, arr.length, comparator);
    }

    /**
     * mergeSort helper method
     * Recursive call to split the array for merge sort
     *
     * @param arr data type to sort
     * @param left left index
     * @param right right index
     * @param comparator comparator to use
     * @param <T> data type to sort
     */
    private static <T> void mergeSort(T[] arr, int left, int right, Comparator<T> comparator) {
        if (right - left <= 1) {
            return;
        }
        int mid = (right + left) / 2;
        mergeSort(arr, left, mid, comparator);
        mergeSort(arr, mid, right, comparator);
        merge(arr, left, mid, right, comparator);
    }

    /**
     * mergeSort helper method
     * Merges and swaps data
     *
     * @param arr data type to sort
     * @param left left index
     * @param mid mid index
     * @param right right index
     * @param comparator comparator to use
     * @param <T> data type to sort
     */
    private static <T> void merge(T[] arr, int left, int mid, int right, Comparator<T> comparator) {
        T[] sorted = (T[]) new Object[right - left];

        int lLeft = left;
        int rRight = mid;

        for (int i = 0; lLeft < mid || rRight < right; i++) {
            if (lLeft < mid && rRight < right) {
                if (comparator.compare(arr[lLeft], arr[rRight]) < 0) {
                    sorted[i] = arr[lLeft];
                    lLeft++;
                } else {
                    sorted[i] = arr[rRight];
                    rRight++;
                }
            } else if (lLeft < mid) {
                sorted[i] = arr[lLeft];
                lLeft++;
            } else {
                sorted[i] = arr[rRight];
                rRight++;
            }
        }
        for (int i = 0; i < sorted.length; i++) {
            arr[left + i] = sorted[i];
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null and cannot be sorted.");
        }
        int iterations = longestNum(arr);

        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
        int exp = 1;

        for (int k = 0; k < iterations; k++) {
            for (int i = 0; i < arr.length; i++) {
                int index = (arr[i] / exp) % 10 + 9;
                buckets[index].add(arr[i]);
            }
            exp = exp * 10;
            int j = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[j++] = bucket.remove();
                }
            }
        }
    }

    /**
     * Finds the number of digits in the longest number
     *
     * @param arr the array being searched
     * @return length of the longest number
     */
    private static int longestNum(int[] arr) {
        int longest = 0;
        for (int i = 0; i < arr.length; i++) {
            int div = Math.abs(arr[i]);
            int count = 1;
            int exp = 1;
            while ((div / exp) > 9) {
                count++;
                exp = exp * 10;
            }
            if (count > longest) {
                longest = count;
            }
        }
        return longest;
    }


    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data does not exist.");
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(data);
        int index = 0;
        int[] arr = new int[data.size()];

        while (!queue.isEmpty()) {
            arr[index] = queue.poll();
            index++;
        }
        return arr;
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                       Random rand) {
        if (arr == null || rand == null) {
            throw new IllegalArgumentException("The array or random object does not exist.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator does not exist.");
        } else if (k < 0 || k > arr.length) {
            throw new IllegalArgumentException("The range is invalid.");
        }

        kthSelectHelper(arr, 0, arr.length - 1, rand, comparator, k - 1);
        return arr[k - 1];
    }

    /**
     * kthSelect helper method
     *
     * @param <T>           data type to sort
     * @param arr           the array that should be modified after the method is finished executing as needed
     * @param a             left index
     * @param b             right index
     * @param rand          the Random object used to select pivots
     * @param comparator    the Comparator used to compare the data in arr
     * @param k             the index to retrieve data from + 1
     */
    private static <T> void kthSelectHelper(T[] arr, int a, int b, Random rand, Comparator<T> comparator, int k) {
        int pivotIndex = rand.nextInt(b - a + 1) + a;
        swap(arr, a, pivotIndex);
        T data = arr[a];
        int l = a + 1;
        int r = b;
        while (l <= r) {
            while (l <= r && comparator.compare(data, arr[r]) <= 0) {
                r--;
            }
            while (l <= r && comparator.compare(data, arr[l]) >= 0) {
                l++;
            }
            if (l < r) {
                swap(arr, l, r);
                l++;
                r--;
            }
        }
        swap(arr, r, a);
        if (r < k) {
            kthSelectHelper(arr, r + 1, b, rand, comparator, k);
        } else if (r > k) {
            kthSelectHelper(arr, a, r - 1, rand, comparator, k);
        }
    }

    /**
     * swaps data
     *
     * @param <T>       data type to sort
     * @param arr       the array that swap acts on
     * @param i         first index swapped
     * @param j         second index swapped
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
