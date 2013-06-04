
package by.epam.yummybook.dao;

import java.util.Map;

/**
 * Working with orderedBook DB table.
 * @author Elena Vizgalova
 */
public interface OrderedBookDAO {
    
    /**
     * Adds book in DB table with given <tt>bookID</tt> and it's <tt>quantity</tt> for 
     * customer with given <tt>customerOrderID</tt>
     * @param bookID
     * @param quantity book quantity
     * @param customerOrderID
     * @throws OrderedBookDAOException 
     */
    public abstract void addBook( String bookID, int quantity, int customerOrderID ) throws OrderedBookDAOException;
    
    /**
     * Gets bookID, quantity map from DB from order with given <tt>customerOrderID</tt>
     * @param customerOrderID order id
     * @return map, in which the <tt>Key<tt> is book id and <tt>Value</tt> is book quantity
     * @throws OrderedBookDAOException 
     */
    public abstract Map<String, Integer> getOrderedBooks( int customerOrderID ) throws OrderedBookDAOException;
    
    /**
     * Deletes <tt>quantity</tt> of books with given <tt>bookID</tt> from order with given id.
     * @param bookID
     * @param quantity book quantity
     * @param customerOrderID order id
     * @throws OrderedBookDAOException 
     */
    public abstract void deleteBookFromOrder( String bookID, int quantity, int customerOrderID ) throws OrderedBookDAOException;
    
    /**
     * Checks if there is book with given <tt>bookID</tt> in order with given id.
     * @param bookID
     * @param customerOrderID order id
     * @return <code>true</code> if it is, <code>false</code> - otherwise.
     * @throws OrderedBookDAOException 
     */
    public abstract boolean isBookInOrder( String bookID, int customerOrderID ) throws OrderedBookDAOException;
    
    /**
     * Gets book amount for book with given <tt>bookID</tt> from order with given id.
     * @param bookID
     * @param customerOrderID order id
     * @return amount of book in order
     * @throws OrderedBookDAOException 
     */
    public abstract int getBookAmount( String bookID, int customerOrderID ) throws OrderedBookDAOException;
    
    /**
     * Deletes order with given <tt>orderID</tt>
     * @param orderID
     * @throws OrderedBookDAOException 
     */
    public abstract void deleteOrder( int orderID ) throws OrderedBookDAOException;
    
    /**
     * Updates <tt>quantity</tt> for book with given <tt>bookID</tt> in order 
     * with given order id.
     * @param bookID book id
     * @param quantity book quantity
     * @param customerOrderID order id
     * @throws OrderedBookDAOException 
     */
    public abstract void updateBookQuantity( String bookID, int quantity, int customerOrderID ) throws OrderedBookDAOException;
}
