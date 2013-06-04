package by.epam.yummybook.exception;

/**
 * Base exception class for application.
 * @author Elena Vizgalova
 */
public class YummyBooksException extends Exception {
    
    public YummyBooksException() {
    }
    
    public YummyBooksException( Throwable cause ) {
        super( cause );
    }

    public YummyBooksException( String message, Throwable cause ) {
        super( message, cause );
    }

    public YummyBooksException( String message ) {
        super( message );
    }
    
}
