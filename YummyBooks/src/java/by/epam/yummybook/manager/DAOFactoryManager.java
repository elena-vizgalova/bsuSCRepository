package by.epam.yummybook.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manager singleton class that can read DB factory types from .property file
 * and handles keys of .property file.
 * @author Elena Vizgalova
 */
public class DAOFactoryManager {
    
    private static DAOFactoryManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "resource.daoFactoryTypes";
    public static final String MYSQL_FACTORY = "MYSQL_FACTORY";
    
    private DAOFactoryManager() {
        resourceBundle = ResourceBundle.getBundle( BUNDLE_NAME );
    }
    
    public static synchronized DAOFactoryManager getInstance() {
        
        if ( null == instance ) {
            instance = new DAOFactoryManager();
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
     * Gets DAOFactory type from file by <tt>key</tt>.
     * @param key
     * @return 
     */
    public int getProperty( String key ) {
        String stringValue = resourceBundle.getString( key );
        return Integer.parseInt( stringValue );
    }
    
}
