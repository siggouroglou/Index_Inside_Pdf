package gr.indexinsidepdf.lib;

import gr.indexinsidepdf.lib.storage.PdfConfigurationManager;
import gr.indexinsidepdf.model.PdfNode;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTree;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTreeNode;
import gr.softaware.java_1_0.data.structure.tree.basic.TreeOrderingOutput;
import gr.softaware.java_1_0.data.types.FileType;
import java.io.File;
import java.io.FilenameFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class PdfProccessManager {

    private static PdfProccessManager INSTANCE;
    private TreeTableView<PdfNode> treeTableView;
    private BasicTree<PdfNode> tree;

    private PdfProccessManager() {
        this.treeTableView = null;
        this.tree = null;
    }

    public void setTreeTableView(TreeTableView<PdfNode> treeTableView) {
        this.treeTableView = treeTableView;
    }

    public BasicTree<PdfNode> getTree() {
        return tree;
    }

    public static PdfProccessManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PdfProccessManager();
        }
        return INSTANCE;
    }

    public BasicTree<PdfNode> generateTree() {
        // Get the root directory.
        File rootFile = PdfConfigurationManager.getInstance().getPdfConfiguration().getSrcFolder();

        // Create the root tree parentTreeItem.
        PdfNode rootNode = new PdfNode();
        rootNode.setFile(rootFile);
        rootNode.setTitle(rootFile.getName());
        rootNode.setFileType(FileType.DIRECTORY);

        // Generate the tree to fill with files.
        tree = new BasicTree(rootNode);

        // Fill the tree.
        readFileSystem(rootFile);

        return tree;
    }

    private void readFileSystem(File rootDirectory) {
        // The filename filter.
        FilenameFilter filenameFilter = (File file, String name) -> {
            return name.toLowerCase().endsWith(".pdf") || file.isDirectory();
        };

        // Create the root tree parentTreeItem.
        PdfNode parentNode = new PdfNode();
        parentNode.setFile(rootDirectory);
        parentNode.setTitle(rootDirectory.getName());
        parentNode.setFileType(FileType.DIRECTORY);

        // Raad recurcively.
        for (File file : rootDirectory.listFiles(filenameFilter)) {
            // Create the child tree parentTreeItem.
            PdfNode childNode = new PdfNode();
            childNode.setFile(file);
            childNode.setTitle(file.getName());
            tree.add(parentNode, childNode);

            if (file.isFile()) {
                parentNode.setFileType(FileType.FILE);
            }
            if (file.isDirectory()) {
                parentNode.setFileType(FileType.DIRECTORY);
                readFileSystem(file);
            }
        }
    }

    public void buildTreeView() {
        if (treeTableView == null) {
            throw new NullPointerException("Argument must not be null.");
        }

        // Clear the existing root.
        if (treeTableView.getRoot() != null) {
            treeTableView.setRoot(null);
        }

        // Create the new root.
        PdfNode root = (PdfNode) tree.getRoot().getData();
        root.setExpanded(false);

        // Read the tree and fill the root children.
        tree.preOrder(tree.getRoot(), tree.getRoot(), (BasicTreeNode parent, BasicTreeNode curent) -> {
            // Avoid the first run.
            if (curent == tree.getRoot()) {
                return TreeOrderingOutput.CONTINUE;
            }

            // Add the child to the parent's children.
            PdfNode parentTreeItem = (PdfNode) parent.getData();
            PdfNode childTreeItem = (PdfNode) curent.getData();
            parentTreeItem.getChildren().add(childTreeItem);
            return TreeOrderingOutput.CONTINUE;
        });

        // Create the only extisting column.
        TreeTableColumn<PdfNode, String> column = new TreeTableColumn<>("Περιεχόμενα");
        column.setCellValueFactory((CellDataFeatures<PdfNode, String> p) -> {
            PdfNode value = (PdfNode) p.getValue();
            String title = value.getTitle();
            return new SimpleStringProperty(title);
        });

        // Add the root to the tree table view.
        root.setExpanded(true);
        treeTableView.setRoot(root);
        treeTableView.getColumns().add(column);
    }

    public void removeNode(PdfNode node) {
        if (tree == null) {
            throw new NullPointerException("Tree must not be null.");
        }
        if (treeTableView == null) {
            throw new NullPointerException("TreeTableView must not be null.");
        }

        // Find the parent of this node.
        PdfNode parent = tree.findParent(node);

        // Remove from the tree.
        tree.remove(node);

        // Remove from the tree table view.
        parent.getChildren().remove(node);
    }

    public boolean moveNodeUp(PdfNode node) {
        if (node == null) {
            throw new NullPointerException("Argument must not be null.");
        }
        final int STEP = -1;

        // Move this child in tree.
        try {
            tree.moveWithStep(node, STEP);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        // Move the child to the treeTableView.
        PdfNode parent = tree.findParent(node);
        int index = parent.getChildren().indexOf(node);
        parent.getChildren().remove(index);
        parent.getChildren().add(index + STEP, node);
        
        // Select the item again.
        treeTableView.getSelectionModel().select(node);
        treeTableView.requestFocus();

        return true;
    }

    public boolean moveNodeDown(PdfNode node) {
        if (node == null) {
            throw new NullPointerException("Argument must not be null.");
        }
        final int STEP = 1;

        // Move this child in tree.
        try {
            tree.moveWithStep(node, STEP);
        } catch (IllegalArgumentException ex) {
            return false;
        }

        // Move the child to the treeTableView.
        PdfNode parent = tree.findParent(node);
        int index = parent.getChildren().indexOf(node);
        parent.getChildren().remove(index);
        parent.getChildren().add(index + STEP, node);
        
        // Select the item again.
        treeTableView.getSelectionModel().select(node);
        treeTableView.requestFocus();

        return true;
    }
}
