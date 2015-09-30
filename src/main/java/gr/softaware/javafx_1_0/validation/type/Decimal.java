package gr.softaware.javafx_1_0.validation.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a decimal number (double).
 * Acceptable only decimals in the specific range.
 *
 * @author syggouroglou@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Decimal {

    /**
     * Min preferred value. Default the Double.MIN_VALUE.
     *
     * @return
     */
    double min() default Double.MIN_VALUE;

    /**
     * Max preferred value. Default the Double.MAX_VALUE.
     * 
     * @return 
     */
    double max() default Double.MAX_VALUE;

    /**
     * The error message to appear.
     *
     * @return
     */
    String message() default "Decimal Error";

}
