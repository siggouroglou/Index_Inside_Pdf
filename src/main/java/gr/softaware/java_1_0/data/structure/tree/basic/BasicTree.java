package gr.softaware.java_1_0.data.structure.tree.basic;

import gr.softaware.java_1_0.data.structure.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that represent a tree without restrictions. Be nice with equals cause
 * the only one equals method that is used is for type T. Not for subtypes.
 *
 * @author siggouroglou
 * @param <T> The class type that will be stored to the tree.
 */
public final class BasicTree<T extends TreeNode> implements TreeCollection<T> {

    private BasicTreeNode<T> root;
    private int size;

    /**
     * Initialize the tree without root node and zero size. This operation is
     * not yet permitted.
     */
    private BasicTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Initialize the tree with a root note with the data provided.
     *
     * @param rootData data that will be stored to the root node.
     * @throws NullPointerException if the root data argument is null.
     */
    public BasicTree(T rootData) {
        if (rootData == null) {
            throw new NullPointerException("Root data cannot be null.");
        }
        this.root = new BasicTreeNode<>(rootData);
        this.size = 1;
    }

    public BasicTreeNode<T> getRoot() {
        return root;
    }

    /**
     * Search for an object-data inside the tree.
     *
     * @param node an object that is maybe stored inside the tree.
     * @return null in case of not finding the node or the object-data of the
     * node.
     * @throws NullPointerException in case of a null argument.
     */
    @Override
    public T find(T node) {
        // Argument validation.
        if (node == null) {
            throw new NullPointerException("Search for a not not allows null argument.");
        }

        // Are there any elements in the tree.
        if (size == 0) {
            return null;
        }

        BasicTreeNode<T> nodeFound = findNode(node);

        return nodeFound == null ? null : nodeFound.getData();
    }

    /**
     * Returns null if the node is not existing.
     *
     * @param node the object-data to search for.
     * @return the object-node if found. In other cases null.
     * @throws NullPointerException in case of a null argument.
     */
    private BasicTreeNode<T> findNode(T node) {
        assert node != null : "Search for a node not allows null argument.";

        // Are there any elements in the tree.
        if (size == 0) {
            return null;
        }

        BasicTreeNode<T> nodeFound = preOrder(this.root, this.root, (BasicTreeNode parent, BasicTreeNode current) -> {
            if (current.getData().equals(node)) {
                return TreeOrderingOutput.RETURN_AND_EXIT;
            } else {
                return TreeOrderingOutput.CONTINUE;
            }
        });

        return nodeFound == null ? null : nodeFound;
    }

    /**
     * Find the parent of an object-data (not the tree node). The parent of root
     * is root.
     *
     * @param node the object-data that will search for his/her parent.
     * @return null or the parent object-data.
     * @throws NullPointerException in case of a null argument.
     */
    public T findParent(final T node) {
        // Argument validation.
        if (node == null) {
            throw new NullPointerException("Search for parent not allows null child.");
        }

        // Are there any elements in the tree.
        if (size == 0) {
            return null;
        }

        BasicTreeNode<T> parentFound = findParentNode(node);

        // Return parent.
        return parentFound == null ? null : parentFound.getData();
    }

    /**
     * Find the parent of an object-data (not the tree node) and returns the
     * parent object-node. The parent of root is root.
     *
     * @param node the object-data that will search for his/her parent
     * object-node.
     * @return null or the parent object-node.
     * @throws NullPointerException in case of a null argument.
     */
    private BasicTreeNode<T> findParentNode(final T node) {
        assert node != null : "Search for parent of a node not allows null argument.";

        // Are there any elements in the tree.
        if (size == 0) {
            return null;
        }

        // Search for parent.
        BasicTreeNode<T> parentFound = preOrder(this.root, this.root, (BasicTreeNode parent, BasicTreeNode current) -> {
            if (node.equals(current.getData())) {
                return TreeOrderingOutput.RETURN_PARENT_AND_EXIT;
            }
            return TreeOrderingOutput.CONTINUE;
        });

        // Return parent.
        return parentFound == null ? null : parentFound;
    }

