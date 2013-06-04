package by.epam.yummybook.logic;

import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.List;

/**
 * Provides methods for viewing customers.
 * @author Elena Vizgalova
 */
public class ViewCustomersLogic {
    
    private static ViewCustomersLogic instance;
    
    private ViewCustomersLogic(){
    }
    
    public static synchronized ViewCustomersLogic getInstance() {
        if ( null == instance ) {
            instance = new ViewCustomersLogic();
        }
        return instance;
    }
    
    /**
     * Gets customers list from DB.
     * @return {@link Customer} list
     * @throws LogicException 
     */
    public List<Customer> loadAllCustomers() throws LogicException {
        try {
            List<Customer> customers;
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();

            customers = customerDAO.getAllCustomers();
            
            return customers;
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(ViewCustomersLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
    }
    
}
