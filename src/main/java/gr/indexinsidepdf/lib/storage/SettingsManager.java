package gr.indexinsidepdf.lib.storage;

import gr.indexinsidepdf.model.SettingsModel;

/**
 * Singleton class that will store the current settings.
 *
 * @author siggouroglou@gmail.com
 */
public class SettingsManager {

    private static SettingsManager INSTANCE;
    private SettingsModel settingsModel;

    private SettingsManager() {
        settingsModel = new SettingsModel();
        settingsModel.setFileName("Table_Of_Contents.pdf");
    }

    public static SettingsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsManager();
        }
        return INSTANCE;
    }

    public boolean isDefaultLocation() {
        return settingsModel.isDefaultLocation();
    }

    public void setDefaultLocation(boolean defaultLocation) {
        this.settingsModel.setDefaultLocation(defaultLocation);
    }

    public String getPdfFolderPath() {
        return settingsModel.getPdfFolderPath();
    }

    public void setPdfFolderPath(String pdfFolderPath) {
        this.settingsModel.setPdfFolderPath(pdfFolderPath);
    }

    public String getFileName() {
        return this.settingsModel.getFileName();
    }

    public void setFileName(String fileName) {
        this.settingsModel.setFileName(fileName);
    }

}
