package by.epam.yummybook.logic;

import by.epam.yummybook.dao.BookDAO;
import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.WarehouseDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Book;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.CustomerLoginInfo;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides methods for updating cart.
 *
 * @author Elena Vizgalova
 */
public class UpdateCartLogic {

    private static UpdateCartLogic instance;
    /**
     * Quantity must be only 1 or 2 digits number.
     */
    private static final String QUANTITY_REGEX = "[0-9]{1,2}";

    private UpdateCartLogic() {
    }

    public static synchronized UpdateCartLogic getInstance() {
        if (null == instance) {
            instance = new UpdateCartLogic();
        }
        return instance;
    }

    /**
     * Checks if the <tt>quantityStr</tt> matches
     * {@link UpdateCartLogic#QUANTITY_REGEX}.
     *
     * @param quantityStr
     * @return <code>true</code> if it's matches, <code>false</code> otherwise.
     */
    public boolean isValidQuantity(String quantityStr) {
        Pattern pattern = Pattern.compile(QUANTITY_REGEX);
        Matcher matcher = pattern.matcher(quantityStr);

        return matcher.matches();
    }

    /**
     * Checks if book with given <tt>bookID</tt> exists in warehouse DB.
     *
     * @param bookID
     * @return <code>true</code> if it's exists, <code>false</code> otherwise.
     * @throws LogicException
     */
    public boolean isExistsInWarehouse(String bookID) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            WarehouseDAO warehouseDAO = mysqlDAOFactory.getWarehouseDAO();

            return warehouseDAO.isExistBookInWarehouse(bookID);
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(UpdateCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }

    }

    /**
     * Gets book amount from warehouse for book with given <tt>bookID</tt>.
     *
     * @param bookID
     * @return book amount
     * @see WarehouseDAO#getBookAmount(java.lang.String)
     * @throws LogicException
     */
    public int getBookAmoutFromWarehouse(String bookID) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            WarehouseDAO warehouseDAO = mysqlDAOFactory.getWarehouseDAO();

            return warehouseDAO.getBookAmount(bookID);
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(UpdateCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }

    /**
     * Gets book by given <tt>bookID</tt>
     *
     * @param bookID
     * @return {@link Book} object
     * @see BookDAO#getBookInfo(java.lang.String)
     * @throws LogicException
     */
    public Book getBookByID(String bookID) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            BookDAO bookDAO = mysqlDAOFactory.getBookDAO();

            return bookDAO.getBookInfo(bookID);
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(UpdateCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }

    /**
     * Sets book with given <tt>bookID</tt> and it's <tt>quantity</tt> in
     * <tt>order</tt>
     *
     * @param order
     * @param bookID
     * @param quantity
     * @return updated {@link CustomerOrder}
     * @throws LogicException
     */
    public CustomerOrder updateOrder(CustomerOrder order, String bookID, int quantity) throws LogicException {
        try {
            Book book = getBookByID(bookID);

            order.updateBookQuantity(bookID, quantity, book.getPrice());

            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;
            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            double newTotal = order.getTotal();
            orderDAO.updateBookQuantity(bookID, quantity, order.getId(), newTotal);

            return order;
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(UpdateCartLogic.class);
            logger.error(ex);
            throw new LogicException();
        } catch (EntityException ex) {
            LogicLogger logger = LogicLogger.getInstance(UpdateCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }

    /**
     * Gets customer order by his <tt>loginInfo</tt>.
     * @param loginInfo
     * @return
     * @throws LogicException 
     */
    public CustomerOrder getCustomerOrder(LoginInfo loginInfo) throws LogicException {
        try {
            CustomerOrder order = new CustomerOrder();
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;
            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();

            Customer customer = customerDAO.getCustomerByUsername(loginInfo.getUsername());
            if (null == customer) {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerLoginInfo((CustomerLoginInfo) loginInfo);
                customerDAO.addCustomer(newCustomer);
                customer = customerDAO.getCustomerByUsername(loginInfo.getUsername());
            }

            List<CustomerOrder> orders = orderDAO.getOrdersByCustomerID(customer.getId());

            if (orders.isEmpty()) {
                orderDAO.createOrder(customer.getId());
                orders = orderDAO.getOrdersByCustomerID(customer.getId());
                order = orders.get(0);
            } else {
                for (CustomerOrder customerOrder : orders) {
                    if (!customerOrder.isIsPayed()) {
                        order = customerOrder;
                        break;
                    }
                }
            }
            return order;
        } catch (EntityException | DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(UpdateCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }
}
