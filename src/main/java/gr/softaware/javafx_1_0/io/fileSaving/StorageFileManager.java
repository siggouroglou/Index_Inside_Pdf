package gr.softaware.javafx_1_0.io.fileSaving;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class StorageFileManager {

    private File file;
    private final BooleanProperty saved;
    private final List<FileChooser.ExtensionFilter> extensionFilters;
    private final StorageFileManagerStrategy storageFileManagerStrategy;
    // Question for save.
    private final String questionForSaveText;
    private final String questionForSaveHeader;
    private final String questionForSaveTitle;
    private final String questionForSaveYesButtonText;
    private final String questionForSaveNoButtonText;
    // Save file.
    private final String saveFileChooserTitle;
    // SaveAs file.
    private final String saveAsFileChooserTitle;
    // Choose file.
    private final String selectFileChooserTitle;
    // Error while saving.
    private final String errorTitle;
    private final String errorHeader;
    private final String errorText;

    private StorageFileManager(StorageFileManagerBuilder builder) {
        this.file = null;
        this.saved = new SimpleBooleanProperty(true);
        this.extensionFilters = builder.extensionFilters;
        this.storageFileManagerStrategy = builder.storageFileManagerStrategy;
        // Question for save.
        this.questionForSaveText = builder.questionForSaveContent;
        this.questionForSaveHeader = builder.questionForSaveText;
        this.questionForSaveTitle = builder.questionForSaveTitle;
        this.questionForSaveYesButtonText = builder.questionForSaveYesButtonText;
        this.questionForSaveNoButtonText = builder.questionForSaveNoButtonText;
        // Save file.
        this.saveFileChooserTitle = builder.saveFileChooserTitle;
        // Save file.
        this.saveAsFileChooserTitle = builder.saveAsFileChooserTitle;
        // Choose file.
        this.selectFileChooserTitle = builder.selectFileChooserTitle;
        // Error while saving.
        this.errorTitle = builder.errorTitle;
        this.errorHeader = builder.errorHeader;
        this.errorText = builder.errorText;
    }

    public static class StorageFileManagerBuilder {

        private List<FileChooser.ExtensionFilter> extensionFilters;
        private StorageFileManagerStrategy storageFileManagerStrategy;
        // Question for save.
        private String questionForSaveContent;
        private String questionForSaveText;
        private String questionForSaveTitle;
        private String questionForSaveYesButtonText;
        private String questionForSaveNoButtonText;
        // Save file.
        private String saveFileChooserTitle;
        // SaveAs file.
        private String saveAsFileChooserTitle;
        // Choose file.
        private String selectFileChooserTitle;
        // Error while saving.
        private String errorTitle;
        private String errorHeader;
        private String errorText;

        public StorageFileManagerBuilder() {
            this.extensionFilters = new ArrayList<>();
            this.storageFileManagerStrategy = null;
            // Question for save.
            this.questionForSaveContent = "Θα θέλατε να αποθηκεύσετε τις αλλαγές που έχετε κάνει?";
            this.questionForSaveText = null;
            this.questionForSaveTitle = "Αποθήκευση Αλλαγών";
            this.questionForSaveYesButtonText = "Ναι";
            this.questionForSaveNoButtonText = "Όχι";
            // Save file.
            this.saveFileChooserTitle = "Αποθήκευση - Επιλογή αρχείου";
            // SaveAs file.
            this.saveAsFileChooserTitle = "Αποθήκευση Ως - Επιλογή αρχείου";
            // SaveAs file.
            this.selectFileChooserTitle = "Επιλογή αρχείου";
            // Error while saving.
            this.errorTitle = "Πρόβλημα";
            this.errorHeader = "Δημιουργήθηκε ένα πρόβλημα κατα τη δημιουργία του αρχείου!";
            this.errorText = null;
        }

        /**
         * A list of filters for using when showing the file chooser.
         *
         * @param extensionFilters
         * @return
         */
        public StorageFileManagerBuilder extensionFilters(List<FileChooser.ExtensionFilter> extensionFilters) {
            this.extensionFilters = extensionFilters;
            return this;
        }

        /**
         * Set the strategy that will be run after the call of the save methods.
         *
         * @param storageFileManagerStrategy A strategy that will run when the
         * file will be saved.
         * @return
         */
        public StorageFileManagerBuilder storageFileManagerStrategy(StorageFileManagerStrategy storageFileManagerStrategy) {
            this.storageFileManagerStrategy = storageFileManagerStrategy;
            return this;
        }

        /**
         * The content of the modal when requesting a question to user if he/she
         * wish to save changes.
         *
         * @param questionForSaveContent The down content of the question for
         * save modal.
         * @return
         */
        public StorageFileManagerBuilder questionForSaveContent(String questionForSaveContent) {
            this.questionForSaveContent = questionForSaveContent;
            return this;
        }

        /**
         * Set the text for the upper text of the question for save modal.
         *
         * @param questionForSaveText The upper text of the question for save
         * modal.
         * @return
         */
        public StorageFileManagerBuilder questionForSaveText(String questionForSaveText) {
            this.questionForSaveText = questionForSaveText;
            return this;
        }

        /**
         * Set the text for the title of the question for save modal.
         *
         * @param questionForSaveTitle The title of the question for save modal.
         * @return
         */
        public StorageFileManagerBuilder questionForSaveTitle(String questionForSaveTitle) {
            this.questionForSaveTitle = questionForSaveTitle;
            return this;
        }

        /**
         * Set the text for the text of the YES button of the question for save
         * modal.
         *
         * @param questionForSaveYesButtonText The text of the YES button of the
         * question for save modal.
         * @return
         */
        public StorageFileManagerBuilder questionForSaveYesButtonText(String questionForSaveYesButtonText) {
            this.questionForSaveYesButtonText = questionForSaveYesButtonText;
            return this;
        }

        /**
         * Set the text for the text of the NO button of the question for save
         * modal.
         *
         * @param questionForSaveNoButtonText The text of the NO button of the
         * question for save modal.
         * @return
         */
        public StorageFileManagerBuilder questionForSaveNoButtonText(String questionForSaveNoButtonText) {
            this.questionForSaveNoButtonText = questionForSaveNoButtonText;
            return this;
        }

        /**
         * Set the text to appear as title on the file chooser when asking for
         * saving for first time.
         *
         * @param saveFileChooserTitle The text to appear as title on the file
         * chooser when asking for saving for first time.
         * @return
         */
        public StorageFileManagerBuilder saveFileChooserTitle(String saveFileChooserTitle) {
            this.saveFileChooserTitle = saveFileChooserTitle;
            return this;
        }

        /**
         * Set the text to appear as title on the file chooser when asking for
         * saving as for first time.
         *
         * @param saveAsFileChooserTitle The text to appear as title on the file
         * chooser when asking for saving as for first time.
         * @return
         */
        public StorageFileManagerBuilder saveAsFileChooserTitle(String saveAsFileChooserTitle) {
            this.saveAsFileChooserTitle = saveAsFileChooserTitle;
            return this;
        }

        /**
         * Set the text to appear as title on the file chooser when choosing a
         * file.
         *
         * @param selectFileChooserTitle The text to appear as title on the file
         * chooser when choosing a file.
         * @return
         */
        public StorageFileManagerBuilder selectFileChooserTitle(String selectFileChooserTitle) {
            this.selectFileChooserTitle = selectFileChooserTitle;
            return this;
        }

        /**
         * Set the title to appear if an error occurred while creating the file
         * chosen for saving changes.
         *
         * @param errorTitle The title to appear if an error occurred while
         * creating the file chosen for saving changes
         * @return
         */
        public StorageFileManagerBuilder errorTitle(String errorTitle) {
            this.errorTitle = errorTitle;
            return this;
        }

        /**
         * Set the header to appear if an error occurred while creating the file
         * chosen for saving changes.
         *
         * @param errorHeader The header to appear if an error occurred while
         * creating the file chosen for saving changes
         * @return
         */
        public StorageFileManagerBuilder errorHeader(String errorHeader) {
            this.errorHeader = errorHeader;
            return this;
        }

        /**
         * Set the text to appear if an error occurred while creating the file
         * chosen for saving changes. If it is null then the exception message
         * is used.
         *
         * @param errorText The text to appear if an error occurred while
         * creating the file chosen for saving changes.If it is null then the
         * exception message is used.
         * @return
         */
        public StorageFileManagerBuilder errorText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        public StorageFileManager build() {
            return new StorageFileManager(this);
        }
    }

    /**
     * This property is managed by the user and helps to know if changes happen
     * and must be saved.
     *
     * @return
     */
    public BooleanProperty getSavedProperty() {
        return saved;
    }

    /**
     * Get the value of the savedProperty.
     *
     * @return the value of the savedProperty.
     */
    public boolean getSaved() {
        return saved.get();
    }

    /**
     * Set the value of the savedProperty.
     *
     * @param saved return the value of the savedProperty.
     */
    public void setSaved(boolean saved) {
        this.saved.set(saved);
    }

    /**
     * Returns a copy of the file object.
     *
     * @return a copy of the file object.
     */
    public File getFile() {
        return file == null ? null : new File(file.getAbsolutePath());
    }

    /**
     * Returns a copy of the extension filter of this object.
     *
     * @return a copy of the extension filter of this object.
     */
    public List<FileChooser.ExtensionFilter> getExtensionFilters() {
        return new ArrayList<>(extensionFilters);
    }

    /**
     * Clears this object's list and inserts the new items from the list
     * provided.
     *
     * @param extensionFilters A list with items that will override the existing
     * items.
     */
    public void setExtensionFilters(List<FileChooser.ExtensionFilter> extensionFilters) {
        this.extensionFilters.clear();
        extensionFilters.stream().forEach((filter) -> {
            this.extensionFilters.add(filter);
        });
    }

    /**
     * Method that opens a modal with question for saving this file. If user
     * clicks on the YES button then the save method is invoked.
     *
     * @param stageParent The parent stage for the modal.
     * 
     * @throws NullPointerException in case of a null stage.
     */
    public void questionForSave(Stage stageParent) {
        if(stageParent == null){
            throw new NullPointerException("Argument must not be null");
        }
        
        // Create the buttons.
        ButtonType yesButton = new ButtonType(questionForSaveYesButtonText, ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType(questionForSaveNoButtonText, ButtonData.CANCEL_CLOSE);

        // Generate the confirmation modal.
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(questionForSaveTitle);
        confirmation.setHeaderText(questionForSaveHeader);
        confirmation.setContentText(questionForSaveText);
        confirmation.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = confirmation.showAndWait();

        // User click to ok button.
        if (result.get().equals(ButtonType.OK)) {
            save(stageParent);
        }
    }

    /**
     * Select a file if there is not selected yet and save the changes using the
     * StorageFileManagerStrategy. In case the file is not existing create it.
     *
     * @param stageParent The parent stage of this modal.
     *
     * @throws NullPointerException in case of a null stage or file.
     */
    public void save(Stage stageParent) {
        if (stageParent == null) {
            throw new NullPointerException("Argument stageParent must not be null.");
        }
        if (file == null) {
            throw new NullPointerException("The state of this object i not correct. The file property must not be null.");
        }

        // Has user selected a file.
        if (!file.exists()) {
            // Select the file to save to.
            FileChooser choose = new FileChooser();
            choose.setTitle(saveFileChooserTitle);
            if (extensionFilters != null) {
                choose.getExtensionFilters().addAll(extensionFilters);
            }
            file = choose.showOpenDialog(stageParent);
            if (file == null || !file.isFile()) {
                try {
                    FileUtils.touch(file);
                } catch (IOException ex) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle(errorTitle);
                    error.setHeaderText(errorHeader);
                    error.setContentText(errorText == null ? ex.getMessage() : errorText);
                    error.show();
                    return;
                }
            }
        }

        // Save the file.
        storageFileManagerStrategy.saveFile(file);
    }

    /**
     * Save to the given path using the StorageFileManagerStrategy. In case the
     * filePath is not existing create it. <br/>
     * This method checks the arguments and runs the save(stage, file) method.
     *
     * @param stageParent The parent stage of this modal.
     * @param filePath The path to the file that will be used for saving. This
     * file path will be stored to the object for further use.
     *
     * @throws NullPointerException in case of a null arguments.
     */
    public void save(Stage stageParent, String filePath) {
        if (stageParent == null) {
            throw new NullPointerException("Argument stageParent must not be null.");
        }
        if (filePath == null) {
            throw new NullPointerException("Argument filePath must not be null.");
        }

        save(stageParent, new File(filePath));
    }

    /**
     * Save to file given and use the StorageFileManagerStrategy. In case the
     * file is not existing create it.
     *
     * @param stageParent The parent stage of this modal.
     * @param newFile The file to use to save. This file will be saved for
     * further saving.
     *
     * @throws NullPointerException in case of a null stage or file.
     * @throws IllegalArgumentException in case of the given file is a directory
     * etc but not a file.
     */
    public void save(Stage stageParent, File newFile) {
        if (stageParent == null) {
            throw new NullPointerException("Argument stageParent must not be null.");
        }
        if (newFile == null) {
            throw new NullPointerException("Argument newFile must not be null.");
        }
        if (newFile.exists() && !newFile.isFile()) {
            throw new IllegalArgumentException("File must be a type of file (not deirectory etc).");
        }

        // Override the file field.
        file = newFile;
        if (!file.exists()) {
            try {
                FileUtils.touch(file);
            } catch (IOException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(errorTitle);
                error.setHeaderText(errorHeader);
                error.setContentText(errorText == null ? ex.getMessage() : errorText);
                error.show();
                return;
            }
        }

        // Save to the file.
        storageFileManagerStrategy.saveFile(file);
    }

    /**
     * Select a file to save and this file will be used for later saving and
     * save the changes using the StorageFileManagerStrategy.
     *
     * @param stageParent The parent stage of this modal.
     *
     * @throws NullPointerException in case of a null stage.
     */
    public void saveAs(Stage stageParent) {
        if (stageParent == null) {
            throw new NullPointerException("Argument stageParent must not be null.");
        }
        
        // Select the file to save to.
        FileChooser choose = new FileChooser();
        choose.setTitle(saveAsFileChooserTitle);
        if (extensionFilters != null) {
            choose.getExtensionFilters().addAll(extensionFilters);
        }
        file = choose.showOpenDialog(stageParent);
        if (file == null || !file.isFile()) {
            try {
                FileUtils.touch(file);
            } catch (IOException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle(errorTitle);
                error.setHeaderText(errorHeader);
                error.setContentText(errorText == null ? ex.getMessage() : errorText);
                error.show();
                return;
            }
        }

        // Save the file.
        storageFileManagerStrategy.saveFile(file);
    }

    /**
     * Select a file and put the path to a TextInputControl. Returns the full
     * path or in case of error null.
     *
     * @param stageParent The parent stage of this modal.
     * @param textField a TextInputControl that will get the absolute path as
     * text.
     * @return the full path or in case of error null.
     */
    public String selectFile(Stage stageParent, TextInputControl textField) {
        // Select the file.
        FileChooser choose = new FileChooser();
        choose.setTitle(selectFileChooserTitle);
        if (extensionFilters != null) {
            choose.getExtensionFilters().addAll(extensionFilters);
        }
        file = choose.showOpenDialog(stageParent);
        if (file == null || !file.isFile()) {
            return null;
        }

        // Get the full path.
        String filePath = file.getAbsolutePath();

        // Fill the file name to the text field.
        if (textField != null) {
            textField.setText(filePath);
        }

        // Return the full path.
        return filePath;
    }
}
