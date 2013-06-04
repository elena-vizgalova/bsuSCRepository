package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.logic.ChangeBlackListFlagLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.List;

/**
 * It's singleton class, that processes functionality of changing black list
 * flag on page for administrators.
 * @see Commandable
 * @author Elena Vizgalova
 */
public class ChangeBlackListFlagCommand implements Commandable {

    private static ChangeBlackListFlagCommand instance;
    
    private ChangeBlackListFlagCommand() {
    }

    public static synchronized ChangeBlackListFlagCommand getInstance() {
        if ( null == instance ) {
            instance = new ChangeBlackListFlagCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link ChangeBlackListFlagLogic} for changing black list flag.
     * Execute <tt>username</tt> from <code>parameterWrapper</code>. It belongs to the 
     * user whose <tt>blackList</tt> flag will be changed.
     * @param parameterWrapper
     * @return page to go
     * @throws CommandException 
     */
    @Override
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException {
        try {
            String username = (String) parameterWrapper.getRequestParam( IParameterName.USERNAME );
            
            ChangeBlackListFlagLogic changeBlackListFlagLogic = ChangeBlackListFlagLogic.getInstance();
            List<Customer> customers = (List<Customer>) parameterWrapper.getSessionAttribute( IParameterName.CUSTOMERS_LIST );
            
            customers = changeBlackListFlagLogic.changeBlackListFlag(username, customers);
            
            parameterWrapper.setSessionAttribute( IParameterName.CUSTOMERS_LIST , customers);
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            
            return configurationManager.getProperty( PageAddressManager.ADMIN_VIEW_CUSTOMERS_PAGE );
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance(ChangeBlackListFlagCommand.class);
            logger.error( ex );
            throw new CommandException( ex );
        }
    }
    
}
