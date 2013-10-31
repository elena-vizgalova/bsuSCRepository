package by.bsu.appconfig.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class AppConfigManager {

    public final String MAIN_WINDOW_CONFIG = "window/windowconfig.properties";

    private static volatile AppConfigManager instance;
    private Properties properties;

    private AppConfigManager() {
        properties = new Properties();
    }

    public static synchronized AppConfigManager getInstance() {

        if ( null == instance ) {
            synchronized (AppConfigManager.class) {
                if (null == instance)
                {
                    instance = new AppConfigManager();
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
