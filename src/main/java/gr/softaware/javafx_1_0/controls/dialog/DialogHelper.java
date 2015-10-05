package gr.softaware.javafx_1_0.controls.dialog;

import javafx.scene.control.Alert;

/**
 *
 * @author syggouroglou@gmail.com
 */
public class DialogHelper {

    public static void alertErrorGr(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Πρόβλημα");
        error.setHeaderText(null);
        error.setContentText(message);
        error.show();
    }
}
