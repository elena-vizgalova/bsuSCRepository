package by.bsu.patterncommand.logic;

import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides logic for checkout operation.
 * @author Elena Vizgalova
 */
public class CheckoutLogic {

    private static CheckoutLogic instance;

    private CheckoutLogic() {
    }
    
    public static CheckoutLogic getInstance() {
        
        if ( null == instance ) {
            synchronized( CheckoutLogic.class ) {
                if ( null == instance ) {
                    instance = new CheckoutLogic();
                }
            }
        }
        
        return instance;
    }
    
    public Cart clearCart( Cart cart ) {
        return new Cart();
    }
    
    public Order setIsPayedFlag( Order order ) {
        order.setIsPayed( true );
        return order;
    }
    
}
