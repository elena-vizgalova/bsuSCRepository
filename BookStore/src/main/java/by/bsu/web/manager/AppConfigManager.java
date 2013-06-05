package by.bsu.web.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Elena Vizgalova
 */
public class AppConfigManager {
    
    private static AppConfigManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "appconfig";
    
    public final String APPLICATION_CONTEXT_FILE = "app.context.file";
    public final String BOOK_DAO_BEAN = "app.context.bean.dao";
    
    private AppConfigManager() {
        resourceBundle = ResourceBundle.getBundle( BUNDLE_NAME );
    }
    
    public static synchronized AppConfigManager getInstance() {
        
        if ( null == instance ) {
            instance = new AppConfigManager();
        }
        
        return instance;
    }
    
    /**
     * Sets locale to resource bundle.
     * @param locale 
     */
    public void setLocale( Locale locale ) {
        resourceBundle = ResourceBundle.getBundle( BUNDLE_NAME, locale );
    }
    
    /**
     * 
     * @param key
     * @return specific page address
     */
    public String getProperty( String key ) {
        return resourceBundle.getString( key );
    }
    
}
