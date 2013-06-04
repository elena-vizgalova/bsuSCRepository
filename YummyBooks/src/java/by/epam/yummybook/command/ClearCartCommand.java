package by.epam.yummybook.command;

import by.epam.yummybook.command.logger.CommandLogger;
import by.epam.yummybook.command.paramname.IParameterName;
import by.epam.yummybook.controller.ParameterWrapper;
import by.epam.yummybook.controller.ParameterWrapperException;
import by.epam.yummybook.entity.Cart;
import by.epam.yummybook.entity.CustomerOrder;
import by.epam.yummybook.logic.ClearCartLogic;
import by.epam.yummybook.logic.LogicException;
import by.epam.yummybook.manager.PageAddressManager;

/**
 * It's singleton class, that processes functionality of clearing cart.
 * @see Commandable
 * @author Elena Vizgalova
 */
class ClearCartCommand implements Commandable {

    private static ClearCartCommand instance;

    private ClearCartCommand() {
    }
    
    public static synchronized ClearCartCommand getInstance() {
        if ( null == instance ) {
            instance = new ClearCartCommand();
        }
        return instance;
    }
    
    /**
     * Uses {@link ClearCartLogic } for clearing cart.
     * Gets <tt>cart</tt> parameter from <tt>parameterWrapper</tt>,
     * clears cart and writes new cleared cart into session.
     * @param parameterWrapper
     * @return cart page
     * @throws CommandException 
     */
    @Override
    public String execute( ParameterWrapper parameterWrapper ) throws CommandException {
        try {
            Cart cart = (Cart) parameterWrapper.getSessionAttribute( IParameterName.CART );
            
            if ( null != cart ) {             
                ClearCartLogic clearCartLogic = ClearCartLogic.getInstance();
                clearCartLogic.clearCart( cart.getOrderID() );

                parameterWrapper.setSessionAttribute( IParameterName.CUSTOMER_ORDER , new CustomerOrder() );
                parameterWrapper.setSessionAttribute( IParameterName.CART, new Cart() );
            }
            
            PageAddressManager configurationManager = PageAddressManager.getInstance();
            return configurationManager.getProperty( PageAddressManager.CART_PAGE );
        } catch (LogicException | ParameterWrapperException ex) {
            CommandLogger logger = CommandLogger.getInstance(ClearCartCommand.class);
            logger.error( ex );
            throw new CommandException( ex );
        }
        
        
    }
    
}
