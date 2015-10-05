package gr.softaware.javafx_1_0.validation.depricated;

/**
 *
 * @author siggouroglou
 */
public interface ValidationOperation {
    public boolean isValid(Object value, String argument);
    public String getErrorMessageGr();
    public String getErrorMessageEn();
}
