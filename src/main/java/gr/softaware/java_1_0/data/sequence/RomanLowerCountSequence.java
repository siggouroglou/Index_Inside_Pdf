package gr.softaware.java_1_0.data.sequence;

import java.util.TreeMap;

/**
 * An implementation of the CountSequence interface. This class represents a
 * counting sequence of latin, lower case, symbols.
 *
 * @author syggouroglou@gmail.com
 */
public final class RomanLowerCountSequence implements CountSequence {

    private int counter;
    final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "m");
        map.put(900, "cm");
        map.put(500, "d");
        map.put(400, "cd");
        map.put(100, "c");
        map.put(90, "xc");
        map.put(50, "l");
        map.put(40, "xl");
        map.put(10, "x");
        map.put(9, "ix");
        map.put(5, "v");
        map.put(4, "iv");
        map.put(1, "i");

    }

    public RomanLowerCountSequence() {
        this.counter = 0;
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
            return toRoman(counter);
        } catch (IndexOutOfBoundsException ex) {
            counter = 1;
            return toRoman(counter);
        }
    }

    private static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
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
