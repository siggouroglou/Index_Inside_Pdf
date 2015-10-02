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
    }

    public static SettingsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsManager();
        }
        return INSTANCE;
    }

    public static SettingsManager getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(SettingsManager INSTANCE) {
        SettingsManager.INSTANCE = INSTANCE;
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

}
