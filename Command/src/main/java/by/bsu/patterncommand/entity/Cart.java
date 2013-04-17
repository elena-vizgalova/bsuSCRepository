package by.bsu.patterncommand.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Describes cart entity.
 * @author Elena Vizgalova
 */
public class Cart implements Serializable {
    
    /**
     * Order's id is {@code int} value, that can be {@code > 0}.
     */
    private int id;
    
    /**
     * The date of order creation.
     */
    private Date dateCreated;
    
    /**
     * Contains book id as <tt>key</tt> and quantity of book 
     * as <tt>value</tt>.
     */
    private Map<String, Integer> bookQuantity;
    
    /**
     * Total price of all books in order.
     */
    private double total;
    
    public Cart() {
        id = 0;
        dateCreated = new Date();
        bookQuantity = new HashMap<>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
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
    public void setId( int id ) {
        
        if ( id <= 0 ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Negative or 0 id error! id = " + id );
//            throw new EntityException( "id must be > 0 !" );
        }
        
        this.id = id;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }
    
    public void updateBookQuantity( String bookID, int quantity, double price ) {
        
        if ( null == bookID ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Null bookID error" );
//            throw new EntityException( "bookID can't be null!" );
        }
        
        if ( bookID.isEmpty() ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Empty bookID error" );
//            throw new EntityException( "bookID can't be epmty!" );
        }
        
        if ( 0 > quantity ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Negative quantity error! quantity = " + quantity );
//            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        if ( 0 > price ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Negative price error! price = " + price );
//            throw new EntityException( "price must be >= 0 !" );
        }
        
        int previousQuantity = bookQuantity.get( bookID );
        if ( quantity == 0 ) {
            this.bookQuantity.remove( bookID );
        } else {
            this.bookQuantity.put( bookID, quantity );
        }
        
        total = total + ( quantity - previousQuantity ) * price;
        
    }
    
    public void addBook( String bookID, int quantity, double price ) {
        
        if ( 0 >= quantity ) {
//            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        if ( bookQuantity.containsKey( bookID ) ) {
            this.bookQuantity.put( bookID, bookQuantity.get(bookID) + quantity );
        } else {
            this.bookQuantity.put( bookID, quantity );
        }
        
        total += price * quantity;
        
    }
    
    public void removeBook( String bookID, int quantity, double price ) {
        
        if ( null == bookID ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Null bookID error" );
//            throw new EntityException( "bookID can't be null!" );
        }
        
        if ( bookID.isEmpty() ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Empty bookID error" );
//            throw new EntityException( "bookID can't be epmty!" );
        }
        
        if ( 0 > quantity ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Negative quantity error! quantity = " + quantity );
//            throw new EntityException( "Quantity must be >= 0 !" );
        }
        
        if ( 0 > price ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Negative price error! price = " + price );
//            throw new EntityException( "price must be >= 0 !" );
        }
        
        if ( quantity <= 0 ) {
//            throw new EntityException( "Quantity must be >= 0 !" );
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
    public void setTotal(double total) {
        if ( total < 0 ) {
//            EntityLogger logger = EntityLogger.getInstance( CustomerOrder.class );
//            logger.error( "Negative total! Total: " + total );
//            throw new EntityException( "Total must be >= 0!" );
        }
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.dateCreated);
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
        final Cart other = (Cart) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.dateCreated, other.dateCreated)) {
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
        
        for (Map.Entry entry : bookQuantity.entrySet()) {
            bookQuantityStr += " " + entry.getKey();
            bookQuantityStr += " " + entry.getValue();
        }
        
        return getClass().getName() + "@id: " + id + "\ndateCreated: " + dateCreated
                + "\nisConfirmed: " + "\ntotal: " + total 
                + "\nbookQuantity: " + bookQuantityStr;
    }
    
}
