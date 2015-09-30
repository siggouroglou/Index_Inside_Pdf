package gr.indexinsidepdf.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class EditCoverViewController implements Initializable {
    @FXML
    private VBox lenderContainer;

    @FXML
    private TextField endTitleTextField;

    @FXML
    private TextField startTitleTextField;

    @FXML
    private TextField startDateTextField;

    @FXML
    private VBox betweenContainer;

    @FXML
    private TextField startAmmountTextField;

    @FXML
    private TextArea endTextTextArea;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert lenderContainer != null : "fx:id=\"lenderContainer\" was not injected: check your FXML file 'EditCover.fxml'.";
        assert endTitleTextField != null : "fx:id=\"endTitleTextField\" was not injected: check your FXML file 'EditCover.fxml'.";
        assert startTitleTextField != null : "fx:id=\"startTitleTextField\" was not injected: check your FXML file 'EditCover.fxml'.";
        assert startDateTextField != null : "fx:id=\"startDateTextField\" was not injected: check your FXML file 'EditCover.fxml'.";
        assert betweenContainer != null : "fx:id=\"betweenContainer\" was not injected: check your FXML file 'EditCover.fxml'.";
        assert startAmmountTextField != null : "fx:id=\"startAmmountTextField\" was not injected: check your FXML file 'EditCover.fxml'.";
        assert endTextTextArea != null : "fx:id=\"endTextTextArea\" was not injected: check your FXML file 'EditCover.fxml'.";

    }
    
    private Stage getStage() {
        return (Stage) startTitleTextField.getScene().getWindow();
    }

    @FXML
    void addBetweenClick(ActionEvent event) {

    }

    @FXML
    void addLenderClick(ActionEvent event) {

    }

    @FXML
    void saveClick(ActionEvent event) {

    }

    @FXML
    void cancelClick(ActionEvent event) {
        getStage().close();
    }
    
}
