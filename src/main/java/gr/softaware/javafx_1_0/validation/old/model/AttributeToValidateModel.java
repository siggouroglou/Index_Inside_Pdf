package gr.softaware.javafx_1_0.validation.old.model;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class AttributeToValidateModel {

    private final String fieldName;
    private final String fieldTitle;
    private final ValidationConfigurationModel[] configuration;

    private AttributeToValidateModel(ModelToValidateBuilder builder) {
        this.fieldName = builder.fieldName;
        this.fieldTitle = builder.fieldTitle;
        this.configuration = builder.configuration;
    }

    public static class ModelToValidateBuilder {

        private String fieldName;
        private String fieldTitle;
        private ValidationConfigurationModel[] configuration;

        public ModelToValidateBuilder() {
            this.fieldName = "";
            this.fieldTitle = "";
            this.configuration = null;
        }

        public ModelToValidateBuilder fieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public ModelToValidateBuilder fieldTitle(String fieldTitle) {
            this.fieldTitle = fieldTitle;
            return this;
        }

        public ModelToValidateBuilder configuration(ValidationConfigurationModel[] configuration) {
            this.configuration = configuration;
            return this;
        }

        public AttributeToValidateModel build() {
            return new AttributeToValidateModel(this);
        }

    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public ValidationConfigurationModel[] getConfiguration() {
        return configuration;
    }
}
