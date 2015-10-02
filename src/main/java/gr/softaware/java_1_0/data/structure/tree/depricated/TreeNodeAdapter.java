package gr.softaware.java_1_0.data.structure.tree.depricated;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 * @param <T>
 */
public class TreeNodeAdapter<T> implements TreeNode<T> {
    private T data;
    private List<TreeNode<T>> children;

    public TreeNodeAdapter(T data) {
        this.data = data;
        this.children = new LinkedList<>();
    }

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public void setData(T data) {
        this.data  = data;
    }

    @Override
    public List<TreeNode<T>> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

}
