package by.bsu.patterncommand.command;

import by.bsu.patterncommand.entity.Book;
import by.bsu.patterncommand.entity.Cart;
import by.bsu.patterncommand.entity.Order;
import by.bsu.patterncommand.paramwrapper.ParameterWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elena Vizgalova
 */
public class AddToCartCommandTest {
    
    private ParameterWrapper parameterWrapper;
    
    private Book bookSpring;
    private Book bookPatterns;
    
    private int bookSpringQuantity;
    private int bookPatternsQuantity;
    
    @Before
    public void setUp() {
        
        parameterWrapper = new ParameterWrapper();
        
        bookSpring = new Book();
        bookSpring.setId( "ISBN9781932394351" );
        bookSpring.setLanguage( "english" );
        bookSpring.setName( "Spring in Action" );
        bookSpring.setPrice( 49.99 );
        bookSpringQuantity = 1;
        
        bookPatterns = new Book();
        bookPatterns.setId( "ISBN9780321127426" );
        bookPatterns.setLanguage( "english" );
        bookPatterns.setName( "Patterns of Enterprise Application Architecture" );
        bookPatterns.setPrice( 48.66 );
        bookPatternsQuantity = 2;
        
        Map<Book,Integer> bookMap = new HashMap<>();
        bookMap.put(bookSpring, bookSpringQuantity);
        bookMap.put(bookPatterns, bookPatternsQuantity);
        
        parameterWrapper.setBooksMap( bookMap );
        parameterWrapper.setCart( new Cart() );
        parameterWrapper.setOrder( new Order() );
    }
    
    @Test
    public void execute() {
        AddToCartCommand addToCartCommand = AddToCartCommand.getInstance();
        
        ParameterWrapper result = addToCartCommand.execute( parameterWrapper );
        Cart cart = parameterWrapper.getCart();
        Order order = parameterWrapper.getOrder();
        
        Assert.assertTrue( "There must be 2 books in cart!", 
                cart.getBookQuantity().entrySet().size() == 2 );        
        
        List<String> toStringBook = new ArrayList<>();
        List<Integer> bookQuantity = new ArrayList<>();
        
        for (Map.Entry entry : result.getBooksMap().entrySet()) {
            toStringBook.add( entry.getKey().toString() );
            bookQuantity.add( (Integer) entry.getValue());
        }
        
        Assert.assertEquals( toStringBook.get( 1 ), bookSpring.toString() );
        Assert.assertEquals( toStringBook.get( 0 ), bookPatterns.toString() );
        Assert.assertTrue( bookQuantity.get( 1 ).intValue() == bookSpringQuantity );
        Assert.assertTrue( bookQuantity.get( 0 ).intValue() == bookPatternsQuantity );
        
        Assert.assertNotNull( order );
        
    }
    
}
