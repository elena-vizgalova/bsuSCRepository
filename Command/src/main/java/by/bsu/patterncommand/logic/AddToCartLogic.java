package by.bsu.patterncommand.logic;

import by.bsu.patterncommand.entity.Book;
import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;
import java.util.Iterator;
import java.util.Map;

public class AddToCartLogic {
    
    private static volatile AddToCartLogic instance;

    private AddToCartLogic() {
    }
    
    public static AddToCartLogic getInstance() {
        
        if ( null == instance ) {
            synchronized( AddToCartLogic.class ) {
                if ( null == instance ) {
                    instance = new AddToCartLogic();
                }
            }
        }
        
        return instance;
                
    }
    
    public Cart addToCart( Map<Book, Integer> booksMap, Cart cart ) {
        
        for ( Map.Entry pair : booksMap.entrySet() ) {
            Book book = (Book) pair.getKey();
            
            int quantity = (int) pair.getValue();
            cart.addBook(book.getId(), quantity, book.getPrice());
        }
        
        return cart;
    }
    
    public Order makeOrderFromCart( Cart cart ) {
        return makeOrderFromCart(cart);
    }
}