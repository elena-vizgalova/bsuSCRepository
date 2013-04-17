package by.bsu.patterncommand.command;

import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;
import by.bsu.patterncommand.logic.CheckoutLogic;
import by.bsu.patterncommand.paramwrapper.ParameterWrapper;

/**
 *
 * @author Elena Vizgalova
 */
public class CheckoutCommand implements Command {

    private static volatile CheckoutCommand instance;

    private CheckoutCommand() {
    }
    
    public static CheckoutCommand getInstance() {
        
        if ( null == instance ) {
            synchronized( CheckoutCommand.class ) {
                if ( null == instance ) {
                    instance = new CheckoutCommand();
                }
            }
        }
        
        return instance;
    }
    
    @Override
    public ParameterWrapper execute( ParameterWrapper parameterWrapper ) {
        CheckoutLogic checkoutLogic = CheckoutLogic.getInstance();
        
        Cart cart = parameterWrapper.getCart();
        Order order = parameterWrapper.getOrder();
        
        cart = checkoutLogic.clearCart( cart );
        parameterWrapper.setCart( cart );
        
        order = checkoutLogic.setIsPayedFlag( order );
        parameterWrapper.setOrder(order);
        
        return parameterWrapper;
    }
    
}
