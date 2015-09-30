package gr.softaware.javafx_1_0.validation.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a date with a custom format.
 * Field type must be of type String.
 *
 * @author syggouroglou@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Date {

    /**
     * The date format pattern.
     * @return 
     */
    String value() default "dd.MM.YYYY";

    /**
     * The error message to appear.
     * @return 
     */
    String message() default "Date Error";
}
