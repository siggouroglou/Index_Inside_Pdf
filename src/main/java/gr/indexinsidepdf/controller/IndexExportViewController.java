package gr.indexinsidepdf.controller;

import gr.indexinsidepdf.lib.storage.IOManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class IndexExportViewController implements Initializable {

    @FXML
    private TextField locationTextField;
    @FXML
    private Button exportButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert locationTextField != null : "fx:id=\"locationTextField\" was not injected: check your FXML file 'IndexExportView.fxml'.";
        assert exportButton != null : "fx:id=\"exportButton\" was not injected: check your FXML file 'IndexExportView.fxml'.";
        
        // Bindings.
        exportButton.disableProperty().bind(Bindings.isEmpty(locationTextField.textProperty()));
    }
    
    private Stage getStage() {
        return (Stage) locationTextField.getScene().getWindow();
    }

    @FXML
    void scrLocationSearchClick(ActionEvent event) {
        IOManager.getInstance().chooseIndexFile(getStage(), locationTextField);
    }

    @FXML
    void exportClick(ActionEvent event) {
        IOManager.getInstance().saveIndex(getStage(), locationTextField.textProperty().get());
        getStage().close();
    }

    @FXML
    void closeClick(ActionEvent event) {
        getStage().close();
    }
    
}
