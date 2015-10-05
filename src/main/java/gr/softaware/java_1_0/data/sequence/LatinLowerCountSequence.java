package gr.softaware.java_1_0.data.sequence;

/**
 * An implementation of the CountSequence interface. This class represents a
 * counting sequence of latin, lower case, symbols.
 *
 * @author syggouroglou@gmail.com
 */
public final class LatinLowerCountSequence implements CountSequence {

    private int counter;
    private static final String[] VALUES = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public LatinLowerCountSequence() {
        this.counter = -1;
    }

    /**
     * Return the next counting value and update the internal counter.
     *
     * @return the next counting value and update the internal counter.
     */
    @Override
    public String nextValue() {
        counter++;
        try {
            return VALUES[counter];
        } catch (IndexOutOfBoundsException ex) {
            counter = 0;
            return VALUES[counter];
        }
    }

    /**
     * Resets the sequence, in the next call of nextValue the first sequence no
     * will be returned.
     */
    @Override
    public void reset() {
        this.counter = -1;
    }
}
