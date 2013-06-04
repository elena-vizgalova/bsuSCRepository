package by.epam.yummybook.manager;

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

    private static final String BUNDLE_NAME = "resource.app_config";
    
    public static final String LOGIN_PAGE = "login_page";
    public static final String INDEX_PAGE = "index_page";
    public static final String HOME_PAGE = "home_page";
    public static final String ERROR_PAGE = "error_page";
    public static final String CATEGORY_PAGE = "category_page";
    public static final String CART_PAGE = "cart_page";
    public static final String CHECKOUT_PAGE = "checkout_page";
    public static final String SUCCESS_PAGE = "success_page";
    public static final String WELCOME_PAGE = "customer_welcome_page";
    public static final String LOGOUT_PAGE = "logout_page";
    
    public static final String ADMIN_WELCOME_PAGE = "admin_welcome_page";
    public static final String ADMIN_VIEW_CUSTOMERS_PAGE = "view_customers_page";
    public static final String ADMIN_VIEW_ORDERS_PAGE = "view_orders_page";
    
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
