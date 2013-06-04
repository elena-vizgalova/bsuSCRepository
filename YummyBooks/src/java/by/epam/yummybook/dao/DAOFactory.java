package by.epam.yummybook.dao;

import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.manager.DAOFactoryManager;

/**
 * Abstract factory for getting concrete DAO factory.
 * @author Elena Vizgalova
 */
public abstract class DAOFactory {
    
    public abstract LoginDAO getLoginDAO() throws LoginDAOException;
    public abstract BookDAO getBookDAO() throws BookDAOException;
    public abstract WarehouseDAO getWarehouseDAO() throws WarehouseDAOException;
    public abstract OrderDAO getOrderDAO() throws OrderDAOException;
    public abstract CategoryDAO getCategoryDAO() throws CategoryDAOException;
    public abstract CustomerDAO getCustomerDAO() throws CustomerDAOException;

    /**
     * Gets concrete DAOFactory by <tt>whichFactory</tt> parameter.
     * @param whichFactory
     * @return concrete DAOFactory or <code>null</code>
     * @throws DAOException
     */
    public static synchronized DAOFactory getDAOFactory( int whichFactory ) throws DAOException {
        DAOFactoryManager daoFactoryManager = DAOFactoryManager.getInstance();

        if (daoFactoryManager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) == whichFactory) {
            return MysqlDAOFactory.getInstance();
        }
        
        return null;
    }
    
}