    /**
     * Runs the tree in preorder. Runs the given strategy each time it is on an
     * item.
     *
     * @param parent The parent of the current node.
     * @param current The current node.
     * @param treeOrderStrategy a strategy to run for the parent and current
     * nodes.
     * @return a tree node depending the strategy.
     * @throws NullPointerException in case of a null argument.
     */
    public BasicTreeNode<T> preOrder(
            BasicTreeNode<T> parent, BasicTreeNode<T> current, TreeOrderContinueStrategy treeOrderStrategy) {
        // Argument validation.
        if (parent == null) {
            throw new NullPointerException("Parent cannot be null. Maybe a bug exists here.");
        }
        // Argument validation.
        if (current == null) {
            throw new NullPointerException("Child cannot be null. Maybe a bug exists here.");
        }
        // Argument validation.
        if (treeOrderStrategy == null) {
            throw new NullPointerException("TreeOrdering cannot be null. Maybe a bug exists here.");
        }

        // Run the custom method.
        TreeOrderingOutput output = treeOrderStrategy.ordering(parent, current);
        switch (output) {
            case CONTINUE:
                break;
            case RETURN_PARENT_AND_EXIT:
                return parent;
            case RETURN_AND_EXIT:
                return current;
        }

        // Continue with children of this node.
        BasicTreeNode<T> returnNode;
        for (BasicTreeNode<T> child : current.getChildren()) {
            returnNode = preOrder(current, child, treeOrderStrategy);
            if (returnNode != null) {
                return returnNode;
            }
        }
        return null;
    }

    private BasicTreeNode<T> inOrder(BasicTreeNode<T> node, TreeOrderContinueStrategy treeOrderStrategy) {
        assert node != null : "Starting node cannot be null. Maybe a bug exists here.";
        assert treeOrderStrategy != null : "TreeOrdering cannot be null. Maybe a bug exists here.";

        // Continue with children of this node.
        BasicTreeNode<T> returnNode;
        for (BasicTreeNode<T> current : node.getChildren()) {
            returnNode = postOrder(current, treeOrderStrategy);

            // Run the custom method.
            TreeOrderingOutput output = treeOrderStrategy.ordering(node, node);
            switch (output) {
                case CONTINUE:
                    break;
                case RETURN_AND_EXIT:
                    return node;
            }

            // Return the node if it is found.
            if (returnNode != null) {
                return returnNode;
            }
        }

        return null;
    }

    private BasicTreeNode<T> postOrder(BasicTreeNode<T> node, TreeOrderContinueStrategy treeOrderStrategy) {
        assert node != null : "Starting node cannot be null. Maybe a bug exists here.";
        assert treeOrderStrategy != null : "TreeOrdering cannot be null. Maybe a bug exists here.";

        // Continue with children of this node.
        BasicTreeNode<T> returnNode;
        for (BasicTreeNode<T> current : node.getChildren()) {
            returnNode = postOrder(current, treeOrderStrategy);
            if (returnNode != null) {
                return returnNode;
            }
        }

        // Run the custom method.
        TreeOrderingOutput output = treeOrderStrategy.ordering(node, node);
        switch (output) {
            case CONTINUE:
                break;
            case RETURN_AND_EXIT:
                return node;
        }

        return null;
    }

