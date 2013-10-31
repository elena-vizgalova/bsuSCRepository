package by.bsu.appconfig.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class WindowConfigManager {
    private static volatile WindowConfigManager instance;
    private Properties properties;

    public static final String BACKGROUND_COLOR = "window.background.color";
    public static final String HEIGHT = "window.height";
    public static final String WIDTH = "window.width";

    private WindowConfigManager() {
        properties = new Properties();
    }

    public static synchronized WindowConfigManager getInstance() {

        if ( null == instance ) {
            synchronized (WindowConfigManager.class) {
                if (null == instance)
                {
                    instance = new WindowConfigManager();
                }
            }
        }

        return instance;
    }

    public void loadProperties(String filename) {
        try {
            properties.load(new FileInputStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty( String key ) {
        return properties.getProperty( key );
    }
}
