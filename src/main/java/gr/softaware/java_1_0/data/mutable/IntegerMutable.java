package gr.softaware.java_1_0.data.mutable;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class IntegerMutable extends Number implements Comparable<IntegerMutable> {

    private int value;

    public IntegerMutable() {
        this.value = 0;
    }

    public IntegerMutable(int value) {
        this.value = value;
    }

    public void set(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return (long)value;
    }

    @Override
    public float floatValue() {
        return (float)value;
    }

    @Override
    public double doubleValue() {
        return (double)value;
    }

    @Override
    public int compareTo(IntegerMutable o) {
        return compare(this.value, o.value);
    }

    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
