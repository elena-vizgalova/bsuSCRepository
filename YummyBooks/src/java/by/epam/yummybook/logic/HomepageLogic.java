package by.epam.yummybook.logic;

import by.epam.yummybook.dao.CategoryDAO;
import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.Category;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.CustomerLoginInfo;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.List;

/**
 *
 * @author Elena Vizgalova
 */
public class HomepageLogic {

    private static HomepageLogic instance;

    private HomepageLogic() {
    }

    public static synchronized HomepageLogic getInstance() {
        if (null == instance) {
            instance = new HomepageLogic();
        }
        return instance;
    }

    public List<Category> getAllCategories() throws LogicException {

        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            CategoryDAO categoryDAO = mysqlDAOFactory.getCategoryDAO();

            return categoryDAO.getAllCategories();

        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(HomepageLogic.class);
            logger.error(ex);
            throw new LogicException(ex);
        }

    }

    public Cart loadCart( LoginInfo loginInfo ) throws LogicException {
        try {
            CustomerOrder order = null;
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));
            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();

            Customer customer = customerDAO.getCustomerByUsername(loginInfo.getUsername());
            if ( null == customer ) {
                
                Customer newCustomer = new Customer();
                newCustomer.setCustomerLoginInfo((CustomerLoginInfo) loginInfo);
                customerDAO.addCustomer(newCustomer);
                customer = customerDAO.getCustomerByUsername(loginInfo.getUsername());
                
            }

            List<CustomerOrder> orders = orderDAO.getOrdersByCustomerID(customer.getId());

            if ( orders.isEmpty() ) {
                
                orderDAO.createOrder(customer.getId());
                order = new CustomerOrder();
                
            } else {
                
                for (CustomerOrder customerOrder : orders) {
                    if ( !customerOrder.isIsConfirmed() ) {
                        order = customerOrder;
                        break;
                    }
                }
                
                if ( null == order ) {
                    orderDAO.createOrder( customer.getId() );
                    order = new CustomerOrder();
                }
                
            }
            
            return new Cart( order );
        } catch (EntityException | DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(HomepageLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
    }

}
