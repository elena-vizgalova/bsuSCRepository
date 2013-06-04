package by.epam.yummybook.logic;

import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.List;

/**
 * Provides methods for viewing orders.
 * @author Elena Vizgalova
 */
public class ViewOrdersLogic {

    private static ViewOrdersLogic instance;

    private ViewOrdersLogic() {
    }

    public static synchronized ViewOrdersLogic getInstance() {
        if (null == instance) {
            instance = new ViewOrdersLogic();
        }
        return instance;
    }

    /**
     * Gets orders list from DB.
     * @return {@link CustomerOrder} list
     * @throws LogicException 
     */
    public List<CustomerOrder> loadAllOrders() throws LogicException {
        try {
            List<CustomerOrder> orders;
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            orders = orderDAO.getAllOrders();
            
            return orders;
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(ViewOrdersLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
    }
}
