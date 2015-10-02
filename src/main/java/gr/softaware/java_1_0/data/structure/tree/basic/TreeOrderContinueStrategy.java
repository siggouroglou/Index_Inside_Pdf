package gr.softaware.java_1_0.data.structure.tree.basic;

import gr.softaware.java_1_0.data.structure.tree.TreeNode;


/**
 *
 * @author siggouroglou
 * @param <T>
  */
public interface TreeOrderContinueStrategy<T extends TreeNode> {

    public TreeOrderingOutput ordering(BasicTreeNode<T> parent, BasicTreeNode<T> current);
}
