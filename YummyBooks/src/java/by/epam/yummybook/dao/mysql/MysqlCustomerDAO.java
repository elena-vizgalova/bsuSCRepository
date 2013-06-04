package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.CustomerDAOException;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.LoginDAO;
import by.epam.yummybook.dao.logger.DAOLogger;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Works with MySQL customer DB table.
 * @see CustomerDAO
 * @author Elena Vizgalova
 */
public class MysqlCustomerDAO implements CustomerDAO {
    
    private static MysqlCustomerDAO instance;
    private static LoginDAO loginDAO;
    
    private final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
    
    private final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customer WHERE id = ?";
    private final String SELECT_CUSTOMER_BY_USERNAME = "SELECT * FROM customer WHERE users_username = ?";
    
    private final String INSERT_CUSTOMER = "INSERT INTO customer (name, email, phone, address, city_region,"
            + "users_username) VALUES ( ?, ?, ?, ?, ?, ? )";
    private final String UPDATE_CUSTOMER = "UPDATE customer "
            + "SET name = ?, email = ?, phone = ?, address = ?,"
            + "city_region = ? WHERE id = ?";
    private final int ID_UPDATE_INDEX = 6;
    
    private final int NAME_INSERT_UPDATE_INDEX = 1;
    private final int EMAIL_INSERT_UPDATE_INDEX = 2;
    private final int PHONE_INSERT_UPDATE_INDEX = 3;
    private final int ADDRESS_INSERT_UPDATE_INDEX = 4;
    private final int CITY_REGION_INSERT_UPDATE_INDEX = 5;
    
    private final int USERNAME_INSERT_INDEX = 6;
    
    private final int ID_COLUMN_INDEX = 1;
    private final int NAME_COLUMN_INDEX = 2;
    private final int EMAIL_COLUMN_INDEX = 3;
    private final int PHONE_COLUMN_INDEX = 4;
    private final int ADDRESS_COLUMN_INDEX = 5;
    private final int CITY_REGION_COLUMN_INDEX = 6;
    private final int USERNAME_COLUMN_INDEX = 7;
    
    private MysqlCustomerDAO() throws CustomerDAOException {
        try {
            
            MysqlDAOFactory mysqlDAOFactory = MysqlDAOFactory.getInstance();
            loginDAO = mysqlDAOFactory.getLoginDAO();
            
        } catch (DAOException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error( "Error getting loginDAO" , ex );
            throw new CustomerDAOException( ex );
        }
    }
    
    private static void init() throws CustomerDAOException {
        instance = new MysqlCustomerDAO();
    }
    
    public static synchronized MysqlCustomerDAO getInstance() throws CustomerDAOException {
        try {
            
            if (null == instance) {
                init();
            }
            
        } catch ( DAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error( "Initialize error" , ex );
            throw new CustomerDAOException( ex );
        }
        
        return instance;
    }
    
    /**
     * @see CustomerDAO#getCustomerByID(int) 
     */
    @Override
    public Customer getCustomerByID( int id ) throws CustomerDAOException {
        
        Customer customer = new Customer();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_CUSTOMER_BY_ID );
            preparedStatement.setInt( 1, id );
            
            resultSet = preparedStatement.executeQuery();
            
            if ( !resultSet.next() ) {
                
                throw new CustomerDAOException("There are no customer with id: " 
                        + id + ", - in DB!");
                
            } else {
                customer.setId( id );
                customer.setAddress( resultSet.getString( ADDRESS_COLUMN_INDEX ) );
                customer.setName( resultSet.getString( NAME_COLUMN_INDEX ) );
                customer.setCityRegion( resultSet.getString( CITY_REGION_COLUMN_INDEX ) );
                customer.setEmail( resultSet.getString( EMAIL_COLUMN_INDEX ) );
                customer.setPhone( resultSet.getString( PHONE_COLUMN_INDEX ) );

                String username = resultSet.getString( USERNAME_COLUMN_INDEX );
                LoginInfo customerLoginInfo = (LoginInfo) loginDAO.getLoginInfo( username );

                customer.setCustomerLoginInfo( customerLoginInfo );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error( "Error writing fields from DB into Customer object" , ex );
            throw new CustomerDAOException(ex);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error( ex );
            throw new CustomerDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return customer;
    }

    /**
     * @see CustomerDAO#getCustomerByUsername(java.lang.String) 
     */
    @Override
    public Customer getCustomerByUsername( String username ) throws CustomerDAOException {
        Customer customer = new Customer();
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_CUSTOMER_BY_USERNAME );
            preparedStatement.setString( 1, username );
            
            resultSet = preparedStatement.executeQuery();
            
