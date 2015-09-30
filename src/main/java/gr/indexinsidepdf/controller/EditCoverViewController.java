package gr.indexinsidepdf.controller;

import gr.indexinsidepdf.lib.ValidationHelper;
import gr.indexinsidepdf.lib.storage.CoverManager;
import gr.indexinsidepdf.model.CoverModel;
import gr.softaware.javafx_1_0.validation.ModelValidation;
import gr.softaware.javafx_1_0.validation.ValidationError;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.beanutils.BeanUtils;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class EditCoverViewController implements Initializable {

    private final CoverModel MODEL = new CoverModel();
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
     *
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

        // Bind properties from CoverModel of the CoverManager singleton instance.
        CoverModel coverModel = CoverManager.getInstance().getCoverModel();
        try {
            BeanUtils.copyProperties(MODEL, coverModel);
        } catch (IllegalAccessException | InvocationTargetException ex) {
        }
        startTitleTextField.textProperty().bindBidirectional(MODEL.startTitleProperty());
        startDateTextField.textProperty().bindBidirectional(MODEL.startDateProperty());
        startAmmountTextField.textProperty().bindBidirectional(MODEL.startAmmountProperty());
        endTitleTextField.textProperty().bindBidirectional(MODEL.endTitleProperty());
        endTextTextArea.textProperty().bindBidirectional(MODEL.endTextProperty());

        // Construct the between and lender nodes.
        coverModel.getBetweenList().stream().forEach((between) -> {
            MODEL.getBetweenList().add(between);
            addNodeToBody(betweenContainer, between);
        });
        coverModel.getLenderList().stream().forEach((lender) -> {
            MODEL.getLenderList().add(lender);
            addNodeToBody(lenderContainer, lender);
        });
    }

    @FXML
    void addBetweenClick(ActionEvent event) {
        addNodeToBody(betweenContainer);
    }

    @FXML
    void addLenderClick(ActionEvent event) {
        addNodeToBody(lenderContainer);
    }

    @FXML
    void saveClick(ActionEvent event) {
        MODEL.getBetweenList().clear();
        MODEL.getLenderList().clear();
        // Re-construct the model between and lender list.
        for (Node child : betweenContainer.getChildren()) {
            if (child instanceof HBox) {
                for (Node curent : ((HBox) child).getChildren()) {
                    if (curent instanceof TextField) {
                        TextField textField = (TextField) curent;
                        MODEL.getBetweenList().add(textField.getText());
                    }
                }
            }
        }
        for (Node child : lenderContainer.getChildren()) {
            if (child instanceof HBox) {
                for (Node curent : ((HBox) child).getChildren()) {
                    if (curent instanceof TextField) {
                        TextField textField = (TextField) curent;
                        MODEL.getLenderList().add(textField.getText());
                    }
                }
            }
        }

        // Validate.
        if (!ValidationHelper.validate(MODEL)) {
            return;
        }

        // Transfer properties from one to another.
        CoverModel coverModel = CoverManager.getInstance().getCoverModel();
        try {
            BeanUtils.copyProperties(coverModel, MODEL);
            // Add the lists.
            coverModel.getBetweenList().clear();
            MODEL.getBetweenList().stream().forEach((curent) -> {
                coverModel.getBetweenList().add(curent);
            });
            coverModel.getLenderList().clear();
            MODEL.getLenderList().stream().forEach((curent) -> {
                coverModel.getLenderList().add(curent);
            });
        } catch (IllegalAccessException | InvocationTargetException ex) {
        }

        // Close the stage.
        getStage().close();
    }

    @FXML
    void cancelClick(ActionEvent event) {
        getStage().close();
    }

    private Stage getStage() {
        return (Stage) startTitleTextField.getScene().getWindow();
    }

    private void addNodeToBody(VBox container) {
        addNodeToBody(container, "");
    }

    private void addNodeToBody(VBox container, String text) {
        // The container for this specific action in this plan.
        final HBox hBox = new HBox();
        hBox.setPrefHeight(-1);
        hBox.setPrefWidth(-1);
        hBox.setAlignment(container == betweenContainer ? Pos.TOP_LEFT : Pos.TOP_RIGHT);
        hBox.setSpacing(10);
        HBox.setHgrow(hBox, Priority.ALWAYS);

        // The first node in the container as a label.
        final TextField textField = new TextField(text == null ? "" : text);
        hBox.getChildren().add(textField);

        // The third button is the remove button.
        final Button button = new Button("X");
        button.setOnAction((ActionEvent e) -> {
            // Remove this child from the action container.
            for (Node child : container.getChildren()) {
                if (child instanceof HBox && ((HBox) child).equals(hBox)) {
                    container.getChildren().remove(child);
                    break;
                }
            }
        });
        hBox.getChildren().add(button);

        // Add this hbox to the container.
        container.getChildren().add(hBox);
    }

}
