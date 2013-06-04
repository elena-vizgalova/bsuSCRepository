package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.CategoryDAO;
import by.epam.yummybook.dao.CategoryDAOException;
import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.logger.DAOLogger;
import by.epam.yummybook.entity.Category;
import by.epam.yummybook.entity.EntityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with MySQL category DB table.
 * @see CategoryDAO
 * @author Elena Vizgalova
 */
public class MysqlCategoryDAO implements CategoryDAO {

    private static MysqlCategoryDAO instance;
    
    private final String SELECT_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = ?";
    private final String SELECT_NAME_BY_ID = "SELECT name FROM category WHERE id = ?";
    private final String SELECT_ALL_CATEGORIES = "SELECT * FROM category";
    
    private final int ID_COLUMN_INDEX = 1;
    private final int NAME_COLUMN_INDEX = 2;

    private MysqlCategoryDAO(){
    }
    
    private static void init() throws CategoryDAOException {
        instance = new MysqlCategoryDAO();
    }
    
    public static synchronized MysqlCategoryDAO getInstance() throws CategoryDAOException {
        try {
            
            if (null == instance) {
                init();
            }
            
        } catch ( DAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
            logger.error("Error during init", ex);
            throw new CategoryDAOException( ex );
        }
        
        return instance;
    }
    
    /**
     * @see CategoryDAO#getCategoryName(int) 
     */
    @Override
    public String getCategoryName( int id ) throws CategoryDAOException {
        
        String name = null;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_NAME_BY_ID );
            preparedStatement.setInt( 1, id );

            resultSet = preparedStatement.executeQuery();
            
            resultSet.next();
            name = resultSet.getString( 1 );
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
            logger.error("Error during init", ex);
            throw new CategoryDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return name;
    }

    /**
     * @see CategoryDAO#getCategoryById(int) 
     */
    @Override
    public Category getCategoryById( int id ) throws CategoryDAOException {
        
        Category category = new Category();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_CATEGORY_BY_ID );
            preparedStatement.setInt( 1, id );

            resultSet = preparedStatement.executeQuery();
            
            resultSet.next();
            
            String name = resultSet.getString( NAME_COLUMN_INDEX );
            
            category.setId( id );
            category.setName( name );
            
            connectionPool.releaseConnection( connection );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
            logger.error( "Error in writing data into Category, id = " + id, ex );
            throw new CategoryDAOException( ex );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
            logger.error( ex );
            throw new CategoryDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return category;
    }

    /**
     * @see CategoryDAO#getAllCategories() 
     */
    @Override
    public List<Category> getAllCategories() throws CategoryDAOException {
        
        List<Category> categories = new ArrayList<>();
        
        ResultSet resultSet = null;
        Connection connection;
        Statement statement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            statement = connection.createStatement();

            resultSet = statement.executeQuery( SELECT_ALL_CATEGORIES );
            
            while (  resultSet.next() ) {
                Category category = new Category();
                
                int id = resultSet.getInt( ID_COLUMN_INDEX );
                String name = resultSet.getString( NAME_COLUMN_INDEX );
                
                category.setName(name);
                category.setId(id);
                
                categories.add( category );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance( MysqlCategoryDAO.class );
            logger.error( "Error writing data into Category object", ex );
            throw new CategoryDAOException( ex );        
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCategoryDAO.class);
            logger.error( ex );
            throw new CategoryDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCategoryDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCategoryDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return categories;
    }

    /**
     * @see CategoryDAO#isCategoryExists(int) 
     */
    @Override
    public boolean isCategoryExists( int id ) throws CategoryDAOException {
        boolean isExists = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_NAME_BY_ID );
            preparedStatement.setInt( 1, id );

            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                isExists = true;
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCategoryDAO.class);
            logger.error( ex );
            throw new CategoryDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCategoryDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCategoryDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return isExists;
    }
    
}
