package by.epam.yummybook.dao;

/**
 * Exception class for OrderedBookDAO classes.
 * @author Elena Vizgalova
 */
public class OrderedBookDAOException extends DAOException {
        
    public OrderedBookDAOException() {
    }
    
    public OrderedBookDAOException( Throwable cause ) {
        super( cause );
    }

    public OrderedBookDAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public OrderedBookDAOException( String message ) {
        super( message );
    }
    
}
