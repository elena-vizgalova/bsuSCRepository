package by.epam.yummybook.logic;

import by.epam.yummybook.dao.BookDAO;
import by.epam.yummybook.dao.CategoryDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Book;
import by.epam.yummybook.entity.Category;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.List;

/**
 * Singleton that provides methods for working with category.
 * @author Elena Vizgalova
 */
public class CategoryLogic {
    
    private static CategoryLogic instance;
    
    private CategoryLogic() {
    }
    
    public static synchronized CategoryLogic getInstance() {
        if ( null == instance ) {
            instance = new CategoryLogic();
        }
        return instance;
    }
    
    /**
     * Gets all categories from DB.
     * @return {@link Category} list
     * @throws LogicException 
     */
    public List<Category> getAllCategories() throws LogicException {
        
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                    manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );

            CategoryDAO categoryDAO = mysqlDAOFactory.getCategoryDAO();
            
            return categoryDAO.getAllCategories();
            
        } catch ( DAOException ex ) {
            LogicLogger logger = LogicLogger.getInstance(CategoryLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
        
    }
    
    /**
     * Gets category by given <tt>categoryID</tt> from DB.
     * @param categoryID
     * @return {@link Category} object
     * @throws LogicException 
     */
    public Category getCategoryByID( int categoryID ) throws LogicException {
        try {
            DAOFactoryManager factoryManager = DAOFactoryManager.getInstance();
            int mysqlFactoryID = factoryManager.getProperty( DAOFactoryManager.MYSQL_FACTORY );
            DAOFactory daoFactory = DAOFactory.getDAOFactory( mysqlFactoryID );

            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            return categoryDAO.getCategoryById( categoryID );
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(CategoryLogic.class);
            logger.error(ex);
            throw new LogicException( "DAO error! categoryID = " + categoryID, ex );
        }
    }
    
    /**
     * Gets list of books which belong to category with given <tt>categoryID</tt>.
     * @param categoryID
     * @return list of {@link Book} objects
     * @throws LogicException 
     */
    public List<Book> getBookListByCategoryID( int categoryID ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                        manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );
            
            BookDAO bookDAO = mysqlDAOFactory.getBookDAO();
            
            return bookDAO.getBookListByCategoryID( categoryID );  
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(CategoryLogic.class);
            logger.error(ex);
            throw new LogicException( "DAO error! categoryID = " + categoryID, ex );
        }
    }
    
}
