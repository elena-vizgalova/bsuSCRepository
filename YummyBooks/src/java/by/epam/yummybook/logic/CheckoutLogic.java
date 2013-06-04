package by.epam.yummybook.logic;

import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides logic for checkout operation.
 * @author Elena Vizgalova
 */
public class CheckoutLogic {

    private static CheckoutLogic instance;
    private static final String PHONE_REGEX = "[0-9]{5}\\-[0-9]{7}";
    private static final String NAME_REGEX = "^[A-ZА-Я]{1}[a-zа-я]+(?:[ ][A-ZА-Я]{1}[a-zа-я]+(?:[-][A-ZА-Я]{1}[a-zа-я]+){0,})*";
    private static final String EMAIL_REGEX = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";

    private CheckoutLogic() {
    }

    public static synchronized CheckoutLogic getInstance() {
        if (null == instance) {
            instance = new CheckoutLogic();
        }
        return instance;
    }

    /**
     * Checks if <tt>name</tt> matches {@link CheckoutLogic#NAME_REGEX} pattern.
     * @param name
     * @return <code>true</code> if matches, <code>else</code> otherwise.
     */
    public boolean checkName(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    /**
     * Checks if <tt>phone</tt> matches {@link CheckoutLogic#PHONE_REGEX} pattern.
     * @param phone
     * @return <code>true</code> if matches, <code>else</code> otherwise.
     */
    public boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }

    /**
     * Checks if <tt>email</tt> matches {@link CheckoutLogic#EMAIL_REGEX} pattern.
     * @param email
     * @return <code>true</code> if matches, <code>else</code> otherwise.
     */
    public boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Checks if <tt>address</tt> is not empty string.
     * @param address
     * @return <code>true</code> if it's not empty, <code>else</code> otherwise.
     */
    public boolean checkAddress(String address) {
        if (address.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if <tt>cityRegion</tt> is not empty string.
     * @param cityRegion
     * @return <code>true</code> if it's not empty, <code>else</code> otherwise.
     */
    public boolean checkCityRegion(String cityRegion) {
        if (cityRegion.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Updates customer and it's cart information in DB, sets isConfirmed in 
     * customer's order.
     * @param customer
     * @param cart
     * @see CustomerDAO#updateCustomerInfo(by.epam.yummybook.entity.Customer) 
     * @see OrderDAO#setIsConfirmed(int) 
     * @throws LogicException 
     */
    public void rewriteCustomer( Customer customer, Cart cart ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();
            customerDAO.updateCustomerInfo(customer);

            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();
            orderDAO.setIsConfirmed( cart.getOrderID() );
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(CheckoutLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }
}
