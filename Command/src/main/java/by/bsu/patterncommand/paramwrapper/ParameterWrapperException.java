package by.bsu.patterncommand.paramwrapper;

/**
 * Exception class for {@link ParameterWrapper}.
 * @author Elena Vizgalova
 */
public class ParameterWrapperException extends Exception {

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
