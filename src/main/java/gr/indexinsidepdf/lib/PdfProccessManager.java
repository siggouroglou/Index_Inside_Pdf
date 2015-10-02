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
    private BasicTree<PdfNode> tree;

    private PdfProccessManager() {
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

    public void buildTreeView(TreeTableView<PdfNode> treeTableView) {
        if(treeTableView == null){
            throw new NullPointerException("Argument must not be null.");
        }
        
        // Clear the existing root.
        if(treeTableView.getRoot() != null){
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
            PdfNode value = (PdfNode)p.getValue();
            String title = value.getTitle();
            return new SimpleStringProperty(title);
        });

        // Add the root to the tree table view.
        root.setExpanded(true);
        treeTableView.setRoot(root);
        treeTableView.getColumns().add(column);
    }
}
