package by.epam.yummybook.dao;

/**
 * Exception class for BookDAO.
 * @author Elena Vizgalova
 */
public class BookDAOException extends DAOException {
    
    public BookDAOException() {
    }
    
    public BookDAOException( Throwable cause ) {
        super( cause );
    }

    public BookDAOException( String message, Throwable cause ) {
        super( message, cause );
    }

    public BookDAOException( String message ) {
        super( message );
    }
    
}
