package gr.indexinsidepdf.model;

import gr.softaware.java_1_0.data.structure.tree.TreeNode;
import gr.softaware.java_1_0.data.types.FileType;
import java.io.File;
import java.util.Objects;
import javafx.scene.control.TreeItem;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class PdfNode extends TreeItem implements TreeNode {
    
    private String title;
    private File file;
    private FileType fileType;
    private boolean deleted;

    public PdfNode() {
        super();
        this.title = "";
        this.file = null;
        this.fileType = FileType.FILE;
        this.deleted = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
