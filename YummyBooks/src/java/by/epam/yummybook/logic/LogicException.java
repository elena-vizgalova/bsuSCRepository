package by.epam.yummybook.logic;

import by.epam.yummybook.exception.YummyBooksException;

/**
 * Exception class for logic classes.
 * @author Elena Vizgalova
 */
public class LogicException extends YummyBooksException {

    public LogicException() {
    }
    
    public LogicException( Throwable cause ) {
        super( cause );
    }

    public LogicException( String message, Throwable cause ) {
        super( message, cause );
    }

    public LogicException( String message ) {
        super( message );
    }
    
}
