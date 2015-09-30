package gr.softaware.javafx_1_0.controls.table;

/**
 *
 * @author siggouroglou@gmail.com
 */
public final class TableViewColumn {

    private final String columnTitle;
    private final String columnAttribute;
    private final int minWidth;

    private TableViewColumn(TableViewColumnBuilder builder) {
        this.columnTitle = builder.columnTitle;
        this.columnAttribute = builder.columnAttribute;
        this.minWidth = builder.minWidth;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public String getColumnAttribute() {
        return columnAttribute;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public static class TableViewColumnBuilder {

        private String columnTitle;
        private String columnAttribute;
        private int minWidth;

        public TableViewColumnBuilder() {
            this.columnTitle = "";
            this.columnAttribute = "";
            this.minWidth = 0;
        }

        public TableViewColumnBuilder columnTitle(String columnTitle) {
            this.columnTitle = columnTitle;
            return this;
        }

        public TableViewColumnBuilder columnAttribute(String columnAttribute) {
            this.columnAttribute = columnAttribute;
            return this;
        }

        public TableViewColumnBuilder minWidth(int minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public TableViewColumn build() {
            return new TableViewColumn(this);
        }
    }

}
