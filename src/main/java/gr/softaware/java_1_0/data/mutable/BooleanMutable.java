package gr.softaware.java_1_0.data.mutable;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class BooleanMutable implements Comparable<BooleanMutable> {

    private boolean value;

    public BooleanMutable(boolean value) {
        this.value = value;
    }

    public void set(boolean value) {
        this.value = value;
    }

    public boolean booleanValue() {
        return value;
    }

    @Override
    public int compareTo(BooleanMutable o) {
        if (o == null) {
            return -1;
        }
        return o.value == value ? 0 : 1;
    }
}
