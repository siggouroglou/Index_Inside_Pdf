package gr.softaware.javafx_1_0.validation.old;

import gr.softaware.javafx_1_0.validation.old.model.AttributeToValidateModel;
import gr.softaware.javafx_1_0.validation.old.model.ModelError;
import gr.softaware.javafx_1_0.validation.old.model.ValidationConfigurationModel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 * @param <T>
 */
public class ValidateForm<T extends ValidateFormType> {

    private static final Logger logger = Logger.getLogger(ValidateForm.class);

    private final T form;
    private final AttributeToValidateModel[] attributeToValidate;

    private ValidateForm(ValidateFormBuilder<T> builder) {
        this.form = builder.form;
        this.attributeToValidate = builder.attributeToValidate;
    }

    public static class ValidateFormBuilder<T> {

        private T form;
        private AttributeToValidateModel[] attributeToValidate;

        public ValidateFormBuilder() {
            this.form = null;
            this.attributeToValidate = null;
        }

        public ValidateFormBuilder form(T form) {
            this.form = form;
            return this;
        }

        public ValidateFormBuilder attributeToValidate(AttributeToValidateModel[] attributeToValidate) {
            this.attributeToValidate = attributeToValidate;
            return this;
        }

        public ValidateForm build() {
            return new ValidateForm(this);
        }

    }

    public T getForm() {
        return form;
    }

    public AttributeToValidateModel[] getAttributeToValidate() {
        return attributeToValidate;
    }

    public List<ModelError> validate() {
        // Create the list to return.
        List<ModelError> errors = new ArrayList<>();

        // Validate each model.
        for (AttributeToValidateModel attribute : attributeToValidate) {
            Object value = null;

            // Get the value.
            try {
                value = PropertyUtils.getProperty(form, attribute.getFieldName());
//                Field field = form.getClass().getDeclaredField(attribute.getFieldName());
//                field.setAccessible(true);
//                value = field.get(form);
            } catch (InvocationTargetException | NoSuchMethodException ex) {
                logger.warn("Field with name " + attribute.getFieldName() + " cannot be found in the model given. ERR:1", ex);
                continue;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                logger.warn("Field with name " + attribute.getFieldName() + " cannot be found in the model given. ERR:2", ex);
                continue;
            }

            // Validate the value.
            for (ValidationConfigurationModel configuration : attribute.getConfiguration()) {
                // Validate value on the method.
                if (!configuration.getValidationType().isValid(value, configuration.getArgument())) {
                    ModelError modelError = new ModelError(attribute.getFieldName(), attribute.getFieldTitle(),
                            configuration.getErrorMessage() == null ? configuration.getValidationType().getErrorMessageGr() : configuration.getErrorMessage());
                    errors.add(modelError);
                }
            }
        }

        return errors;
    }
}
