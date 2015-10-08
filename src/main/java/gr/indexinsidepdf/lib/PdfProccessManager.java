package gr.indexinsidepdf.lib;

import gr.indexinsidepdf.lib.storage.PdfConfigurationManager;
import gr.indexinsidepdf.model.PdfNode;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTree;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTreeNode;
import gr.softaware.java_1_0.data.structure.tree.basic.TreeOrderingOutput;
import gr.softaware.java_1_0.data.types.FileType;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class PdfProccessManager {

    private static PdfProccessManager INSTANCE;
    private TreeTableView<PdfNode> treeTableView;
    private BasicTree<PdfNode> tree;
    private final Set<PdfNode> deletedNodes;

    private PdfProccessManager() {
        this.treeTableView = null;
        this.tree = null;
        this.deletedNodes = new HashSet<>();
    }

    public void setTreeTableView(TreeTableView<PdfNode> treeTableView) {
        this.treeTableView = treeTableView;
    }

    public BasicTree<PdfNode> getTree() {
        return tree;
    }

    public void clearDeletedNodes() {
        this.deletedNodes.clear();
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
        rootNode.setTitle(getTitle(rootFile));
        rootNode.setFileType(FileType.DIRECTORY);

        // Generate the tree to fill with files.
        tree = new BasicTree(rootNode);

        try {
            // Fill the tree.
            readFileSystem(rootFile);
        } catch (IOException ex) {
        }

        return tree;
    }

    private void readFileSystem(File rootDirectory) throws IOException {
        // The filename filter.
        FilenameFilter filenameFilter = (File file, String name) -> {
            return name.toLowerCase().endsWith(".pdf") || file.isDirectory();
        };

        // Create the root tree parentTreeItem again. The file is the only filed that equals is checking.
        PdfNode parentNode = new PdfNode();
        parentNode.setFile(rootDirectory);

        // Raad recurcively.
        for (File file : rootDirectory.listFiles(filenameFilter)) {
            // Check if this is a link of something else than directory or file.
            if (FileUtils.isSymlink(file) || (!file.isDirectory() && !file.isFile())) {
                continue;
            }
            // Create the child tree parentTreeItem if id doesn't exist.
            PdfNode childNode = new PdfNode();
            childNode.setFile(file);
            childNode.setTitle(getTitle(file));
            if (!deletedNodes.contains(childNode) && !tree.containsChild(parentNode, childNode)) {
                tree.add(parentNode, childNode);
            }

            if (file.isFile()) {
                childNode.setFileType(FileType.FILE);
            }
            if (file.isDirectory()) {
                childNode.setFileType(FileType.DIRECTORY);
                readFileSystem(file);
            }
        }
    }

    private String getTitle(File file) {
        if (file.isDirectory()) {
            return file.getName();
        }
        if (file.isFile()) {
            String filename = file.getName();
            int dotIndex = filename.lastIndexOf(".");

            return filename.substring(0, (dotIndex < 0 ? (int) filename.length() - 1 : dotIndex));
        }

        throw new IllegalArgumentException("Only directory or folred are allowed.");
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
        root.getTreeItem().setExpanded(false);

        // Read the tree and fill the root children.
        tree.preOrder(tree.getRoot(), tree.getRoot(), (BasicTreeNode parent, BasicTreeNode curent) -> {
            // Avoid the first run.
            if (curent == tree.getRoot()) {
                return TreeOrderingOutput.CONTINUE;
            }

            // Add the child to the parent's children.
            PdfNode parentTreeItem = (PdfNode) parent.getData();
            PdfNode childTreeItem = (PdfNode) curent.getData();
            if (!deletedNodes.contains(childTreeItem) && !parentTreeItem.getTreeItem().getChildren().contains(childTreeItem.getTreeItem())) {
                parentTreeItem.getTreeItem().getChildren().add(childTreeItem.getTreeItem());
            }
            return TreeOrderingOutput.CONTINUE;
        });

        // Create the only extisting column.
        TreeTableColumn<PdfNode, String> column = new TreeTableColumn<>("Περιεχόμενα");
        column.setCellValueFactory((CellDataFeatures<PdfNode, String> p) -> {
            TreeItem<PdfNode> treeItem = p.getValue();
            return treeItem.getValue().titlePoperty();
//            PdfNode value = (PdfNode) p.getValue();
//            String title = value.getTitle();
//            return new SimpleStringProperty(treeItem.get);
        });

        // Add the root and column to the tree table view.
        root.getTreeItem().setExpanded(true);
        treeTableView.setRoot(root.getTreeItem());
        treeTableView.getColumns().clear();
        treeTableView.getColumns().add(column);
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
        int index = parent.getTreeItem().getChildren().indexOf(node.getTreeItem());
        parent.getTreeItem().getChildren().remove(index);
        parent.getTreeItem().getChildren().add(index + STEP, node.getTreeItem());

        // Select the item again.
        treeTableView.getSelectionModel().select(node.getTreeItem());
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
        int index = parent.getTreeItem().getChildren().indexOf(node.getTreeItem());
        parent.getTreeItem().getChildren().remove(index);
        parent.getTreeItem().getChildren().add(index + STEP, node.getTreeItem());

        // Select the item again.
        treeTableView.getSelectionModel().select(node.getTreeItem());
        treeTableView.requestFocus();

        return true;
    }

    public void renameNode(PdfNode node, String name) {
        if (node == null || name == null) {
            throw new NullPointerException("Argument must not be null.");
        }

        // Rename the node.
        node.setTitle(name);

        // Request focus for this node.
        treeTableView.requestFocus();
    }

    public void removeNode(PdfNode node) {
        if (tree == null) {
            throw new NullPointerException("Tree must not be null.");
        }
        if (treeTableView == null) {
            throw new NullPointerException("TreeTableView must not be null.");
        }

        // Remove from the tree by changing the deleted property.
        PdfNode parent = tree.findParent(node);
        tree.remove(node);

        // Remember which nodes i deleted.
        deletedNodes.add(node);

        // Remove from the tree table view.
        parent.getTreeItem().getChildren().remove(node.getTreeItem());
        treeTableView.requestFocus();
    }

    public void refreshTree() {
        // Get selected model.
        TreeItem<PdfNode> selectedItem = treeTableView.getSelectionModel().getSelectedItem();

        try {
            // Read the file system again to find any new node and add it to the trees.
            readFileSystem(tree.getRoot().getData().getFile());
        } catch (IOException ex) {
        }
        buildTreeView();

        // Select again the item that was selected.
        if (selectedItem != null) {
            treeTableView.getSelectionModel().select(selectedItem);
            treeTableView.requestFocus();
        }
    }

    public void createPdf(final ProgressBar progressBar, final Label step3ProgressLabel, final Label progressResultLabel) {
        PdfFile pdfFile = new PdfFile.PdfFileBuilder()
                .tree(tree)
                .progressBar(progressBar)
                .progressLabel(step3ProgressLabel)
                .progressResultLabel(progressResultLabel)
                .build();
        pdfFile.create();
    }

    public void createPdfResult(List<PdfNode> brokenNodes) {

    }
}
