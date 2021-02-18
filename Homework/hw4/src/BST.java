import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Hye Lim Kim
 * @version 1.0
 * @userid YOUR USER ID HERE hkim946
 * @GTID YOUR GT ID HERE 903197983
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("there exists a data that is empty");
        }
        size = 0;
        for (T subData : data) {
            add(subData);
        }
    }
    /**
     * Adds the data to the tree.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        } else if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }
    /**
     * Recursive helper method for add
     *
     * @param data is the data to be added to the tree
     * @param node is current node being compared
     */
    private void addHelper(T data, BSTNode<T> node) {
        if (node.getData().compareTo(data) > 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<T>(data));
                size++;
            } else {
                addHelper(data, node.getLeft());
            }
        } else if (node.getData().compareTo(data) < 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<T>(data));
                size++;
            } else {
                addHelper(data, node.getRight());
            }
        }
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (root == null) {
            throw new NoSuchElementException("Root is null");
        }
        T removed = removeHelper(data, null, root);

        if (removed == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        size--;
        return removed;
    }
    /**
     * Recursive helper method for remove
     *
     * @param data is the data to be removed from the tree
     * @param parent is current parent node for the node being compared
     * @param current is the current node being compared
     * @return returns the data being removed
     */
    private T removeHelper(T data, BSTNode<T> parent, BSTNode<T> current) {
        if (current == null) {
            return null;
        }
        if (data.equals(current.getData())) {
            T oldData = current.getData();
            boolean parentLeft = parent != null && current == parent.getLeft();
            if (current.getLeft() == null && current.getRight() == null) {
                current = null;
            } else if (current.getLeft() != null && current.getRight() == null) {
                current = current.getLeft();
            } else if (current.getLeft() == null && current.getRight() != null) {
                current = current.getRight();
            } else {
                current.setData(predecessor(current, current.getLeft()));
            }

            if (parent != null) {
                if (parentLeft) {
                    parent.setLeft(current);
                } else {
                    parent.setRight(current);
                }
            } else {
                root = current;
            }
            return oldData;
        } else if (current.getData().compareTo(data) < 0) {
            return removeHelper(data, current, current.getRight());
        } else {
            return removeHelper(data, current, current.getLeft());
        }
    }
    /**
     * Recursive helper method to find the predecessor node when removing a
     * node with two children
     *
     * @param parent is current parent node for the node being compared
     * @param current is the current node being compared
     * @return returns the data that is the predecessor
     */
    private T predecessor(BSTNode<T> parent, BSTNode<T> current) {
        if (current.getRight() != null) {
            return predecessor(current, current.getRight());
        } else {
            if (parent.getLeft() == current) {
                parent.setLeft(current.getLeft());
            } else if (parent.getRight() == current) {
                parent.setRight(current.getLeft());
            }
        }
        return current.getData();
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be found");
        }
        return getHelper(root, data);
    }
    /**
     * Recursive helper method for get
     *
     * @param data is the data to be found in the tree
     * @param current is the current node being compared
     * @return returns the data that is trying to be found
     */
    private T getHelper(BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("Data not found in tree.");
        } else if (current.getData().equals(data)) {
            return current.getData();
        } else if (current.getData().compareTo(data) < 0) {
            return getHelper(current.getRight(), data);
        } else if (current.getData().compareTo(data) > 0) {
            return getHelper(current.getLeft(), data);
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find a null in a BST");
        }
        return containsHelper(root, data);
    }
    /**
     * Recursive helper method for contains
     *
     * @param data is the data to be found in the tree
     * @param current is the current node being compared
     * @return returns whether or not the data was found
     */
    private boolean containsHelper(BSTNode<T> current, T data) {
        if (current == null) {
            return false;
        } else if (current.getData().equals(data)) {
            return true;
        } else if (current.getData().compareTo(data) < 0) {
            return containsHelper(current.getRight(), data);
        } else if (current.getData().compareTo(data) > 0) {
            return containsHelper(current.getLeft(), data);
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderList = new ArrayList<>();
        return preorderHelper(root, preorderList);
    }
    /**
     * Recursive helper method for preorder traversal
     *
     * @param current is the current node traversal is started
     * @param list is a List of the nodes
     * @return returns a list with the data in preorder
     */
    private List<T> preorderHelper(BSTNode<T> current, List<T> list) {
        if (current != null) {
            list.add(current.getData());
            preorderHelper(current.getLeft(), list);
            preorderHelper(current.getRight(), list);
        }
        return list;
    }
    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderList = new ArrayList<>();
        return inorderHelper(root, inorderList);
    }
    /**
     * Recursive helper method for inorder traversal
     *
     * @param current is the current node traversal is started
     * @param list is a List of the nodes
     * @return returns a list with the data in inorder
     */
    private List<T> inorderHelper(BSTNode<T> current, List<T> list) {
        if (current != null) {
            inorderHelper(current.getLeft(), list);
            list.add(current.getData());
            inorderHelper(current.getRight(), list);
        }
        return list;
    }
    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderList = new ArrayList<>();
        return postorderHelper(root, postorderList);
    }
    /**
     * Recursive helper method for postorder traversal
     *
     * @param current is the current node traversal is started
     * @param list is a List of the nodes
     * @return returns a list with the data in postorder
     */
    private List<T> postorderHelper(BSTNode<T> current, List<T> list) {
        if (current != null) {
            postorderHelper(current.getLeft(), list);
            postorderHelper(current.getRight(), list);
            list.add(current.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelorderList = new ArrayList<T>();
        Queue<BSTNode<T>> levelorderQueue = new LinkedList<>();
        return levelorderHelper(root, levelorderList, levelorderQueue);
    }
    /**
     * Recursive helper method for levelorder traversal
     *
     * @param current is the current node traversal is started
     * @param list is a List of the nodes
     * @param queue a queue that has the nodes stored
     * @return returns a list with the data in preorder
     */
    private List<T> levelorderHelper(BSTNode<T> current, List<T> list, Queue<BSTNode<T>> queue) {
        if (current != null) {
            queue.add(current);
            while (!queue.isEmpty()) {
                current = queue.remove();
                list.add(current.getData());
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return height(root);
    }
    /**
     * Recursive helper method for height
     *
     * @param current is the current node finding the height
     * @return returns the height of the tree
     */
    private int height(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else if (current.getLeft() == null
                && current.getRight() == null) {
            return 0;
        } else {
            return Math.max(height(current.getLeft()),
                    height(current.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * <p>
     * This list should contain the last node of each level.
     * <p>
     * If the tree is empty, an empty list should be returned.
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 2
     * /   \
     * 1     4
     * /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 10   15   40
     * /
     * 13
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 13] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     * <p>
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        return list;
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
