package by.epam.yummybook.dao;

import by.epam.yummybook.entity.CustomerOrder;
import java.util.List;

/**
 * Handles basic operations for {@link CustomerOrder} entity, working with corresponding
 * DB table.
 * @author Elena Vizgalova
 */
public interface OrderDAO {
    
    /**
     * Gets {@link CustomerOrder} with specific <tt>orderID</tt> from DB.
     * @param orderID
     * @return {@link CustomerOrder} object
     * @throws OrderDAOException 
     */
    public abstract CustomerOrder getOrderByOrderID( int orderID ) throws OrderDAOException;
    
    /**
     * Gets list of {@link CustomerOrder} with specific <tt>customerID</tt> from DB.
     * @param customerID
     * @return list of {@link CustomerOrder}
     * @throws OrderDAOException 
     */
    public abstract List<CustomerOrder> getOrdersByCustomerID( int customerID ) throws OrderDAOException;
    
    /**
     * Gets list of all {@link CustomerOrder} object.
     * @return list of {@link CustomerOrder}
     * @throws OrderDAOException 
     */
    public abstract List<CustomerOrder> getAllOrders() throws OrderDAOException;
    
    /**
     * Sets isPayed flag for order with a specific <tt>orderID</tt>.
     * @param orderID
     * @throws OrderDAOException 
     */
    public abstract void setIsPayed( int orderID ) throws OrderDAOException;
    
    /**
     * Sets isConfirmed flag for order with a specific <tt>orderID</tt>.
     * @param orderID
     * @throws OrderDAOException 
     */
    public abstract void setIsConfirmed( int orderID ) throws OrderDAOException;
    
    /**
     * Deletes order with a specific <tt>orderID</tt>.
     * @param orderID
     * @throws OrderDAOException 
     */
    public abstract void deleteOrder( int orderID ) throws OrderDAOException;
    
    /**
     * Creates order for customer with given <tt>customerID</tt>.
     * @param customerID
     * @throws OrderDAOException 
     */
    public abstract void createOrder( int customerID ) throws OrderDAOException;
    
    /**
     * Adds book in order with given <tt>orderID</tt>.
     * @param bookID
     * @param amount book amount
     * @param orderID
     * @param price for one book
     * @throws OrderDAOException 
     */
    public abstract void addBookInOrder( String bookID, int amount, int orderID, double price ) throws OrderDAOException;
    
    /**
     * Updates <tt>bookID</tt>, it's <tt>amount</tt> and <tt>price</tt> for order with specific <tt>orderID</tt>
     * @param bookID
     * @param amount book amount
     * @param orderID
     * @param price book price
     * @throws OrderDAOException 
     */
    public abstract void updateBookQuantity( String bookID, int amount, int orderID, double price ) throws OrderDAOException;
    
    /**
     * Sets isPayed flag for order with given <tt>orderID</tt>.
     * @param orderID
     * @param flagToSet
     * @throws OrderDAOException 
     */
    public abstract void setIsPayedFlag( int orderID, boolean flagToSet ) throws OrderDAOException;
}
