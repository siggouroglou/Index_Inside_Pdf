package gr.softaware.java_1_0.data.structure.tree.basic;

import gr.softaware.java_1_0.data.structure.tree.TreeNode;
import java.util.Collection;

/**
 * A class representing a tree data structure.
 *
 * @author siggouroglou
 * @param <T>
 */
public interface TreeCollection<T extends TreeNode> extends Collection<T> {

    /**
     * Searching on the tree with preorder to find the specified node's data,
     * depending on the identifier. It uses the equals/hashCode functions of the
     * data T.
     *
     * @param node The object that is stored as data in the tree.
     * @return the reference of the data field of the node or null if this node
     * is not existing on the tree.
     */
    public T find(T node);

//    /**
//     * This method is reading all nodes on the tree using pre ordering. On every
//     * node it reads runs the treeOrdering method. If this method return the
//     * code RETURN_AND_EXIT then it returns the current node. If this method
//     * return the code CONTINUE then it continues reading the tree nodes.
//     *
//     * @param node The root node to start reading.
//     * @param treeOrdering The method to run on each node reading.
//     * @return null or a tree node depending on the treeOrdering method.
//     */
//    public TreeNode preOrder(TreeNode node, TreeOrderContinueStrategy<T> treeOrdering);
//
//    /**
//     * This method is reading all nodes on the tree using in ordering. On every
//     * node it reads runs the treeOrdering method. If this method return the
//     * code RETURN_AND_EXIT then it returns the current node. If this method
//     * return the code CONTINUE then it continues reading the tree nodes.
//     *
//     * @param node The root node to start reading.
//     * @param treeOrdering The method to run on each node reading.
//     * @return null or a tree node depending on the treeOrdering method.
//     */
//    public TreeNode inOrder(TreeNode node, TreeOrderContinueStrategy<T> treeOrdering);
//
//    /**
//     * This method is reading all nodes on the tree using post ordering. On
//     * every node it reads runs the treeOrdering method. If this method return
//     * the code RETURN_AND_EXIT then it returns the current node. If this method
//     * return the code CONTINUE then it continues reading the tree nodes.
//     *
//     * @param node The root node to start reading.
//     * @param treeOrdering The method to run on each node reading.
//     * @return null or a tree node depending on the treeOrdering method.
//     */
//    public TreeNode postOrder(TreeNode node, TreeOrderContinueStrategy<T> treeOrdering);
}
