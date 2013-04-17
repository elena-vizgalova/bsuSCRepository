package by.bsu.patterncommand.logic;

import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;

/**
 * Provides logic for clearing cart operation.
 * @author Elena Vizgaloca
 */
public class ClearCartLogic {
    
    private static volatile ClearCartLogic instance;

    private static final boolean IS_PAYED = false;
    
    private ClearCartLogic() {
    }
    
    public static ClearCartLogic getInstance() {
        
        if ( null == instance ) {
            synchronized( ClearCartLogic.class ) {
                if ( null == instance ) {
                    instance = new ClearCartLogic();
                }
            }
        }
        
        return instance;
    }
    
    public Cart makeNewCart( Cart cart ) {
        
        cart = new Cart();
        
        return cart;
    }
    
    public Order clearCart( Order order ) {

        order.setCart( new Cart() );
        order.setIsPayed( IS_PAYED );
        
        return order;
        
    }
    
}
