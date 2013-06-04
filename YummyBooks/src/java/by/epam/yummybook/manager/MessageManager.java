package by.epam.yummybook.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manager singleton class that can read messages from .property file
 * and handles keys of .property file.
 * @author Elena Vizgalova
 */
public class MessageManager {

    private static MessageManager instance;
    private ResourceBundle resourceBundle;
    
    private static final String BUNDLE_NAME = "resource.messages";
    
    public static final String GREETING_GUEST_TITLE = "greeting_guest";
    public static final String GREETING_USER_TITLE = "greeting_user";
    public static final String GREETING_ADMIN_TITLE = "greeting_admin";
    
    public static final String ADMINNAME_TO_CHANGE = "ADMINNAME";
    public static final String USERNAME_TO_CHANGE = "USERNAME";
    public static final String AMOUNT_TO_CHANGE = "AMOUNT";
    
    public static final String GREETING_USER_MESSAGE = "greeting_user_message";
    public static final String GREETING_ADMIN_MESSAGE = "greeting_admin_message";
    
    public static final String LOGOUT_TITLE = "logout_title";
    public static final String LOGOUT_MESSAGE = "logout_message";
    
    public static final String INDEX_PAGE_TO_CHANGE = "INDEX_PAGE";
    public static final String ADMIN_MAIN_TO_CHANGE = "ADMIN_MAIN";
    
    public static final String LOGIN_PAGE_TO_CHANGE = "LOGIN_PAGE";
    
    public static final String ERROR_NO_PERMISSIONS = "no_permissions_error";
    public static final String ERROR_LOGIN_FIRST = "login_first_error";
    public static final String ERROR_IN_QUANTITY = "quantity_error";
    public static final String ERROR_LOGIN_MESSAGE = "login_error";
    public static final String ERROR_IS_IN_BLACK_LIST = "is_in_black_list_error";
    public static final String ERROR_UNKNOWN_CATEGORY = "unknown_category_error";
    public static final String ERROR_BOOK_AMOUNT = "book_amount_error";
    public static final String ERROR_ONE_BOOK = "book_one_error";
    public static final String ERROR_NO_SUCH_BOOK = "no_such_book_error";
    
    public static final String ERROR_CUSTOMER_NAME = "name_error";
    public static final String ERROR_CUSTOMER_EMAIL = "email_error";
    public static final String ERROR_CUSTOMER_PHONE = "phone_error";
    public static final String ERROR_CUSTOMER_ADDRESS = "address_error";
    public static final String ERROR_CUSTOMER_CITY_REGION = "city_region_error";
    
    private MessageManager() {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static synchronized MessageManager getInstance() {

        if (null == instance) {
            instance = new MessageManager();
        }

        return instance;
    }

    /**
     * Sets locale to resource bundle.
     * @param locale 
     */
    public void setLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    /**
     * 
     * @param key
     * @return specific message
     */
    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
