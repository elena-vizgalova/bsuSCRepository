package by.bsu.patterncommand.paramwrapper;

import by.bsu.patterncommand.entity.Book;
import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;
import java.util.HashMap;
import java.util.Map;

/**
 * TO that handles book, cart and order information.
 * @author Elena Vizgalova
 */
public class ParameterWrapper {
    
    private Map<Book, Integer> booksMap;
    private Cart cart;
    private Order order;

    public ParameterWrapper() {
        booksMap = new HashMap<>();
        cart = new Cart();
        order = new Order();
    }

    public Map<Book, Integer> getBooksMap() {
        return booksMap;
    }

    public void setBooksMap(Map<Book, Integer> booksMap) {
        this.booksMap = booksMap;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
}
