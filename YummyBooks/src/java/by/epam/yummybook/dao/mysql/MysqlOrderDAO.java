package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.CustomerDAOException;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.OrderDAO;
import by.epam.yummybook.dao.OrderDAOException;
import by.epam.yummybook.dao.OrderedBookDAOException;
import by.epam.yummybook.dao.logger.DAOLogger;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.entity.EntityException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with MySQL customer_order DB table.
 * @see OrderDAO
 * @author Elena Vizgalova
 */
public class MysqlOrderDAO implements OrderDAO {

    private static MysqlOrderDAO instance;
    private static MysqlOrderedBookDAO orderedBookDAO;
    private static MysqlCustomerDAO customerDAO;
    
    private final String SELECT_ALL_ORDERS = "SELECT * FROM customer_order";
    private final String SELECT_CUSTOMER_ORDER = "SELECT * FROM customer_order WHERE id = ?";
    private final int ID_SELECT_INDEX = 1;
    private final int IS_PAYED_SELECT_INDEX = 2;
    private final int DATE_CREATED_SELECT_INDEX = 3;
    private final int CUSTOMER_ID_SELECT_INDEX = 4;
    private final int TOTAL_COST_SELECT_INDEX = 5;
    private final int IS_CONFIRMED_SELECT_INDEX = 6;
    
    private final String SELECT_ORDERID_BY_CUSTOMER_ID = 
            "SELECT id FROM customer_order WHERE customer_id = ?";
    
    private final String UPDATE_SET_IS_PAYED_TRUE = 
            "UPDATE customer_order SET is_payed = TRUE WHERE id = ?";
    private final String UPDATE_SET_IS_CONFIRMED = 
            "UPDATE customer_order SET is_confirmed = TRUE WHERE id = ?";
    
    private final String UPDATE_SET_IS_PAYED_FLAG = 
            "UPDATE customer_order SET is_payed = ? WHERE id = ?";
    private final int IS_PAYED_UPDATE_IS_PAYED_FLAG = 1;
    private final int ID_UPDATE_IS_PAYED_FLAG = 2;
    
    private final String DELETE_ORDER = 
            "DELETE FROM customer_order WHERE id = ?";
    
    private final String UPDATE_BOOK_QUANTITY = 
            "UPDATE customer_order SET total_cost = ? WHERE id = ?";
    private final String UPDATE_TOTAL_ADD_BOOK = 
            "UPDATE customer_order SET total_cost = total_cost + ? WHERE id = ?";
    private final int TOTAL_COST_UPDATE_TOTAL_INDEX = 1;
    private final int ORDER_ID_UPDATE_TOTAL_INDEX = 2;
    
    private final String INSERT_NEW_ORDER = 
            "INSERT INTO customer_order (is_payed, is_confirmed, customer_id, total_cost) "
            + "VALUES (FALSE, FALSE, ?, ?)";
    private final int CUSTOMER_ID_INSERT_INDEX = 1;
    private final int TOTAL_COST_INSERT_INDEX = 2;
    
    
    private MysqlOrderDAO() throws OrderDAOException{
        try {
            
            orderedBookDAO = MysqlOrderedBookDAO.getInstance();
            customerDAO = MysqlCustomerDAO.getInstance();
            
        } catch (CustomerDAOException | OrderedBookDAOException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException( ex );
        }
    }
    
    private static void init() throws OrderDAOException {
        instance = new MysqlOrderDAO();
    }
    
    public static synchronized MysqlOrderDAO getInstance() throws OrderDAOException {
        try {
            if (null == instance) {
                init();
            }
        } catch( DAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException( ex );
        }
        return instance;
    }
    
    /**
     * @see OrderDAO#getOrderByOrderID(int) 
     */
    @Override
    public CustomerOrder getOrderByOrderID( int orderID ) throws OrderDAOException {
        CustomerOrder order = new CustomerOrder();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_CUSTOMER_ORDER );
            preparedStatement.setInt( 1, orderID );
            
            resultSet = preparedStatement.executeQuery();
            
            resultSet.next();
            
            order.setId( orderID );
            
            Date dateCreated = resultSet.getDate( DATE_CREATED_SELECT_INDEX );
            java.util.Date newDate = dateCreated;
            order.setDateCreated( newDate );
            
            order.setIsPayed( resultSet.getBoolean( IS_PAYED_SELECT_INDEX ) );
            
            int customerID = resultSet.getInt( CUSTOMER_ID_SELECT_INDEX );
            order.setCustomer( customerDAO.getCustomerByID( customerID ) );
            
            order.setBookQuantity( orderedBookDAO.getOrderedBooks( orderID ) );
            
