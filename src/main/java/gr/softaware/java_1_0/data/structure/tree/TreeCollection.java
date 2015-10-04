package gr.softaware.java_1_0.data.structure.tree;

/**
 * A class representing a tree data structure.
 *
 * @author syggouroglou@gmail.com
 * @param <T> The type of the node in the tree.
 */
//public interface TreeCollection<T extends TreeNode> extends Collection<T> {
public interface TreeCollection<T extends TreeNode> {

    /**
     * Searching on the tree to find the specified node's data, depending on the
     * identifier.
     *
     * @param node The object that is stored as data in the tree.
     * @return the reference of the data field of the node or null if this node
     * is not existing on the tree.
     */
    public T find(T node);

    /**
     * Find the parent of a node.
     *
     * @param node the node that i am looking for the parent of.
     * @return the reference to the data field of the parent of the node given.
     */
    public T findParent(T node);

    /**
     * The size of the tree. Root node is always the first node in the tree.
     *
     * @return the current size of the tree.
     */
    public int size();

    /**
     * Returns true if the tree is empty, without root node.
     *
     * @return true if the tree is empty, without root node.
     */
    public boolean isEmpty();

    /**
     * Check if the node given is existing inside the tree.
     *
     * @param node the node to find if is existing in the tree.
     *
     * @return
     */
    public boolean contains(T node);

    /**
     * Find if the node is existing as a child on the given node. This is
     * fastest than contains cause the method must search only for the parent.
     * It checks then the list of the parent's children.
     *
     * @param parent the parent of the node i am checking if is existing in the
     * tree.
     * @param child the node i am checking if is existing in the tree.
     * @return true if the child is existing in the tree as a child of the
     * parent node given.
     */
    public boolean containsChild(T parent, T child);

    /**
     * Add the node as child of the parent given.
     *
     * <p>
     * This method will search for the parent in the tree and then add the node
     * in the children list of the parent.</p>
     *
     * @param parent The parent that must exists in the tree.
     * @param node The node to be added in the tree as child of parent node
     * given.
     * @return true if the insertion succeed. false if the insertion not succeed
     * cause the parent is not existing in the tree.
     */
    public boolean add(T parent, T node);

    /**
     * Remove the node from the tree.
     *
     * @param node The node to remove.
     * @return true if the node found and removed, false in all other cases.
     */
    public boolean remove(T node);

    /**
     * Clear all the nodes in the tree.
     */
    public void clear();
}
