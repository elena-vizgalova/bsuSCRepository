package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.entity.LoginInfo;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.logic.LoginLogic;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * It's singleton class, that processes logging in.
 * @author Elena Vizgalova
 */
public class LoginCommand implements Commandable {

    private static LoginCommand instance;

    private LoginCommand() {
    }
    
    public static synchronized LoginCommand getInstance() {
        if ( null == instance ) {
            instance = new LoginCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link LoginLogic }.
     * Gets <tt>username</tt> and <tt>password</tt> from <tt>parameterWrapper</tt>.
     * Sets error messages and returns error page if parameters are incorrect or empty or if the user
     * with given <tt>username</tt> is in black list.
     * If parameters are correct, sets greeting messages into session and returns welcome page.
     * @param parameterWrapper
     * @return {@link PageAddressManager#WELCOME_PAGE} or {@link PageAddressManager#ERROR_PAGE}
     * @throws CommandException 
     */
    @Override
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException {
        try {
            
            LoginLogic loginLogic = LoginLogic.getInstance();
            
            String username = parameterWrapper.getRequestParam( IParameterName.LOGIN );
            String password = parameterWrapper.getRequestParam( IParameterName.PASSWORD );
            
            if ( username.isEmpty() || password.isEmpty() ) {
                PageAddressManager pageAddressManager = PageAddressManager.getInstance();
                MessageManager messageManager = MessageManager.getInstance();
                
                String errorMessage =  messageManager.getProperty( MessageManager.ERROR_LOGIN_MESSAGE );
                parameterWrapper.setSessionAttribute( IParameterName.ERROR_VARIABLE, errorMessage );
                
                return pageAddressManager.getProperty( PageAddressManager.ERROR_PAGE );
            }
            
            if ( !loginLogic.isUserExists( username, password ) ) {
                PageAddressManager pageAddressManager = PageAddressManager.getInstance();
                MessageManager messageManager = MessageManager.getInstance();
                
                String errorMessage =  messageManager.getProperty( MessageManager.ERROR_LOGIN_MESSAGE );
                parameterWrapper.setSessionAttribute( IParameterName.ERROR_VARIABLE, errorMessage );
                
                return pageAddressManager.getProperty( PageAddressManager.ERROR_PAGE );
                
            }
                
            if ( loginLogic.isInBlackList( username ) ) {
                PageAddressManager pageAddressManager = PageAddressManager.getInstance();
                MessageManager messageManager = MessageManager.getInstance();
            
                String errorMessage = messageManager.getProperty( MessageManager.ERROR_IS_IN_BLACK_LIST );
                parameterWrapper.setSessionAttribute( IParameterName.ERROR_VARIABLE, errorMessage );
                
                return pageAddressManager.getProperty( PageAddressManager.ERROR_PAGE );

            }
            
            LoginInfo loginInfo = loginLogic.getLoginInfo( username );
            parameterWrapper.setSessionAttribute( IParameterName.LOGIN_INFO, loginInfo );
            
            
            Customer customer = (Customer) parameterWrapper.getSessionAttribute( IParameterName.CUSTOMER );
            if ( null == customer ) {
                customer = loginLogic.loadCustomer( loginInfo );
                parameterWrapper.setSessionAttribute( IParameterName.CUSTOMER , customer);
            }
            
            if ( loginInfo.isIsAdmin() ) {
                PageAddressManager pageAddressManager = PageAddressManager.getInstance();
                MessageManager messageManager = MessageManager.getInstance();
                
                String greetingTitle = messageManager.getProperty( MessageManager.GREETING_ADMIN_TITLE );
                greetingTitle = greetingTitle.replace( MessageManager.ADMINNAME_TO_CHANGE , loginInfo.getUsername() );
                
                String greetingMessage = messageManager.getProperty( MessageManager.GREETING_ADMIN_MESSAGE );
                
                parameterWrapper.setSessionAttribute( IParameterName.GREETING_TITLE, greetingTitle );
                parameterWrapper.setSessionAttribute( IParameterName.GREETING_MESSAGE, greetingMessage );
                
                
                return pageAddressManager.getProperty( PageAddressManager.ADMIN_WELCOME_PAGE );
            }
            
            MessageManager messageManager = MessageManager.getInstance();
            
            String greetingTitle = messageManager.getProperty( MessageManager.GREETING_USER_TITLE );
            greetingTitle = greetingTitle.replace( MessageManager.USERNAME_TO_CHANGE , loginInfo.getUsername() );

            PageAddressManager pageAddressManager = PageAddressManager.getInstance();
            String indexPage = pageAddressManager.getProperty( PageAddressManager.HOME_PAGE );

            String greetingMessage = messageManager.getProperty( MessageManager.GREETING_USER_MESSAGE );
            greetingMessage = greetingMessage.replace( MessageManager.INDEX_PAGE_TO_CHANGE , indexPage);

            parameterWrapper.setSessionAttribute( IParameterName.GREETING_TITLE, greetingTitle );
            parameterWrapper.setSessionAttribute( IParameterName.GREETING_MESSAGE, greetingMessage );

            return pageAddressManager.getProperty( PageAddressManager.WELCOME_PAGE );
            
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance(LoginCommand.class);
            logger.error( "Error during logination!", ex );
            throw new CommandException( "Error during logination!", ex );
        }
    }
    
}
