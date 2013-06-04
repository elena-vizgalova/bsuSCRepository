package by.epam.yummybook.entity;

import by.epam.yummybook.dao.BookDAO;
import by.epam.yummybook.dao.DAOException;
import by.epam.yummybook.dao.DAOFactory;
import by.epam.yummybook.dao.mysql.MysqlDAOFactory;
import by.epam.yummybook.entity.logger.EntityLogger;
import by.epam.yummybook.manager.DAOFactoryManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Customer's cart entity. Can be created from {@link CustomerOrder} entity.
 * @author Elena Vizgalova
 */
public class Cart implements Serializable {
    
    /**
     * Number of books in cart.
     */
    private int booksNumber;
    
    /**
     * ID of customer's order.
     */
    private int orderID;
    
    /**
     * Contains book as key and it's quantity in cart as value.
     */
    private Map<Book, Integer> bookQuantity;
    
    /**
     * Total value of all books in cart.
     */
    private double totalCost;

    public Cart() {
        booksNumber = 0;
        orderID = 0;
        totalCost = 0.0;
        bookQuantity = new HashMap<>();
    }
    
    /**
     * Creates cart object from @{link CustomerOrder}.
     * @see CustomerOrder
     * @param order
     * @throws EntityException when <tt>order</tt> is <tt>null</tt>
     * or when 
     */
    public Cart( CustomerOrder order ) throws EntityException {
        
        if ( null == order ) {
            EntityLogger logger = EntityLogger.getInstance( Cart.class );
            logger.error("Null order error!");
            throw new EntityException( "Order from what to create cart can't be null!" );
        }
        
        try {
            bookQuantity = new HashMap<>();
            totalCost = 0.0;
            orderID = order.getId();
            
            DAOFactoryManager manager = DAOFactoryManager.getInstance();
            MysqlDAOFactory mysqlDAOFactory;
                
            mysqlDAOFactory = (MysqlDAOFactory) DAOFactory.getDAOFactory( 
                            manager.getProperty( DAOFactoryManager.MYSQL_FACTORY ) );
            BookDAO bookDAO = mysqlDAOFactory.getBookDAO();
            
            Iterator iterator = order.getBookQuantity().entrySet().iterator();
            while( iterator.hasNext() ) {
                Map.Entry pair = (Map.Entry) iterator.next();
                booksNumber += (Integer)pair.getValue();
                Book book = bookDAO.getBookInfo( (String)pair.getKey() );
                bookQuantity.put( book, (Integer)pair.getValue() );
            }
             totalCost = order.getTotal();
        } catch ( DAOException ex ) {
            EntityLogger logger = EntityLogger.getInstance( Cart.class );
            logger.error(ex);
            throw new EntityException(ex);
        }
        
    }
    
    /**
     * @return the numberOfItems
     */
    public int getBooksNumber() {
        return booksNumber;
    }

    /**
     * @return the orderID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * @return the bookQuantity
     */
    public Map<Book, Integer> getBookQuantity() {
        return bookQuantity;
    }

    /**
     * @param numberOfItems the numberOfItems to set
     */
    public void setBooksNumber(int booksNumber) {
        this.booksNumber = booksNumber;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(double totalCost) throws EntityException {
        if ( totalCost < 0 ) {
            EntityLogger logger = EntityLogger.getInstance( Cart.class );
            logger.error("Negative total cost! totalCost: " + totalCost);
            throw new EntityException( "totalCost to set can't be negative!" );
        }
        this.totalCost = totalCost;
    }

    /**
     * @param bookQuantity the bookQuantity to set
     */
    public void setBookQuantity(Map<Book, Integer> bookQuantity) throws EntityException {
        
        if ( null == bookQuantity ) {
            EntityLogger logger = EntityLogger.getInstance( Cart.class );
            logger.error("Null bookQuantity error!");
            throw new EntityException("bookQuantity to set can't be null!");
        }
        
        this.bookQuantity = bookQuantity;
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
        if (this.booksNumber != other.booksNumber) {
            return false;
        }
        if (this.orderID != other.orderID) {
            return false;
        }
        if (!Objects.equals(this.bookQuantity, other.bookQuantity)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalCost) != Double.doubleToLongBits(other.totalCost)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.booksNumber;
        hash = 67 * hash + this.orderID;
        hash = 67 * hash + Objects.hashCode(this.bookQuantity);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.totalCost) ^ (Double.doubleToLongBits(this.totalCost) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@orderID: " + this.orderID + 
                "\ntotalCost: " + this.totalCost + "\nbookNumber: " + this.booksNumber + 
                "\nbookQuantity: " + getBookQuantity();
    }
    
    private String toStringBookQuantity(){
        String bookQuantityStr = "";
        Iterator entries = bookQuantity.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            bookQuantityStr += " " + entry.getKey().toString();
            bookQuantityStr += " " + entry.getValue().toString();
        }
        return bookQuantityStr;
    }
    
}