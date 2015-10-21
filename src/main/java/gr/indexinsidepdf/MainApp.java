package gr.indexinsidepdf;

import gr.softaware.java_1_0.text.PropertiesUtilities;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class MainApp extends Application {

    // Local final information.
    private static final String PATH_TO_CONF = "." + File.separator + "conf";
    private static final String LOGGING_PROPERTIES_FILENAME = "logging.properties";
    private static final String COVER_PROPERTIES_FILENAME = "cover.properties";

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
        Logger logger = Logger.getLogger(MainApp.class.getName());

        // Initialize logger.
        try {
            // Set the correct path for the logging properties.
            File loggingFile = new File(PATH_TO_CONF + File.separator + LOGGING_PROPERTIES_FILENAME);
            if (loggingFile.exists() && loggingFile.isFile()) {
                PropertyConfigurator.configure(loggingFile.toString());
                logger.log(Level.INFO, "-----------------------------------------");
                logger.log(Level.INFO, "The logger initialized successfully.");
            } else {
                throw new FileNotFoundException("Log file path not found:" + LOGGING_PROPERTIES_FILENAME);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            System.exit(1);
        }
        
        // Initialize PropertiesUtilities.
        PropertiesUtilities.initFromFile(PATH_TO_CONF + File.separator + COVER_PROPERTIES_FILENAME);
        
        launch(args);
    }

}
