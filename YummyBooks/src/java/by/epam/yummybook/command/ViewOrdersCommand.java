package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.logic.ViewOrdersLogic;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.List;

/**
 * Singleton class for view orders.
 * @see Commandable
 * @author Elena Vizgalova
 */
public class ViewOrdersCommand implements Commandable {
    
    private static ViewOrdersCommand instance;
    
    private ViewOrdersCommand(){
    }
    
    public static synchronized ViewOrdersCommand getInstance() {
        if ( null == instance ) {
            instance = new ViewOrdersCommand();
        }
        return instance;
    }

    /**
     * Uses {@link ViewOrdersLogic}.
     * Loads all orders and sets them in <tt>parameterWrapper</tt>.
     * @param parameterWrapper
     * @return {@link PageAddressManager#ADMIN_VIEW_ORDERS_PAGE}
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            ViewOrdersLogic viewOrdersLogic = ViewOrdersLogic.getInstance();
            List<CustomerOrder> orders = viewOrdersLogic.loadAllOrders();
            
            parameterWrapper.setSessionAttribute( IParameterName.ORDERS_LIST, orders);
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            
            return configurationManager.getProperty( PageAddressManager.ADMIN_VIEW_ORDERS_PAGE );
        } catch (ParameterWrapperException | LogicException ex) {
            CommandLogger logger = CommandLogger.getInstance( ViewOrdersCommand.class );
            logger.error(ex);
            throw new CommandException( ex );
        }
    }
    
}
