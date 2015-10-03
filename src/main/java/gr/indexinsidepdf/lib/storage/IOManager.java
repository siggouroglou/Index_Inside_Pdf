package gr.indexinsidepdf.lib.storage;

import com.google.gson.Gson;
import gr.indexinsidepdf.model.CoverModel;
import gr.softaware.javafx_1_0.io.fileSaving.StorageFileManager;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * A utilities class that manages the saved state and the io of this
 * application.
 *
 * @author siggouroglou@gmail.com
 */
public final class IOManager {

    private static final Logger logger = Logger.getLogger(IOManager.class);

    private static IOManager INSTANCE;
    private final StorageFileManager coverFile;
    private final StorageFileManager indexFile;

    private IOManager() {
        this.coverFile = new StorageFileManager.StorageFileManagerBuilder()
                .errorTitle("Πρόβλημα")
                .errorHeader(null)
                .errorText(null)
                .storageFileManagerStrategy((file) -> {
                    Gson gson = new Gson();
                    String json = gson.toJson(CoverManager.getInstance().getCoverModel(), CoverModel.class);
                    try {
                        FileUtils.writeStringToFile(file, json);
                    } catch (IOException ex) {
                        logger.error("Οι ρυθμίσεις δεν αποθηκεύθηκαν στο αρχείο για το εξώφυλλο.", ex);
                    }
                })
                .build();
        this.indexFile = new StorageFileManager.StorageFileManagerBuilder()
                .build();
    }

    public static IOManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IOManager();
        }
        return INSTANCE;
    }

    public BooleanProperty coverSavedProperty() {
        return coverFile.getSavedProperty();
    }

    public BooleanProperty indexSaveProperty() {
        return indexFile.getSavedProperty();
    }

    public void questionForSaveCover(Stage stage) {
        coverFile.questionForSave(stage);
    }

    public void questionForSaveIndex(Stage stage) {
        indexFile.questionForSave(stage);
    }

    public void questionForSaveBoth(Stage stage) {
        // Create the buttons.
        ButtonType yesButton = new ButtonType("Ναι", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("Όχι", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Generate the confirmation modal.
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Αποθήκευση Αρχείων");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Θέλετε να αποθηκεύσετε τις αλλαγές στο ευρετήριο και στο εξώφυλλο?");
        confirmation.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = confirmation.showAndWait();

        // User click to ok button.
        if (result.get().equals(ButtonType.OK)) {
            coverFile.save(stage);
            indexFile.save(stage);
        }
    }

    public void saveCover(Stage stage) {
        coverFile.save(stage);
    }

    public void saveIndex(Stage stage) {
        indexFile.save(stage);
    }

    public void chooseFile(Stage stage, TextField textField) {
        String fullPath = coverFile.selectFile(stage, textField);

        if (fullPath == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Πρόβλημα");
            error.setHeaderText(null);
            error.setContentText("Το αρχείο που δώσατε δεν είναι αποδεκτό.");
            error.show();
        }
    }

    public boolean loadCover(Stage stage) {
        try {
            String json = FileUtils.readFileToString(coverFile.getFile());
            CoverModel coverModelNew = new Gson().fromJson(json, CoverModel.class);
            CoverModel coverModel = CoverManager.getInstance().getCoverModel();
            BeanUtils.copyProperties(coverModel, coverModelNew);
            return true;
        } catch (IOException ex) {
            logger.error("Το αρχείο δεν βρέθηκε για να γίνει load στο εξώφυλλο.", ex);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            logger.error("Δεν μπόρεσε να μεταφραστεί το αρχείο από json σε object.", ex);
        } catch(Exception ex) {
            logger.error("Δεν μπόρεσε να μεταφραστεί το αρχείο από json σε object για γενικό λάθος.", ex);
        }
        return false;
    }

}
