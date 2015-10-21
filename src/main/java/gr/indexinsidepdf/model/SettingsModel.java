package gr.indexinsidepdf.model;

import gr.indexinsidepdf.lib.storage.Language;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class SettingsModel {
    private boolean defaultLocation;
    private String pdfFolderPath;
    private String fileName;
    private Language language;

    public SettingsModel() {
        this.defaultLocation = true;
        this.pdfFolderPath = "";
        this.fileName = "";
        this.language = Language.ENGLISH;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
