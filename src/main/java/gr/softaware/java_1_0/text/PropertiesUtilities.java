package gr.softaware.java_1_0.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Jun 30, 2014, 8:34:11 PM
 *
 * @author syggouroglou@gmail.com
 */
public final class PropertiesUtilities {

    private static String DEFAULT_LOCATION = "." + File.separator + "log" + File.separator + "logging.properties";
    private static PropertiesUtilities INSTANCE;

    private final Logger logger = Logger.getLogger(PropertiesUtilities.class);
    private final Properties properties;

    private PropertiesUtilities() {
        throw new RuntimeException("Not valid constructor");
    }

    private PropertiesUtilities(InputStream inputStream) throws IOException {
        // Properties initilization.
        properties = new Properties();
        properties.load(inputStream);
        logger.log(Level.INFO, "Property file loaded successfully.");
    }

    public static PropertiesUtilities initFromFile(String pathToPropertiesFile) {
        if (INSTANCE == null) {
            try {
                INSTANCE = new PropertiesUtilities(new FileInputStream(pathToPropertiesFile));
            } catch (IOException ex) {
                Logger.getLogger(PropertiesUtilities.class.getName()).log(Level.FATAL, null, ex);
            }
        }
        return INSTANCE;
    }

    public static PropertiesUtilities initFromJarLocation(String jarLocation) {
        if (INSTANCE == null) {
            try {
                InputStream inputStream = PropertiesUtilities.class.getClassLoader().getResourceAsStream(jarLocation);
                INSTANCE = new PropertiesUtilities(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(PropertiesUtilities.class.getName()).log(Level.FATAL, null, ex);
            }
        }
        return INSTANCE;
    }

    public static PropertiesUtilities getInstance() {
        if (INSTANCE == null) {
            // Load from default location.
            INSTANCE = initFromJarLocation(DEFAULT_LOCATION);
        }
        return INSTANCE;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultvalue) {
        return properties.getProperty(key, defaultvalue);
    }

    public int getInt(String key) {
        return getInt(key, "-1");
    }

    public int getInt(String key, String defaultvalue) {
        String value = properties.getProperty(key, defaultvalue);
        int valueInt = -1;
        try {
            valueInt = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            logger.log(Level.ERROR, "The value from configuration requested wasn't an integer(key:'" + key + "', value:'" + value + "').", ex);
            valueInt = -1;
        }
        return valueInt;
    }

    public Float getFloat(String key) {
        return getFloat(key, "-1");
    }

    public Float getFloat(String key, String defaultvalue) {
        String value = properties.getProperty(key, defaultvalue);
        float valueFloat = -1F;
        try {
            valueFloat = Float.parseFloat(value);
        } catch (NumberFormatException ex) {
            logger.log(Level.ERROR, "The value from configuration requested wasn't an integer(key:'" + key + "', value:'" + value + "').", ex);
            valueFloat = -1;
        }
        return valueFloat;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, "false");
    }

    public boolean getBoolean(String key, String defaultvalue) {
        String value = properties.getProperty(key, defaultvalue);
        boolean valueBoolean = false;
        try {
            valueBoolean = Boolean.parseBoolean(value);
        } catch (NumberFormatException ex) {
            logger.log(Level.ERROR, "The value from configuration requested wasn't an integer(key:'" + key + "', value:'" + value + "').", ex);
            valueBoolean = false;
        }
        return valueBoolean;
    }
}
