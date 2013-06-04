package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.BookDAO;
import by.epam.yummybook.dao.BookDAOException;
import by.epam.yummybook.dao.CategoryDAO;
import by.epam.yummybook.dao.CategoryDAOException;
import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.CustomerDAOException;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.LoginDAO;
import by.epam.yummybook.dao.LoginDAOException;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.OrderDAOException;
import by.epam.yummybook.dao.WarehouseDAO;
import by.epam.yummybook.dao.WarehouseDAOException;

/**
 * Singleton factory for MysqlDAO.
 * @see DAOFactory
 * @author Elena Vizgalova
 */
public class MysqlDAOFactory extends DAOFactory {

    private static MysqlDAOFactory instance;
    
    private MysqlDAOFactory() {
    }
    
    private static void init() throws DAOException {
        instance = new MysqlDAOFactory();
    }
    
    public static synchronized MysqlDAOFactory getInstance() throws DAOException {
        if ( null == instance ) {
            init();
        }
        return instance;
    }

    @Override
    public LoginDAO getLoginDAO() throws LoginDAOException {
        return MysqlLoginDAO.getInstance();
    }

    @Override
    public BookDAO getBookDAO() throws BookDAOException {
        return MysqlBookDAO.getInstance();
    }

    @Override
    public OrderDAO getOrderDAO() throws OrderDAOException {
        return MysqlOrderDAO.getInstance();
    }

    @Override
    public CategoryDAO getCategoryDAO() throws CategoryDAOException {
        return MysqlCategoryDAO.getInstance();
    }

    @Override
    public CustomerDAO getCustomerDAO() throws CustomerDAOException {
        return MysqlCustomerDAO.getInstance();
    }

    @Override
    public WarehouseDAO getWarehouseDAO() throws WarehouseDAOException {
        return MysqlWarehouseDAO.getInstance();
    }
    
}
