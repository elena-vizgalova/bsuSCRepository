package by.epam.yummybook.dao;

/**
 * Exception class for OrderDAO classes.
 * @author Elena Vizgalova
 */
public class OrderDAOException extends DAOException {
        
    public OrderDAOException() {
    }
    
    public OrderDAOException(Throwable cause) {
        super(cause);
    }

    public OrderDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDAOException(String message) {
        super(message);
    }
    
}
