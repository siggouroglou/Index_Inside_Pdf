package gr.softaware.java_1_0.data.structure.tree.depricated;

import java.util.List;

/**
 *
 * @author siggouroglou
 * @param <T>
 */
public interface TreeNode<T> {
    
    /**
     * Method to retrieve the saved data. Never return null.
     * @return the saved data. Never returns null.
     */
    public T getData();

    public void setData(T data);

    /**
     * Method to retrieve this node's children. Never return null.
     * @return a list with the children of this node. An empty list if no children exists. Never return null.
     */
    public List<TreeNode<T>> getChildren();

    public void setChildren(List<TreeNode<T>> children);

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
    
}
