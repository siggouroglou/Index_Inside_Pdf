package gr.indexinsidepdf.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class CoverModel {

    private final StringProperty startTitle;

//    @Date(message = "Η ημερομηνία δεν βρίσκεται στην κατάλληλη μορφή.")
    private final StringProperty startDate;

//    @Decimal(min = 0, message = "Το ποσό δεν είναι αποδεκτός αριθμός.")
    private final StringProperty startAmmount;
    private final StringProperty endTitle;
    private final StringProperty endText;
    private List<String> betweenList;
    private List<String> lenderList;

    public CoverModel() {
        this.startTitle = new SimpleStringProperty("");
        this.startDate = new SimpleStringProperty("");
        this.startAmmount = new SimpleStringProperty("");
        this.endTitle = new SimpleStringProperty("");
        this.endText = new SimpleStringProperty("");
        this.betweenList = new ArrayList<>();
        this.lenderList = new ArrayList<>();
    }

    public StringProperty startTitleProperty() {
        return startTitle;
    }

    public String getStartTitle() {
        return startTitle.get();
    }

    public void setStartTitle(String startTitle) {
        this.startTitle.set(startTitle);
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public StringProperty startAmmountProperty() {
        return startAmmount;
    }

    public String getStartAmmount() {
        return startAmmount.get();
    }

    public void setStartAmmount(String startAmmount) {
        this.startAmmount.set(startAmmount);
    }

    public StringProperty endTitleProperty() {
        return endTitle;
    }

    public String getEndTitle() {
        return endTitle.get();
    }

    public void setEndTitle(String endTitle) {
        this.endTitle.set(endTitle);
    }

    public StringProperty endTextProperty() {
        return endText;
    }

    public String getEndText() {
        return endText.get();
    }

    public void setEndText(String endText) {
        this.endText.set(endText);
    }

    public List<String> getBetweenList() {
        return betweenList;
    }

    public List<String> getLenderList() {
        return lenderList;
    }

}
