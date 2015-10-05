package gr.softaware.javafx_1_0.validation.depricated.model;

import gr.softaware.javafx_1_0.validation.depricated.ValidationType;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class ValidationConfigurationModel {

    private final ValidationType validationType;
    private final String argument;
    private final String errorMessage;

    private ValidationConfigurationModel(ValidationConfigurationBuilder builder) {
            this.validationType = builder.validationType;
            this.argument = builder.argument;
            this.errorMessage = builder.errorMessage;
    }

    public static class ValidationConfigurationBuilder {

    private ValidationType validationType;
    private String argument;
    private String errorMessage;

        public ValidationConfigurationBuilder() {
            this.errorMessage = null;
            this.argument = null;
            this.errorMessage = null;
        }

        public ValidationConfigurationBuilder validationType(ValidationType validationType) {
            this.validationType = validationType;
            return this;
        }

        public ValidationConfigurationBuilder argument(String argument) {
            this.argument = argument;
            return this;
        }

        public ValidationConfigurationBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ValidationConfigurationModel build() {
            return new ValidationConfigurationModel(this);
        }

    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public String getArgument() {
        return argument;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
