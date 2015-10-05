package gr.softaware.java_1_0.data.sequence;

/**
 * An implementation of the CountSequence interface. This class represents a
 * counting sequence of number symbols.
 *
 * @author syggouroglou@gmail.com
 */
public final class NumberCountSequence implements CountSequence {
    private int counter;

    public NumberCountSequence() {
        this.counter = 0;
    }

    /**
     * Return the next counting value and update the internal counter.
     * @return the next counting value and update the internal counter.
     */
    @Override
    public String nextValue() {
        counter++;
        return String.valueOf(counter);
    }

    /**
     * Resets the sequence, in the next call of nextValue the first sequence no
     * will be returned.
     */
    @Override
    public void reset() {
        this.counter = 0;
    }

}
