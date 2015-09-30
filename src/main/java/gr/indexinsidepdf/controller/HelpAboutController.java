package gr.indexinsidepdf.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class HelpAboutController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'HelpAboutView.fxml'.";
        anchorPane.setOnMouseClicked((MouseEvent event) -> {
            getStage().close();
        });
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }
    
}
