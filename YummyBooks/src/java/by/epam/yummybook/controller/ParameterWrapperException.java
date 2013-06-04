package by.epam.yummybook.controller;

import by.epam.yummybook.exception.YummyBooksException;

/**
 * Exception class for {@link ParameterWrapper}.
 * @author Elena Vizgalova
 */
public class ParameterWrapperException extends YummyBooksException {

    public ParameterWrapperException() {
    }
    
    public ParameterWrapperException( Throwable cause ) {
        super( cause );
    }

    public ParameterWrapperException( String message, Throwable cause ) {
        super( message, cause );
    }

    public ParameterWrapperException( String message ) {
        super( message );
    }
}
