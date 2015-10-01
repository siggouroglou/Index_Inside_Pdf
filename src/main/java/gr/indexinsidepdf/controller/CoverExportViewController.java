package gr.indexinsidepdf.controller;

import gr.indexinsidepdf.lib.storage.IOManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class CoverExportViewController implements Initializable {

    @FXML
    private TextField locationTextField;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert locationTextField != null : "fx:id=\"locationTextField\" was not injected: check your FXML file 'CoverExportView.fxml'.";
    }

    private Stage getStage() {
        return (Stage) locationTextField.getScene().getWindow();
    }

    @FXML
    void scrLocationSearchClick(ActionEvent event) {
        IOManager.getInstance().chooseFile(getStage(), locationTextField);
    }

    @FXML
    void exportClick(ActionEvent event) {
        IOManager.getInstance().saveCover(getStage());
        getStage().close();
    }

    @FXML
    void closeClick(ActionEvent event) {
        getStage().close();
    }

}
