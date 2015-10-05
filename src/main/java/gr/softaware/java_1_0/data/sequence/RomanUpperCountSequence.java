package gr.softaware.java_1_0.data.sequence;

import java.util.TreeMap;

/**
 * An implementation of the CountSequence interface. This class represents a
 * counting sequence of latin, lower case, symbols.
 *
 * @author syggouroglou@gmail.com
 */
public final class RomanUpperCountSequence implements CountSequence {

    private int counter;
    final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    public RomanUpperCountSequence() {
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
