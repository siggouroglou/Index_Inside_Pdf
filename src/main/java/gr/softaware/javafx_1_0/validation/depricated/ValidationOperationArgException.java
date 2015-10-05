package gr.softaware.javafx_1_0.validation.depricated;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class ValidationOperationArgException extends RuntimeException {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ValidationOperationArgException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public ValidationOperationArgException(String message) {
        super(message);
    }

}
