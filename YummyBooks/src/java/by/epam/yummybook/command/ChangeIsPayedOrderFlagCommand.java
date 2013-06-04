package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.logic.ChangeIsPayedOrderFlagLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.PageAddressManager;
import java.util.List;

/**
 * It's singleton class, that processes functionality of changing <tt>isPayed</tt>
 * flag on page for administrators.
 * @author Elena Vizgalova
 */
public class ChangeIsPayedOrderFlagCommand implements Commandable {
    
    private static ChangeIsPayedOrderFlagCommand instance;
    
    private ChangeIsPayedOrderFlagCommand() {
    }

    public static synchronized ChangeIsPayedOrderFlagCommand getInstance() {
        if ( null == instance ) {
            instance = new ChangeIsPayedOrderFlagCommand();
        }
        return instance;
    }
    /**
     * Uses {@link ChangeIsPayedOrderFlagLogic} for changing <tt>isPayed</tt> flag.
     * Execute <tt>username</tt> from <code>parameterWrapper</code>. It belongs to the 
     * user whose <tt>isPayed</tt> flag will be changed.
     * @param parameterWrapper
     * @return page to go
     * @throws CommandException 
     */
    @Override
    public String execute(ParameterWrapper parameterWrapper) throws CommandException {
        try {
            int orderID = Integer.parseInt( 
                    (String)parameterWrapper.getRequestParam( IParameterName.ORDER_ID) );
            boolean isPayed = Boolean.parseBoolean( 
                    (String)parameterWrapper.getRequestParam( IParameterName.IS_PAYED ) );
            
            List<CustomerOrder> orders = (List<CustomerOrder>) parameterWrapper.getSessionAttribute( 
                    IParameterName.ORDERS_LIST );
            ChangeIsPayedOrderFlagLogic changeIsPayedOrderFlagLogic = ChangeIsPayedOrderFlagLogic.getInstance();
            orders = changeIsPayedOrderFlagLogic.setIsPayedOrderFlag( orderID, !isPayed, orders );
            
            parameterWrapper.setSessionAttribute( IParameterName.ORDERS_LIST , orders );
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            
            return configurationManager.getProperty( PageAddressManager.ADMIN_VIEW_ORDERS_PAGE );
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance(ChangeIsPayedOrderFlagCommand.class);
            logger.error( ex );
            throw new CommandException( ex );
        }
    }
    
    
    
}
