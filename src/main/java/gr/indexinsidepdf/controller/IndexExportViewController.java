package gr.indexinsidepdf.controller;

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
public class IndexExportViewController implements Initializable {

    @FXML
    private TextField locationTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert locationTextField != null : "fx:id=\"locationTextField\" was not injected: check your FXML file 'IndexExportView.fxml'.";
    }
    
    private Stage getStage() {
        return (Stage) locationTextField.getScene().getWindow();
    }

    @FXML
    void scrLocationSearchClick(ActionEvent event) {

    }

    @FXML
    void exportClick(ActionEvent event) {

    }

    @FXML
    void closeClick(ActionEvent event) {
        getStage().close();
    }
    
}
