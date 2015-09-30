package gr.softaware.javafx_1_0.controls.table;

import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author siggouroglou@gmail.com
 * @param <T>
 */
public class TableViewInitiator<T> {

    private String emptyMessage;
    private List<TableViewColumn> columns;
    private ObservableList<T> datas;

    public TableViewInitiator() {
        this.emptyMessage = "Δεν υπάρχουν δεδομένα";
        this.columns = null;
        this.datas = null;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public List<TableViewColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableViewColumn> columns) {
        this.columns = columns;
    }

    public ObservableList<? super T> getDatas() {
        return datas;
    }

    public void setDatas(ObservableList<? extends T> datas) {
        this.datas = (ObservableList<T>) datas;
    }
}
