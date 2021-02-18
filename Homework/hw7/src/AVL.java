import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of an AVL.
 *
 * @author Hye Lim Kim
 * @version 1.0
 * @userid YOUR USER ID HERE hkim946
 * @GTID YOUR GT ID HERE 903197983
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        for (T subData : data) {
            add(subData);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        root = rotate(addHelper(data, root));
        size++;
    }

    /**
     * Add helper method
     * @param data data added
     * @param curr current node
     * @return current node
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rotate(addHelper(data, curr.getLeft())));
            return curr;
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rotate(addHelper(data, curr.getRight())));
            return curr;
        }
        return null;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (root == null) {
            throw new NoSuchElementException("Data is not found in the tree.");
        }
        AVLNode<T> temp = new AVLNode<>(null);
        root = rotate(removeHelper(data, root, temp));
        size--;
        return temp.getData();
    }
    /**
     * Remove helper method
     *
     * @param data data to be removed
     * @param node current parent node
     * @param dummy dummy node for node that's being removed
     * @return returns the data being removed
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node, AVLNode<T> dummy) {
        if (node == null) {
            return node;
        } else if (data.equals(node.getData())) {
            dummy.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                T pred = predecessor(node, node.getLeft());
                remove(pred);
                size++;
                node.setData(pred);
                return node;
            }
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(rotate(removeHelper(data, node.getLeft(), dummy)));
            return node;
        } else {
            node.setRight(rotate(removeHelper(data, node.getRight(), dummy)));
            return node;
        }
    }

    /**
     * Helper method to find the predecessor
     * @param parent current parent node
     * @param current current node
     * @return returns the predecessor's data
     */
    private T predecessor(AVLNode<T> parent, AVLNode<T> current) {
        if (current.getRight() != null) {
            T predata = predecessor(current, current.getRight());
            rotate(parent);
            return predata;
        } else {
            if (parent.getLeft() == current) {
                parent.setLeft(current.getLeft());
            } else if (parent.getRight() == current) {
                parent.setRight(current.getLeft());
            }
        }
        rotate(parent);
        return current.getData();
    }

    /**
     * Calls assigned rotation methods
     * @param node is current node being rotated
     * @return returns the current node
     */
    private AVLNode<T> rotate(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        update(node);
        if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() >= 0) {
                return rightRot(node);
            } else {
                return leftRightRot(node);
            }
        } else if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() <= 0) {
                return leftRot(node);
            } else {
                return rightLeftRot(node);
            }
        } else {
            return node;
        }
    }

    /**
     * Updates balance factor and height of node
     * @param curr current node updated
     */
    private void update(AVLNode<T> curr) {
        if (curr == null) {
            return;
        }
        if (curr.getLeft() != null && curr.getRight() == null) {
            curr.setHeight(Math.max(curr.getLeft().getHeight(), -1) + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
        } else if (curr.getLeft() == null && curr.getRight() != null) {
            curr.setHeight(Math.max(-1, curr.getRight().getHeight()) + 1);
            curr.setBalanceFactor(-1 - curr.getRight().getHeight());
        } else if (curr.getLeft() == null && curr.getRight() == null) {
            curr.setHeight(0);
            curr.setBalanceFactor(0);
        } else {
            curr.setHeight(Math.max(curr.getLeft().getHeight(), curr.getRight().getHeight()) + 1);
            curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
        }
    }

    /**
     * Right right rotation
     * @param curr current node rotated
     * @return returns the current node
     */
    private AVLNode<T> rightRot(AVLNode<T> curr) {
        AVLNode<T> left = curr.getLeft();
        AVLNode<T> lr = left.getRight();

        curr.setLeft(lr);
        left.setRight(curr);

        update(curr);
        update(left);

        return left;
    }

    /**
     * Left left rotation
     * @param curr current node rotated
     * @return returns the current node
     */
    private AVLNode<T> leftRot(AVLNode<T> curr) {
        AVLNode<T> right = curr.getRight();
        AVLNode<T> rl = right.getLeft();

        curr.setRight(rl);
        right.setLeft(curr);

        update(curr);
        update(right);

        return right;
    }

    /**
     * Left right rotation
     * @param curr current node rotated
     * @return returns the current node
     */
    private AVLNode<T> leftRightRot(AVLNode<T> curr) {
        AVLNode<T> left = curr.getLeft();
        AVLNode<T> lr = left.getRight();
        AVLNode<T> lrl = lr.getLeft();
        AVLNode<T> lrr = lr.getRight();

        lr.setLeft(left);
        lr.setRight(curr);
        left.setRight(lrl);
        curr.setLeft(lrr);

        update(left);
        update(curr);
        update(lr);

        return lr;
    }

    /**
     * Right left rotation
     * @param curr current node rotated
     * @return returns the current node
     */
    private AVLNode<T> rightLeftRot(AVLNode<T> curr) {
        AVLNode<T> right = curr.getRight();
        AVLNode<T> rl = right.getLeft();
        AVLNode<T> two = rl.getLeft();
        AVLNode<T> three = rl.getRight();

        rl.setLeft(curr);
        rl.setRight(right);
        curr.setRight(two);
        right.setLeft(three);

        update(curr);
        update(right);
        update(rl);

        return rl;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data does not exist");
        }
        return getHelper(root, data);
    }

    /**
     * Get Helper
     * @param data data to be found
     * @param curr current node
     * @return returns the data that is trying to be found
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree.");
        } else if (curr.getData().equals(data)) {
            return curr.getData();
        } else if (curr.getData().compareTo(data) < 0) {
            return getHelper(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return getHelper(curr.getLeft(), data);
        }
        return null;
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data does not exist");
        }

        return containsHelper(root, data);
    }

    /**
     * Contains helper method
     * @param data data found in tree
     * @param curr current node
     * @return returns whether or not the data was found
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().equals(data)) {
            return true;
        } else if (curr.getData().compareTo(data) < 0) {
            return containsHelper(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return containsHelper(curr.getLeft(), data);
        }
        return false;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Height helper method
     * @param curr current node
     * @return returns the height of the tree
     */
    private int heightHelper(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        } else if (curr.getLeft() == null && curr.getRight() == null) {
            return 0;
        } else {
            return Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find all elements within a certain distance from the given data.
     * "Distance" means the number of edges between two nodes in the tree.
     *
     * To do this, first find the data in the tree. Keep track of the distance
     * of the current node on the path to the data (you can use the return
     * value of a helper method to denote the current distance to the target
     * data - but note that you must find the data first before you can
     * calculate this information). After you have found the data, you should
     * know the distance of each node on the path to the data. With that
     * information, you can determine how much farther away you can traverse
     * from the main path while remaining within distance of the target data.
     *
     * Use a HashSet as the Set you return. Keep in mind that since it is a
     * Set, you do not have to worry about any specific order in the Set.
     *
     * Note: We recommend 2 helper methods:
     * 1. One helper method should be for adding the nodes on the path (from
     * the root to the node containing the data) to the Set (if they are within
     * the distance). This helper method will also need to find the distance
     * between each node on the path and the target data node.
     * 2. One helper method should be for adding the children of the nodes
     * along the path to the Set (if they are within the distance). The
     * private method stub called elementsWithinDistanceBelow is intended to
     * be the second helper method. You do NOT have to implement
     * elementsWithinDistanceBelow. However, we recommend you use this method
     * to help implement elementsWithinDistance.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * elementsWithinDistance(37, 3) should return the set {12, 13, 15, 25,
     * 37, 40, 50, 75}
     * elementsWithinDistance(85, 2) should return the set {75, 80, 85}
     * elementsWithinDistance(13, 1) should return the set {12, 13, 15, 25}
     *
     * @param data the data to begin calculating distance from
     * @param distance the maximum distance allowed
     * @return the set of all data within a certain distance from the given data
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   is the data is not in the tree
     * @throws java.lang.IllegalArgumentException if distance is negative
     */
    public Set<T> elementsWithinDistance(T data, int distance) {
        if (data == null || distance < 0) {
            throw new IllegalArgumentException("data is not in the tree or distance is negative");
        }
        if (size ==0) {
            throw new NoSuchElementException("Tree is empty");
        }
        HashSet<T> set = new HashSet<>();
        eWDPath(root, distance, data, set);
        if (set.isEmpty()) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        return set;
    }
    /**
     * Path of elements
     *
     * @param curNode         the current node
     * @param maxDistance the maximum distance allowed
     * @param data target node data
     * @param currSet   the current set of data within the maximum
     *                        distance
     */
    private int eWDPath(AVLNode<T> curNode, int maxDistance, T data, Set<T> currSet) {
        int i = maxDistance + 1;
        if (curNode == null) {
            return i;
        }
        if (curNode.getData().compareTo(data) > 0) {
            i = eWDPath(curNode.getLeft(), maxDistance, data, currSet);
            if (maxDistance >= i) {
                currSet.add(curNode.getData());
                elementsWithinDistanceBelow(curNode.getRight(), maxDistance - i - 1, 0, currSet);
            }
        } else if (curNode.getData().compareTo(data) < 0) {
            i = eWDPath(curNode.getRight(), maxDistance, data, currSet);
            if (maxDistance >= i) {
                currSet.add(curNode.getData());
                elementsWithinDistanceBelow(curNode.getLeft(), maxDistance - i - 1, 0, currSet);
            }
        } else {
            elementsWithinDistanceBelow(curNode, maxDistance, 0, currSet);
            return 1;
        }
        return i + 1;
    }

    /**
     * You do NOT have to implement this method if you choose not to.
     * However, this will help with the elementsWithinDistance method.
     *
     * Adds data to the Set if the current node is within the maximum distance
     * from the target node. Recursively call on the current node's children to
     * add their data too if the children's data are also within the maximum
     * distance from the target node.
     *
     * @param curNode         the current node
     * @param maximumDistance the maximum distance allowed
     * @param currentDistance the distance between the current node and the
     *                        target node
     * @param currentResult   the current set of data within the maximum
     *                        distance
     */
    private void elementsWithinDistanceBelow(AVLNode<T> curNode,
                                             int maximumDistance,
                                             int currentDistance,
                                             Set<T> currentResult) {
        // STUDENT CODE HERE
        if (curNode == null) {
            return;
        }
        if (currentDistance <= maximumDistance) {
            currentResult.add(curNode.getData());
        }
        if (currentDistance < maximumDistance) {
            elementsWithinDistanceBelow(curNode.getLeft(), maximumDistance, currentDistance + 1, currentResult);
            elementsWithinDistanceBelow(curNode.getRight(), maximumDistance, currentDistance + 1, currentResult);
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
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
