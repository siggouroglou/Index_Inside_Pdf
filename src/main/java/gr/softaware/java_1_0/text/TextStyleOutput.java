package gr.softaware.java_1_0.text;

/**
 *
 * @author syggouroglou@gmail.com
 */
public class TextStyleOutput {
    private TextStyleType styleType;
    private StringBuilder value;

    public TextStyleOutput() {
        this.styleType = TextStyleType.NORMAL;
        this.value = new StringBuilder();
    }

    public TextStyleOutput(TextStyleType styleType, StringBuilder value) {
        this.styleType = styleType;
        this.value = value;
    }

    public TextStyleType getStyleType() {
        return styleType;
    }

    public void setStyleType(TextStyleType styleType) {
        this.styleType = styleType;
    }

    public StringBuilder getValue() {
        return value;
    }

    public void setValue(StringBuilder value) {
        this.value = value;
    }
}
