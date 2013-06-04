package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.OrderedBookDAO;
import by.epam.yummybook.dao.OrderedBookDAOException;
import by.epam.yummybook.dao.WarehouseDAOException;
import by.epam.yummybook.dao.logger.DAOLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Works with MySQL ordered_book DB table.
 * @author Elena Vizgalova
 */
public class MysqlOrderedBookDAO implements OrderedBookDAO {

    private static MysqlOrderedBookDAO instance;
    private static MysqlWarehouseDAO warehouseDAO;
    
    private final String SELECT_AMOUNT = "SELECT quantity FROM ordered_book WHERE book_id = ? "
            + "AND customer_order_id = ?";
    private final int BOOK_ID_SELECT_AMOUNT_INDEX = 1;
    private final int CUSTOMER_ORDER_ID_SELECT_AMOUNT_INDEX = 2;
    
    private final String SELECT_ORDERED_BOOKS = "SELECT book_id, quantity FROM ordered_book "
            + "WHERE customer_order_id = ?";
    
    private final String INSERT_BOOK_IN_ORDER = "INSERT INTO ordered_book "
            + "(book_id, quantity, customer_order_id) VALUES ( ?, ?, ? )";
    private final int BOOK_ID_INSERT_INDEX = 1;
    private final int QUANTITY_INSERT_INDEX = 2;
    private final int CUSTOMER_ORDER_ID_INSERT_INDEX = 3;
    
    private final String DELETE_BOOK_WITH_ID = "DELETE FROM ordered_book WHERE book_id = ?";
    private final int BOOK_ID_DELETE_INDEX = 1;
    
    private final String UPDATE_BOOK_QUANTITY = 
            "UPDATE ordered_book SET quantity = ? WHERE customer_order_id = ? AND "
            + "book_id = ?";
    private final String UPDATE_ADD_BOOK = 
            "UPDATE ordered_book SET quantity = quantity + ? WHERE customer_order_id = ? AND "
            + "book_id = ?";
    private final String UPDATE_DELETE_BOOK = 
            "UPDATE ordered_book SET quantity = quantity - ? WHERE customer_order_id = ? AND "
            + "book_id = ?";
    private final int QUANTITY_UPDATE_INDEX = 1;
    private final int CUSTOMER_ORDER_ID_UPDATE_INDEX = 2;
    private final int BOOK_ID_UPDATE_INDEX = 3;
    
    private final String DELETE_ORDER = "DELETE FROM ordered_book WHERE customer_order_id = ?";

    private MysqlOrderedBookDAO() throws OrderedBookDAOException {
        try {
            warehouseDAO = MysqlWarehouseDAO.getInstance();
        } catch ( WarehouseDAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error( "WarehouseDAO get instance error", ex);
            throw new OrderedBookDAOException( ex );
        }
    }
    
    private static void init() throws OrderedBookDAOException {
        instance = new MysqlOrderedBookDAO();
    }
    
    public static synchronized MysqlOrderedBookDAO getInstance() throws OrderedBookDAOException {
        
        try {
            if (null == instance) {
                    init();
            }
        } catch (OrderedBookDAOException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        }
        
        return instance;
    }
    
    /**
     * @see OrderedBookDAO#addBook(java.lang.String, int, int) 
     */
    @Override
    public void addBook(String bookID, int quantity, int customerOrderID) throws OrderedBookDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            int amountInWarehouse = warehouseDAO.getBookAmount( bookID );
            
            if ( 0 >= amountInWarehouse - quantity ) {
                throw new OrderedBookDAOException( "There is only " + amountInWarehouse +
                        " books in warehouse!");
            }
            
            if ( !isBookInOrder(bookID, customerOrderID) ) {
                
                preparedStatement = (PreparedStatement) connection.
                    prepareStatement( INSERT_BOOK_IN_ORDER );
                preparedStatement.setString( BOOK_ID_INSERT_INDEX , bookID);
                preparedStatement.setInt( CUSTOMER_ORDER_ID_INSERT_INDEX, customerOrderID);
                preparedStatement.setInt( QUANTITY_INSERT_INDEX , quantity);
                
                preparedStatement.executeUpdate();
                
                warehouseDAO.deleteNBooksFromWarehouse( bookID, quantity );
                
            } else {
                preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_ADD_BOOK );
                preparedStatement.setString( BOOK_ID_UPDATE_INDEX , bookID);
                preparedStatement.setInt( QUANTITY_UPDATE_INDEX , quantity);
                preparedStatement.setInt( CUSTOMER_ORDER_ID_UPDATE_INDEX , customerOrderID);
                
                preparedStatement.executeUpdate();
                
