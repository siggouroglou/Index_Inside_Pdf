package gr.softaware.java_1_0.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author syggouroglou@gmail.com
 */
public class TextStyleFormat {

    private static final Map<String, TextStyleType> styles = new HashMap<>();

    static {
        styles.put("*", TextStyleType.ITALIC);
        styles.put("**", TextStyleType.BOLD);
        styles.put("***", TextStyleType.BOLD_ITALIC);
    }

    public TextStyleFormat() {
    }

    /**
     * Check the format of the valued and separate the text that includes. It
     * returns a list of object that hold the separated text in the order of the
     * given. Its separated text includes a format.
     *
     * @param value the value to be formatted.
     * @return a list of object that hold the separated text in the order of the
     * given. Its separated text includes a format.
     *
     * @throws NullPointerException in case of null argument.
     */
    public List<TextStyleOutput> format(String value) {
        if (value == null) {
            throw new NullPointerException("Argument must not be null.");
        }

        // Run the original method.
        StringBuilder builder = new StringBuilder(value);
        return format(builder);
    }

    /**
     * Check the format of the valued and separate the text that includes. It
     * returns a list of object that hold the separated text in the order of the
     * given. Its separated text includes a format.
     *
     * @param builder the value to be formatted.
     * @return a list of object that hold the separated text in the order of the
     * given. Its separated text includes a format.
     *
     * @throws NullPointerException in case of null argument.
     */
    public List<TextStyleOutput> format(StringBuilder builder) {
        if (builder == null) {
            throw new NullPointerException("Argument must not be null.");
        }
        if (builder.length() == 0) {
            return Collections.emptyList();
        }

        // Create the list to return.
        List<TextStyleOutput> valueFormatted = new ArrayList<>();

        // Create the first node for the list.
        StringBuilder currentBuilder = new StringBuilder();
        TextStyleType normalStyle = TextStyleType.NORMAL;
        TextStyleOutput textStyleOutput = new TextStyleOutput(normalStyle, currentBuilder);
        valueFormatted.add(textStyleOutput);

        // Initialize variables.
        char currentChar;
        char lastChar = '}';

        // Read the builder per character.
        for (int index = 0; index < builder.length(); index++) {
            currentChar = builder.charAt(index);

            // Check if it is a star.
            if (currentChar == '*') {
                // Check if it used an escape pattern.
                if (lastChar == '\\') {
                    // Replace the escape \* with the * only.
                    currentBuilder.replace(currentBuilder.length(), currentBuilder.length(), "*");
                } else {
                    // Find the type of this style.
                    StringBuilder starTypeBuilder = new StringBuilder("*");
                    for (++index; index < builder.length(); index++) {
                        currentChar = builder.charAt(index);
                        if (currentChar != '*') {
                            break;
                        }
                        starTypeBuilder.append("*");
                    }
                    TextStyleType newStyle = styles.get(starTypeBuilder.toString());
                    if (newStyle != null) {
                        // Find the index of the stars that end this area and add this text to the list.
                        String formatedText = builder.substring(index);
                        int indexOfEndStars = formatedText.indexOf(starTypeBuilder.toString());
                        if (indexOfEndStars > -1) {
                            String value = formatedText.substring(0, indexOfEndStars);
                            currentBuilder = new StringBuilder(value);
                            textStyleOutput = new TextStyleOutput(newStyle, currentBuilder);
                            valueFormatted.add(textStyleOutput);
                            index += value.length() + starTypeBuilder.length();
                            currentChar = builder.charAt(index);
                        }

                        // Refresh the currentBuilder to contain the normal text.
                        currentBuilder = new StringBuilder();
                        textStyleOutput = new TextStyleOutput(normalStyle, currentBuilder);
                        valueFormatted.add(textStyleOutput);
                    }
                }
            }

            // Add the currentChar to the current builder.
            currentBuilder.append(currentChar);

            // Save the last char.
            lastChar = currentChar;
        }

        return valueFormatted;
    }

}
