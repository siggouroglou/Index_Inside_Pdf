package gr.indexinsidepdf.lib;

import gr.indexinsidepdf.model.CoverModel;
import gr.softaware.javafx_1_0.validation.ModelValidation;
import gr.softaware.javafx_1_0.validation.ValidationError;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class ValidationHelper {

    public static boolean validate(CoverModel model) {
        // Validate.
        ModelValidation<CoverModel> modelValidation = new ModelValidation<>();
        List<ValidationError> validateErrorList = modelValidation.validate(model);

        // Check for errors.
        if (validateErrorList.isEmpty()) {
            return true;
        }

        // Create the error message.
        StringBuilder builder = new StringBuilder();
        for (ValidationError error : validateErrorList) {
            builder.append(error.getErrorMessage()).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Προβλημε - Μη αποδεκτές τιμές");
        alert.setHeaderText("Κάποιο(α) πεδίο(α) δεν έχουν αποδεκτές τιμές");
        alert.setContentText(builder.toString());
        alert.show();

        return false;
    }
}
