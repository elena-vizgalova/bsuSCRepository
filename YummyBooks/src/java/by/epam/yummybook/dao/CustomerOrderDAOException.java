package by.epam.yummybook.dao;

/**
 * Exception class for OrderDAO classes.
 * @author Elena Vizgalova
 */
public class CustomerOrderDAOException extends DAOException {
    
    public CustomerOrderDAOException() {
    }
    
    public CustomerOrderDAOException( Throwable cause ) {
        super( cause );
    }

    public CustomerOrderDAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public CustomerOrderDAOException( String message ) {
        super( message );
    }
    
}
