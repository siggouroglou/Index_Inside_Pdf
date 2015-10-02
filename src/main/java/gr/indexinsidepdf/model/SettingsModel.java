package gr.indexinsidepdf.model;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class SettingsModel {
    private boolean defaultLocation;
    private String pdfFolderPath;

    public SettingsModel() {
        this.defaultLocation = true;
        this.pdfFolderPath = "";
    }

    public boolean isDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(boolean defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public String getPdfFolderPath() {
        return pdfFolderPath;
    }

    public void setPdfFolderPath(String pdfFolderPath) {
        this.pdfFolderPath = pdfFolderPath;
    }
}
