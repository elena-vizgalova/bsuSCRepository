package by.epam.yummybook.logic;

import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.LoginDAO;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.logger.LogicLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.util.List;

/**
 * Singleton class for realizing isInBlackList flag changing.
 * @author Elena Vizgalova
 */
public class ChangeBlackListFlagLogic {
    
    private static ChangeBlackListFlagLogic instance;
    
    private ChangeBlackListFlagLogic() {
    }
    
    public static synchronized ChangeBlackListFlagLogic getInstance() {
        
        if ( null == instance ) {
            instance = new ChangeBlackListFlagLogic();
        }
        
        return instance;
    }
    
    /**
     * Gets {@link LoginInfo} object from DB for user with given <tt>username</tt>.
     * @param username
     * @return {@link LoginInfo} object
     * @throws LogicException when <tt>username</tt> is <code>null</code> or 
     * empty.
     */
    private LoginInfo getLoginInfo( String username ) throws LogicException {
        
        if ( null == username ) {
            LogicLogger logger = LogicLogger.getInstance(ChangeBlackListFlagLogic.class);
            logger.error("Null username error");
            throw new LogicException( "Username can't be null!" );
        }
        
        if ( username.isEmpty() ) {
            LogicLogger logger = LogicLogger.getInstance(ChangeBlackListFlagLogic.class);
            logger.error("Empty username error");
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
            LogicLogger logger = LogicLogger.getInstance(ChangeBlackListFlagLogic.class);
            logger.error( ex );
            throw new LogicException( "DAO error. username = " + username, ex );
        }
    }
    
    /**
     * Changes isInBlackList flag for user with given <tt>username</tt> in <tt>customers</tt> list.
     * @param username
     * @param customers
     * @return <tt>customers</tt> list with changed flag for user with given <tt>username</tt>
     * @throws LogicException 
     */
    public List<Customer> changeBlackListFlag( String username, List<Customer> customers ) throws LogicException {
        try {
            
            LoginInfo loginInfo = getLoginInfo( username );
            
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;

            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                            manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );

            LoginDAO loginDAO = mysqlDAOFactory.getLoginDAO();
            
            loginDAO.setIsInBlackListFlag( username, !loginInfo.isIsInBlackList() );
            
            for ( Customer customer: customers ) {
                LoginInfo customerLoginInfo = customer.getCustomerLoginInfo();
                if ( customerLoginInfo.getUsername().equals(username) ) {
                    customerLoginInfo.setIsInBlackList( !customerLoginInfo.isIsInBlackList() );
                }
            }
        
            return customers;
        } catch (DAOException ex) {
            LogicLogger logger = LogicLogger.getInstance(ChangeBlackListFlagLogic.class);
            logger.error(ex);
            throw new LogicException( "DAO error. username = " + username, ex );
        }
        
    }
    
    
    
}