                warehouseDAO.deleteNBooksFromWarehouse( bookID, quantity );
            }

            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderedBookDAO#getOrderedBooks(int) 
     */
    @Override
    public Map<String, Integer> getOrderedBooks( int customerOrderID ) throws OrderedBookDAOException {
        Map<String, Integer> bookQuantity = new HashMap<>();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_ORDERED_BOOKS );
            
            preparedStatement.setInt( 1, customerOrderID );
            resultSet = preparedStatement.executeQuery();
            
            while( resultSet.next() ) {
                
                String bookID = resultSet.getString( 1 );
                int quantity = resultSet.getInt( 2 );
                
                bookQuantity.put( bookID, quantity );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return bookQuantity;
        
    }

    /**
     * @see OrderedBookDAO#deleteBookFromOrder(java.lang.String, int, int) 
     */
    @Override
    public void deleteBookFromOrder(String bookID, int quantityToDelete, int customerOrderID) throws OrderedBookDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            Map<String, Integer> booksQuantity = getOrderedBooks( customerOrderID );
            booksQuantity.size();
            
            int bookAmount = getBookAmount( bookID, customerOrderID );
            
            if ( 1 == booksQuantity.size() ) {
                
                preparedStatement = (PreparedStatement) connection.
                    prepareStatement( DELETE_ORDER );
                preparedStatement.setInt( 1, customerOrderID );
                
                preparedStatement.executeUpdate();
                
                warehouseDAO.addBookWithIDToWarehouse( bookID, bookAmount );
                
            } else {
                if ( 0 == ( bookAmount - quantityToDelete ) ) {
                    preparedStatement = (PreparedStatement) connection.
                        prepareStatement( DELETE_BOOK_WITH_ID );
                    preparedStatement.setString( BOOK_ID_DELETE_INDEX, bookID );
                    
                    preparedStatement.executeUpdate();
                    
                    warehouseDAO.addBookWithIDToWarehouse( bookID, quantityToDelete );
                } else {
                    preparedStatement = (PreparedStatement) connection.
                        prepareStatement( UPDATE_DELETE_BOOK );
                    preparedStatement.setString( BOOK_ID_UPDATE_INDEX , bookID);
                    preparedStatement.setInt( QUANTITY_UPDATE_INDEX , quantityToDelete);
                    preparedStatement.setInt( CUSTOMER_ORDER_ID_UPDATE_INDEX , customerOrderID);

                    preparedStatement.executeUpdate();

                    warehouseDAO.addBookWithIDToWarehouse( bookID, quantityToDelete );
                }
            }

            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderedBookDAO#isBookInOrder(java.lang.String, int) 
     */
    @Override
    public boolean isBookInOrder(String bookID, int customerOrderID) throws OrderedBookDAOException {
        boolean isInOrder = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_AMOUNT );
            preparedStatement.setString( BOOK_ID_SELECT_AMOUNT_INDEX, bookID );
            preparedStatement.setInt( CUSTOMER_ORDER_ID_SELECT_AMOUNT_INDEX , customerOrderID);
            
            resultSet = preparedStatement.executeQuery();
            
            isInOrder = resultSet.next();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return isInOrder;
    }

    /**
     * @see OrderedBookDAO#getBookAmount(java.lang.String, int) 
     */
    @Override
    public int getBookAmount(String bookID, int customerOrderID) throws OrderedBookDAOException {
        int amount = 0;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_AMOUNT );
            preparedStatement.setString( BOOK_ID_SELECT_AMOUNT_INDEX, bookID );
            preparedStatement.setInt( CUSTOMER_ORDER_ID_SELECT_AMOUNT_INDEX, customerOrderID );
            
            resultSet = preparedStatement.executeQuery();
            
            resultSet.next();
            
            amount = resultSet.getInt( 1 );
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return amount;
    }

    /**
     * @see OrderedBookDAO#deleteOrder(int) 
     */
    @Override
    public void deleteOrder( int orderID ) throws OrderedBookDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_ORDERED_BOOKS );
            preparedStatement.setInt( 1, orderID );
            
            resultSet = preparedStatement.executeQuery();

            while ( resultSet.next() ) {
                String bookID = resultSet.getString( 1 );
                int bookAmount = resultSet.getInt( 2 );
                warehouseDAO.addBookWithIDToWarehouse( bookID, bookAmount );
            }
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( DELETE_ORDER );
            preparedStatement.setInt( 1, orderID );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderedBookDAO#updateBookQuantity(java.lang.String, int, int) 
     */
    @Override
    public void updateBookQuantity(String bookID, int quantityToSet, int customerOrderID) 
            throws OrderedBookDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            int amountInWarehouse = warehouseDAO.getBookAmount( bookID );
            
            if ( 0 >= amountInWarehouse - quantityToSet ) {
                throw new OrderedBookDAOException( "There is only " + amountInWarehouse +
                        " books in warehouse!");
            }
            
            int previousQuantity = getBookAmount( bookID, customerOrderID );
            
            if ( 0 == quantityToSet ) {
                deleteBookFromOrder( bookID, previousQuantity, customerOrderID );
            } else {
            
                preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_BOOK_QUANTITY );
                preparedStatement.setString( BOOK_ID_UPDATE_INDEX , bookID );
                preparedStatement.setInt( QUANTITY_UPDATE_INDEX , quantityToSet );
                preparedStatement.setInt( CUSTOMER_ORDER_ID_UPDATE_INDEX , customerOrderID );

                preparedStatement.executeUpdate();

                if ( previousQuantity - quantityToSet > 0 ) {
                    warehouseDAO.deleteNBooksFromWarehouse( bookID, previousQuantity - quantityToSet );
                } else if ( previousQuantity - quantityToSet < 0 ) {
                    warehouseDAO.addBookWithIDToWarehouse( bookID, previousQuantity - quantityToSet );
                }

                connectionPool.releaseConnection( connection );
            }
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
            logger.error(ex);
            throw new OrderedBookDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderedBookDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }
    
}
