package gr.indexinsidepdf.model;

import gr.softaware.java_1_0.data.structure.tree.TreeNode;
import gr.softaware.java_1_0.data.types.FileType;
import java.io.File;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class PdfNode implements TreeNode {

    private String title;
    private final StringProperty titlePoperty;
    private File file;
    private FileType fileType;
    private final TreeItem treeItem;

    public PdfNode() {
        this.title = "";
        this.titlePoperty = new SimpleStringProperty("");
        this.file = null;
        this.fileType = FileType.FILE;
        this.treeItem = new TreeItem(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.titlePoperty.set(title);
    }

    public StringProperty titlePoperty() {
        this.titlePoperty.set(title);
        return titlePoperty;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public TreeItem getTreeItem() {
        return treeItem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.file);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PdfNode other = (PdfNode) obj;
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        return true;
    }

}
