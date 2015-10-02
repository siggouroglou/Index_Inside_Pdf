package gr.indexinsidepdf.controller;

import gr.indexinsidepdf.lib.storage.SettingsManager;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class EditSettingsViewController implements Initializable {

    @FXML
    private ToggleGroup IndexLocationGroup;

    @FXML
    private HBox locationHbox;

    @FXML
    private TextField locationTextField;

    @FXML
    private RadioButton indexLocationInsideFolderRadioBtn;

    @FXML
    private RadioButton indexLocationOtherRadioButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert IndexLocationGroup != null : "fx:id=\"IndexLocationGroup\" was not injected: check your FXML file 'EditSettingsView.fxml'.";
        assert locationHbox != null : "fx:id=\"locationHbox\" was not injected: check your FXML file 'EditSettingsView.fxml'.";
        assert locationTextField != null : "fx:id=\"locationTextField\" was not injected: check your FXML file 'EditSettingsView.fxml'.";
        assert indexLocationInsideFolderRadioBtn != null : "fx:id=\"indexLocationInsideFolderRadioBtn\" was not injected: check your FXML file 'EditSettingsView.fxml'.";
        assert indexLocationOtherRadioButton != null : "fx:id=\"indexLocationOtherRadioButton\" was not injected: check your FXML file 'EditSettingsView.fxml'.";

        // Binding.
        locationHbox.disableProperty().bind(Bindings.not(indexLocationOtherRadioButton.selectedProperty()));
        
        // Set the view.
        indexLocationInsideFolderRadioBtn.selectedProperty().set(SettingsManager.getInstance().isDefaultLocation());
        indexLocationOtherRadioButton.selectedProperty().set(!SettingsManager.getInstance().isDefaultLocation());
        locationTextField.textProperty().set(SettingsManager.getInstance().getPdfFolderPath());
    }

    private Stage getStage() {
        return (Stage) locationTextField.getScene().getWindow();
    }

    @FXML
    void searchLocationClick(ActionEvent event) {
        // Select the file.
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Επιλογή τοποθεσίας ευρετηρίου");
        File folder = choose.showDialog(getStage());
        if (folder == null || !folder.isDirectory()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Πρόβλημα");
            error.setHeaderText(null);
            error.setContentText("Η διαδρομή που δώσατε δεν είναι αποδεκτή.");
            error.show();
            return;
        }

        locationTextField.textProperty().set(folder.getAbsolutePath());
    }

    @FXML
    void saveClick(ActionEvent event) {
        SettingsManager.getInstance().setDefaultLocation(indexLocationInsideFolderRadioBtn.selectedProperty().get());
        SettingsManager.getInstance().setPdfFolderPath(locationTextField.textProperty().get());
        
        getStage().close();
    }

    @FXML
    void cancelClick(ActionEvent event) {
        getStage().close();
    }

}
