package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.Customer;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.logic.ViewCustomersLogic;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.List;

/**
 * Singleton class for view customers actions.
 * @see Commandable
 * @author Elena Vizgalova
 */
public class ViewCustomersCommand implements Commandable {
    
    private static ViewCustomersCommand instance;
    
    private ViewCustomersCommand(){
    }
    
    public static synchronized ViewCustomersCommand getInstance() {
        if ( null == instance ) {
            instance = new ViewCustomersCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link ViewCustomersLogic}.
     * Loads customers list from DB, sets it into <tt>parameterWrapper</tt>
     * @param parameterWrapper
     * @return {@link PageAddressManager#ADMIN_VIEW_CUSTOMERS_PAGE}
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            List<Customer> customers;
            
            ViewCustomersLogic viewCustomersLogic = ViewCustomersLogic.getInstance();
            
            customers = viewCustomersLogic.loadAllCustomers();
            
            parameterWrapper.setSessionAttribute( IParameterName.CUSTOMERS_LIST, customers);
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            
            return configurationManager.getProperty( PageAddressManager.ADMIN_VIEW_CUSTOMERS_PAGE );
            
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance( ViewCustomersCommand.class );
            logger.error(ex);
            throw new CommandException( ex );
        }
    }
    
}
