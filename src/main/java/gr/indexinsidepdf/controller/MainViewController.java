package gr.indexinsidepdf.controller;

import static java.awt.SystemColor.text;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
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
        assert step3ProgressLabel != null : "fx:id=\"step3ProgressLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert srcLocationTxtField != null : "fx:id=\"srcLocationTxtField\" was not injected: check your FXML file 'MainView.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'MainView.fxml'.";
        assert step3ResultLabel != null : "fx:id=\"step3ResultLabel\" was not injected: check your FXML file 'MainView.fxml'.";

    }

    @FXML
    void srcLocationSearchClick(ActionEvent event) {

    }

    @FXML
    void step1NextClick(ActionEvent event) {
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(gridPane);
        tt.setFromX(0);
        tt.setToX(-600);
        tt.setDuration(new Duration(250));
        tt.setInterpolator(Interpolator.LINEAR);
        tt.setAutoReverse(false);
        tt.setCycleCount(1);
        tt.play();
    }

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

    }

    @FXML
    void step2ImportIndexClick(ActionEvent event) {

    }

    @FXML
    void step2CoverSettingsClick(ActionEvent event) {

    }

    @FXML
    void step2BackClick(ActionEvent event) {

    }

    @FXML
    void step2NextClick(ActionEvent event) {

    }

    @FXML
    void step3BackClick(ActionEvent event) {

    }

    @FXML
    void step3NextClick(ActionEvent event) {

    }

    @FXML
    void fileMenuExportCoverClick(ActionEvent event) {

    }

    @FXML
    void fileMenuImportCoverClick(ActionEvent event) {

    }

    @FXML
    void fileMenuExitClick(ActionEvent event) {

    }

    @FXML
    void edtiMenuCoverClick(ActionEvent event) {

    }

    @FXML
    void edtiMenuSettingsClick(ActionEvent event) {

    }

    @FXML
    void helpMenuAbout(ActionEvent event) {

    }

    @FXML
    void closeBtnClick(ActionEvent event) {

    }

}
