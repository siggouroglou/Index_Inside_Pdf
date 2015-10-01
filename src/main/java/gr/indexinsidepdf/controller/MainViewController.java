package gr.indexinsidepdf.controller;

import gr.indexinsidepdf.lib.storage.IOManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author syggouroglou@gmail.com
 */
public class MainViewController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TreeView<?> treeView;
    @FXML
    private MenuItem fileMenuExportCoverItem;
    @FXML
    private Button step2ExportIndexButton;

    @FXML
    private Label step3ProgressLabel;

    @FXML
    private TextField srcLocationTxtField;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label step3ResultLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'MainView.fxml'.";
        assert treeView != null : "fx:id=\"treeView\" was not injected: check your FXML file 'MainView.fxml'.";
        assert fileMenuExportCoverItem != null : "fx:id=\"fileMenuExportCoverItem\" was not injected: check your FXML file 'MainView.fxml'.";
        assert step2ExportIndexButton != null : "fx:id=\"step2ExportIndexButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert step3ProgressLabel != null : "fx:id=\"step3ProgressLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert srcLocationTxtField != null : "fx:id=\"srcLocationTxtField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'MainView.fxml'.";
        assert step3ResultLabel != null : "fx:id=\"step3ResultLabel\" was not injected: check your FXML file 'MainView.fxml'.";

        // Menu binding.
        fileMenuExportCoverItem.disableProperty().bind(IOManager.getInstance().coverSavedProperty());
        step2ExportIndexButton.disableProperty().bind(IOManager.getInstance().indexSaveProperty());
    }
    
    private Stage getStage() {
        return (Stage) gridPane.getScene().getWindow();
    }

    //<editor-fold defaultstate="collapsed" desc="Moving to Steps">
    @FXML
    void step1NextClick(ActionEvent event) {
        move(StepDirection.FORWARD);
    }

    @FXML
    void step2BackClick(ActionEvent event) {
        move(StepDirection.BACKWARD);
    }

    @FXML
    void step2NextClick(ActionEvent event) {
        move(StepDirection.FORWARD);
    }

    @FXML
    void step3BackClick(ActionEvent event) {
        move(StepDirection.BACKWARD);
    }

    @FXML
    void step3StopClick(ActionEvent event) {
        getStage().close();
    }

    private int currentStepOffset = 0;
    private enum StepDirection {

        FORWARD, BACKWARD
    };

    private void move(StepDirection stepDirection) {
        int oldStepOffset = currentStepOffset;
        currentStepOffset += stepDirection == StepDirection.FORWARD ? -600 : 600;
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(gridPane);
        translateTransition.setFromX(oldStepOffset);
        translateTransition.setToX(currentStepOffset);
        translateTransition.setDuration(new Duration(250));
        translateTransition.setInterpolator(Interpolator.LINEAR);
        translateTransition.setAutoReverse(false);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Step 1">
    @FXML
    void srcLocationSearchClick(ActionEvent event) {

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Step 2">
    @FXML
    void step2UpClick(ActionEvent event) {

    }

    @FXML
    void step2DownClick(ActionEvent event) {

    }

    @FXML
    void step2RenameClick(ActionEvent event) {

    }

    @FXML
    void step2RemoveClick(ActionEvent event) {

    }

    @FXML
    void step2RefreshTreeClick(ActionEvent event) {

    }

    @FXML
    void step2ExportIndexClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/IndexExportView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Εξαγωγή Ευρετηρίου");
        stageNew.show();
    }

    @FXML
    void step2ImportIndexClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/IndexImportView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Εισαγωγή Ευρετηρίου");
        stageNew.show();
    }

    @FXML
    void step2CoverSettingsClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditCoverView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Ρυθμίσεις Εξώφυλλου");
        stageNew.show();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="File Menu">
    @FXML
    void fileMenuExportCoverClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoverExportView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Εξαγωγή Εξώφυλλου");
        stageNew.show();
    }

    @FXML
    void fileMenuImportCoverClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CoverImportView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Εισαγωγή Εξώφυλλου");
        stageNew.show();
    }

    @FXML
    void fileMenuExitClick(ActionEvent event) {
        getStage().close();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Edit Menu">
    @FXML
    void editMenuCoverClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditCoverView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Ρυθμίσεις Εξώφυλλου");
        stageNew.show();
    }

    @FXML
    void editMenuSettingsClick(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditSettingsView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));

        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.setTitle("softaware.gr - Ρυθμίσεις");
        stageNew.show();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Help Menu">
    @FXML
    void helpMenuAbout(ActionEvent event) {
        Stage stageNew = new Stage();
        stageNew.initModality(Modality.WINDOW_MODAL);
        stageNew.initStyle(StageStyle.UNDECORATED);
        stageNew.initOwner(getStage());


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HelpAboutView.fxml"));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ignored) {
        }
        stageNew.setScene(new Scene(root));
        
        stageNew.getIcons().add(new Image("/files/images/logo.png"));
        stageNew.show();
    }
    //</editor-fold>

    @FXML
    void closeBtnClick(ActionEvent event) {
        getStage().close();
    }

}
