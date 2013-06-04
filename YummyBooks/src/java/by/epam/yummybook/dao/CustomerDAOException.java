package by.epam.yummybook.dao;

/**
 * Exception class for CustomerDAO classes.
 * @author Elena Vizgalova
 */
public class CustomerDAOException extends DAOException {
    
    public CustomerDAOException() {
    }
    
    public CustomerDAOException(Throwable cause) {
        super(cause);
    }

    public CustomerDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerDAOException(String message) {
        super(message);
    }
    
}
