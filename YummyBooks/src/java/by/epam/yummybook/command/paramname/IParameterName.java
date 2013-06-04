package by.epam.yummybook.command.paramname;

/**
 * <tt>ParameterName</tt> contains all parameter and attribute names, that
 * used in commands, passed from/to the jsp page.
 * 
 * @author Elena Vizgalova
 */
public interface IParameterName {
    
    public final String LOGIN = "login";
    public final String PASSWORD = "password";
    public final String LOGIN_INFO = "login_info";
    public final String CUSTOMER_ORDER = "order";
    public final String CART = "cart";
    public final String CUSTOMER = "customer";
    
    public final String GREETING_TITLE = "greetingTitle";
    public final String GREETING_MESSAGE = "greetingMessage";
    
    public final String LOGOUT_TITLE = "logoutTitle";
    public final String LOGOUT_MESSAGE = "logoutMessage";
    
    public final String BOOK_ID = "bookID";
    public final String SELECTED_CATEGORY = "selectedCategory";
    public final String CATEGORIES_LIST = "categories";
    public final String CATEGORY_BOOKS = "categoryBooks";
    public final String BOOK_QUANTITY = "booksQuantity";
    
    public final String USERNAME = "username";
    public final String ORDER_ID = "orderID";
    public final String IS_PAYED = "isPayed";
    public final String PAGE_NAME = "pageName";
    public final String LANGUAGE = "language";
    
    public final String CUSTOMERS_LIST = "customersList";
    public final String ORDERS_LIST = "ordersList";
    public final String CUSTOMER_NAME = "customer_name";
    public final String CUSTOMER_EMAIL = "customer_email";
    public final String CUSTOMER_PHONE = "customer_phone";
    public final String CUSTOMER_ADDRESS = "customer_address";
    public final String CUSTOMER_CITYREGION = "city_region";
    
    public final String ERROR_NAME = "nameError";
    public final String ERROR_EMAIL = "emailError";
    public final String ERROR_PHONE = "phoneError";
    public final String ERROR_ADDRESS = "addressError";
    public final String ERROR_CITY_REGION = "cityRegionError";
    
    public final String ERROR_VARIABLE = "errorMessage";
    public final String ERROR = "error";
    public final String BOOK_WITH_ERROR = "bookIDError";
    
}
