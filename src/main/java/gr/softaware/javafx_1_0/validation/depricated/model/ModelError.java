package gr.softaware.javafx_1_0.validation.depricated.model;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class ModelError {

    private final String fieldName;
    private final String fieldTitle;
    private final String errorMessage;

    public ModelError(String fieldName, String fieldTitle, String errorMessage) {
        this.fieldName = fieldName;
        this.fieldTitle = fieldTitle;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
}
