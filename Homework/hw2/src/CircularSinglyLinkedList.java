import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Hye Lim (Hannah) Kim
 * @version 1.0
 * @userid hkim946
 * @GTID 903197983
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        CircularSinglyLinkedListNode<T> curr = head;
        CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot access this index");
        }

        if (head == null) {
            head = newNode;
            head.setNext(head);
        } else {
            if (index == 0) {
                newNode.setData(head.getData());
                head.setData(data);
                newNode.setNext(head.getNext());
                head.setNext(newNode);
            } else {
                int count = 0;
                while (count != index - 1) {
                    curr = curr.getNext();
                    count++;
                }
                newNode.setNext(curr.getNext());
                curr.setNext(newNode);
            }
        }
        size++;
    }


    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
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
     *
     * Must be O(1).
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
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot access this index");
        }
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }
        CircularSinglyLinkedListNode<T> curr = head;
        T removed;
        if (index == 0) {
            removed = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
        }
        size--;
        return removed;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("List is empty!");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("List is empty!");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot access this index");
        }
        if (index == 0) {
            return head.getData();
        }
        int count = 0;
        CircularSinglyLinkedListNode<T> curr = head;
        while (count != index) {
            curr = curr.getNext();
            count++;
        }
        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }
        CircularSinglyLinkedListNode<T> curr = head;
        int count = 0;
        int index = 0;
        while (curr.getNext() != head) { //not the last element in the list to go through entire list
            if (curr.getNext().getData() == data) {
                index = count + 1;
            }
            curr = curr.getNext();
            count++;
        }
        return removeAtIndex(index);
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        CircularSinglyLinkedListNode<T> curr = head;
        T[] array = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] =  curr.getData();
            curr = curr.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
        // DO NOT MODIFY!
        return size;
    }
}
