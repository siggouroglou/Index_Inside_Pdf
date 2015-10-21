package gr.indexinsidepdf.lib.storage;

/**
 *
 * @author syggouroglou@gmail.com
 */
public enum Language {

    GREEK, ENGLISH;

    @Override
    public String toString() {
        switch (this) {
            case GREEK:
                return "Ελληνικά";
            case ENGLISH:
                return "Αγγλικά";
        }
        throw new RuntimeException("Not allowed to be here.");
    }

}
