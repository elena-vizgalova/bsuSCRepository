package by.bsu.patterncommand.command;

import by.bsu.patterncommand.entity.Book;
import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;
import by.bsu.patterncommand.logic.AddToCartLogic;
import by.bsu.patterncommand.paramwrapper.ParameterWrapper;
import java.util.Map;

/**
 * It's singleton class, that processes functionality of adding products to cart.
 * @see Commandable
 * @author Elena Vizgalova
 */
class AddToCartCommand implements Command {
    
    private static volatile AddToCartCommand instance;
    
    private AddToCartCommand() {
    }
    
    public static AddToCartCommand getInstance() {
        if ( null == instance ) {
            synchronized( AddToCartCommand.class ) {
                if ( null == instance ) {
                    instance = new AddToCartCommand();
                }
            }
        }
        return instance;
    }
    
    @Override
    public ParameterWrapper execute( ParameterWrapper parameterWrapper ) {

        Map<Book, Integer> booksMap = parameterWrapper.getBooksMap();
        Cart cart = parameterWrapper.getCart();
        
        AddToCartLogic addToCartLogic = AddToCartLogic.getInstance();
        
        cart = addToCartLogic.addToCart( booksMap, cart );
        
        Order order = parameterWrapper.getOrder();
        
        parameterWrapper.setCart( cart );
        parameterWrapper.setOrder( order );
  
        return parameterWrapper;
    }
    
}