package by.epam.yummybook.dao.mysql;

import by.epam.yummybook.dao.ConnectionPool;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.LoginDAO;
import by.epam.yummybook.dao.LoginDAOException;
import by.epam.yummybook.dao.logger.DAOLogger;
import by.epam.yummybook.entity.AdminLoginInfo;
import by.epam.yummybook.entity.CustomerLoginInfo;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.encoder.MacStringSaltEncoder;
import by.epam.yummybook.logic.encoder.SaltEncryptorException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sun.misc.BASE64Encoder;
import sun.misc.CharacterEncoder;

/**
 * Works with MySQL users DB table, corrects information about books in warehouse
 * MySQL DB table.
 * @author Alena_Vizgalava
 */
public class MysqlLoginDAO implements LoginDAO {
    
    private static MysqlLoginDAO instance;
    
    private final String SET_IS_IN_BLACKLIST_FLAG = 
            "UPDATE users SET is_in_blacklist = ? WHERE username = ?";
    private final int FLAG_SET_BLACKLIST_INDEX = 1;
    private final int USERNAME_SET_BLACKLIST_INDEX = 2;
    
    private final String CHECK_USER_PASSWORD = 
            "SELECT * FROM users WHERE username = ? AND password = ?";
    private final String CHECK_ADMIN = "SELECT is_admin FROM users WHERE username = ?";
    private final String CHECK_USER_IN_BLACKLIST = "SELECT is_in_blacklist FROM users WHERE username = ?";
    private final String SELECT_BY_USER = "SELECT * FROM users WHERE username = ?";
    
    private final int USER_INDEX = 1;
    private final int PASSWORD_INDEX = 2;
    private final int IS_ADMIN_INDEX = 3;
    private final int IS_IN_BLACK_LIST_INDEX = 4;
    
    private final String MAC_ENCODING_ALGORITHM = "HmacSHA1";
    private final CharacterEncoder CHARACTER_ENCODER = new BASE64Encoder();
    private final String SALT = "asdgfkdf73vkjl299^&hkd";

    private static void init() throws LoginDAOException {
        instance = new MysqlLoginDAO();
    }
    
    public static synchronized MysqlLoginDAO getInstance() throws LoginDAOException {
        try {
            
            if (null == instance) {
                init();
            }
            
        } catch ( DAOException ex ) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error("Get instance error", ex);
            throw new LoginDAOException( ex );
        }
        
        return instance;
    }

    /**
     * @see LoginDAO#checkLoginInfo(java.lang.String, java.lang.String) 
     */
    @Override
    public boolean checkLoginInfo(String user, String password) throws LoginDAOException {
        boolean isCorrect = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            MacStringSaltEncoder encoder = new MacStringSaltEncoder( MAC_ENCODING_ALGORITHM, CHARACTER_ENCODER);
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.prepareStatement(
                        CHECK_USER_PASSWORD );
            String encryptedPassword = encoder.encryptWithSalt( password.getBytes(), SALT.getBytes() );
            preparedStatement.setString( USER_INDEX, user );
            preparedStatement.setString( PASSWORD_INDEX, encryptedPassword );
            
            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                isCorrect = true;
            }
            
            connectionPool.releaseConnection( connection );
        } catch (SaltEncryptorException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error( "Password encryption error", ex );
            throw new LoginDAOException( ex );
        } catch (NoSuchAlgorithmException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error("No such encryption algorythm. Algorithm: " + MAC_ENCODING_ALGORITHM, ex);
            throw new LoginDAOException( ex );
        }  catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error(ex);
            throw new LoginDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return isCorrect;
    }

    /**
     * @see LoginDAO#isAdmin(java.lang.String) 
     */
    @Override
    public boolean isAdmin( String user ) throws LoginDAOException {
        boolean isAdmin = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( CHECK_ADMIN );
            preparedStatement.setString( USER_INDEX, user );

            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                isAdmin = resultSet.getBoolean( 1 );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error(ex);
            throw new LoginDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return isAdmin;
    }

    /**
     * @see LoginDAO#isInBlackList(java.lang.String) 
     */
    @Override
    public boolean isInBlackList( String user ) throws LoginDAOException {
        boolean isInBlacklist = false;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( CHECK_USER_IN_BLACKLIST );
            preparedStatement.setString( USER_INDEX, user );
            
            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                isInBlacklist = resultSet.getBoolean( 1 );
            }
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error(ex);
            throw new LoginDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return isInBlacklist;
    }

    /**
     * @see LoginDAO#getLoginInfo(java.lang.String) 
     */
    @Override
    public LoginInfo getLoginInfo(String user) throws LoginDAOException {
        LoginInfo loginInfo = null;
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            
            if ( isAdmin( user ) ) {
                loginInfo = new AdminLoginInfo();
            } else {
                loginInfo = new CustomerLoginInfo();
            }
            
            loginInfo.setUsername( user );
            
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SELECT_BY_USER );
            preparedStatement.setString( USER_INDEX, user );

            resultSet = preparedStatement.executeQuery();
            
            while( resultSet.next() ) {
                loginInfo.setIsInBlackList( resultSet.getBoolean( IS_IN_BLACK_LIST_INDEX ) );
                loginInfo.setPassword( resultSet.getString( PASSWORD_INDEX ) );
            }
            
            connectionPool.releaseConnection( connection );
        } catch ( EntityException ex ) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error("Error writing data from DB into LoginInfo object", ex);
            throw new LoginDAOException( ex );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error(ex);
            throw new LoginDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
        return loginInfo;
    }

    /**
     * @see LoginDAO#setIsInBlackListFlag(java.lang.String, boolean) 
     */
    @Override
    public void setIsInBlackListFlag( String user, boolean flagToSet ) throws LoginDAOException {
        
        ResultSet resultSet = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.takeConnection();
            
            preparedStatement = (PreparedStatement) connection.
                    prepareStatement( SET_IS_IN_BLACKLIST_FLAG );
            
            preparedStatement.setString( USERNAME_SET_BLACKLIST_INDEX, user );
            preparedStatement.setBoolean( FLAG_SET_BLACKLIST_INDEX, flagToSet );
            
            preparedStatement.executeUpdate();
            
            connectionPool.releaseConnection( connection );
        } catch (DAOException | SQLException ex) {
            DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
            logger.error(ex);
            throw new LoginDAOException( ex );
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The result set cannot be closed.", e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    DAOLogger logger = DAOLogger.getInstance(MysqlLoginDAO.class);
                    logger.error("The statement cannot be closed.", e);
                }
            }
        }
        
    }
    
}
