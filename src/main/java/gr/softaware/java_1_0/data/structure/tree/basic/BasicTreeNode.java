package gr.softaware.java_1_0.data.structure.tree.basic;

import gr.softaware.java_1_0.data.structure.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is package private.
 *
 * @author siggouroglou@gmail.com
 * @param <T> Every class that implements or extends a class that implement the
 * TreeNode interface.
 */
public class BasicTreeNode<T extends TreeNode> {

    private T data;
    private List<BasicTreeNode<T>> children;

    public BasicTreeNode() {
        this.data = null;
        this.children = null;
    }

    public BasicTreeNode(T data) {
        this.data = data;
        this.children = null;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<BasicTreeNode<T>> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>(5);
        }

        return this.children;
    }

}
