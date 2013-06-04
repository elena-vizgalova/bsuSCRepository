package by.bsu.web.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manager singleton class that can read page's addresses from .property file
 * and handles keys of .property file.
 * @author Elena Vizgalova
 */
public class PageAddressManager {
    
    private static PageAddressManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "pagename";
    
    public static final String XSL_CATEGORIES = "xsl.categories";
    public static final String XML_CATEGORIES = "xml.categories";
    public static final String GENERATE_CATEGORIES = "generate.categories";
    
    public static final String HOME_PAGENAME="page.home";
    public static final String CATEGORIES_PAGENAME="page.categories";
    
    private PageAddressManager() {
        resourceBundle = ResourceBundle.getBundle( BUNDLE_NAME );
    }
    
    public static synchronized PageAddressManager getInstance() {
        
        if ( null == instance ) {
            instance = new PageAddressManager();
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
