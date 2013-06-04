package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.logic.LogoutLogic;
import by.epam.yummybook.manager.MessageManager;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * It's singleton class, that processes logging out.
 * @see Commandable
 * @author Elena Vizgalova
 */
public class LogoutCommand implements Commandable {
    
    private static LogoutCommand instance;
    
    private LogoutCommand() {
    }
    
    public static synchronized LogoutCommand getInstance() {
        if ( null == instance ) {
            instance = new LogoutCommand();
        }
        return instance;
    }

    /**
     * Uses {@link LogoutLogic}.
     * Logs out, sets logout messages and returns logout page.
     * @param parameterWrapper
     * @return {@link PageAddressManager#LOGOUT_PAGE}
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            LogoutLogic logoutLogic = LogoutLogic.getInstance();
            logoutLogic.logout( parameterWrapper );
            
            MessageManager messageManager = MessageManager.getInstance();
            
            String logoutTitle = messageManager.getProperty( MessageManager.LOGOUT_TITLE );
            String logoutMessage = messageManager.getProperty( MessageManager.LOGOUT_MESSAGE );
            
            PageAddressManager pageAddressManager = PageAddressManager.getInstance();
            String indexPage = pageAddressManager.getProperty( PageAddressManager.HOME_PAGE );
            
            logoutMessage = logoutMessage.replace( MessageManager.INDEX_PAGE_TO_CHANGE, indexPage );
            
            parameterWrapper.setSessionAttribute( IParameterName.LOGOUT_TITLE, logoutTitle);
            parameterWrapper.setSessionAttribute( IParameterName.LOGOUT_MESSAGE, logoutMessage);
            
            return pageAddressManager.getProperty( PageAddressManager.LOGOUT_PAGE );
        } catch (ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance( LogoutLogic.class );
            logger.error(ex);
            throw new CommandException( ex );
        }
        
    }
    
    
    
}
