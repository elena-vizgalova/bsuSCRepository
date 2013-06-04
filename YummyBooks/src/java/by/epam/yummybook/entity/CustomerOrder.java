package by.epam.yummybook.entity;

import by.epam.yummybook.entity.logger.EntityLogger;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Describes customer order.
 * @author Elena Vizgalova
 */
public class CustomerOrder implements Serializable {
    
    /**
     * Order's id is {@code int} value, that can be {@code > 0}.
     */
    private int id;
    
    private boolean isPayed;
    
    private boolean isConfirmed;
    
    /**
     * The date of order creation.
     */
    private Date dateCreated;
    private Customer customer;
    
    /**
     * Contains book id as <tt>key</tt> and quantity of book 
     * as <tt>value</tt>.
     */
    private Map<String, Integer> bookQuantity;
    
    /**
     * Total price of all books in order.
     */
    private double total;
    
    public CustomerOrder() {
        id = 0;
        isPayed = false;
        dateCreated = new Date();
        customer = new Customer();
        bookQuantity = new HashMap<>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the isPayed
     */
    public boolean isIsPayed() {
        return isPayed;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return the bookQuantity
     */
    public Map<String, Integer> getBookQuantity() {
        return bookQuantity;
    }

    /**
     * @param id the id to set
     * @throws EntityException
     */
    public void setId( int id ) throws EntityException {
        
        if ( id <= 0 ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Negative or 0 id error! id = " + id );
            throw new EntityException( "id must be > 0 !" );
        }
        
        this.id = id;
    }

    /**
     * @param isPayed the isPayed to set
     */
    public void setIsPayed( boolean isPayed ) {
        this.isPayed = isPayed;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer( Customer customer ) throws EntityException {
        if ( null == customer ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Null customer error!" );
            throw new EntityException( "Customer to set can't be null!" );
        }
        this.customer = customer;
    }
    
    public void updateBookQuantity( String bookID, int quantity, double price ) throws EntityException {
        
        if ( null == bookID ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Null bookID error" );
            throw new EntityException( "bookID can't be null!" );
        }
        
        if ( bookID.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Empty bookID error" );
            throw new EntityException( "bookID can't be epmty!" );
        }
        
        if ( 0 > quantity ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Negative quantity error! quantity = " + quantity );
            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        if ( 0 > price ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Negative price error! price = " + price );
            throw new EntityException( "price must be >= 0 !" );
        }
        
        int previousQuantity = bookQuantity.get( bookID );
        if ( quantity == 0 ) {
            this.bookQuantity.remove( bookID );
        } else {
            this.bookQuantity.put( bookID, quantity );
        }
        
        total = total + ( quantity - previousQuantity ) * price;
        
    }
    
    public void addBook( String bookID, int quantity, double price ) throws EntityException {
        
        if ( 0 >= quantity ) {
            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        if ( bookQuantity.containsKey( bookID ) ) {
            this.bookQuantity.put( bookID, bookQuantity.get(bookID) + quantity );
        } else {
            this.bookQuantity.put( bookID, quantity );
        }
        
        total += price * quantity;
        
    }
    
    public void removeBook( String bookID, int quantity, double price ) throws EntityException {
        
        if ( null == bookID ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Null bookID error" );
            throw new EntityException( "bookID can't be null!" );
        }
        
        if ( bookID.isEmpty() ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Empty bookID error" );
            throw new EntityException( "bookID can't be epmty!" );
        }
        
        if ( 0 > quantity ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Negative quantity error! quantity = " + quantity );
            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        if ( 0 > price ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Negative price error! price = " + price );
            throw new EntityException( "price must be >= 0 !" );
        }
        
        if ( quantity <= 0 ) {
            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        int oldQuantity = bookQuantity.get( bookID );
        
        if ( oldQuantity - quantity <= 0 ) {
            bookQuantity.remove( bookID );
        } else {
            bookQuantity.remove( bookID );
            bookQuantity.put(bookID, oldQuantity - quantity);
        }
        total -= price * quantity;
        
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param bookQuantity the bookQuantity to set
     */
    public void setBookQuantity(Map<String, Integer> bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) throws EntityException {
        if ( total < 0 ) {
            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
            logger.error( "Negative total! Total: " + total );
            throw new EntityException( "Total must be >= 0!" );
        }
        this.total = total;
    }

    /**
     * @return the isConfirmed
     */
    public boolean isIsConfirmed() {
        return isConfirmed;
    }

    /**
     * @param isConfirmed the isConfirmed to set
     */
    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + (this.isPayed ? 1 : 0);
        hash = 59 * hash + (this.isConfirmed ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.dateCreated);
        hash = 59 * hash + Objects.hashCode(this.customer);
        hash = 59 * hash + Objects.hashCode(this.bookQuantity);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.total) ^ (Double.doubleToLongBits(this.total) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomerOrder other = (CustomerOrder) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.isPayed != other.isPayed) {
            return false;
        }
        if (this.isConfirmed != other.isConfirmed) {
            return false;
        }
        if (!Objects.equals(this.dateCreated, other.dateCreated)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.bookQuantity, other.bookQuantity)) {
            return false;
        }
        if (Double.doubleToLongBits(this.total) != Double.doubleToLongBits(other.total)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String bookQuantityStr = "";
        Iterator entries = bookQuantity.entrySet().iterator();
        
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            bookQuantityStr += " " + entry.getKey();
            bookQuantityStr += " " + entry.getValue();
        }
        return getClass().getName() + "@id: " + id + "\ndateCreated: " + dateCreated
                + "\nisConfirmed: " + isConfirmed + "\nisPayed" + isPayed
                + "\ntotal: " + total + "\ncustomer: " + customer.toString()
                + "\nbookQuantity: " + bookQuantityStr;
    }
    
}
