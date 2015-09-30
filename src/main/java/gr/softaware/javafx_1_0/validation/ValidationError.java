package gr.softaware.javafx_1_0.validation;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class ValidationError {

    private final String fieldName;
    private final String errorMessage;

    public ValidationError(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
}
