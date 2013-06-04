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
 * Singleton class for realizing isPayed flag changing.
 * @author Elena Vizgalova
 */
public class ChangeIsPayedOrderFlagLogic {
    
    private static ChangeIsPayedOrderFlagLogic instance;

    private ChangeIsPayedOrderFlagLogic() {
    }
    
    public static synchronized ChangeIsPayedOrderFlagLogic getInstance() {
        if ( null == instance ) {
            instance = new ChangeIsPayedOrderFlagLogic();
        }
        return instance;
    }
    
    /**
     * Sets isPayed flag for order with given <tt>orderID</tt> in <tt>orders</tt> list.
     * @param orderID
     * @param flag
     * @param orders
     * @return <tt>orders</tt> list with changed flag for order with given <tt>orderID</tt>
     * @throws LogicException 
     */
    public List<CustomerOrder> setIsPayedOrderFlag( int orderID , boolean flag, List<CustomerOrder> orders ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                                manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );
            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();

            orderDAO.setIsPayedFlag( orderID, flag );
            
            for ( CustomerOrder order: orders ) {
                if ( order.getId() == orderID ) {
                    order.setIsPayed( flag );
                    break;
                }
            }
            
            return orders;
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(ChangeIsPayedOrderFlagLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
    }
    
    
}
