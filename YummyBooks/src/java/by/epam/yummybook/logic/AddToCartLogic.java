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

/**
 * Singleton that provides add to cart logic.
 * @author Elena Vizgalova
 */
public class AddToCartLogic {

    private static AddToCartLogic instance;
    
    private AddToCartLogic() {
    }
    
    public static synchronized AddToCartLogic getInstance() {
        if ( null == instance ) {
            instance = new AddToCartLogic();
        }
        return instance;
    }
    
    /**
     * Gets customer with given <tt>username</tt> from DB.
     * @param username
     * @return {@link Customer} object
     * @throws LogicException when <tt>username</tt> is <code>null</code> 
     * or empty
     */
    public Customer getCustomer( String username ) throws LogicException {

        if (null == username) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error("Null username error!");
            throw new LogicException("username can't be null!");
        }
        if (username.isEmpty()) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error("empty username error!");
            throw new LogicException("username can't be empty!");
        }

        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();

            return customerDAO.getCustomerByUsername(username);
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException("DAO Error! username = " + username, ex);
        }
    }

    /**
     * Creates new customer with given <tt>loginInfo</tt>
     * @param loginInfo
     * @return {@link Customer} object
     * @see LoginInfo
     * @throws LogicException 
     */
    private Customer createNewCustomer(LoginInfo loginInfo) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;
            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();

            Customer customer = new Customer();
            customer.setCustomerLoginInfo((CustomerLoginInfo) loginInfo);

            customerDAO.addCustomer(customer);
            customer = customerDAO.getCustomerByUsername(loginInfo.getUsername());

            return customer;
        } catch (EntityException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error("Error writing LoginInfo object into Customer object");
            throw new LogicException(ex);
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }

    /**
     * Gets all orders with given <tt>customerID</tt>.
     * @param customerID
     * @return list of {@link CustomerOrder} objects
     * @throws LogicException 
     */
    private List<CustomerOrder> getAllCustomerOrdersFromDB(int customerID) throws LogicException {
        try {
            List<CustomerOrder> orders;

            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            orders = orderDAO.getOrdersByCustomerID(customerID);

            return orders;
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }

    /**
     * Creates new order in DB for customer with given <tt>customerID</tt>.
     * @param customerID
     * @return {@link CustomerOrder} object
     * @throws LogicException 
     */
    public CustomerOrder createNewOrder(int customerID) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            orderDAO.createOrder(customerID);

            List<CustomerOrder> orderList = orderDAO.getAllOrders();
            
            return orderList.get( orderList.size() - 1 );
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }
    
    /**
     * Gets first not confirmed order from <tt>orders</tt> list.
     * @param orders
     * @return {@link CustomerOrder} object or <code>null</code> if all orders
     * are confirmed
     */
    private CustomerOrder getFirstNotConfirmedCustomerOrder( List<CustomerOrder> orders ) {
        for (CustomerOrder customerOrder : orders) {
            if ( !customerOrder.isIsPayed() && !customerOrder.isIsConfirmed() ) {
                return customerOrder;
            }
        }
        return null;
    }

    /**
     * Gets order by <tt>loginInfo</tt>.
     * @param loginInfo
     * @return
     * @throws LogicException
     */
    public CustomerOrder getCustomerOrder( LoginInfo loginInfo ) throws LogicException {
        CustomerOrder order = null;

        Customer customer = getCustomer( loginInfo.getUsername() );
        if ( null == customer) {
            customer = createNewCustomer(loginInfo);
        }
        
        List<CustomerOrder> orders = getAllCustomerOrdersFromDB(customer.getId());
        if ( orders.isEmpty() ) {
            createNewOrder( customer.getId() );
        } else {
            order = getFirstNotConfirmedCustomerOrder( orders );
            if (null == order) {
                order = createNewOrder( customer.getId() );
            }

        }
        
        return order;
    }
    
    /**
     * Checks if book with given <tt>bookID</tt> exists in warehouse in DB.
     * @param bookID
     * @return <code>true</code> if exists, <code>else</code> otherwise.
     * @throws LogicException 
     */
    public boolean isExistsBookOnWarehouse( String bookID ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                        manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            WarehouseDAO warehouseDAO = mysqlDAOFactory.getWarehouseDAO();
            
            return warehouseDAO.isExistBookInWarehouse( bookID );
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }
    
    /**
     * Gets book amount from warehouse table in DB.
     * @param bookID
     * @see WarehouseDAO#getBookAmount(java.lang.String) 
     * @throws LogicException 
     */
    public int getBookAmountInWarehouse( String bookID ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                        manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            WarehouseDAO warehouseDAO = mysqlDAOFactory.getWarehouseDAO();
            
            return warehouseDAO.getBookAmount( bookID );
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
    }
    
    /**
     * Adds book with given <tt>bookID</tt> in <tt>order</tt>.
     * @param order
     * @param bookID
     * @return {@link CustomerOrder} object
     * @throws LogicException 
     */
    public CustomerOrder addBookInOrder( CustomerOrder order, String bookID ) throws LogicException{
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                        manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            BookDAO bookDAO = mysqlDAOFactory.getBookDAO();
            Book book = bookDAO.getBookInfo( bookID );
            
            order.addBook( bookID, 1, book.getPrice() );
            
            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();
            
            orderDAO.addBookInOrder( bookID, 1, order.getId(), book.getPrice() );
            
        } catch (EntityException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error("Error adding book in Order object", ex);
            throw new LogicException(ex);
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(AddToCartLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }
        return order;
        
    }
}
