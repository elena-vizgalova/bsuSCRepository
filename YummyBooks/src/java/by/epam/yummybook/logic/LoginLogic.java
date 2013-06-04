package by.epam.yummybook.logic;

import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.dao.CustomerDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.LoginDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.CustomerLoginInfo;
import by.epam.yummybook.entity.EntityException;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;

/**
 * Provides logic operations for log in.
 * @author Elena Vizgalova
 */
public class LoginLogic implements IParameterName {

    private static LoginLogic instance;
    
    private LoginLogic() {
    }
    
    public static synchronized LoginLogic getInstance() {
        
        if ( null == instance ) {
            instance = new LoginLogic();
        }
        
        return instance;
    }
    
    /**
     * Checks, if user with such username and password exists in database.
     * @param username
     * @param password
     * @return {@code true}, if exists, {@code false} otherwise
     * @throws LogicExeption, when <tt>username</tt> is {@code null} or empty, 
     * or <tt>password</tt> is {@code null} or empty
     */
    public boolean isUserExists( String username, String password ) throws LogicException {
        
        if ( null == username ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("Null username error!");
            throw new LogicException( "username can't be null!" );
        }
        
        if ( username.isEmpty() ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("Empty username error!");
            throw new LogicException( "username can't be empty!" );
        }
        
        if ( null == password ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("Null password error!");
            throw new LogicException("password can't be null!");
        }
        
        if ( password.isEmpty() ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("Empty username error!");
            throw new LogicException( "password can't be empty!" );
        }
        
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                            manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );
            
            LoginDAO loginDAO = mysqlDAOFactory.getLoginDAO();
            
            return loginDAO.checkLoginInfo( username, password );
        } catch ( DAOException ex ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error(ex);
            throw new LogicException( "DAO error. username = " + 
                    username + " and password = " + password, ex );
        }
    }
    
    /**
     * Checks if the user with given username is in black list.
     * @param username
     * @return {@code true}, if user is in black list, {@code else} otherwise
     * @throws LogicExeption, when <tt>username</tt> is {@code null} or empty
     */
    public boolean isInBlackList( String username ) throws LogicException {
        
        if ( null == username ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("null username error!");
            throw new LogicException( "username can't be null!" );
        }
        
        if ( username.isEmpty() ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("Empty username error!");
            throw new LogicException( "username can't be empty!" );
        }
        
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                            manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );
            
            LoginDAO loginDAO = mysqlDAOFactory.getLoginDAO();
            
            return loginDAO.isInBlackList( username );
        } catch ( DAOException ex ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error(ex);
            throw new LogicException( "DAO error. username = " + username, ex );
        }
    }
    
    /**
     * Gets {@link LoginInfo } object of user with given <tt>username</tt>.
     * @param username
     * @return <tt>LoginInfo</tt> object or {@code null} if there is no such user with <tt>username</tt>
     * @throws LogicExeption, when <tt>username</tt> is {@code null} or empty
     */
    public LoginInfo getLoginInfo( String username ) throws LogicException {
        
        if ( null == username ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("null username error!");
            throw new LogicException( "Username can't be null!" );
        }
        
        if ( username.isEmpty() ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error("Empty username error!");
            throw new LogicException( "username can't be empty!" );
        }
        
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                            manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );
            
            LoginDAO loginDAO = mysqlDAOFactory.getLoginDAO();
            
            return loginDAO.getLoginInfo( username );
        } catch ( DAOException ex ) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error(ex);
            throw new LogicException( "DAO error. username = " + username, ex );
        }
    }
    
    public Customer loadCustomer( LoginInfo loginInfo ) throws LogicException {
        try {
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory(
                    manager.getProperty(DAOFactoryManager.MYSQL_FACTORY));

            CustomerDAO customerDAO = mysqlDAOFactory.getCustomerDAO();

            Customer customer;

            if ( !customerDAO.isCustomerExists( loginInfo.getUsername() ) ) {
                customer = new Customer();
                customer.setCustomerLoginInfo( (CustomerLoginInfo) loginInfo );
                customerDAO.addCustomer( customer );
            } else {
                customer = customerDAO.getCustomerByUsername( loginInfo.getUsername() );
            }
            return customer;
        } catch (EntityException | DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(LoginLogic.class);
            logger.error(ex);
            throw new LogicException( ex );
        }
    }

}