            if ( !resultSet.next() ) {
                throw new CustomerDAOException("There are no customer with username: " + 
                        username + ", - in DB!");
            } else {
                customer.setName( resultSet.getString( NAME_COLUMN_INDEX ) );
                customer.setId( resultSet.getInt( ID_COLUMN_INDEX ) );
                customer.setAddress( resultSet.getString( ADDRESS_COLUMN_INDEX ) );
                customer.setCityRegion( resultSet.getString( CITY_REGION_COLUMN_INDEX ) );
                customer.setEmail( resultSet.getString( EMAIL_COLUMN_INDEX ) );
                customer.setPhone( resultSet.getString( PHONE_COLUMN_INDEX ) );
                
                LoginInfo customerLoginInfo = loginDAO.getLoginInfo( username );
                customer.setCustomerLoginInfo( customerLoginInfo );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error("Error writing data from DB into Customer object", ex);
            throw new CustomerDAOException(ex);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error( ex );
            throw new CustomerDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return customer;
    }

    /**
     * @see CustomerDAO#addCustomer(by.epam.yummybook.entity.Customer) 
     */
    @Override
    public void addCustomer( Customer customer ) throws CustomerDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_CUSTOMER_BY_USERNAME );
            String username = customer.getCustomerLoginInfo().getUsername();
            preparedStatement.setString( 1, username );
            
            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                throw new CustomerDAOException("Customer with the username: " + 
                        username + ", - has already existed!");
            }
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( INSERT_CUSTOMER );
            preparedStatement.setString( NAME_INSERT_UPDATE_INDEX , customer.getName() );
            preparedStatement.setString( EMAIL_INSERT_UPDATE_INDEX , customer.getEmail() );
            preparedStatement.setString( PHONE_INSERT_UPDATE_INDEX , customer.getPhone() );
            preparedStatement.setString( ADDRESS_INSERT_UPDATE_INDEX, customer.getAddress() );
            preparedStatement.setString( CITY_REGION_INSERT_UPDATE_INDEX, customer.getCityRegion() );
            preparedStatement.setString( USERNAME_INSERT_INDEX, 
                    customer.getCustomerLoginInfo().getUsername() );
            
            resultSet.next();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error(ex);
            throw new CustomerDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see CustomerDAO#isCustomerExists(java.lang.String) 
     */
    @Override
    public boolean isCustomerExists( String username ) throws CustomerDAOException {
        boolean isExists = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_CUSTOMER_BY_USERNAME );
            preparedStatement.setString( 1, username );
            
            resultSet = preparedStatement.executeQuery();
            
            isExists = resultSet.next();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error(ex);
            throw new CustomerDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        return isExists;
    }

    /**
     * @see CustomerDAO#updateCustomerInfo(by.epam.yummybook.entity.Customer) 
     */
    @Override
    public void updateCustomerInfo( Customer customer ) throws CustomerDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( UPDATE_CUSTOMER );
            preparedStatement.setInt( ID_UPDATE_INDEX, customer.getId() );
            preparedStatement.setString( NAME_INSERT_UPDATE_INDEX , customer.getName() );
            preparedStatement.setString( EMAIL_INSERT_UPDATE_INDEX , customer.getEmail() );
            preparedStatement.setString( PHONE_INSERT_UPDATE_INDEX , customer.getPhone() );
            preparedStatement.setString( ADDRESS_INSERT_UPDATE_INDEX, customer.getAddress() );
            preparedStatement.setString( CITY_REGION_INSERT_UPDATE_INDEX, customer.getCityRegion() );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error(ex);
            throw new CustomerDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }

    /**
     * @see CustomerDAO#getAllCustomers() 
     */
    @Override
    public List<Customer> getAllCustomers() throws CustomerDAOException {
        List<Customer> customers = new ArrayList<>();
        
        ResultSet resultSet = null;
        Connection connection;
        Statement statement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery( SELECT_ALL_CUSTOMERS );
            
            while ( resultSet.next() ) {
                Customer customer = new Customer();
                
                customer.setId( resultSet.getInt( ID_COLUMN_INDEX ) );
                customer.setName( resultSet.getString( NAME_COLUMN_INDEX ) );
                customer.setAddress( resultSet.getString( ADDRESS_COLUMN_INDEX ) );
                customer.setCityRegion( resultSet.getString( CITY_REGION_COLUMN_INDEX ) );
                customer.setEmail( resultSet.getString( EMAIL_COLUMN_INDEX ) );
                customer.setPhone( resultSet.getString( PHONE_COLUMN_INDEX ) );

                String username = resultSet.getString( USERNAME_COLUMN_INDEX );
                LoginInfo customerLoginInfo = (LoginInfo) loginDAO.getLoginInfo( username );

                customer.setCustomerLoginInfo( customerLoginInfo );
                
                customers.add( customer );
            }
            
            connectionPool.releaseConnection( connection );
            
            return customers;
        } catch (EntityException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error("Error writing data from DB into Customer object", ex);
            throw new CustomerDAOException(ex);
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
            logger.error(ex);
            throw new CustomerDAOException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlCustomerDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
    }
    
}
