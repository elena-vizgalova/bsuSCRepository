package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.BookDAO;
import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.WarehouseDAO;
import by.epam.yummybook.dao.WarehouseDAOException;
import by.epam.yummybook.dao.logger.DAOLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Works with MySQL warehouse DB table.
 * @author Elena Vizgalova
 */
public class MysqlWarehouseDAO implements WarehouseDAO {
    
    private static MysqlWarehouseDAO instance;
    private static BookDAO bookDAO;

    private final String SELECT_BOOK_AMOUNT = "SELECT amount FROM warehouse WHERE book_id = ?";
    
    private final String UPDATE_ADD_BOOK_AMOUNT = "UPDATE warehouse "
            + "SET amount = amount + ? WHERE book_id = ?";
    private final String UPDATE_DELETE_BOOK_AMOUNT = "UPDATE warehouse "
            + "SET amount = amount - ? WHERE book_id = ?";
    private final int BOOK_ID_UPDATE_INDEX = 2;
    private final int AMOUNT_UPDATE_INDEX = 1;
    
    private final String INSERT_BOOK = "INSERT INTO warehouse (book_id, "
            + "amount) VALUES ( ?, ? )";
    private final int BOOK_ID_INSERT_INDEX = 1;
    private final int AMOUNT_INSERT_INDEX = 2;
    
    private MysqlWarehouseDAO() throws WarehouseDAOException {
        try {
            
            MysqlDAOFactory mysqlDAOFactory = MysqlDAOFactory.getInstance();
            bookDAO = mysqlDAOFactory.getBookDAO();
            
        } catch (DAOException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
            logger.error( "Error getting bookDAO", ex);
            throw new WarehouseDAOException( ex );
        }
    }
    
    private static void init() throws WarehouseDAOException {
        instance = new MysqlWarehouseDAO();
    }
    
    public static synchronized MysqlWarehouseDAO getInstance() throws WarehouseDAOException {
        try {
            
            if (null == instance) {
                init();
            }
            
        } catch ( DAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
            logger.error(ex);
            throw new WarehouseDAOException( ex );
        }
        return instance;
    }

    /**
     * @see WarehouseDAO#getBookAmount(java.lang.String) 
     */
    @Override
    public int getBookAmount( String bookID ) throws WarehouseDAOException {
        int amount = 0;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();

            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_BOOK_AMOUNT );
            preparedStatement.setString( 1, bookID );
            
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            amount = resultSet.getInt( 1 );

            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
            logger.error(ex);
            throw new WarehouseDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return amount;
    }

    /**
     * @see WarehouseDAO#addBookWithIDToWarehouse(java.lang.String, int) 
     */
    @Override
    public void addBookWithIDToWarehouse(String bookID, int quantity) throws WarehouseDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try{
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();

            if ( !bookDAO.isAlreadyExists( bookID ) ){
                throw new WarehouseDAOException("There are no book with id: " + bookID + " in DB!");
            }
            
            if ( !isExistBookInWarehouse( bookID ) ) {
                preparedStatement = (PreparedStatement)connection.
                    prepareStatement( INSERT_BOOK );
                
                preparedStatement.setString( BOOK_ID_INSERT_INDEX, bookID);
                preparedStatement.setInt( AMOUNT_INSERT_INDEX, quantity);
                
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = (PreparedStatement) connection.
                        prepareStatement(UPDATE_ADD_BOOK_AMOUNT);

                preparedStatement.setInt(AMOUNT_UPDATE_INDEX, quantity);
                preparedStatement.setString(BOOK_ID_UPDATE_INDEX, bookID);

                preparedStatement.executeUpdate();
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
            logger.error(ex);
            throw new WarehouseDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see WarehouseDAO#isExistBookInWarehouse(java.lang.String) 
     */
    @Override
    public boolean isExistBookInWarehouse(String bookID) throws WarehouseDAOException {
        boolean isExists = false;
        
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement =  (PreparedStatement)connection.
                    prepareStatement( SELECT_BOOK_AMOUNT );
            preparedStatement.setString( 1, bookID);
            
            resultSet = preparedStatement.executeQuery();
            isExists = resultSet.next();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
            logger.error(ex);
            throw new WarehouseDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }

        return isExists;
    }

    /**
     * @see WarehouseDAO#deleteNBooksFromWarehouse(java.lang.String, int) 
     */
    @Override
    public void deleteNBooksFromWarehouse(String bookID, int n) throws WarehouseDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            int amount = getBookAmount( bookID );
            
            if ( amount - n < 0 ) {
                throw new WarehouseDAOException("Impossible delete from warehouse more books that is has!");
            }
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement =  (PreparedStatement)connection.
                    prepareStatement( UPDATE_DELETE_BOOK_AMOUNT );
            
            preparedStatement.setString( BOOK_ID_UPDATE_INDEX, bookID);
            preparedStatement.setInt( AMOUNT_UPDATE_INDEX, n );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
            logger.error(ex);
            throw new WarehouseDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlWarehouseDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }
    
}