            order.setTotal( resultSet.getDouble( TOTAL_COST_SELECT_INDEX ) );
            
            order.setIsConfirmed( resultSet.getBoolean( IS_CONFIRMED_SELECT_INDEX ) );
            
            connectionPool.releaseConnection( connection );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error("Error writing data from DB into Order object", ex);
            throw new OrderDAOException( ex );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return order;
    }

    /**
     * @see OrderDAO#getOrdersByCustomerID(int) 
     */
    @Override
    public List<CustomerOrder> getOrdersByCustomerID( int customerID ) throws OrderDAOException {
        List<CustomerOrder> customerOrders = new ArrayList<>();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {

            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_ORDERID_BY_CUSTOMER_ID );
            preparedStatement.setInt( 1, customerID );
            
            resultSet = preparedStatement.executeQuery();
            
            while( resultSet.next() ) {
                int orderID = resultSet.getInt( ID_SELECT_INDEX );
                CustomerOrder order = getOrderByOrderID( orderID );
                customerOrders.add( order );
            }
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return customerOrders;
    }

    /**
     * @see OrderDAO#setIsPayed(int) 
     */
    @Override
    public void setIsPayed( int orderID ) throws OrderDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_SET_IS_PAYED_TRUE );
            preparedStatement.setInt( 1, orderID );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderDAO#deleteOrder(int) 
     */
    @Override
    public void deleteOrder( int orderID ) throws OrderDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            orderedBookDAO.deleteOrder( orderID );
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( DELETE_ORDER );
            preparedStatement.setInt( 1, orderID );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }

    }

    /**
     * @see OrderDAO#createOrder(int) 
     */
    @Override
    public void createOrder(int customerID) throws OrderDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( INSERT_NEW_ORDER );
            preparedStatement.setInt( CUSTOMER_ID_INSERT_INDEX, customerID );
            preparedStatement.setDouble( TOTAL_COST_INSERT_INDEX , 0.0 );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderDAO#addBookInOrder(java.lang.String, int, int, double) 
     */
    @Override
    public void addBookInOrder(String bookID, int amount, int orderID, double price) throws OrderDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            orderedBookDAO.addBook( bookID, amount, orderID );
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_TOTAL_ADD_BOOK );
            
            preparedStatement.setDouble( TOTAL_COST_UPDATE_TOTAL_INDEX , price );
            preparedStatement.setInt( ORDER_ID_UPDATE_TOTAL_INDEX , orderID );
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (SQLException | DAOException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderDAO#updateBookQuantity(java.lang.String, int, int, double) 
     */
    @Override
    public void updateBookQuantity(String bookID, int amount, int orderID, double newPrice) throws OrderDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try{
            orderedBookDAO.updateBookQuantity( bookID, amount, orderID );
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            if ( 0.0 == newPrice ) {
                deleteOrder( orderID );
            } else {
            
                preparedStatement = (PreparedStatement) connection.
                        prepareStatement( UPDATE_BOOK_QUANTITY );

                preparedStatement.setDouble( TOTAL_COST_UPDATE_TOTAL_INDEX , newPrice );
                preparedStatement.setInt( ORDER_ID_UPDATE_TOTAL_INDEX , orderID );
                
                preparedStatement.executeUpdate();

            }
            connectionPool.releaseConnection( connection );
        } catch( SQLException | DAOException ex ) {
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderDAO#setIsConfirmed(int) 
     */
    @Override
    public void setIsConfirmed(int orderID) throws OrderDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_SET_IS_CONFIRMED );
            preparedStatement.setInt( 1, orderID );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see OrderDAO#getAllOrders() 
     */
    @Override
    public List<CustomerOrder> getAllOrders() throws OrderDAOException {
        
        List<CustomerOrder> customerOrders = new ArrayList<>();
        
        ResultSet resultSet = null;
        Connection connection;
        Statement statement = null;
        
        try {
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery( SELECT_ALL_ORDERS );
            
            while ( resultSet.next() ) {
                
                int orderID = resultSet.getInt( ID_SELECT_INDEX );
                
                CustomerOrder order = getOrderByOrderID( orderID );
                
                customerOrders.add( order );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return customerOrders;
    }

    /**
     * @see OrderDAO#setIsPayedFlag(int, boolean) 
     */
    @Override
    public void setIsPayedFlag(int orderID, boolean flagToSet) throws OrderDAOException {
        
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_SET_IS_PAYED_FLAG );
            preparedStatement.setInt( ID_UPDATE_IS_PAYED_FLAG , orderID );
            preparedStatement.setBoolean( IS_PAYED_UPDATE_IS_PAYED_FLAG , flagToSet);
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection(connection);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
            logger.error(ex);
            throw new OrderDAOException(ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlOrderDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }
    
}
