package gr.indexinsidepdf.controller;

import gr.indexinsidepdf.lib.storage.IOManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class IndexImportViewController implements Initializable {

    @FXML
    private TextField locationTextField;
    @FXML
    private Button importButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert locationTextField != null : "fx:id=\"locationTextField\" was not injected: check your FXML file 'IndexImportView.fxml'.";
        assert importButton != null : "fx:id=\"importButton\" was not injected: check your FXML file 'IndexImportView.fxml'.";
        
        // Bindings.
        importButton.disableProperty().bind(Bindings.isEmpty(locationTextField.textProperty()));
    }
    
    private Stage getStage() {
        return (Stage) locationTextField.getScene().getWindow();
    }

    @FXML
    void scrLocationSearchClick(ActionEvent event) {
        IOManager.getInstance().chooseIndexFile(getStage(), locationTextField);
    }

    @FXML
    void importClick(ActionEvent event) {
        if (!IOManager.getInstance().loadIndex(getStage())) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Πρόβλημα");
            error.setHeaderText(null);
            error.setContentText("Το αρχείο που επιλέξατε δεν είναι αποδεκτό.");
            error.show();
            return;
        }
        IOManager.getInstance().indexSavedProperty().set(true);
        IOManager.getInstance().indexImportedProperty().set(true);
        getStage().close();
    }

    @FXML
    void closeClick(ActionEvent event) {
        getStage().close();
    }
    
}
