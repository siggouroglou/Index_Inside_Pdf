package gr.softaware.java_1_0.data.sequence;

/**
 * Interface that is implemented by classes that are generating counting
 * sequences.
 *
 * @author syggouroglou@gmail.com
 */
public interface CountSequence {

    /**
     * Return the next value. This method must update the internal counter of
     * the implementation.
     *
     * @return the sequence symbol
     */
    public String nextValue();
    
    /**
     * Reset the internal counter to the first value.
     */
    public void reset();
}
