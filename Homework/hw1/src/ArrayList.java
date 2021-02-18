import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
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
public class ArrayList<T> {
    /*
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the data to the specified index.
     * <p>
     * This add may require elements to be shifted.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot access this index");
        }
        if (size == backingArray.length) {
            T[] tempArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                tempArray[i] = backingArray[i];
            }
            backingArray = tempArray;
        }
        T var;
        if (backingArray[index] != null) {
            for (int i = index; i < size + 1; i++) {
                var = backingArray[i];
                backingArray[i] = data;
                data = var;
            }
            size += 1;
        } else {
            backingArray[index] = data;
            size += 1;
        }
    }

    /**
     * Adds the data to the front of the list.
     * <p>
     * This add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * This remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot access this index");
        }
        T data = backingArray[index];
        for (int i = index; i < size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the first data of the list.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * This remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("There is an empty list");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("There is an empty list");
        }
        return removeAtIndex(size - 1);
    }


    /**
     * Returns the data at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot access this index");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;

        /**
         * Clears the list.
         *
         * Resets the backing array to a new array of the initial capacity and
         * resets the size.
         *
         * Must be O(1).
         */
        public void clear() {
            this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
            this.size = 0;
        }

        /**
         * Returns the backing array of the list.
         *
         * For grading purposes only. You shouldn't need to use this method since
         * you have direct access to the variable.
         *
         * @return the backing array of the list
         */
        public T[] getBackingArray () {
            // DO NOT MODIFY THIS METHOD!
            return backingArray;
        }

        /**
         * Returns the size of the list.
         *
         * For grading purposes only. You shouldn't need to use this method since
         * you have direct access to the variable.
         *
         * @return the size of the list
         */
        public int size() {
            // DO NOT MODIFY THIS METHOD!
            return size;
        }
    }
}
