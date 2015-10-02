package gr.softaware.java_1_0.data.structure.tree.depricated.normal;

import gr.softaware.java_1_0.data.structure.tree.depricated.TreeCollection;
import gr.softaware.java_1_0.data.structure.tree.depricated.TreeNode;
import gr.softaware.java_1_0.data.structure.tree.depricated.TreeNodeAdapter;
import gr.softaware.java_1_0.data.structure.tree.depricated.TreeNodeData;
import gr.softaware.java_1_0.data.structure.tree.depricated.TreeOrdering;
import gr.softaware.java_1_0.data.structure.tree.depricated.TreeOrderingOutput;
import static gr.softaware.java_1_0.data.structure.tree.depricated.TreeOrderingOutput.CONTINUE;
import static gr.softaware.java_1_0.data.structure.tree.depricated.TreeOrderingOutput.RETURN_AND_EXIT;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author siggouroglou
 * @param <T> The class type that will be stored to the tree.
 */
public final class BasicTree<T extends TreeNodeData> implements TreeCollection<T> {

    private TreeNode<T> root;
    private int size;

    public BasicTree() {
        this.root = null;
        this.size = 0;
    }

    public BasicTree(TreeNode<T> root) {
        this.root = root;
        this.size = 1;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    @Override
    public TreeNode<T> find(T node) {
        return preOrder(this.root, (TreeNode<T> current) -> {
            if (current.getData().equals(node)) {
                return TreeOrderingOutput.RETURN_AND_EXIT;
            } else {
                return TreeOrderingOutput.CONTINUE;
            }
        });
    }

    public TreeNode<T> findParent(T node) {
        return preOrderForParent(this.root, this.root, node);
    }

    private TreeNode<T> preOrderForParent(TreeNode<T> parent, TreeNode<T> current, T node) {
        if (parent == null || current == null || node == null) {
            return null;
        }

        // Check and return parent.
        if (current.getData().equals(node)) {
            return parent;
        }

        // Continue with children of this node.
        TreeNode<T> returnNode;
        for (TreeNode child : current.getChildren()) {
            returnNode = preOrderForParent(current, child, node);
            if (returnNode != null) {
                return returnNode;
            }
        }
        return null;
    }

    @Override
    public TreeNode<T> preOrder(TreeNode<T> node, TreeOrdering<T> treeOrdering) {
        if (node == null || treeOrdering == null) {
            return null;
        }

        // Run the custom method.
        TreeOrderingOutput output = treeOrdering.ordering(node);
        switch (output) {
            case CONTINUE:
                break;
            case RETURN_AND_EXIT:
                return node;
        }

        // Continue with children of this node.
        TreeNode<T> returnNode;
        for (Object current : node.getChildren()) {
            returnNode = preOrder((TreeNode) current, treeOrdering);
            if (returnNode != null) {
                return returnNode;
            }
        }
        return null;
    }

    @Override
    public TreeNode<T> inOrder(TreeNode<T> node, TreeOrdering<T> treeOrdering) {
        throw new UnsupportedOperationException("Not supported for this type of tree.");
    }

    @Override
    public TreeNode<T> postOrder(TreeNode<T> node, TreeOrdering<T> treeOrdering) {
        if (node == null || treeOrdering == null) {
            return null;
        }

        // Continue with children of this node.
        TreeNode<T> returnNode;
        for (Object current : node.getChildren()) {
            returnNode = postOrder((TreeNode) current, treeOrdering);
            if (returnNode != null) {
                return returnNode;
            }
        }

        // Run the custom method.
        TreeOrderingOutput output = treeOrdering.ordering(node);
        switch (output) {
            case CONTINUE:
                break;
            case RETURN_AND_EXIT:
                return node;
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Object found = preOrder(this.root, (TreeNode<T> current) -> {
            if (current.equals(o)) {
                return TreeOrderingOutput.RETURN_AND_EXIT;
            } else {
                return TreeOrderingOutput.CONTINUE;
            }
        });
        return found != null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        List<T> objectPreOrdered = new ArrayList<>(this.size);
        this.preOrder(root, (TreeNode<T> current) -> {
            objectPreOrdered.add((T) current);
            return TreeOrderingOutput.CONTINUE;
        });

        return objectPreOrdered.toArray();
    }

    @Override
    public <M> M[] toArray(M[] a) {
        List<T> objectPreOrdered = new ArrayList<>(this.size);
        this.preOrder(root, (TreeNode<T> current) -> {
            objectPreOrdered.add((T) current);
            return TreeOrderingOutput.CONTINUE;
        });

        return objectPreOrdered.toArray(a);
    }

    @Override
    public boolean add(T e) {
        if (e == null) {
            return false;
        }

        // Check if root is existing.
        if (root == null) {
            // Set the root.
            root = new TreeNodeAdapter<>(e);
        } else {
            // Find the parent.
            TreeNode<T> parent = findParent(e);
            if (parent == null) {
                return false;
            }

            // Add this child to the parent's children.
            List<TreeNode<T>> children = parent.getChildren();
            children.add(new TreeNodeAdapter<>(e));
        }

        // Update the size.
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        boolean removed = false;

        // Check if it is data object.
        T data = null;
        try {
            data = (T) o;
        } catch (ClassCastException ignored) {
        }
        // Continue if it is.
        if (!removed && data != null) {
            TreeNode<T> parent = findParent(data);
            for (TreeNode<T> child : parent.getChildren()) {
                if (child.getData().equals(data)) {
                    parent.getChildren().remove(child);
                    removed = true;
                    break;
                }
            }
        }

        // Check if it is node.
        if (!removed && o instanceof TreeNodeData) {
            TreeNode<T> node = (TreeNode<T>) o;
            TreeNode<T> parent = findParent(node.getData());
            parent.getChildren().remove(node);
        }

        // Update the size in case it is removed.
        if (removed) {
            size -= 1;
        }

        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T current : c) {
            this.add(current);
        }
        return true;
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

    public List<T> getDataPreOrdered() {
        List<T> categoryPreOrdered = new LinkedList<>();
        this.preOrder(root, (TreeNode<T> current) -> {
            categoryPreOrdered.add(current.getData());
            return TreeOrderingOutput.CONTINUE;
        });
        return categoryPreOrdered;
    }
}
