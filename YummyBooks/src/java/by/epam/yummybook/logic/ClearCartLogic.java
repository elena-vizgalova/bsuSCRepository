package by.epam.yummybook.logic;

import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;

/**
 * Provides logic for clearing cart operation.
 * @author Elena Vizgaloca
 */
public class ClearCartLogic {
    
    private static ClearCartLogic instance;

    private ClearCartLogic() {
    }
    
    public static synchronized ClearCartLogic getInstance() {
        if ( null == instance ) {
            instance = new ClearCartLogic();
        }
        return instance;
    }
    
    /**
     * Deletes order with specific <tt>orderID</tt> from DB.
     * @param orderID
     * @throws LogicException 
     */
    public void clearCart( int orderID ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                        manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );

            OrderDAO orderDAO = mysqlDAOFactory.getOrderDAO();
            orderDAO.deleteOrder( orderID );
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(ClearCartLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
    }
    
}
