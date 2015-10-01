package gr.indexinsidepdf.lib.storage;

/**
 * Singleton class that will store the current settings.
 *
 * @author siggouroglou@gmail.com
 */
public class SettingsManager {

    private static SettingsManager INSTANCE;
    private boolean defaultLocation;
    private String pdfFolderPath;

    private SettingsManager() {
        defaultLocation = true;
        pdfFolderPath = "";
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
