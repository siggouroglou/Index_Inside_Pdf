package gr.softaware.java_1_0.data.sequence;

/**
 *
 * An implementation of the CountSequence interface. This class represents a
 * counting sequence of latin, upper case, symbols.
 *
 * @author syggouroglou@gmail.com
 */
public final class LatinUpperCountSequence implements CountSequence {

    private int counter;
    private static final String[] VALUES = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public LatinUpperCountSequence() {
        this.counter = -1;
    }

    /**
     * Return the next counting value and update the internal counter.
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
