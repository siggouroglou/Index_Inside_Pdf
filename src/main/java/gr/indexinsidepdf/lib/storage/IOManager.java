package gr.indexinsidepdf.lib.storage;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import gr.indexinsidepdf.lib.PdfProccessManager;
import gr.indexinsidepdf.model.CoverModel;
import gr.indexinsidepdf.model.PdfNode;
import gr.softaware.java_1_0.data.structure.tree.basic.BasicTreeNode;
import gr.softaware.javafx_1_0.io.fileSaving.StorageFileManager;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * A utilities class that manages the saved state and the io of this
 * application.
 *
 * @author syggouroglou@gmail.com
 */
public final class IOManager {

    private static final Logger logger = Logger.getLogger(IOManager.class);

    private static IOManager INSTANCE;
    private final StorageFileManager coverFile;
    private final StorageFileManager indexFile;
    private final BooleanProperty indexImported;

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
                .errorTitle("Πρόβλημα")
                .errorHeader(null)
                .errorText(null)
                .storageFileManagerStrategy((file) -> {
                    Gson gson = new GsonBuilder()
                    .addSerializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                            return false;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> aClass) {
                            return aClass.equals(TreeItem.class) || aClass.equals(StringProperty.class);
                        }
                    })
                    .create();
                    final Type basicTreeNodeType = new TypeToken<BasicTreeNode<PdfNode>>() {
                    }.getType();
                    String json = gson.toJson(PdfProccessManager.getInstance().getTree().getRoot(), basicTreeNodeType);
                    try {
                        FileUtils.writeStringToFile(file, json);
                    } catch (IOException ex) {
                        logger.error("Οι ρυθμίσεις δεν αποθηκεύθηκαν στο αρχείο για το εξώφυλλο.", ex);
                    }
                })
                .build();
        this.indexImported = new SimpleBooleanProperty(false);
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

    public BooleanProperty indexSavedProperty() {
        return indexFile.getSavedProperty();
    }

    public BooleanProperty indexImportedProperty() {
        return indexImported;
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

    public void saveCover(String filePath) {
        coverFile.save(filePath);
        coverSavedProperty().set(true);
    }

    public void saveIndex(String filePath) {
        indexFile.save(filePath);
        indexSavedProperty().set(true);
    }

    public void chooseCoverFile(Stage stage, TextField textField) {
        File file = coverFile.selectFile(stage, textField);
        if (file == null) {
            return;
        }
    }

    public void chooseIndexFile(Stage stage, TextField textField) {
        File file = indexFile.selectFile(stage, textField);
        if (file == null) {
            return;
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
        } catch (Exception ex) {
            logger.error("Δεν μπόρεσε να μεταφραστεί το αρχείο από json σε object για γενικό λάθος.", ex);
        }
        return false;
    }

    public boolean loadIndex(Stage stage) {
        Gson gson = new GsonBuilder()
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return aClass.equals(TreeItem.class) || aClass.equals(StringProperty.class);
                    }
                })
                .create();
        try {
            // Get the root.
            String json = FileUtils.readFileToString(indexFile.getFile());
            final Type basicTreeNodeType = new TypeToken<BasicTreeNode<PdfNode>>() {
            }.getType();
            BasicTreeNode<PdfNode> rootNew = gson.fromJson(json, basicTreeNodeType);
            if (rootNew == null || rootNew.getData() == null) {
                return false;
            }

            // Clear deleted cache.
            PdfProccessManager.getInstance().clearDeletedNodes();

            // Set the new root to the the tree.
            PdfProccessManager.getInstance().getTree().setRoot(rootNew);

            // Rebuild tree view.
            PdfProccessManager.getInstance().buildTreeView();
            return true;
        } catch (IOException ex) {
            logger.error("Το αρχείο δεν βρέθηκε για να γίνει load στο εξώφυλλο.", ex);
        } catch (Exception ex) {
            logger.error("Δεν μπόρεσε να μεταφραστεί το αρχείο από json σε object για γενικό λάθος.", ex);
        }
        return false;
    }

}
