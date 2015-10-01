package gr.indexinsidepdf;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        
        Parent root = (Parent)loader.load();
        stage.setScene(new Scene(root));
        stage.setWidth(600D);
        stage.setResizable(false);
        
        stage.getIcons().add(new Image("/files/images/logo.png"));
        stage.setTitle("softaware.gr - Πρόγραμμα κατασκευής ευρετηρίου σε pdf");
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