    /**
     * The current size of the tree. How many tree nodes exist in the tree.
     *
     * @return the count of the nodes in the tree.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if the tree is empty. This means that it does not include a
     * root node also.
     *
     * @return true if the tree is empty. This means that it does not include a
     * root node also.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Check if the node is inside the tree and returns true or false.
     *
     * @param o this object must be a type of T to continue searching.
     * @return true if this object is included in the tree at least one time.
     * @throws ClassCastException in case of the object is not type of T.
     */
    @Override
    public boolean contains(Object o) {
        // Check for null argument.
        if (o == null) {
            return false;
        }

        // The instance must be a type of T. Throws ClassCastException.
        T instance = (T) o;

        // Find the node in the tree.
        T nodeFound = find(instance);

        // Return true if the node is found.
        return nodeFound != null;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Convert the tree to an array that is a preordered view of the tree.
     *
     * @return a preordered view of the tree as an array.
     */
    @Override
    public Object[] toArray() {
        final List<TreeNode> objectPreOrdered = new ArrayList<>(this.size);
        this.preOrder(root, root, (parent, current) -> {
            objectPreOrdered.add(current.getData());
            return TreeOrderingOutput.CONTINUE;
        });

        return objectPreOrdered.toArray();
    }

    /**
     * Convert the tree to an array that is a preordered view of the tree
     * including the items provided.
     *
     * @return a preordered view of the tree as an array.
     * @throws NullPointerException in case of a null argument's
     * @throws ClassCastException in case of argument type is not a type of
     * TreeNode.
     */
    @Override
    public <M> M[] toArray(M[] a) {
        // Validate the argument.
        if (a == null) {
            throw new NullPointerException("Argument must be not null");
        }

        // Read the list and add the items inside this list.
        List<TreeNode> objectPreOrdered = new ArrayList<>(this.size);
        this.preOrder(root, root, (parent, current) -> {
            objectPreOrdered.add(current.getData());
            return TreeOrderingOutput.CONTINUE;
        });

        return objectPreOrdered.toArray(a);
    }

    /**
     * Add the item as a child of the root item.
     *
     * @param e the data to be added.
     * @return true in all cases.
     * @throws NullPointerException in case of a null argument or a null root.
     */
    @Override
    public boolean add(T e) {
        if (e == null) {
            throw new NullPointerException("Argument must not be null");
        }

        // Check if root is existing.
        if (root == null) {
            // Set the root.
            throw new NullPointerException("Element root must not be null");
        }

        // Add this child as a root's child.
        root.getChildren().add(new BasicTreeNode<>(e));

        // Update the size.
        size++;

        return true;
    }

    /**
     * Add the item as a child of the item provided as parent.
     *
     * @param parent the parent of the node to be added.
     * @param node the node to be added.
     * @return true if the parent found and child inserted. If the parent is not
     * found then returns false.
     * @throws NullPointerException in case of a null argument or a null root.
     */
    public boolean add(T parent, T node) {
        if (parent == null) {
            throw new NullPointerException("Argument parent must not be null");
        }
        if (node == null) {
            throw new NullPointerException("Argument node must not be null");
        }

        // Check if root is existing.
        if (root == null) {
            // Set the root.
            throw new NullPointerException("Element root must not be null");
        }

        // Find the parent.
        BasicTreeNode<T> parentNode = findNode(parent);
        if (parentNode == null) {
            return false;
        }

        // Add this child as a root's child.
        parentNode.getChildren().add(new BasicTreeNode<>(node));

        // Update the size.
        size++;

        return true;
    }

    /**
     * Removes the first occurrence of the node argument from the tree if it is
     * existing.
     *
     * @param node The node to be removed.
     * @return true if the node has been found and removed or false if the node
     * was not found.
     * @throws NullPointerException in case of a null argument or a null root.
     * @throws ClassCastException in case of the type is not of T.
     */
    @Override
    public boolean remove(Object node) {
        if (node == null) {
            throw new NullPointerException("Argument must not be null.");
        }

        // Check if it is data object.
        T data = (T) node;

        // To remove this node i have to find the parent and remove it from the children list.
        BasicTreeNode<T> parentNode = findParentNode(data);
        if (parentNode == null) {
            return false;
        }

        // Remove the child node from the parent's list. It is for sure existing.
        for (BasicTreeNode<T> child : parentNode.getChildren()) {
            if (child.getData().equals(data)) {
                parentNode.getChildren().remove(child);
                size -= 1;
                break;
            }
        }

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Moves the nodes before or after his position depending the step. Node is
     * moving on the same level. If the node is not found nothing happens.
     *
     * @param node the node to be moved.
     * @param step negative or positive means before or after.
     * @throws NullPointerException in case of a null argument.
     * @throws IllegalArgumentException in case the step is out of bounds.
     */
    public void moveWithStep(T node, int step) {
        if (node == null) {
            throw new NullPointerException("Argument must not be null.");
        }

        // Find the parent of this node.
        BasicTreeNode<T> parentNode = findParentNode(node);
        if (parentNode == null) {
            return;
        }

        // Find the position of the node.
        BasicTreeNode<T> childNode = findNode(node);
        int index = parentNode.getChildren().indexOf(childNode);

        // Remove the node. Now the index will be index.
        parentNode.getChildren().remove(index);

        // Add the node to the index-1+step position.
        try {
            parentNode.getChildren().add(index + step, childNode);
        } catch (IndexOutOfBoundsException ex) {
            // Add the node again.
            parentNode.getChildren().add(index, childNode);
            // Throw the correct exception.
            throw new IllegalArgumentException("Step is not valid.");
        }
    }

    public List<? super TreeNode> getDataPreOrdered() {
        List<TreeNode> categoryPreOrdered = new LinkedList<>();
        this.preOrder(root, root, (parent, current) -> {
            categoryPreOrdered.add(current.getData());
            return TreeOrderingOutput.CONTINUE;
        });
        return categoryPreOrdered;
    }
}
