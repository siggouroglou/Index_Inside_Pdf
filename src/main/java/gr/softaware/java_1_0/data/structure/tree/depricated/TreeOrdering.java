package gr.softaware.java_1_0.data.structure.tree.depricated;

/**
 *
 * @author siggouroglou
 * @param <T>
  */
public interface TreeOrdering<T extends TreeNodeData> {

    public TreeOrderingOutput ordering(TreeNode<T> current);
}
