package gr.softaware.javafx_1_0.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 * @param <T> The type of the model to validate.
 */
public class ModelValidation<T> {

    private static final Logger logger = Logger.getLogger(ModelValidation.class);

    public ModelValidation() {
    }

    public List<ValidationError> validate(T model) {
        List<ValidationError> validationErrors = new ArrayList<>();
        // Check if there is any validation anotation.

        for (Field field : model.getClass().getDeclaredFields()) {
            // Get the field value invoking getter.
            String fieldName = field.getName();
            Object fieldValue;
            try {
                // Run the getter.
                Method method = model.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                fieldValue = method.invoke(model);
            } catch (Exception ex) {
                logger.warn(ex);
                continue;
            }

            // Check all the anotations specified for this field.
            for (Annotation annotation : field.getAnnotations()) {
                try {
                    if (annotation.annotationType() == gr.softaware.javafx_1_0.validation.type.Date.class) {
                        ValidationError error = validateDate(fieldValue, fieldName, annotation);
                        if (error != null) {
                            validationErrors.add(error);
                        }
                        continue;
                    }
                    if (annotation.annotationType() == gr.softaware.javafx_1_0.validation.type.Decimal.class) {
                        ValidationError error = validateDecimal(fieldValue, fieldName, annotation);
                        if (error != null) {
                            validationErrors.add(error);
                        }
                        continue;
                    }
                } catch (ValidationOperationException ex) {
                    logger.warn(ex);
                }
            }
        }

        return validationErrors;
    }

    private ValidationError validateDate(Object fieldValue, String fieldName, Annotation annotation) {
        // Validate annotation arg.
        if (!(annotation instanceof gr.softaware.javafx_1_0.validation.type.Date)) {
            throw new ValidationOperationException("Annotation type not acceptable. Aceptable type: gr.softaware.javafx_1_0.validation.type.Date");
        }
        gr.softaware.javafx_1_0.validation.type.Date dateAnnotation = (gr.softaware.javafx_1_0.validation.type.Date) annotation;

        // Validate value arg.
        if (!(fieldValue instanceof String)) {
            throw new ValidationOperationException("Field value type not acceptable. Aceptable types: String");
        }
        String value = (String) fieldValue;

        // Validate.
        DateFormat format = new SimpleDateFormat(dateAnnotation.value());

        try {
            format.parse(value);
        } catch (ParseException ex) {
            return new ValidationError(fieldName, dateAnnotation.message());
        }

        return null;
    }

    private ValidationError validateDecimal(Object fieldValue, String fieldName, Annotation annotation) {
        // Validate annotation arg.
        if (!(annotation instanceof gr.softaware.javafx_1_0.validation.type.Decimal)) {
            throw new ValidationOperationException("Annotation type not acceptable. Aceptable type: gr.softaware.javafx_1_0.validation.type.Decimal");
        }
        gr.softaware.javafx_1_0.validation.type.Decimal decimalAnnotation = (gr.softaware.javafx_1_0.validation.type.Decimal) annotation;

        // Validate value arg.
        if (!(fieldValue instanceof String)) {
            throw new ValidationOperationException("Field value type not acceptable. Aceptable types: String");
        }
        String value = (String) fieldValue;

        // Validate the value type.
        double num;
        try {
            num = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            return new ValidationError(fieldName, decimalAnnotation.message());
        }
        // Validate the min-max.
        if (num < decimalAnnotation.min() || num > decimalAnnotation.max()) {
            return new ValidationError(fieldName, decimalAnnotation.message());
        }

        return null;
    }
}
