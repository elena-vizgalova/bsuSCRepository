package by.epam.yummybook.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manager singleton class that can read logger's properties .property file
 * and handles keys of .property file.
 * @author Elena Vizgalova
 */
public class LoggerPropertyManager {
    private static LoggerPropertyManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "resource.loggerConfig";
    
    public static final String CONTROLLER_LOG_FILENAME = "controllerlog.filename";
    public static final String DAO_LOG_FILENAME = "daolog.filename";
    public static final String ENTITY_LOG_FILENAME = "entitylog.filename";
    public static final String LOGIC_LOG_FILENAME = "logiclog.filename";
    public static final String CUSTOMERTAG_LOG_FILENAME = "customertaglog.filename";
    public static final String COMMAND_LOG_FILENAME = "commandlog.filename";
    
    public static final String CONTROLLER_LOG_LEVEL = "controllerlog.level";
    public static final String DAO_LOG_LEVEL = "daolog.level";
    public static final String ENTITY_LOG_LEVEL = "entitylog.level";
    public static final String LOGIC_LOG_LEVEL = "logiclog.level";
    public static final String CUSTOMERTAG_LOG_LEVEL = "customertaglog.level";
    public static final String COMMAND_LOG_LEVEL = "commandlog.level";
    
    public static final String CONVERSION_PATTERN = "appender.layout.conversionpattern";
    
    private LoggerPropertyManager(){
        resourceBundle = ResourceBundle.getBundle( BUNDLE_NAME, new Locale("en") );
    }
    
    public static synchronized LoggerPropertyManager getInstance() {

        if (null == instance) {
            instance = new LoggerPropertyManager();
        }

        return instance;
    }

    /**
     * 
     * @param key
     * @return specific logger property
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
    
}
