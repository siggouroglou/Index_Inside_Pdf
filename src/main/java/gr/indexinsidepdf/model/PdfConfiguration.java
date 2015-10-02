package gr.indexinsidepdf.model;

import java.io.File;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class PdfConfiguration {

    private File srcFolder;

    public PdfConfiguration() {
        this.srcFolder = null;
    }

    public File getSrcFolder() {
        return srcFolder;
    }

    public void setSrcFolder(File srcFolder) {
        this.srcFolder = srcFolder;
    }
}
